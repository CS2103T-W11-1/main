package seedu.algobase.model.problem;

import java.util.function.Predicate;

/**
 * Tests that a {@code Problem}'s {@code Source} matches the given keyword exactly.
 */
public class SourceMatchesKeywordPredicate implements Predicate<Problem> {

    private final String keyword;

    public SourceMatchesKeywordPredicate(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public boolean test(Problem problem) {
        return problem.getSource().equals(new Source(keyword));
    }
}