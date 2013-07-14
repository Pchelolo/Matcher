package pchelolo.matcher;

import pchelolo.matcher.nfa.NFAUtils;
import pchelolo.matcher.nfa.UnmodifiableNode;

import java.util.Set;

public class Matcher {

    private final UnmodifiableNode nfa;

    Matcher(UnmodifiableNode nfa) {
        this.nfa = nfa;
    }

    public boolean matches(String string) {
        char[] characters = string.toCharArray();
        Set<UnmodifiableNode> currentStates = NFAUtils.getInitialSetForState(nfa);
        for (char c : characters) {
            if (currentStates.isEmpty()) return false;
            currentStates = NFAUtils.getNextStates(currentStates, c);
        }
        return currentStates.contains(UnmodifiableNode.FINAL);
    }

}
