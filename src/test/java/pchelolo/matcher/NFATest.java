package pchelolo.matcher;


import org.antlr.v4.runtime.tree.ParseTree;
import org.junit.Test;
import pchelolo.matcher.nfa.NFAConstructionVisitor;
import pchelolo.matcher.nfa.NFAFragment;
import pchelolo.matcher.nfa.Node;
import pchelolo.matcher.nfa.UnmodifiableNode;

import java.util.List;

import static org.junit.Assert.*;

public class NFATest {

    @Test
    public void NFAConstructionTest() throws Exception {
        ParseTree tree = Pattern.parse("a|bc");
        /*
               'a' -------
              /           \
            0 - 'b' - 'c' - - fin
         */
        NFAFragment nfa = new NFAConstructionVisitor().visit(tree);
        assertNotNull(nfa);
        Node firstState = nfa.getStart();
        assertNull(firstState.getC());
        List<UnmodifiableNode> firstOuts = firstState.getOut();
        assertNotNull(firstOuts);
        assertEquals(2, firstOuts.size());
        boolean visitedA = false;
        boolean visitedB = false;
        for (UnmodifiableNode node : firstOuts) {
            if (node.getC() == 'a') {
                List<UnmodifiableNode> aOuts = node.getOut();
                assertEquals(1, aOuts.size());
                assertTrue(aOuts.contains(UnmodifiableNode.FINAL));
                visitedA = true;
            } else if (node.getC() == 'b') {
                List<UnmodifiableNode> bOuts = node.getOut();
                assertEquals(1, bOuts.size());
                UnmodifiableNode cState = bOuts.iterator().next();
                assertTrue(cState.getC() == 'c');
                assertEquals(1, cState.getOut().size());
                assertTrue(cState.getOut().contains(UnmodifiableNode.FINAL));
                visitedB = true;
            } else {
                assertTrue("Unexpected state " + node, false);
            }
        }
        assertTrue(visitedA && visitedB);
    }
}
