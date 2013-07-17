package pchelolo.matcher.nfa;


import org.antlr.v4.runtime.misc.NotNull;
import pchelolo.matcher.RegexBaseVisitor;
import pchelolo.matcher.RegexParser;

import java.util.List;

/**
 * Visitor implementation. Connects the fragments of the Nondeterministic Finite Automata
 * accourdong to the Thompson's construction algorithm
 */
class NFAConstructionVisitor extends RegexBaseVisitor<NFAFragment> {

    @Override
    public NFAFragment visitId(@NotNull RegexParser.IdContext ctx) {
        // Create a new node for the symbol
        Node node = Node.commonNode(ctx.ID().getSymbol().getText().charAt(0));
        NFAFragment result = new NFAFragment(node);
        result.getOutNodes().add(node);
        return result;
    }

    @Override
    public NFAFragment visitBraced(@NotNull RegexParser.BracedContext ctx) {
        return visit(ctx.disjunction());
    }

    @Override
    public NFAFragment visitConjunction(@NotNull RegexParser.ConjunctionContext ctx) {
        // Connects fragments into a chain
        List<RegexParser.AtomContext> atoms = ctx.atom();
        // Should have at least 1 closure
        NFAFragment resultFragment = visit(atoms.get(0));
        for (int idx = 1; idx < atoms.size(); idx ++) {
            NFAFragment nextFragment = visit(atoms.get(idx));
            nextFragment.updateCounts(resultFragment.getNodesCount());
            resultFragment.connectOutNodes(nextFragment.getStart());
            resultFragment.getNodes().addAll(nextFragment.getNodes());
            resultFragment.setOutNodes(nextFragment.getOutNodes());
        }
        return resultFragment;
    }

    @Override
    public NFAFragment visitDisjunction(@NotNull RegexParser.DisjunctionContext ctx) {
        // Connects fragments using a split node
        int fragmentsNum = ctx.conjunction().size();
        if (fragmentsNum == 1) return visit(ctx.conjunction(0));
        Node newStartNode = Node.splitNode(fragmentsNum);
        NFAFragment resultFragment = new NFAFragment(newStartNode);
        for (int i = 0; i < fragmentsNum; i++) {
            NFAFragment conjunctionFragment = visit(ctx.conjunction(i));
            conjunctionFragment.updateCounts(resultFragment.getNodesCount());
            newStartNode.setOutNode(conjunctionFragment.getStart(), i);
            resultFragment.getNodes().addAll(conjunctionFragment.getNodes());
            resultFragment.getOutNodes().addAll(conjunctionFragment.getOutNodes());
        }
        return resultFragment;
    }

    @Override
    public NFAFragment visitClosure(@NotNull RegexParser.ClosureContext ctx) {
        // Makes a loop around the subexpression fragment
        //Create a new fragment with only a split node
        Node newStartNode = Node.splitNode(2);
        NFAFragment resultFragment = new NFAFragment(newStartNode);
        NFAFragment atomFragment = visit(ctx.atom());
        atomFragment.updateCounts(1);
        // Loop the out states of a fragment back to new state
        atomFragment.connectOutNodes(newStartNode);
        resultFragment.getNodes().addAll(atomFragment.getNodes());
        newStartNode.setOutNode(atomFragment.getStart(), 1);
        resultFragment.setOutNode(newStartNode);
        return resultFragment;
    }

    @Override
    public NFAFragment visitRange(@NotNull RegexParser.RangeContext ctx) {
        // Connects nodes labeled with possible symbols using a split node.
        char start = ctx.ID(0).getSymbol().getText().charAt(0);
        char finish = ctx.ID(1).getSymbol().getText().charAt(0);
        if (start > finish)
            throw new IllegalArgumentException(String.format("Illegal range: %c..%c", start, finish));
        Node startNode = Node.splitNode(finish - start + 1);
        NFAFragment result = new NFAFragment(startNode);
        for (char c = start; c <= finish; c++) {
            Node charNode = Node.commonNode(c);
            charNode.setNumber(result.getNodesCount());
            startNode.setOutNode(charNode, c - start);
            result.getNodes().add(charNode);
            result.getOutNodes().add(charNode);
        }
        return result;
    }

    @Override
    public NFAFragment visitListGroup(@NotNull RegexParser.ListGroupContext ctx) {
        // Connects nodes labeled with possible symbols using a split node.
        int fragmentsCount = ctx.ID().size();
        Node startNode = Node.splitNode(fragmentsCount);
        NFAFragment result = new NFAFragment(startNode);
        for (int i = 0; i < fragmentsCount; i++) {
            Node charNode = Node.commonNode(ctx.ID(i).getSymbol().getText().charAt(0));
            charNode.setNumber(result.getNodesCount());
            startNode.setOutNode(charNode, i);
            result.getNodes().add(charNode);
            result.getOutNodes().add(charNode);
        }
        return result;
    }

    @Override
    public NFAFragment visitRangeGroup(@NotNull RegexParser.RangeGroupContext ctx) {
        // Connects different Range fragments using a split node
        int fragmentsCount = ctx.range().size();
        Node startNode = Node.splitNode(fragmentsCount);
        NFAFragment result = new NFAFragment(startNode);
        for (int i = 0; i < fragmentsCount; i++) {
            NFAFragment rangeFragment = visit(ctx.range(i));
            rangeFragment.updateCounts(result.getNodesCount());
            startNode.setOutNode(rangeFragment.getStart(), i);
            result.getNodes().addAll(rangeFragment.getNodes());
            result.getOutNodes().addAll(rangeFragment.getOutNodes());
        }
        return result;
    }

    @Override
    public NFAFragment visitRegex(@NotNull RegexParser.RegexContext ctx) {
        // The top-level rule. Finalize the construction of an NFA, add final state and allow to clean up non-needed collections
        NFAFragment resultFragment = visit(ctx.disjunction());
        Node finalNode = Node.finalNode();
        finalNode.setNumber(resultFragment.getNodesCount());
        resultFragment.connectOutNodes(finalNode);
        resultFragment.getNodes().add(finalNode);
        // Would not need out nodes any more. Clean up.
        resultFragment.setOutNode(finalNode);
        return resultFragment;
    }

}
