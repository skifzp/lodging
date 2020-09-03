package il.elpisor.lodging.tests;

import il.elpisor.lodging.model.ContactData;
import il.elpisor.lodging.model.Contacts;
import il.elpisor.lodging.model.GroupData;
import il.elpisor.lodging.model.Groups;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.lang.Math.abs;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class JoinGroupToContact extends TestBase {
    private GroupData newGroup = null;

    @BeforeMethod
    public void ensurePreconditions() {
        isThereGroup(); //if there is no group
        isThereContact(); //if there is no contact
        app().goTo().homePage();
    }

    @Test
    public void contactToGroup() {
        GroupData groupToJoin;
        Contacts contactsDB = app().db().contacts();
        ContactData contact = contactsDB.iterator().next();
        Groups groupsUI = app().contact().groupsFromHomePage();

        // JOIN 'groupToJoin' to 'contact'
        groupToJoin = groupsUI.iterator().next();
        // but if 'contact.getGroups().size() != 0' then we need redefine 'groupToJoin'
        //      - we find the groups that is't joined with the 'contact'
        //      OR
        //      - if the 'contact' already joined to all existing groups, we need to create a new group
        if (contact.getGroups().size() != 0) {
            // we keep the groups that is't joined with contact
            groupsUI.removeAll(contact.getGroups().stream()
                    .map((g) -> g.withFooter("").withHeader(""))
                    .collect(Collectors.toSet()));

            if (groupsUI.size() == 0) { // contact joined to all existing groups, so we need to create a new group
                groupToJoin = this.newGroup;
                app().goTo().groupPage();
                app().group().create(groupToJoin);
                groupToJoin
                        .withId(
                            app().db().groups()
                            .stream()
                            .max((g1, g2) -> Integer.compare(g1.getId(), g2.getId()))
                            .get().getId()
                        );
                app().goTo().homePage();
            }
        }

        // join 'contact' to Group 'groupToJoin'
        app().contact().joinContactToGroup(contact, groupToJoin);

        // checking what was done above
        ContactData joinedContact = app().db().contacts().stream().filter((c) -> c.getId() == contact.getId()).findFirst().get();

        assertThat(
                contact.getGroups().withAdded(groupToJoin),
                equalTo(
                        joinedContact.getGroups()
                                .stream()
                                .map((g) -> g.withFooter("").withHeader(""))
                                .collect(Collectors.toSet())
                )
        );
    }

    private void isThereGroup() {
        Groups groupsDB = app().db().groups();
        this.newGroup = genGroup();

        if (groupsDB.size() > 0) {
            // create a new group in case the selected contact is joined to all existing groups
            setUniqueGroup(groupsDB);
        } else if (groupsDB.size() == 0) { // if no group exists, we will create some group
            app().goTo().groupPage();
            app().group().create(this.newGroup);
            app().goTo().homePage();
        }

        //check UI>-<Db
        assertThat(app().contact().groupsFromHomePage(),
                equalTo(
                        app().db().groups().stream().map((g) -> g.withFooter("").withHeader("")).collect(Collectors.toSet())));
    }

    private void setUniqueGroup(Groups groups) {
        while (groups.contains(this.newGroup)) {
            this.newGroup = genGroup();
        }
    }

    private void isThereContact() {
        app().goTo().homePage();
        if (app().db().contacts().size() == 0) {
            // we are creating new default contact
            app().contact().create(
                    new ContactData().withFirstName("FirstName_" + genNum()).withLastName("DefaultLastName"));
        }
    }

    private GroupData genGroup() {
        return new GroupData().withName("group_name_" + genNum()).withFooter("").withHeader("");
    }

    private int genNum() {
        return Stream.generate(new Random()::nextInt).limit(1).map((i) -> abs(i)).iterator().next();
    }

}

