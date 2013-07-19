package pchelolo.matcher;

/**
 * The mather is used to decide whether a given string matches a regular expression.
 * Also supports iteration on the matching substrings
 */
public final class Matcher {

    private final UnmodifiableNFA nfa;
    private final String testString;
    private final Pattern pattern;

    private final Object lock = new Object();

    // Mutable state:
    // The start of the region where the next matching substring is searched
    private int substringOffset = 0;

    Matcher(final UnmodifiableNFA nfa, final String testString, final Pattern pattern) {
        this.nfa = nfa;
        this.testString = testString;
        this.pattern = pattern;
    }

    /**
     * @return a pattern used to construct this matcher
     */
    public Pattern pattern() {
        return pattern;
    }

    /**
     * @return a string which is matched by the mather
     */
    public String getTestString() {
        return testString;
    }

    /**
     * @return true if a whole given string matches the regular expression
     */
    public boolean matches() {
        boolean[] state = nfa.getInitialState();
        for (int i = 0; i < testString.length(); i++) {
            state = nfa.getNextState(state, testString.charAt(i));
        }
        return state[state.length - 1];
    }

    private String nextMatchingSubstring_impl() {
        boolean[] currentState = nfa.getInitialState();
        boolean wasAccepted = false;
        String longestMatchingByNow = null;
        for (int i = substringOffset; i < testString.length(); i++) {
            // We are greedy and do not allow empty substring. So feed the pattern in first
            currentState = nfa.getNextState(currentState, testString.charAt(i));
            if (wasAccepted && !currentState[currentState.length - 1]) {
                // This means the previous pattern was accepted and the new one is not, return the previous one.
                longestMatchingByNow = testString.substring(substringOffset, i);
            }
            wasAccepted = currentState[currentState.length - 1];
        }
        return longestMatchingByNow;
    }

    /**
     * Finds the longest substring matching the give regular expression.
     * On subsequent invocations starts searching at the end of the previously returned substring
     *
     * @return a matching substring or null if the no such string found
     */
    public String nextMatchingSubstring() {
        synchronized (lock) {
            String result = null;
            do {
                result = nextMatchingSubstring_impl();
                substringOffset ++;
            } while (result == null && substringOffset < testString.length());
            if (result != null) {

                substringOffset += result.length() - 1;
            }
            return result;
        }
    }

    /**
     * @return the beginning of the region where the next matching substring will be searched
     */
    public int getOffset() {
        synchronized (lock) {
            return substringOffset;
        }
    }

    /**
     * Sets the beginning of the region where the next matching substring will be searched
     */
    public void setOffset(int offset) {
        synchronized (lock) {
            substringOffset = offset;
        }
    }

    /**
     * Resets the matcher to it initial state.
     */
    public void reset() {
        synchronized (lock) {
            substringOffset = 0;
        }
    }
}
