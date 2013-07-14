package pchelolo.matcher.nfa;

import java.util.Set;

public interface UnmodifiableNode {
    public static final Node FINAL = new Node(' ');
    public Character getC();
    public Set<UnmodifiableNode> getOut();
}
