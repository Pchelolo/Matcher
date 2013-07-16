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
        int[] firstOuts = firstState.getOut();
        assertNotNull(firstOuts);
        assertEquals(2, firstOuts.length);
        boolean visitedA = false;
        boolean visitedB = false;
        for (int idx : firstOuts) {
            UnmodifiableNode node = nfa.getNodes().get(idx);
            if (node.getC() == 'a') {
                int[] aOuts = node.getOut();
                assertEquals(1, aOuts.length);
                assertTrue(nfa.getNodes().get(aOuts[0]).isFinal());
                visitedA = true;
            } else if (node.getC() == 'b') {
                int[] bOuts = node.getOut();
                assertEquals(1, bOuts.length);
                UnmodifiableNode cState = nfa.getNodes().get(bOuts[0]);
                assertTrue(cState.getC() == 'c');
                assertEquals(1, cState.getOut().length);
                assertTrue(nfa.getNodes().get(cState.getOut()[0]).isFinal());
                visitedB = true;
            } else {
                assertTrue("Unexpected state " + node, false);
            }
        }
        assertTrue(visitedA && visitedB);
    }
}
