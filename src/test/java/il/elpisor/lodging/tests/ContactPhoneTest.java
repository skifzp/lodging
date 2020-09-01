package il.elpisor.lodging.tests;

import il.elpisor.lodging.model.ContactData;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static java.util.stream.Collectors.joining;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactPhoneTest extends TestBase {
    @BeforeMethod
    public void ensurePreconditions() {
        app().goTo().homePage();
        if (app().db().contacts().size() == 0) {
            // we are creating new default contact
            app().contact().create(new ContactData().withFirstName("DefaultName").withLastName("DefaultLastName")
            .withTelHome("111").withTelMobile("222").withTelWork("333"));
        }
    }

    @Test(enabled = true)
    public void testContactPhones(){
        ContactData contact = app().contact().all().iterator().next();
        ContactData contactFromMainForm = new ContactData().withId(contact.getId()).withFirstName(contact.getFirstName())
                .withLastName(contact.getLastName()).withAllPhones(contact.getAllPhones());
        ContactData contactFromEditForm = app().contact().infoFromEditForm(contactFromMainForm);

        assertThat(contactFromMainForm.getAllPhones(), equalTo(contactFromEditForm.withAllPhones().getAllPhones()));
    }


}
