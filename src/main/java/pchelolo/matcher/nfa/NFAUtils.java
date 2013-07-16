package pchelolo.matcher.nfa;

import org.antlr.v4.runtime.tree.ParseTree;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class NFAUtils {

    private NFAUtils() {
        //Avoid instantiation
    }

    public static NFAFragment createNFA(ParseTree tree) {
        return new NFAConstructionVisitor().visit(tree);
    }

    public static boolean[] getInitialSetForState(UnmodifiableNFA nfa) {
        boolean[] state = new boolean[nfa.getNodesCount()];
        addEReachableStates(nfa, nfa.getStart(), state);
        return state;
    }

    public static boolean[] getNextStates(UnmodifiableNFA nfa, boolean[] state, char nextChar) {
        boolean[] newState = new boolean[state.length];
        for (int i = 0; i < state.length; i++) {
            if (state[i]) {
                UnmodifiableNode node = nfa.getNodeAtIndex(i);
                if (node.getC() != null && node.getC() == nextChar) {
                    for (int outIdx = 0; outIdx < node.getOutCount(); outIdx++) {
                        addEReachableStates(nfa, nfa.getNodeAtIndex(node.getOut(outIdx)), newState);
                    }
                }
            }
        }
        return newState;
    }

    private static void addEReachableStates(UnmodifiableNFA nfa, UnmodifiableNode node, boolean[] state) {
        if (!state[node.getNumber()]) {
            state[node.getNumber()] = true;
            if (node.getC() == null) {
                for (int i = 0; i < node.getOutCount(); i++) {
                    addEReachableStates(nfa, nfa.getNodeAtIndex(node.getOut(i)), state);
                }
            }
        }
    }
}
