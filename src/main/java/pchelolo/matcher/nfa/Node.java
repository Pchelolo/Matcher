package pchelolo.matcher.nfa;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Node implements UnmodifiableNode {

    private final Character c;
    private final Set<Node> out = new HashSet<>();

    public Node(Character c) {
        this.c = c;
    }

    public void addOutNode(Node newNode) {
        out.add(newNode);
    }


    @Override
    public Character getC() {
        return c;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Set<UnmodifiableNode> getOut() {
        return (Set)Collections.unmodifiableSet(out);
    }

    @Override
    public String toString() {
        return String.format("Node: c=%c outs=%d", c, out.size());
    }
}
