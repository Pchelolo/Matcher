package pchelolo.matcher.nfa;

import java.util.*;

public class Node implements UnmodifiableNode {

    private final Character c;
    private final List<Node> out = new ArrayList<>(1);

    public Node(Character c) {
        this.c = c;
    }

    public void addOutNode(Node newNode) {
        out.add(newNode);
    }

    public static Node splitNode() {
        return new Node(null);
    }

    @Override
    public Character getC() {
        return c;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<UnmodifiableNode> getOut() {
        return (List)Collections.unmodifiableList(out);
    }

    @Override
    public String toString() {
        return String.format("Node: c=%c outs=%d", c, out.size());
    }
}
