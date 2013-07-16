package pchelolo.matcher;

import pchelolo.matcher.nfa.NFAUtils;
import pchelolo.matcher.nfa.UnmodifiableNFA;

public final class Matcher {

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
