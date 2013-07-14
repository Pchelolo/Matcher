package pchelolo.matcher.nfa;

import java.util.HashSet;
import java.util.Set;

public class NFAFragment {
    private Node start;
    private Set<Node> outStates;

    NFAFragment(Node start) {
        this.start = start;
        outStates = new HashSet<>();
    }

    public Set<Node> getOutStates() {
        return outStates;
    }

    public Node getStart() {
        return start;
    }

    public void setStart(Node newStart) {
        this.start = newStart;
    }

    public void setOutStates(Set<Node> newOutStates) {
        this.outStates = newOutStates;
    }

    public void setOutState(Node newOutState) {
        this.outStates = new HashSet<>();
        this.outStates.add(newOutState);
    }
}
