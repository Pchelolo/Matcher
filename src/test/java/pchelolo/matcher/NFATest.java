package pchelolo.matcher;


import static org.junit.Assert.*;
import org.antlr.v4.runtime.tree.ParseTree;
import org.junit.Test;
import pchelolo.matcher.Pattern;
import pchelolo.matcher.nfa.NFA;
import pchelolo.matcher.nfa.NFAConstructionVisitor;
import pchelolo.matcher.nfa.NFAFragment;

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
        NFA.State firstState = nfa.getStart();
        assertNull(firstState.getC());
        assertNotNull(firstState.getFirstOut());
        assertNotNull(firstState.getSecondOut());
        NFA.State aState = firstState.getFirstOut();
        assertTrue(aState.getC() == 'a');
        assertNotNull(aState.getFirstOut());
        assertNull(aState.getSecondOut());
        NFA.State bState = firstState.getSecondOut();
        assertTrue(bState.getC() == 'b');
        assertNull(bState.getSecondOut());
        NFA.State cState = bState.getFirstOut();
        assertTrue(cState.getC() == 'c');
        assertNull(cState.getSecondOut());
        NFA.State finState = cState.getFirstOut();
        assertTrue(finState.isFinal());
        assertEquals(finState, aState.getFirstOut());
    }
}
