package pchelolo.matcher.nfa;

import java.util.ArrayList;
import java.util.List;

class NFAFragment implements NFA {
    NFA.State start;
    List<StateImpl> outStates;

    NFAFragment(StateImpl start) {
        this.start = start;
        outStates = new ArrayList<>();
        outStates.add(start);
    }

    NFAFragment(NFA.State start, List<StateImpl> outStates) {
        this.start = start;
        this.outStates = outStates;
    }

    public static NFAFragment concatenation(NFAFragment first, NFAFragment second) {
        for (StateImpl outState : first.outStates) {
            outState.setFirstOut(second.start);
        }
        first.outStates = second.outStates;
        return first;
    }

    public static NFAFragment disjunction(NFAFragment first, NFAFragment second) {
        first.outStates.addAll(second.outStates);
        return new NFAFragment(new StateImpl(' ', first.start, second.start),
                first.outStates);
    }
}
