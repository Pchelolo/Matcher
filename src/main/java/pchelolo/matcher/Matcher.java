package pchelolo.matcher;

import pchelolo.matcher.nfa.NFAUtils;
import pchelolo.matcher.nfa.UnmodifiableNFA;

public final class Matcher {

    private final UnmodifiableNFA nfa;
    private final String testString;

    Matcher(final UnmodifiableNFA nfa, final String testString) {
        this.nfa = nfa;
        this.testString = testString;
    }

    public boolean matches() {
        boolean[] state = NFAUtils.getInitialSetForState(nfa);
        for (int i = 0; i < testString.length(); i++) {
            state = NFAUtils.getNextStates(nfa, state, testString.charAt(i));
        }
        return state[state.length - 1];
    }

}
