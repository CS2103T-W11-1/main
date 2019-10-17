package seedu.algobase.model.plan;

import static seedu.algobase.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.algobase.model.task.Task;

/**
 * Represents a Plan in the algobase.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Plan {

    public static final String DATE_TIME_CONSTRAINTS = "DateTime format should be 'yyyy-MM-dd HH:mm:ss'.";
    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    // Identity fields
    private final PlanName planName;

    // Data fields
    private final PlanDescription planDescription;
    private final LocalDateTime startDate;
    private final LocalDateTime endDate;
    private final Set<Task> tasks;

    /**
     * Every field must be present and not null.
     */
    public Plan(PlanName planName, PlanDescription planDescription, LocalDateTime startDate, LocalDateTime endDate,
                Set<Task> tasks) {
        requireAllNonNull(planName, planDescription, startDate, endDate, tasks);
        this.planName = planName;
        this.planDescription = planDescription;
        this.startDate = startDate;
        this.endDate = endDate;
        this.tasks = new HashSet<>();
        this.tasks.addAll(tasks);
    }

    /**
     * Creates and returns a {@code Plan} with the details of {@code planToUpdate}
     * with an updated {@code taskSet}.
     */
    public static Plan createUpdatedPlan(Plan planToUpdate, Set taskSet) {
        assert planToUpdate != null;

        PlanName updatedName = planToUpdate.getPlanName();
        PlanDescription updatedDescription = planToUpdate.getPlanDescription();
        LocalDateTime startDate = planToUpdate.getStartDate();
        LocalDateTime endDate = planToUpdate.getEndDate();

        return new Plan(updatedName, updatedDescription, startDate, endDate, taskSet);
    }

    public PlanName getPlanName() {
        return planName;
    }

    public PlanDescription getPlanDescription() {
        return planDescription;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    /**
     * Returns an immutable task set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Task> getTasks() {
        return Collections.unmodifiableSet(tasks);
    }

    /**
     * Returns true if both plans of the same planName have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two plans.
     */
    public boolean isSamePlan(Plan otherPlan) {
        if (otherPlan == this) {
            return true;
        }

        return otherPlan != null
                && otherPlan.getPlanName().equals(getPlanName());
    }

    /**
     * Returns true if both plans have the same identity and data fields.
     * This defines a stronger notion of equality between two plans.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Plan)) {
            return false;
        }

        Plan otherPlan = (Plan) other;
        return otherPlan.getPlanName().equals(getPlanName())
                && otherPlan.getPlanDescription().equals(getPlanDescription())
                && otherPlan.getStartDate().equals(getStartDate())
                && otherPlan.getEndDate().equals(getEndDate())
                && otherPlan.getTasks().equals(getTasks());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(planName, planDescription, startDate, endDate, tasks);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getPlanName())
                .append(" Description: ")
                .append(getPlanDescription())
                .append(" Start Date: ")
                .append(getStartDate())
                .append(" End Date: ")
                .append(getEndDate())
                .append(" Tasks: ");
        getTasks().forEach(builder::append);
        return builder.toString();
    }

}
