package il.elpisor.lodging.tests;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import il.elpisor.lodging.model.ContactData;
import il.elpisor.lodging.model.Contacts;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertEquals;

public class ContactCreation extends TestBase {

    @DataProvider
    private Iterator<Object[]> dataProviderFromJsonFile() throws IOException {
        String json = "";
        try (BufferedReader reader = new BufferedReader(new FileReader(new File("src/test/resources/contacts.json")))) {
            String line = reader.readLine();
            while (line != null) {
                json += line;
                line = reader.readLine();
            }
        }
        Gson gson = new Gson();
        Type collectionType = new TypeToken<List<ContactData>>(){}.getType();
        List<ContactData> newContacts = gson.fromJson(json, collectionType);
        return newContacts.stream().map((c)-> new Object[]{c}).collect(Collectors.toList()).iterator();
    }

    @BeforeMethod
    public void ensurePreconditions() {
        app().goTo().homePage();
    }

    @Test(enabled = true, dataProvider = "dataProviderFromJsonFile")
    // if we want to execute in 3 threads 6 times this method:
    //@Test(enabled = true, dataProvider = "dataProviderFromJsonFile", threadPoolSize = 3, invocationCount = 6)
    // and we have to change annotation @BeforeClass to @BeforeMethod in TestBase->setUp(tearDown)
    public void testCreationContact(ContactData newContact) throws Exception {
        Contacts before = app().db().contacts();
        app().contact().create(newContact);
        app().goTo().homePage();
        Contacts after = app().db().contacts();

        newContact
                .withId(
                        after.stream()
                                .max((o1, o2) -> Integer.compare(o1.getId(), o2.getId()))
                                .get().getId()
                );

        assertEquals(after.size(), before.size() + 1);
        assertThat(before.withAdded(newContact.withValuesToDbFormat()), equalTo(after));
    }
}
