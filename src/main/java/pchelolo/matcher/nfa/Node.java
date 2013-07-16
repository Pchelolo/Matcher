package pchelolo.matcher.nfa;

abstract class Node implements UnmodifiableNode {
    private int number = 0;

    Node() {
    }

    void setNumber(int number) {
        this.number = number;
    }

    void updateCount(int offset) {
        this.number += offset;
        for (int i = 0; i < getOutCount(); i++) {
            setOutNode(getOut(i) + offset, i);
        }
    }

    void setOutNode(UnmodifiableNode newNode) {
        setOutNode(newNode, 0);
    }

    void setOutNode(UnmodifiableNode newNode, int index) {
        setOutNode(newNode.getNumber(), index);
    }

    abstract void setOutNode(int newNode, int index);

    // ------- Factory methods -------------------- //

    static Node splitNode(int splitCount) {
        return new SplitNode(splitCount);
    }

    static Node commonNode(char c) {
        return new CommonNode(c);
    }

    static Node finalNode() {
        return new FinalNode();
    }

    // ------- Unmodifiable Node public API ------- //

    @Override
    public int getNumber() {
        return number;
    }

    @Override
    public boolean isEpsilon() {
        return true;
    }

    private static class CommonNode extends Node {
        private final char c;
        private int out = 0;

        private CommonNode(char c) {
            this.c = c;
        }

        @Override
        public char getC() {
            return c;
        }

        @Override
        public int getOutCount() {
            return 1;
        }

        @Override
        public int getOut(int index) {
            assert index == 0;
            return out;
        }

        @Override
        public boolean isEpsilon() {
            return false;
        }

        @Override
        void setOutNode(int newNode, int index) {
            assert index == 0;
            out = newNode;
        }
    }

    private static class SplitNode extends Node {
        private final int[] out;

        SplitNode(int splitCount) {
            out = new int[splitCount];
        }

        @Override
        public char getC() {
            return 0;
        }

        @Override
        public int getOutCount() {
            return out.length;
        }

        @Override
        public int getOut(int index) {
            return out[index];
        }

        @Override
        void setOutNode(int newNode, int index) {
            this.out[index] = newNode;
        }

    }

    private static class FinalNode extends Node {

        FinalNode() {
        }

        @Override
        void setOutNode(int newNode, int index) {
            throw new IllegalStateException("Could not set out nodes of a final node");
        }

        @Override
        public char getC() {
            return 0;
        }

        @Override
        public int getOutCount() {
            return 0;
        }

        @Override
        public int getOut(int index) {
            throw new IllegalStateException("Could not get out nodes of a final node");
        }
    }

}
