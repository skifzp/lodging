package il.elpisor.lodging.tests;

import il.elpisor.lodging.model.GroupData;
import il.elpisor.lodging.model.Groups;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class GroupModification extends TestBase {

    @BeforeMethod
    public void ensurePreconditions() {
        app().goTo().groupPage();
        if (app().db().groups().size() == 0) { // if no group exists, we will create same group
            app().group().create(new GroupData().withName("default_group_01"));
        }
    }

    @Test
    public void testGroupModification() {
        Groups before = app().db().groups();
        GroupData beforeGroup = before.iterator().next();
        GroupData newGroup = new GroupData().withId(beforeGroup.getId()).withName("modify_01")
                .withValuesToDbFormat();

        app().group().modify(newGroup);
        Groups after = app().db().groups();

        assertThat(app().group().count(), equalTo(before.size()));
        assertThat(after, equalTo(before.withOut(beforeGroup)));

        // will be started only with -VM_option '-DverifyUI=true'
        verifyGroupListInUI();
    }
}
