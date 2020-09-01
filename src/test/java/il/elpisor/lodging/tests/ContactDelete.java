package il.elpisor.lodging.tests;

import il.elpisor.lodging.model.ContactData;
import il.elpisor.lodging.model.Contacts;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactDelete extends TestBase {
    @BeforeMethod
    public void ensurePreconditions() {
        app().goTo().homePage();
        if (app().db().contacts().size() == 0) {
            // we are creating new default contact
            app().contact().create(new ContactData().withFirstName("DefaultName").withLastName("DefaultLastName"));
        }
    }

    @Test()
    public void testContactDelete() {
        Contacts before = app().db().contacts();
        ContactData deletedContact = before.iterator().next();
        app().contact().delete(deletedContact);
        Contacts after = app().db().contacts();

        assertThat(after.size(), equalTo(before.size() - 1));
        assertThat(after, equalTo(before.withOut(deletedContact)));
    }
}
