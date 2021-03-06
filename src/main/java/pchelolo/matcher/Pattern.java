package pchelolo.matcher;


import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import java.io.Serializable;

/**
 * Represents a compiled regular expression pattern.
 * <p/>
 * Immutable.
 */
public final class Pattern implements Serializable {

    private transient UnmodifiableNFA nfa;

    private final String patternString;

    private transient volatile boolean isCompiled = false;

    private Pattern(ParseTree tree, String patternString) {
        this.patternString = patternString;
        nfa = new NFAConstructionVisitor().visit(tree);
        isCompiled = true;
    }

    private void recompile() {
        nfa = new NFAConstructionVisitor().visit(parse(patternString));
        isCompiled = true;
    }

    /**
     * Compiles a given pattern into an internal representation
     * <p/>
     * The regular expression supports the following syntax constructs.
     * The constructs are given in the order of operator precedence.
     * <p/>
     * [abc]   - list          - a shorthand for a|b|b
     * [x..y]  - range         - matches any character from x to y
     * a*      - closure       - mathes 0+ repeats of a given subpattern
     * ab      - concatenation - matches the given sequence of subpatterns
     * a|b     - disjunction   - matches either subpattern a or b
     */
    public static Pattern compile(final String patternString) {
        ParseTree parseTree = parse(patternString);
        if (parseTree == null) return null;
        Pattern pattern = new Pattern(parseTree, patternString);
        pattern.isCompiled = true;
        return pattern;
    }

    /**
     * Constructs a matcher to match a given string
     */
    public Matcher matcher(final String testString) {
        if (!isCompiled) {
            synchronized (this) {
                if (!isCompiled) {
                    recompile();
                }
            }
        }

        return new Matcher(nfa, testString, this);
    }

    static ParseTree parse(final String patternString) {
        if (patternString.isEmpty()) return null;
        RegexParser parser = new RegexParser(
                new CommonTokenStream(
                        new RegexLexer(
                                new ANTLRInputStream(patternString))));
        return parser.regex();
    }
}