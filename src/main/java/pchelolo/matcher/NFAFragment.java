package pchelolo.matcher;

import java.util.ArrayList;
import java.util.List;

class NFAFragment implements UnmodifiableNFA{
    private final List<Node> nodes;
    private List<Node> outNodes;

    NFAFragment(Node start) {
        nodes = new ArrayList<>();
        nodes.add(start);
        outNodes = new ArrayList<>();
    }

    List<Node> getNodes() {
        return nodes;
    }

    List<Node> getOutNodes() {
        return outNodes;
    }

    void updateCounts(int offset) {
        for (Node node : nodes) {
            node.updateCount(offset);
        }
    }

    void connectOutNodes(UnmodifiableNode nextNode) {
        for (Node outNode : outNodes) {
            outNode.setOutNode(nextNode);
        }
    }

    void setOutNodes(List<Node> newOutStates) {
        this.outNodes = newOutStates;
    }

    void setOutNode(Node node) {
        this.outNodes = new ArrayList<>();
        this.outNodes.add(node);
    }

    private void addEReachableStates(UnmodifiableNode node, boolean[] state) {
        if (!state[node.getNumber()]) {
            state[node.getNumber()] = true;
            if (node.isEpsilon()) {
                for (int i = 0; i < node.getOutCount(); i++) {
                    addEReachableStates(getNodeAtIndex(node.getOut(i)), state);
                }
            }
        }
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

    @Override
    public boolean[] getInitialState() {
        boolean[] state = new boolean[getNodesCount()];
        addEReachableStates(getStart(), state);
        return state;
    }

    @Override
    public boolean[] getNextState(boolean[] state, char nextChar) {
        boolean[] newState = new boolean[state.length];
        for (int i = 0; i < state.length; i++) {
            if (state[i]) {
                UnmodifiableNode node = getNodeAtIndex(i);
                if (node.getC() == nextChar) {
                    for (int outIdx = 0; outIdx < node.getOutCount(); outIdx++) {
                        addEReachableStates(getNodeAtIndex(node.getOut(outIdx)), newState);
                    }
                }
            }
        }
        return newState;
    }
}
