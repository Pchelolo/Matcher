package pchelolo.matcher;

import pchelolo.matcher.nfa.NFA;

import java.util.Set;

public class Matcher {

    private final NFA nfa;

    Matcher(NFA nfa) {
        this.nfa = nfa;
    }

    public boolean matches(String string) {
        char[] characters = string.toCharArray();
        Set<NFA.State> currentStates = nfa.getInitialStates();
        for (char c : characters) {
            if (currentStates.isEmpty()) return false;
            currentStates = nfa.getNextStates(currentStates, c);
        }
        return nfa.containsFinalState(currentStates);
    }

}
