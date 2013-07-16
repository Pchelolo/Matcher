package pchelolo.matcher.nfa;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class NFAFragment implements UnmodifiableNFA{
    private List<Node> nodes;
    private List<Node> outNodes;

    NFAFragment(Node start) {
        nodes = new ArrayList<>();
        nodes.add(start);
        outNodes = new ArrayList<>();
    }

    List<Node> getNodes() {
        return nodes;
    }

    List<Node> getOutStates() {
        return outNodes;
    }

    void updateCounts(int offset) {
        for (Node node : nodes) {
            node.updateCount(offset);
        }
    }

    void setOutNodes(List<Node> newOutStates) {
        this.outNodes = newOutStates;
    }

    void setOutNode(Node node) {
        this.outNodes = new ArrayList<>();
        this.outNodes.add(node);
    }

    // ---------- UnmodifiableNFA public API -------- //
    @Override
    public Node getStart() {
        return nodes.get(0);
    }

    @Override
    public UnmodifiableNode getNodeAtIndex(int index) {
        return nodes.get(index);
    }

    @Override
    public int getNodesCount() {
        return nodes.size();
    }
}
