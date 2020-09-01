package il.elpisor.lodging.tests;

import il.elpisor.lodging.model.ContactData;
import il.elpisor.lodging.model.GroupData;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Set;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class UnJoinGroupFromContactTest extends TestBase {

    @BeforeMethod
    public void ensurePreconditions() {
        Set<GroupData> groups =
                app().db().groups().stream().filter((g) -> g.getContacts().size() > 0).collect(Collectors.toSet());
        GroupData group;
        ContactData contact;

        if (groups.size() == 0) { // there is't group with joined contact
            if (app().db().contacts().size() == 0) { // there is't any contact
                contact = new ContactData().withFirstName("gen_contact_name");
                app().goTo().homePage();
                app().contact().create(contact);
            }
            contact = app().db().contacts().iterator().next();

            if (app().db().groups().size() == 0) { // there is't any group
                group = new GroupData().withName("gen_group_name");
                app().goTo().groupPage();
                app().group().create(group);
            }
            group = app().db().groups().iterator().next();

            app().goTo().homePage();

            app().contact().joinContactToGroup(contact, group);
        }
        app().goTo().homePage();
    }

    @Test
    public void testUnJoinGroupFromContact() {

        GroupData groupBefore = app().db().groups()
                .stream().filter((g) -> g.getContacts().size() > 0).findFirst().get();
        //System.out.println(groupBefore);
        ContactData contactBefore = groupBefore.getContacts().iterator().next();
        app().contact().unJoinGroup(groupBefore, contactBefore);

        // check
        ContactData contactAfter = app().db().contactById(contactBefore.getId());
        assertThat(
                contactAfter.getGroups().withAdded(groupBefore),
                equalTo(contactBefore.getGroups())
        );
    }

}
