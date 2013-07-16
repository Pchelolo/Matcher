package pchelolo.matcher.nfa;

import java.util.List;

public interface UnmodifiableNode {
    public static final Node FINAL = new Node(' ');
    public Character getC();
    public UnmodifiableNode[] getOut();
}
