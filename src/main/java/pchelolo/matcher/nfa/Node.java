package pchelolo.matcher.nfa;

import java.util.*;

public class Node implements UnmodifiableNode {

    private final Character c;
    private final Node[] out;

    public Node(Character c) {
        this(c, 1);
    }

    private Node(Character c, int splitCount) {
        this.c = c;
        this.out = new Node[splitCount];
    }

    public void setOutNode(Node newNode) {
        setOutNode(newNode, 0);
    }

    public void setOutNode(Node newNode, int index) {
        this.out[index] = newNode;
    }

    static Node splitNode(int splitCount) {
        return new Node(null, splitCount);
    }

    @Override
    public Character getC() {
        return c;
    }

    @Override
    @SuppressWarnings("unchecked")
    public UnmodifiableNode[] getOut() {
        //TODO: clone?
        return out;
    }

    @Override
    public String toString() {
        return String.format("Node: c=%c outs=%d", c, out.length);
    }
}
