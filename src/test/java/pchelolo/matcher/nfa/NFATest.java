package pchelolo.matcher.nfa;


import org.antlr.v4.runtime.tree.ParseTree;
import org.junit.Test;
import pchelolo.matcher.Pattern;

import java.lang.reflect.Method;

import static org.junit.Assert.*;

public class NFATest {

    @Test
    public void NFAConstructionTest() throws Exception {
        Class<Pattern> clazz = Pattern.class;
        Method parse = clazz.getDeclaredMethod("parse", String.class);
        parse.setAccessible(true);
        ParseTree tree = (ParseTree) parse.invoke(clazz, "a|bc");
        /*
               'a' -------
              /           \
            0 - 'b' - 'c' - - fin
         */
        NFAFragment nfa = new NFAConstructionVisitor().visit(tree);
        assertNotNull(nfa);
        Node firstState = nfa.getStart();
        assertTrue(firstState.isEpsilon());
        assertEquals(2, firstState.getOutCount());
        boolean visitedA = false;
        boolean visitedB = false;
        for (int idx = 0; idx < firstState.getOutCount(); idx++) {
            UnmodifiableNode node = nfa.getNodes().get(firstState.getOut(idx));
            if (node.getC() == 'a') {
                assertEquals(1, node.getOutCount());
                visitedA = true;
            } else if (node.getC() == 'b') {
                assertEquals(1, node.getOutCount());
                UnmodifiableNode cState = nfa.getNodes().get(node.getOut(0));
                assertTrue(cState.getC() == 'c');
                assertEquals(1, cState.getOutCount());
                visitedB = true;
            } else {
                assertTrue("Unexpected state " + node, false);
            }
        }
        assertTrue(visitedA && visitedB);
    }
}
