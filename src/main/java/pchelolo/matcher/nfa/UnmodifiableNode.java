package pchelolo.matcher.nfa;

public interface UnmodifiableNode {
    Character getC();
    int[] getOut();
    int getNumber();
    boolean isFinal();
}
