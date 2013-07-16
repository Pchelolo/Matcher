package pchelolo.matcher.nfa;

public class Node implements UnmodifiableNode {

    private static final char FINAL_CHAR = ' ';

    private int number = 0;
    private final Character c;
    private final int[] out;

    public Node(Character c) {
        this(c, 1);
    }

    private Node(Character c, int splitCount) {
        this.c = c;
        this.out = new int[splitCount];
    }

    public int getNumber() {
        return number;
    }

    @Override
    public boolean isFinal() {
        return c == FINAL_CHAR;
    }

    static Node createFinal() {
        return new Node(FINAL_CHAR);
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setOutNode(Node newNode) {
        setOutNode(newNode, 0);
    }

    public void setOutNode(Node newNode, int index) {
        this.out[index] = newNode.getNumber();
    }

    static Node splitNode(int splitCount) {
        return new Node(null, splitCount);
    }

    void updateCount(int offset) {
        this.number += offset;
        for (int i = 0; i < out.length; i++) {
            out[i] += offset;
        }
    }

    @Override
    public Character getC() {
        return c;
    }

    @Override
    @SuppressWarnings("unchecked")
    public int[] getOut() {
        //TODO: clone?
        return out;
    }

    @Override
    public String toString() {
        return String.format("Node: c=%c outs=%d", c, out.length);
    }
}
