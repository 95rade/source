package swip.ch16table.tests;

import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import swip.ch15pageflow.framework.Browser;
import swip.ch15pageflow.framework.BrowserRunner;
import swip.ch16table.domain.Person;
import swip.ch16table.v1.PersonTable;
import swip.ch16table.v1.PersonTableContents;

import javax.inject.Inject;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static swip.ch15pageflow.locators.TagName.TABLE;

@RunWith(BrowserRunner.class)
public class PersonTableV1IT {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    @Inject
    private Browser browser;

    public static final PersonTableContents EXPECTED =
        new PersonTableContents(
            Arrays.asList("Id", "First Name", "Last Name", "Age"),
            Arrays.asList(
                new Person(1, "Eve", "Jackson", 94)
                , new Person(2, "John", "Doe", 80)
                , new Person(3, "Adam", "Johnson", 67)
                , new Person(4, "Jill", "Smith", 50)
            )
        );

    @Test
    public void testReadFromPersonTable() {

        PersonTable table = new PersonTable(browser.untilFound(TABLE));

        PersonTableContents actual = table.getContents();

        assertEquals(EXPECTED.describeDiff(actual), EXPECTED, actual);
    }

    public static final PersonTableContents OUTDATED_EXPECTED =
        new PersonTableContents(
            Arrays.asList("Id", "First Name", "Last Name", "Age"),
            Arrays.asList(
                new Person(1, "Eve", "Jackson", 94)
                , new Person(2, "John", "Doe", 80)
                , new Person(4, "Jill", "Smith", 50)
                , new Person(5, "Jack", "Clyde", 78)
            )
        );

    @Test
    @Ignore("You can remove this to run it and check the output")
    public void testReadFromPersonTableButFailed() {

        PersonTable table = new PersonTable(browser.untilFound(TABLE));

        PersonTableContents actual = table.getContents();

        assertEquals(OUTDATED_EXPECTED.describeDiff(actual), OUTDATED_EXPECTED, actual);
    }

}
