package pchelolo.matcher.nfa;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class NFAFragment {
    private List<Node> nodes;
    private List<Node> outNodes;

    NFAFragment(Node start) {
        nodes = new ArrayList<>();
        nodes.add(start);
        outNodes = new ArrayList<>();
    }

    public List<Node> getOutStates() {
        return outNodes;
    }

    public Node getStart() {
        return nodes.get(0);
    }

    public List<Node> getNodes() {
        return nodes;
    }

    public void updateCounts(int offset) {
        for (Node node : nodes) {
            node.updateCount(offset);
        }
    }

    public void setOutNodes(List<Node> newOutStates) {
        this.outNodes = newOutStates;
    }

    public void setOutNode(Node node) {
        this.outNodes = new ArrayList<>();
        this.outNodes.add(node);
    }
}
