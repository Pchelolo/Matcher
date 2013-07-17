package pchelolo.matcher;

/**
 * Represents a single node in a Nondeterministic Finite Automata.
 *
 * A node can be one of 3 types:
 *  1. Labeled node - common node with a character label and a single out transition
 *  2. Split node   - a node with multiple out transitions and no label
 *  3. Final node   - a special node which represents a final state of the automata
 *
 * Immutable.
 */
interface UnmodifiableNode {

    /**
     * @return node label or 0 if not applicable to the node type
     */
    char getC();

    /**
     * @return the number of output transitions
     */
    int getOutCount();

    /**
     * Output transitions are represented as node numbers in an NFA (see: {@link UnmodifiableNFA})
     *
     * @param index index of a transition
     * @return next node number
     */
    int getOut(int index);

    /**
     * @return the number of a given node in an NFA
     */
    int getNumber();

    /**
     * Checks if a given node is a split node with epsilon transitions.
     */
    boolean isEpsilon();
}
