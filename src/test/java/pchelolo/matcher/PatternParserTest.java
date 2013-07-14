package pchelolo.matcher;

import static org.junit.Assert.*;

import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTree;
import org.junit.Test;

/**
    Basic testing of an auto-generated parser.
 */
public class PatternParserTest {

    @Test
    public void testConcatenation() throws Exception {
        ParseTree tree = Pattern.parse("ab");
        assertNotNull(tree);
        boolean result = new RegexBaseVisitor<Boolean>() {
            @Override
            public Boolean visitConjunction(@NotNull RegexParser.ConjunctionContext ctx) {
                return true;
            }
        }.visit(tree);
        assertTrue(result);
    }

    @Test
    public void testDisjunction() throws Exception {
        ParseTree tree = Pattern.parse("a|b");
        assertNotNull(tree);
        boolean result = new RegexBaseVisitor<Boolean>() {
            @Override
            public Boolean visitDisjunction(@NotNull RegexParser.DisjunctionContext ctx) {
                return true;
            }
        }.visit(tree);
        assertTrue(result);
    }

    @Test
    public void testClosure() throws Exception {
        ParseTree tree = Pattern.parse("a*");
        assertNotNull(tree);
        boolean result = new RegexBaseVisitor<Boolean>() {
            @Override
            public Boolean visitClosure(@NotNull RegexParser.ClosureContext ctx) {
                return true;
            }
        }.visit(tree);
        assertTrue(result);
    }

}
