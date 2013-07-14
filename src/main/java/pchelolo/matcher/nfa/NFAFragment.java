package pchelolo.matcher.nfa;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class NFAFragment {
    private Node start;
    private List<Node> outStates;

    NFAFragment(Node start) {
        this.start = start;
        outStates = new ArrayList<>();
    }

    public List<Node> getOutStates() {
        return outStates;
    }

    public Node getStart() {
        return start;
    }

    public void setStart(Node newStart) {
        this.start = newStart;
    }

    public void setOutStates(List<Node> newOutStates) {
        this.outStates = newOutStates;
    }

    public void setOutState(Node newOutState) {
        this.outStates = new ArrayList<>();
        this.outStates.add(newOutState);
    }
}
