package pchelolo.matcher;


import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

/**
 * Represents a compiled regular expression pattern.
 *
 * Immutable.
 */
public final class Pattern {

    private final UnmodifiableNFA nfa;
    // Memory visibility assurance
    private volatile boolean isCompiled = false;

    private Pattern(ParseTree tree) {
         nfa = new NFAConstructionVisitor().visit(tree);
    }

    static ParseTree parse(final String patternString) {
        if (patternString.isEmpty()) return null;
        ANTLRInputStream stream = new ANTLRInputStream(patternString);
        RegexLexer lexer = new RegexLexer(stream);
        CommonTokenStream tokenStream = new CommonTokenStream(lexer);
        RegexParser parser = new RegexParser(tokenStream);
        return parser.regex();
    }


    /**
     * Compiles a given pattern into an internal representation
     *
     * The regular expression supports the following syntax constructs.
     * The constructs are given in the order of operator precedence.
     *
     * [abc]   - list          - a shorthand for a|b|b
     * [x..y]  - range         - matches any character from x to y
     * a*      - closure       - mathes 0+ repeats of a given subpattern
     * ab      - concatenation - matches the given sequence of subpatterns
     * a|b     - disjunction   - matches either subpattern a or b
     *
     */
    public static Pattern compile(final String patternString) {
        ParseTree parseTree = parse(patternString);
        if (parseTree == null) return null;
        Pattern pattern = new Pattern(parseTree);
        pattern.isCompiled = true;
        return pattern;
    }

    /**
     * Constructs a matcher to match a given string
     */
    public Matcher matcher(final String testString) {
        if (!isCompiled) {
            // Impossible
            throw new IllegalStateException("The pattern was not compiled");
        }
        return new Matcher(nfa, testString, this);
    }
}