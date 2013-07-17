package pchelolo.matcher;

/**
 * Represents the Nondeterministic Finite Automata.
 *
 * Immutable.
 */
interface UnmodifiableNFA {

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

    /**
     * The array of boolean is representing the current automata states
     * @return all states reachable from the starting node by epsilon transitions
     */
    boolean[] getInitialState();

    /**
     * @param state The current state of the automata
     * @param nextChar The for the transition
     * @return converts a state of the automata for the given char
     */
    boolean[] getNextState(boolean[] state, char nextChar);

}
