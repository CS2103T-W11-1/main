package seedu.algobase.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_NAME = new Prefix("n/");
    public static final Prefix PREFIX_AUTHOR = new Prefix("a/");
    public static final Prefix PREFIX_WEBLINK = new Prefix("w/");
    public static final Prefix PREFIX_DESCRIPTION = new Prefix("d/");
    public static final Prefix PREFIX_TAG = new Prefix("t/");
    public static final Prefix PREFIX_DIFFICULTY = new Prefix("diff/");
    public static final Prefix PREFIX_REMARK = new Prefix("r/");
    public static final Prefix PREFIX_SOURCE = new Prefix("src/");
    public static final Prefix PREFIX_SORTING_METHOD = new Prefix("m/");
    public static final Prefix PREFIX_SORTING_ORDER = new Prefix("ord/");
    public static final Prefix PREFIX_PLAN = new Prefix("plan/");
    public static final Prefix PREFIX_PROBLEM = new Prefix("prob/");
    public static final Prefix PREFIX_TASK = new Prefix("task/");
    public static final Prefix PREFIX_START_DATE = new Prefix("s/");
    public static final Prefix PREFIX_END_DATE = new Prefix("e/");
}
