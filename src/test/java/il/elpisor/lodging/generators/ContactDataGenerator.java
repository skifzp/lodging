package il.elpisor.lodging.generators;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import il.elpisor.lodging.model.ContactData;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class ContactDataGenerator {

    @Parameter(names ="-c", description = "group's count")
    public int count;
    @Parameter(names ="-f", description = "target file")
    public String file;
    @Parameter(names ="-d", description = "data format")
    public String format;

    public static void main(String[] args) throws IOException {
        ContactDataGenerator contactDataGenerator = new ContactDataGenerator();
        JCommander jCommander = new JCommander().newBuilder()
                .addObject(contactDataGenerator).build();

        try{
            jCommander.parse(args);
        }catch (ParameterException pe){
            jCommander.usage();
            return;
        }

        contactDataGenerator.run();
    }

    public void run() throws IOException {
        if(format.equals("json")) {
            saveAsJsonFile(genContacts(), new File(file));
        }else{
            System.out.println("Unrecognized format '" + format +"'");
        }
    }

    private static void saveAsJsonFile(List<ContactData> contacts, File file) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create();
        try(Writer writer = new FileWriter(file);) {
            String json = gson.toJson(contacts);
            writer.write(json);
        }
    }

    private List<ContactData> genContacts(){
        List<ContactData> contacts = new ArrayList<ContactData>();
        for (int i=0; i<count ; i++){
            contacts.add(new ContactData().withFirstName("gen_first_name " + i).withLastName("gen_last_name "+ i));
            /*File photo = new File("src/test/resources/stru.png");
                .withFirstName("First").withMiddleName("Middle").withLastName("Last")
                .withNickName("NickName01").withTitle("Title01").withCompanyName("Company01")
                .withFirstAddress("01: City, Street, Building, Apartment, Index, Country")
                .withTelHome("TelHome01").withTelMobile("TelMobile01").withTelWork("Tel Work01")
                .withFirstEMail("EMail01").withSecondEMail("EMail01_2")
                .withBDay("22").withBMonth("January").withBYear("2002")
                .withHomePage("HomePage01")
                .withSecondAddress("01 SecondAddress: City, Street, Building, Apartment, Index, Country")
                .withNotes("Notes01").withGroup("name03").withPhoto(photo); */
        }
        return contacts;
    }
}
