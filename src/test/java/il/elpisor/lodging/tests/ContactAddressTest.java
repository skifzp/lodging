package il.elpisor.lodging.tests;

import il.elpisor.lodging.model.ContactData;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactAddressTest extends TestBase {

    @BeforeMethod
    public void ensurePreconditions() {
        app().goTo().homePage();
        if (app().db().contacts().size() == 0) {
            // we are creating new default contact
            app().contact().create(new ContactData().withFirstName("DefaultName").withLastName("DefaultLastName")
                    .withFirstAddress("DefaultFirstAddress: City, Street, Building, Apartment, Index, Country"));
        }
    }

    @Test
    public void testContactAddress(){
        ContactData contactHomePage = app().db().contacts().iterator().next();
        ContactData contactEditPage = app().contact().infoFromEditForm(contactHomePage);

        assertThat(contactHomePage.getFirstAddress(), equalTo(contactEditPage.getFirstAddress()));
    }
}
