package seedu.algobase.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.algobase.commons.core.Messages;
import seedu.algobase.commons.core.index.Index;
import seedu.algobase.logic.commands.exceptions.CommandException;
import seedu.algobase.model.Model;
import seedu.algobase.model.searchrule.problemsearchrule.ProblemSearchRule;

/**
 * Deletes a Find Rule using its displayed index in the AlgoBase UI.
 */
public class DeleteFindRuleCommand extends Command {

    public static final String COMMAND_WORD = "deletefindrule";
    // TODO: write message usage for delete find rule command
    public static final String MESSAGE_USAGE = "to be finished";
    public static final String MESSAGE_SUCCESS = "Deleted Find Rule: %1$s";

    private final Index targetIndex;

    public DeleteFindRuleCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<ProblemSearchRule> lastShownList = model.getFilteredFindRuleList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_FIND_RULE_DISPLAYED_INDEX);
        }

        ProblemSearchRule ruleToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deleteFindRule(ruleToDelete);
        return new CommandResult(String.format(MESSAGE_SUCCESS, ruleToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteFindRuleCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteFindRuleCommand) other).targetIndex)); // state check
    }
}
