package il.elpisor.lodging.generators;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.thoughtworks.xstream.XStream;
import il.elpisor.lodging.model.GroupData;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class GroupDataGenerator {

    @Parameter(names ="-c", description = "group's count")
    public int count;
    @Parameter(names ="-f", description = "target file")
    public String file;
    @Parameter(names ="-d", description = "data format")
    public String format;

    public static void main(String[] args) throws IOException {
        GroupDataGenerator generator = new GroupDataGenerator();
        JCommander jCommander = new JCommander(generator);

        try{
            jCommander.parse(args);
        }catch (ParameterException pe){
            jCommander.usage();
            return;
        }
        generator.run();
    }

    private void run() throws IOException {
        List<GroupData> groups = generateGroups(count);
        if (format.equals("csv")) {
            saveAsCSV(groups, new File(file));
        }else if(format.equals("xml")){
            saveAsXML(groups, new File(file));
        }else if(format.equals("json")){
            saveAsJSON(groups, new File(file));
        }else{
            System.out.println("Unrecognized format '" + format +"'");
        }
    }

    private void saveAsJSON(List<GroupData> groups, File file) throws IOException {
        //Gson gson = new Gson();
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();
        String json = gson.toJson(groups);
        try (Writer writer = new FileWriter(file)) {
            writer.write(json);
        }
    }

    private void saveAsXML(List<GroupData> groups, File file) throws IOException {
        XStream xStream = new XStream();

        //xStream.alias("group",GroupData.class);
        //xStream.omitField(GroupData.class,"id");
        xStream.processAnnotations(GroupData.class);

        String xml = xStream.toXML(groups);
        try (Writer writer = new FileWriter(file)) {
            writer.write(xml);
        }
    }

    private void saveAsCSV(List<GroupData> groups, File file) throws IOException {
        try(Writer writer = new FileWriter(file)) {
            for (GroupData group : groups) {
                writer.write(String.format("%s;%s;%s\n", group.getName(), group.getHeader(), group.getFooter()));
            }
        }
    }

    private List<GroupData> generateGroups(int count) {
        List<GroupData> groups = new ArrayList<GroupData>();
        for(int i = 0; i < count; i++){
            groups.add(new GroupData().withName(String.format("Name %s", i))
                .withHeader(String.format("Header %s", i))
                    .withFooter(String.format("Footer %s", i)));
        }
        return groups;
    }
}
