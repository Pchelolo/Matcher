package pchelolo.matcher;

import pchelolo.matcher.nfa.NFAFragment;
import pchelolo.matcher.nfa.NFAUtils;
import pchelolo.matcher.nfa.UnmodifiableNFA;
import pchelolo.matcher.nfa.UnmodifiableNode;

import java.util.Set;

public class Matcher {

    private final UnmodifiableNFA nfa;

    Matcher(UnmodifiableNFA nfa) {
        this.nfa = nfa;
    }

    public boolean matches(String string) {
        char[] characters = string.toCharArray();
        boolean[] state = NFAUtils.getInitialSetForState(nfa);
        for (char c : characters) {
            state = NFAUtils.getNextStates(nfa, state, c);
        }
        return state[state.length - 1];
    }

}
