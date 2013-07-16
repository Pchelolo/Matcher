package pchelolo.matcher.nfa;

public interface UnmodifiableNode {
    char getC();
    int getOutCount();
    int getOut(int index);
    int getNumber();
    boolean isFinal();
    boolean isEpsilon();
}
