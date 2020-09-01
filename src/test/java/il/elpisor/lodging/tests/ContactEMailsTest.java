package il.elpisor.lodging.tests;

import il.elpisor.lodging.model.ContactData;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static java.util.stream.Collectors.joining;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactEMailsTest extends TestBase {

    @BeforeMethod
    public void ensurePreconditions() {
        app().goTo().homePage();
        if (app().db().contacts().size() == 0) {
            // we are creating new default contact
            app().contact().create(new ContactData().withFirstName("DefaultName").withLastName("DefaultLastName")
                    .withFirstEMail("DefaultFirstEMail").withSecondEMail("DefaultSecondEMail"));
        }
    }

    @Test
    public void testContactEMails(){
        ContactData contactHomePage = app().contact().all().iterator().next();
        ContactData contactEditPage = app().contact().infoFromEditForm(contactHomePage);
        assertThat(contactHomePage.getAllEMails(), equalTo(contactEditPage.withAllEMails().getAllEMails()));
    }


}
