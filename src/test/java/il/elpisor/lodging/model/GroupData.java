package il.elpisor.lodging.model;

import com.google.gson.annotations.Expose;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@XStreamAlias("group")
@Entity
@Table(name = "group_list")
public class GroupData {

    @XStreamOmitField
    @Id
    @Column(name = "group_id")
    private int id = Integer.MAX_VALUE;

    @Expose
    @Column(name = "group_name")
    private String name;

    @Expose
    @Column(name = "group_header")
    @Type(type = "text")
    private String header;

    @Expose
    @Column(name = "group_footer")
    @Type(type = "text")
    private String footer;

    @ManyToMany(mappedBy = "groups", fetch = FetchType.EAGER)
    private Set<ContactData> contacts = new HashSet<ContactData>();

    //-----------   WITH's  -------------------
    public GroupData withId(int id) {
        this.id = id;
        return this;
    }

    public GroupData withName(String name) {
        this.name = name;
        return this;
    }

    public GroupData withHeader(String header) {
        this.header = header;
        return this;
    }

    public GroupData withFooter(String footer) {
        this.footer = footer;
        return this;
    }

    // ---------   GETERs ----------------------
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getHeader() {
        return header;
    }

    public String getFooter() {
        return footer;
    }

    public Contacts getContacts() {
        return new Contacts(contacts);
    }

    // ------------  Utils -----------

    public GroupData withValuesToDbFormat() {
        // Convert HibernateInstance to JavaInstance
        return withName(nullStringToEmpty(this.name))
                .withFooter(nullStringToEmpty(this.footer)).withHeader(nullStringToEmpty(this.header));
    }

    public String nullStringToEmpty(String s) {
        if (s == null) {
            return "";
        }
        return s;
    }

    public GroupData withValuesToJavaFormat() {
        // Convert HibernateInstance to JavaInstance
        return withName(emptyStringToNull(this.name))
                .withHeader(emptyStringToNull(this.header)).withFooter(emptyStringToNull(this.footer));
    }

    public String emptyStringToNull(String s) {
        if (s != null && s.length() == 0) {
            return null;
        }
        return s;
    }

    // ------------  OVERRIDEs -----------
    @Override
    public String toString() {
        return "GroupData{" +
                "idGroup='" + id + '\'' +
                ", nameGroup='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GroupData groupData = (GroupData) o;
        return id == groupData.id &&
                Objects.equals(name, groupData.name) &&
                Objects.equals(header, groupData.header) &&
                Objects.equals(footer, groupData.footer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, header, footer);
    }

}
