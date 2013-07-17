package pchelolo.matcher;

import static org.junit.Assert.*;

import org.junit.Test;


public class MatcherTest {

    @Test
    public void testMatchesA() throws Exception {
        Pattern p = Pattern.compile("a");
        assertTrue(p.matcher("a").matches());
        assertFalse(p.matcher("b").matches());
        assertFalse(p.matcher("ab").matches());
    }

    @Test
    public void testMatchesAorB() throws Exception {
        Pattern p = Pattern.compile("ab|cd");
        assertTrue(p.matcher("ab").matches());
        assertTrue(p.matcher("cd").matches());
        assertFalse(p.matcher("abc").matches());
    }

    @Test
    public void testMatchesClosure() throws Exception {
        Pattern p = Pattern.compile("a*");
        assertTrue(p.matcher("a").matches());
        assertTrue(p.matcher("aaaaaaaaaaa").matches());
        assertFalse(p.matcher("ab").matches());
    }

    @Test
    public void testMatchesAB() throws Exception {
        Pattern p = Pattern.compile("ab");
        assertFalse(p.matcher("a").matches());
        assertFalse(p.matcher("b").matches());
        assertTrue(p.matcher("ab").matches());
    }

    @Test
    public void testMatchesRangeGroup() throws Exception {
        Pattern p = Pattern.compile("[a-z0-9]*");
        assertTrue(p.matcher("a").matches());
        assertTrue(p.matcher("absdfvsdfvsdcadc90129").matches());
        assertFalse(p.matcher("A").matches());
        assertFalse(p.matcher("asdasdcasdcasdcasdcA").matches());
    }

    @Test
    public void testClosureWithDisjunction() throws Exception {
        String pattern = "(a|b)*";
        Pattern p = Pattern.compile(pattern);
        java.util.regex.Pattern javaPattern = java.util.regex.Pattern.compile(pattern);
        String[] testSequences = {
                "aaa",
                "bbbbbbb",
                "abbbabababaab",
                "",
                "c",
                "ababbabababababababaaac"
        };
        for (String test : testSequences) {
            assertTrue("AssertionError for string: " + test, p.matcher(test).matches() == javaPattern.matcher(test).matches());
        }

    }

    @Test
    public void testMatchesComplex1() throws Exception {
        String pattern = "a*|(a*ba*ba*)*[a-zA-Z]*";
        Pattern p = Pattern.compile(pattern);
        java.util.regex.Pattern javaPattern = java.util.regex.Pattern.compile(pattern);
        String[] testSequences = {
                "a",
                "aaaasdcaksjdcnajksdcaASDCAasdcaDCa",
                "bbaabbasdcASca",
                "babaaasdASDCa0",
                "ab0a",
                "bbbASDc0",
                "babbaaaasdc)A"
        };
        for (String test : testSequences) {
            assertTrue("AssertionError for string: " + test, p.matcher(test).matches() == javaPattern.matcher(test).matches());
        }
    }
}
