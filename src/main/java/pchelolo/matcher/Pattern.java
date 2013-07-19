package pchelolo.matcher;


import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import pchelolo.matcher.nfa.NFAUtils;
import pchelolo.matcher.nfa.UnmodifiableNFA;

public final class Pattern {

    private final UnmodifiableNFA nfa;
    // Memory visibility assurance
    private volatile boolean isCompiled = false;

    private Pattern(ParseTree tree) {
         nfa = NFAUtils.createNFA(tree);
    }

    public static Pattern compile(String patternString) {
        ParseTree parseTree = parse(patternString);
        if (parseTree == null) return null;
        Pattern pattern = new Pattern(parseTree);
        pattern.isCompiled = true;
        return pattern;
    }

    public Matcher matcher() {
        if (!isCompiled) {
            // Impossible
            throw new IllegalStateException("The pattern was not compiled");
        }
        return new Matcher(nfa);
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