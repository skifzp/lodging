package il.elpisor.lodging.tests;

import il.elpisor.lodging.model.GroupData;
import il.elpisor.lodging.model.Groups;
import org.hamcrest.CoreMatchers;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class GroupDelete extends TestBase {

    @BeforeMethod
    public void ensurePreconditions() {
        app().goTo().groupPage();
        if (app().db().groups().size() == 0) { // if no group exists, we will create a default group
            app().group().create(new GroupData().withName("default_group_01"));
        }
    }

    @Test
    public void testDeleteGroup() throws Exception {
        Groups before = app().db().groups();
        GroupData deletedGroup = before.iterator().next();
        app().group().delete(deletedGroup);
        Groups after = app().db().groups();

        assertThat(app().group().count(), equalTo(before.size() - 1));
        assertThat(after, CoreMatchers.equalTo(before.withOut(deletedGroup)));
    }

}
