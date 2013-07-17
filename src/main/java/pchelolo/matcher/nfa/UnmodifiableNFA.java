package pchelolo.matcher.nfa;

/**
 * Represents the Nondeterministic Finite Automata.
 *
 * Immutable.
 */
public interface UnmodifiableNFA {

    /**
     * @return the starting node.
     */
    UnmodifiableNode getStart();

    /**
     * All nodes in the automata are numbered. This method could be use to quickly
     * get a node at specified index. Only one node can be associated with a particular
     * index. All nodes have indexes.
     *
     * @param index index of a node to get
     * @return node with a specified index
     */
    UnmodifiableNode getNodeAtIndex(int index);

    /**
     * The number of nodes in an automata
     */
    int getNodesCount();
}
