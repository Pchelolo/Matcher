package pchelolo.matcher;

import static org.junit.Assert.*;

import org.junit.Test;


public class MatcherTest {

    @Test
    public void testMatchesA() throws Exception {
        Matcher m = Pattern.compile("a").matcher();
        assertTrue(m.matches("a"));
        assertFalse(m.matches("b"));
        assertFalse(m.matches("ab"));
    }

    @Test
    public void testMatchesAorB() throws Exception {
        Matcher m = Pattern.compile("ab|cd").matcher();
        assertTrue(m.matches("ab"));
        assertTrue(m.matches("cd"));
        assertFalse(m.matches("abc"));
    }

    @Test
    public void testMatchesClosure() throws Exception {
        Matcher m = Pattern.compile("a*").matcher();
        assertTrue(m.matches("a"));
        assertTrue(m.matches("aaaaaaaaaaa"));
        assertFalse(m.matches("ab"));
    }

    @Test
    public void testMatchesAB() throws Exception {
        Matcher m = Pattern.compile("ab").matcher();
        assertFalse(m.matches("a"));
        assertFalse(m.matches("b"));
        assertTrue(m.matches("ab"));
    }

    @Test
    public void testClosureWithDisjunction() throws Exception {
        String pattern = "(a|b)*";
        Matcher m = Pattern.compile(pattern).matcher();
        java.util.regex.Pattern javaPattern = java.util.regex.Pattern.compile(pattern);
        String[] testSequences = {
                "aaa",
                "bbbbbbb",
                "abbbabababaab",
                "",
                "c",
                "ababbabababababababaaac"
        };
        System.out.println(m.matches(testSequences[0]));
        for (String test : testSequences) {
            assertTrue("AssertionError for string: " + test, m.matches(test) == javaPattern.matcher(test).matches());
        }

    }

    @Test
    public void testMatchesComplex1() throws Exception {
        String pattern = "a*|(a*ba*ba*)*";
        Matcher m = Pattern.compile(pattern).matcher();
        java.util.regex.Pattern javaPattern = java.util.regex.Pattern.compile(pattern);
        String[] testSequences = {
                "a",
                "aaa",
                "bbaabb",
                "babaa",
                "aba",
                "bbb",
                "babbaaa"
        };
        for (String test : testSequences) {
            assertTrue("AssertionError for string: " + test, m.matches(test) == javaPattern.matcher(test).matches());
        }
    }
}
