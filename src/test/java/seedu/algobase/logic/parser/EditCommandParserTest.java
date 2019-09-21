package seedu.algobase.logic.parser;

import static seedu.algobase.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.algobase.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static seedu.algobase.logic.commands.CommandTestUtil.ADDRESS_DESC_BOB;
import static seedu.algobase.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.algobase.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.algobase.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
import static seedu.algobase.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.algobase.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.algobase.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.algobase.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.algobase.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.algobase.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.algobase.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.algobase.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.algobase.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.algobase.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.algobase.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.algobase.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.algobase.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.algobase.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.algobase.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.algobase.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.algobase.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.algobase.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.algobase.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.algobase.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.algobase.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.algobase.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.algobase.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.algobase.testutil.TypicalIndexes.INDEX_THIRD_PERSON;

import org.junit.jupiter.api.Test;

import seedu.algobase.commons.core.index.Index;
import seedu.algobase.logic.commands.EditCommand;
import seedu.algobase.logic.commands.EditCommand.EditProblemDescriptor;
import seedu.algobase.model.Problem.Author;
import seedu.algobase.model.Problem.Description;
import seedu.algobase.model.Problem.WebLink;
import seedu.algobase.model.Problem.Name;
import seedu.algobase.model.tag.Tag;
import seedu.algobase.testutil.EditProblemDescriptorBuilder;

public class EditCommandParserTest {

    private static final String TAG_EMPTY = " " + PREFIX_TAG;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE);

    private EditCommandParser parser = new EditCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_NAME_AMY, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", EditCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_NAME_DESC, Name.MESSAGE_CONSTRAINTS); // invalid name
        assertParseFailure(parser, "1" + INVALID_PHONE_DESC, Author.MESSAGE_CONSTRAINTS); // invalid author
        assertParseFailure(parser, "1" + INVALID_EMAIL_DESC, WebLink.MESSAGE_CONSTRAINTS); // invalid weblink
        assertParseFailure(parser, "1" + INVALID_ADDRESS_DESC, Description.MESSAGE_CONSTRAINTS); // invalid description
        assertParseFailure(parser, "1" + INVALID_TAG_DESC, Tag.MESSAGE_CONSTRAINTS); // invalid tag

        // invalid author followed by valid weblink
        assertParseFailure(parser, "1" + INVALID_PHONE_DESC + EMAIL_DESC_AMY, Author.MESSAGE_CONSTRAINTS);

        // valid author followed by invalid author. The test case for invalid author followed by valid author
        // is tested at {@code parse_invalidValueFollowedByValidValue_success()}
        assertParseFailure(parser, "1" + PHONE_DESC_BOB + INVALID_PHONE_DESC, Author.MESSAGE_CONSTRAINTS);

        // while parsing {@code PREFIX_TAG} alone will reset the tags of the {@code Problem} being edited,
        // parsing it together with a valid tag results in error
        assertParseFailure(parser, "1" + TAG_DESC_FRIEND + TAG_DESC_HUSBAND + TAG_EMPTY, Tag.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + TAG_DESC_FRIEND + TAG_EMPTY + TAG_DESC_HUSBAND, Tag.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + TAG_EMPTY + TAG_DESC_FRIEND + TAG_DESC_HUSBAND, Tag.MESSAGE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + INVALID_NAME_DESC + INVALID_EMAIL_DESC + VALID_ADDRESS_AMY + VALID_PHONE_AMY,
                Name.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_PERSON;
        String userInput = targetIndex.getOneBased() + PHONE_DESC_BOB + TAG_DESC_HUSBAND
                + EMAIL_DESC_AMY + ADDRESS_DESC_AMY + NAME_DESC_AMY + TAG_DESC_FRIEND;

        EditProblemDescriptor descriptor = new EditProblemDescriptorBuilder().withName(VALID_NAME_AMY)
                .withAuthor(VALID_PHONE_BOB).withWeblink(VALID_EMAIL_AMY).withDescription(VALID_ADDRESS_AMY)
                .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + PHONE_DESC_BOB + EMAIL_DESC_AMY;

        EditProblemDescriptor descriptor = new EditProblemDescriptorBuilder().withAuthor(VALID_PHONE_BOB)
                .withWeblink(VALID_EMAIL_AMY).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // name
        Index targetIndex = INDEX_THIRD_PERSON;
        String userInput = targetIndex.getOneBased() + NAME_DESC_AMY;
        EditProblemDescriptor descriptor = new EditProblemDescriptorBuilder().withName(VALID_NAME_AMY).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // author
        userInput = targetIndex.getOneBased() + PHONE_DESC_AMY;
        descriptor = new EditProblemDescriptorBuilder().withAuthor(VALID_PHONE_AMY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // weblink
        userInput = targetIndex.getOneBased() + EMAIL_DESC_AMY;
        descriptor = new EditProblemDescriptorBuilder().withWeblink(VALID_EMAIL_AMY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // description
        userInput = targetIndex.getOneBased() + ADDRESS_DESC_AMY;
        descriptor = new EditProblemDescriptorBuilder().withDescription(VALID_ADDRESS_AMY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // tags
        userInput = targetIndex.getOneBased() + TAG_DESC_FRIEND;
        descriptor = new EditProblemDescriptorBuilder().withTags(VALID_TAG_FRIEND).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + PHONE_DESC_AMY + ADDRESS_DESC_AMY + EMAIL_DESC_AMY
                + TAG_DESC_FRIEND + PHONE_DESC_AMY + ADDRESS_DESC_AMY + EMAIL_DESC_AMY + TAG_DESC_FRIEND
                + PHONE_DESC_BOB + ADDRESS_DESC_BOB + EMAIL_DESC_BOB + TAG_DESC_HUSBAND;

        EditProblemDescriptor descriptor = new EditProblemDescriptorBuilder().withAuthor(VALID_PHONE_BOB)
                .withWeblink(VALID_EMAIL_BOB).withDescription(VALID_ADDRESS_BOB).withTags(VALID_TAG_FRIEND, VALID_TAG_HUSBAND)
                .build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() {
        // no other valid values specified
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + INVALID_PHONE_DESC + PHONE_DESC_BOB;
        EditProblemDescriptor descriptor = new EditProblemDescriptorBuilder().withAuthor(VALID_PHONE_BOB).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // other valid values specified
        userInput = targetIndex.getOneBased() + EMAIL_DESC_BOB + INVALID_PHONE_DESC + ADDRESS_DESC_BOB
                + PHONE_DESC_BOB;
        descriptor = new EditProblemDescriptorBuilder().withAuthor(VALID_PHONE_BOB).withWeblink(VALID_EMAIL_BOB)
                .withDescription(VALID_ADDRESS_BOB).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_resetTags_success() {
        Index targetIndex = INDEX_THIRD_PERSON;
        String userInput = targetIndex.getOneBased() + TAG_EMPTY;

        EditProblemDescriptor descriptor = new EditProblemDescriptorBuilder().withTags().build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}