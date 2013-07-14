package pchelolo.matcher.nfa;

import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.TerminalNode;
import pchelolo.matcher.RegexBaseVisitor;
import pchelolo.matcher.RegexParser;

import java.util.HashSet;
import java.util.List;

public class NFAConstructionVisitor extends RegexBaseVisitor<NFAFragment> {

    @Override
    public NFAFragment visitId(@NotNull RegexParser.IdContext ctx) {
        char c = ctx.ID().getSymbol().getText().charAt(0);
        Node node = new Node(c);
        NFAFragment result = new NFAFragment(node);
        result.getOutStates().add(node);
        return result;
    }

    @Override
    public NFAFragment visitBraced(@NotNull RegexParser.BracedContext ctx) {
        return visit(ctx.disjunction());
    }

    @Override
    public NFAFragment visitConjunction(@NotNull RegexParser.ConjunctionContext ctx) {
        List<RegexParser.AtomContext> atoms = ctx.atom();
        // Should have at least 1 closure
        NFAFragment resultFragment = visit(atoms.get(0));
        for (int idx = 1; idx < atoms.size(); idx ++) {
            NFAFragment nextFragment = visit(atoms.get(idx));
            //Connect out states of a currently constructed fragment to the next one
            for (Node outNode : resultFragment.getOutStates()) {
                outNode.addOutNode(nextFragment.getStart());
            }
            resultFragment.setOutStates(nextFragment.getOutStates());
        }
        return resultFragment;
    }

    @Override
    public NFAFragment visitDisjunction(@NotNull RegexParser.DisjunctionContext ctx) {
        Node newStartNode = Node.splitNode();
        NFAFragment resultFragment = new NFAFragment(newStartNode);
        for (RegexParser.ConjunctionContext context : ctx.conjunction()) {
            NFAFragment conjunctionFragment = visit(context);
            newStartNode.addOutNode(conjunctionFragment.getStart());
            resultFragment.getOutStates().addAll(conjunctionFragment.getOutStates());
        }
        return resultFragment;
    }

    @Override
    public NFAFragment visitClosure(@NotNull RegexParser.ClosureContext ctx) {
        Node newStartNode = Node.splitNode();
        NFAFragment resultFragment = visit(ctx.atom());
        // Loop the out states of a fragment back to new state
        for (Node outState : resultFragment.getOutStates()) {
            outState.addOutNode(newStartNode);
        }
        newStartNode.addOutNode(resultFragment.getStart());
        resultFragment.setStart(newStartNode);
        resultFragment.setOutState(newStartNode);
        return resultFragment;
    }

    @Override
    public NFAFragment visitRange(@NotNull RegexParser.RangeContext ctx) {
        char start = ctx.ID(0).getSymbol().getText().charAt(0);
        char finish = ctx.ID(1).getSymbol().getText().charAt(0);
        if (start > finish)
            throw new IllegalArgumentException(String.format("Illegal range: %c..%c", start, finish));
        Node startNode = Node.splitNode();
        NFAFragment result = new NFAFragment(startNode);
        for (char c = start; c <= finish; c++) {
            Node charNode = new Node(c);
            startNode.addOutNode(charNode);
            result.getOutStates().add(charNode);
        }
        return result;
    }

    @Override
    public NFAFragment visitListGroup(@NotNull RegexParser.ListGroupContext ctx) {
        Node startNode = Node.splitNode();
        NFAFragment result = new NFAFragment(startNode);
        for (TerminalNode id : ctx.ID()) {
            Node charNode = new Node(id.getSymbol().getText().charAt(0));
            startNode.addOutNode(charNode);
            result.getOutStates().add(charNode);
        }
        return result;
    }

    @Override
    public NFAFragment visitRangeGroup(@NotNull RegexParser.RangeGroupContext ctx) {
        Node startNode = Node.splitNode();
        NFAFragment result = new NFAFragment(startNode);
        for (RegexParser.RangeContext rangeContext : ctx.range()) {
            NFAFragment rangeFragment = visit(rangeContext);
            startNode.addOutNode(rangeFragment.getStart());
            result.getOutStates().addAll(rangeFragment.getOutStates());
        }
        return result;
    }

    @Override
    public NFAFragment visitRegex(@NotNull RegexParser.RegexContext ctx) {
        NFAFragment resultFragment = visit(ctx.disjunction());
        for (Node node : resultFragment.getOutStates()) {
            node.addOutNode(Node.FINAL);
        }
        resultFragment.setOutStates(null);
        return resultFragment;
    }
}
