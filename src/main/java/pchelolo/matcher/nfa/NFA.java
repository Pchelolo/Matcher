package pchelolo.matcher.nfa;

import java.util.Set;

public interface NFA {

    Set<State> getInitialStates();
    Set<State> getNextStates(Set<State> states, char nextChar);
    boolean containsFinalState(Set<State> states);


    public static interface State {
        Character getC();
        State getFirstOut();
        State getSecondOut();
        boolean isFinal();
    }

    static class StateImpl implements State {
        Character c;
        State firstOut;
        State secondOut;

        public StateImpl(Character c) {
            this.c = c;
        }

        public StateImpl(Character c, State firstOut, State secondOut) {
            this.c = c;
            this.firstOut = firstOut;
            this.secondOut = secondOut;
        }

        @Override
        public Character getC() {
            return c;
        }

        @Override
        public State getFirstOut() {
            return firstOut;
        }

        @Override
        public State getSecondOut() {
            return secondOut;
        }

        @Override
        public boolean isFinal() {
            return c != null && c == ' ';
        }

        @Override
        public String toString() {
            return String.format("State: c=%c final=%b", c, isFinal());
        }

        public void setC(Character c) {
            this.c = c;
        }

        public void setFirstOut(State firstOut) {
            this.firstOut = firstOut;
        }

        public void setSecondOut(State secondOut) {
            this.secondOut = secondOut;
        }

        public static StateImpl createFinalState() {
            return new StateImpl(' ', null, null);
        }
    }
}
