package pchelolo.matcher.nfa;

public interface UnmodifiableNode {
    Character getC();
    int getOutCount();
    int getOut(int index);
    int getNumber();
    boolean isFinal();
}
