package seedu.algobase.model;

import javafx.collections.ObservableList;
import seedu.algobase.model.plan.Plan;
import seedu.algobase.model.problem.Problem;
import seedu.algobase.model.tag.Tag;
import seedu.algobase.model.task.Task;

/**
 * Unmodifiable view of an algobase
 */
public interface ReadOnlyAlgoBase {

    /**
     * Returns an unmodifiable view of the problems list.
     * This list will not contain any duplicate problems.
     */
    ObservableList<Problem> getProblemList();
    ObservableList<Tag> getTagList();

    /**
     * Returns an unmodifiable view of the plans list.
     */
    ObservableList<Plan> getPlanList();

    /**
     * Returns an unmodifiable view of the current tasks list.
     */
    ObservableList<Task> getCurrentTaskList();

}
