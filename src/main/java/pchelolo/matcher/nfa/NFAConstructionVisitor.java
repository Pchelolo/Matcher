package pchelolo.matcher.nfa;

import org.antlr.v4.runtime.misc.NotNull;
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
        Node newStartNode = new Node(null);
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
        Node newStartNode = new Node(null);
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
    public NFAFragment visitRegex(@NotNull RegexParser.RegexContext ctx) {
        NFAFragment resultFragment = visit(ctx.disjunction());
        for (Node node : resultFragment.getOutStates()) {
            node.addOutNode(Node.FINAL);
        }
        resultFragment.setOutStates(null);
        return resultFragment;
    }
}
