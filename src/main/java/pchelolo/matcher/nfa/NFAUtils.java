package pchelolo.matcher.nfa;

import org.antlr.v4.runtime.tree.ParseTree;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class NFAUtils {

    private NFAUtils() {
        //Avoid instantiation
    }

    public static UnmodifiableNode createNFA(ParseTree tree) {
        return new NFAConstructionVisitor().visit(tree).getStart();
    }

    public static Set<UnmodifiableNode> getInitialSetForState(UnmodifiableNode start) {
        final Set<UnmodifiableNode> states = new HashSet<>();
        addEReachableStates(start, states);
        return states;
    }

    public static Set<UnmodifiableNode> getNextStates(Set<UnmodifiableNode> states, char nextChar) {

        Set<UnmodifiableNode> newSet = new HashSet<>();
        for (UnmodifiableNode state : states) {
            if (state.getC() != null && state.getC() == nextChar) {
                for (UnmodifiableNode node : state.getOut()) {
                    addEReachableStates(node, newSet);
                }
            }
        }
        return newSet;
    }

    private static void addEReachableStates(UnmodifiableNode state, Set<UnmodifiableNode> set) {
        if (state == null) return;
        if (set.add(state) && state.getC() == null) {
            for (UnmodifiableNode node : state.getOut()) {
                addEReachableStates(node, set);
            }
        }
    }
}
