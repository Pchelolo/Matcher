package pchelolo.matcher.nfa;

class Node implements UnmodifiableNode {

    private static final char FINAL_CHAR = ' ';

    private int number = 0;
    private final Character c;
    private final int[] out;

    private Node(Character c, int splitCount) {
        this.c = c;
        this.out = new int[splitCount];
    }

    void setNumber(int number) {
        this.number = number;
    }

    void setOutNode(Node newNode) {
        setOutNode(newNode, 0);
    }

    void setOutNode(Node newNode, int index) {
        this.out[index] = newNode.getNumber();
    }

    void updateCount(int offset) {
        this.number += offset;
        for (int i = 0; i < out.length; i++) {
            out[i] += offset;
        }
    }

    // ------- Factory methods -------------------- //

    static Node splitNode(int splitCount) {
        return new Node(null, splitCount);
    }

    static Node commonNode(Character c) {
        return new Node(c, 1);
    }

    static Node finalNode() {
        return new Node(FINAL_CHAR, 0);
    }

    // ------- Unmodifiable Node public API ------- //

    @Override
    public int getNumber() {
        return number;
    }

    @Override
    public boolean isFinal() {
        return c == FINAL_CHAR;
    }

    @Override
    public Character getC() {
        return c;
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
    public String toString() {
        return String.format("Node: c=%c outs=%d", c, out.length);
    }
}
