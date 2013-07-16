package pchelolo.matcher.nfa;

public interface UnmodifiableNFA {
    UnmodifiableNode getStart();
    UnmodifiableNode getNodeAtIndex(int index);
    int getNodesCount();
}
