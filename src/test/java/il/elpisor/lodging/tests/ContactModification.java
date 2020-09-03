package il.elpisor.lodging.tests;

import il.elpisor.lodging.model.ContactData;
import il.elpisor.lodging.model.Contacts;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactModification extends TestBase {

    @BeforeMethod
    public void ensurePreconditions() {
        app().goTo().homePage();
        if (app().db().contacts().size() == 0) {
            // we are creating new default contact
            app().contact().create(new ContactData().withFirstName("DefaultName").withLastName("DefaultLastName"));
        }
    }

    @Test
    public void testContactModification() {
        Contacts before = app().db().contacts();
        ContactData beforeContact = before.iterator().next();
        ContactData newContact = new ContactData().withFirstName("Modify_FirstName01")
                .withMiddleName("_MiddleName01").withLastName("_LastName01")
                .withId(beforeContact.getId())
                .withValuesToDbFormat();

        app().contact().modifyContact(newContact);
        Contacts after = app().db().contacts();
        app().goTo().homePage();

        assertThat(app().contact().all().size(), equalTo(before.size()));
        assertThat(after, equalTo(before.withOut(beforeContact).withAdded(newContact)));

        verifyContactListInUI();
    }
}
