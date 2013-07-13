package pchelolo.matcher.nfa;

interface NFA {

    State getInitialState;

    public static interface State {
        Character getC();
        State getFirstOut();
        State getSecondOut();
    }

    static class StateImpl implements State {
        Character c;
        State firstOut;
        State secondOut;

        public StateImpl(char c) {
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

        public void setC(Character c) {
            this.c = c;
        }

        public void setFirstOut(State firstOut) {
            this.firstOut = firstOut;
        }

        public void setSecondOut(State secondOut) {
            this.secondOut = secondOut;
        }
    }
}
