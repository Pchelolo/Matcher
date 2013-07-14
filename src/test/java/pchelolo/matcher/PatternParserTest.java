package pchelolo.matcher;

import static org.junit.Assert.*;

import org.antlr.v4.runtime.tree.ParseTree;
import org.junit.Test;

/**
 * Testing the auto-generated parser to ensure grammar correctness
 */
public class PatternParserTest {

    @Test
    public void testConcatenation() throws Exception {
        ParseTree tree = Pattern.parse("ab");
        assertNotNull(tree);
        boolean result = new RegexBaseVisitor<Boolean>() {
            @Override
            public Boolean visitAnd(RegexParser.AndContext ctx) {
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
            public Boolean visitOr(RegexParser.OrContext ctx) {
                return true;
            }
        }.visit(tree);
        assertTrue(result);
    }
}
