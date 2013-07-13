package pchelolo.matcher.nfa;

import pchelolo.matcher.NFAFragment;
import pchelolo.matcher.RegexBaseVisitor;
import pchelolo.matcher.RegexParser;
import pchelolo.matcher.nfa.NFA;

public class NFAConstructionVisitor extends RegexBaseVisitor<NFAFragment> {


    @Override
    public NFAFragment visitId(RegexParser.IdContext ctx) {
        char c = ctx.ID().getSymbol().getText().charAt(0);
        return new NFAFragment(new NFA.StateImpl(c));
    }

    @Override
    public NFAFragment visitAnd(RegexParser.AndContext ctx) {
        NFAFragment first = visit(ctx.pattern(0));
        NFAFragment second = visit(ctx.pattern(1));
        return NFAFragment.concatenation(first, second);
    }

    @Override
    public NFAFragment visitBraced(RegexParser.BracedContext ctx) {
        return visit(ctx.pattern());
    }

    @Override
    public NFAFragment visitOr(RegexParser.OrContext ctx) {
        NFAFragment first = visit(ctx.pattern(0));
        NFAFragment second = visit(ctx.pattern(1));
        return NFAFragment.disjunction(first, second);
    }
}
