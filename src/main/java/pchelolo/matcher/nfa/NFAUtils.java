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

    public static boolean[] getInitialSetForState(NFAFragment nfa) {
        boolean[] state = new boolean[nfa.getNodes().size()];
        addEReachableStates(nfa, nfa.getStart(), state);
        return state;
    }

    public static boolean[] getNextStates(NFAFragment nfaFragment, boolean[] state, char nextChar) {
        boolean[] newState = new boolean[state.length];
        for (int i = 0; i < state.length; i++) {
            if (state[i]) {
                UnmodifiableNode node = nfaFragment.getNodes().get(i);
                if (node.getC() != null && node.getC() == nextChar) {
                    for (int outIdx : node.getOut()) {
                        addEReachableStates(nfaFragment, nfaFragment.getNodes().get(outIdx), newState);
                    }
                }
            }
        }
        return newState;
    }

    private static void addEReachableStates(NFAFragment nfa, UnmodifiableNode node, boolean[] state) {
        if (node == null) return;
        if (!state[node.getNumber()]) {
            state[node.getNumber()] = true;
            if (node.getC() == null) {
                for (int i : node.getOut()) {
                    addEReachableStates(nfa, nfa.getNodes().get(i), state);
                }
            }
        }
    }
}
