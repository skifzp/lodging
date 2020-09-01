package il.elpisor.lodging.tests;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.thoughtworks.xstream.XStream;
import il.elpisor.lodging.model.GroupData;
import il.elpisor.lodging.model.Groups;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class GroupCreation extends TestBase {

    @DataProvider
    public Iterator<Object[]> validGroupsManually(){
        List<Object[]> list = new ArrayList<Object[]>();
        list.add( new Object[] {new GroupData().withName("gen_name 1").withHeader("header 1").withFooter("footer 1")});
        list.add( new Object[] {new GroupData().withName("gen_name 2").withHeader("header 2").withFooter("footer 2")});
        return list.iterator();
    }

    @DataProvider
    public Iterator<Object[]> validGroupsFromCSVFile() throws IOException {
        List<Object[]> list = new ArrayList<Object[]>();
        try(BufferedReader reader =
                    new BufferedReader(new FileReader((new File("src/test/resources/groups.csv"))))) {
            String line = reader.readLine();
            while (line != null) {
                String[] group = line.split(";");
                list.add(new Object[]{new GroupData().withName(group[0]).withHeader(group[1]).withFooter(group[2])});
                line = reader.readLine();
            }
        }
        return list.iterator();
    }

    @DataProvider
    public Iterator<Object[]> validGroupsFromXMLFile() throws IOException {
        List<Object[]> list = new ArrayList<Object[]>();
        String xml = "";
        try (BufferedReader reader =
                     new BufferedReader(new FileReader((new File("src/test/resources/groups.xml"))))) {
            String line = reader.readLine();
            while (line != null) {
                xml += line;
                line = reader.readLine();
            }
        }
        XStream xStream = new XStream();

        //xStream.alias("group",GroupData.class);
        //xStream.omitField(GroupData.class,"id");
        xStream.processAnnotations(GroupData.class);

        List<GroupData> groups = (List<GroupData>) xStream.fromXML(xml);
        return groups.stream().map((g)-> new Object[] {g}).collect(Collectors.toList()).iterator();
    }

    @DataProvider
    public Iterator<Object[]> validGroupsFromJsonFile() throws IOException {
        List<Object[]> list = new ArrayList<Object[]>();
        String json = "";
        try(BufferedReader reader =
                    new BufferedReader(new FileReader((new File("src/test/resources/groups.json"))))) {
            String line = reader.readLine();
            while (line != null) {
                json += line;
                line = reader.readLine();
            }
        }
        Gson gson = new Gson();
        Type collectionType = new TypeToken<List<GroupData>>(){}.getType();
        List<GroupData> groups = gson.fromJson(json, collectionType);

        return groups.stream().map((g)-> new Object[] {g}).collect(Collectors.toList()).iterator();
    }

    @BeforeMethod
    public void ensurePreconditions() {
        app().goTo().groupPage();
    }

    @Test(enabled = true, dataProvider = "validGroupsFromJsonFile")
    public void testGroupCreation(GroupData group) throws Exception {
        Groups before = app().db().groups();
        app().group().create(group);
        assertThat(app().group().count(), equalTo(before.size() + 1));

        Groups after = app().db().groups();
        group.withId(after.stream()
                .mapToInt((g) -> g.getId())
                .max().getAsInt());
        assertThat(after, equalTo(before.withAdded(group)));
    }

    @Test(enabled = false)
    public void testBadGroupCreation() throws Exception {
        GroupData group = new GroupData().withName("name'05");

        Groups before = app().group().all();
        app().group().create(group);

        assertThat(app().group().count(), equalTo(before.size()));

        Groups after = app().group().all();
        group.withId(after.stream().mapToInt((g) -> g.getId()).max().getAsInt());
        assertThat(after, equalTo(before));
    }

}
