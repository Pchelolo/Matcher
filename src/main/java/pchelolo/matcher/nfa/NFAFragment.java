package pchelolo.matcher.nfa;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class NFAFragment implements NFA {
    private NFA.State start;
    private List<StateImpl> outStates;

    NFAFragment(StateImpl start) {
        this.start = start;
        outStates = new ArrayList<>();
        outStates.add(start);
    }

    private NFAFragment(NFA.State start, List<StateImpl> outStates) {
        this.start = start;
        this.outStates = outStates;
    }

    @Override
    public Set<State> getInitialStates() {
        final Set<State> states = new HashSet<>();
        addEReachableStates(start, states);
        return states;
    }

    @Override
    public Set<State> getNextStates(Set<State> states, char nextChar) {
        Set<State> newSet = new HashSet<>();
        for (State state : states) {
            if (state.getC() != null && state.getC() == nextChar) {
                addEReachableStates(state.getFirstOut(), newSet);
                addEReachableStates(state.getSecondOut(), newSet);
            }
        }
        return newSet;
    }

    private void addEReachableStates(State state, Set<State> set) {
        if (state == null) return;
        set.add(state);
        if (state.getC() == null) {
            addEReachableStates(state.getFirstOut(), set);
            addEReachableStates(state.getSecondOut(), set);
        }
    }

    @Override
    public boolean containsFinalState(Set<State> states) {
        for (State state : states) {
            if (state.isFinal()) return true;
        }
        return false;
    }

    public State getStart() {
        return start;
    }

    public static NFAFragment concatenation(NFAFragment first, NFAFragment second) {
        for (StateImpl outState : first.outStates) {
            outState.setFirstOut(second.start);
        }
        first.outStates = second.outStates;
        return first;
    }

    public static NFAFragment disjunction(NFAFragment first, NFAFragment second) {
        StateImpl startState = new StateImpl(null, first.getStart(), second.getStart());
        first.start = startState;
        first.outStates.addAll(second.outStates);
        return first;
    }

    public static NFAFragment finish(NFAFragment fragment) {
        StateImpl finalState = StateImpl.createFinalState();
        for (StateImpl state : fragment.outStates) {
            state.setFirstOut(finalState);
        }
        // Would not need it any more
        fragment.outStates = null;
        return fragment;
    }

    public static NFAFragment closure(NFAFragment fragment) {
        StateImpl startState = new StateImpl(null, null, fragment.start);
        for (StateImpl state : fragment.outStates) {
            state.firstOut = startState;
        }
        fragment.start = startState;
        fragment.outStates.clear();
        fragment.outStates.add(startState);
        return fragment;
    }
}
