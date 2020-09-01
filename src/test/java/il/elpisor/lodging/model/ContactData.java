package il.elpisor.lodging.model;

import com.google.gson.annotations.Expose;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.File;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import static java.util.stream.Collectors.joining;

@Entity
@Table(name = "addressbook")
public class ContactData {
    @Id
    @Column(name = "id")
    private int id;

    @Expose
    @Column(name = "firstname")
    private String firstName;

    @Column(name = "middlename")
    private String middleName;

    @Expose
    @Column(name = "lastname")
    private String lastName;

    @Column(name = "nickname")
    private String nickName;

    @Column(name = "title")
    private String title;

    @Column(name = "company")
    private String companyName;

    @Column(name = "address", columnDefinition = "text")
    private String firstAddress;

    @Column(name = "home", columnDefinition = "text")
    private String telHome;

    @Column(name = "mobile", columnDefinition = "text")
    private String telMobile;

    @Column(name = "work", columnDefinition = "text")
    private String telWork;

    @Column(name = "email", columnDefinition = "text")
    private String firstEMail;

    @Column(name = "email2", columnDefinition = "text")
    private String secondEMail;

    @Column(name = "email3", columnDefinition = "text")
    private String thirdEMail;

    @Column(name = "bday", columnDefinition = "TINYINT")
    private String BDay;

    @Column(name = "bmonth")
    private String BMonth;

    @Column(name = "byear")
    private String BYear;

    @Column(name = "homepage", columnDefinition = "text")
    private String homePage;

    @Column(name = "address2", columnDefinition = "text")
    private String secondAddress;

    @Column(name = "notes", columnDefinition = "text")
    private String notes;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "address_in_groups",
            joinColumns = @JoinColumn(name = "id"),
            inverseJoinColumns = @JoinColumn(name = "group_id")
    )
    private Set<GroupData> groups = new HashSet<GroupData>();

    @Transient //that means HIBERNATE will omit this column
    private String allPhones;
    @Transient
    private String allEMails;

    //@Column(name = "photo", columnDefinition = "text") !!! it does't work
    @Column(name = "photo")
    @Type(type="text")
    private String photo;

    // ------- GETers -----------
    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getNickName() {
        return nickName;
    }

    public String getTitle() {
        return title;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getFirstAddress() {
        return firstAddress;
    }

    public String getTelHome() {
        return telHome;
    }

    public String getTelMobile() {
        return telMobile;
    }

    public String getTelWork() {
        return telWork;
    }

    public String getFirstEMail() {
        return firstEMail;
    }

    public String getSecondEMail() {
        return secondEMail;
    }

    public String getThirdEMail() {
        return thirdEMail;
    }

    public String getBDay() {
        return BDay;
    }

    public String getBMonth() {
        return BMonth;
    }

    public String getBYear() {
        return BYear;
    }

    public String getHomePage() {
        return homePage;
    }

    public String getSecondAddress() {
        return secondAddress;
    }

    public String getNotes() {
        return notes;
    }

    public String getAllPhones() {
        return allPhones;
    }

    public String getAllEMails() {
        return allEMails;
    }

    public File getPhoto() {
        return (this.photo == null || this.photo.equals("")) ? null : new File(photo);
    }

    public Groups getGroups() {
        return new Groups(groups);
    }

    //--------  WITH block
    public ContactData withPhoto(File photo) {
        this.photo = photo.getPath();
        return this;
    }

    public ContactData withId(int id) {
        this.id = id;
        return this;
    }

    public ContactData withGroup(Groups groups) {
        this.groups = new Groups(groups);
        return this;
    }

    public ContactData withFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public ContactData withMiddleName(String middleName) {
        this.middleName = middleName;
        return this;
    }

    public ContactData withLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public ContactData withNickName(String nickName) {
        this.nickName = nickName;
        return this;
    }

    public ContactData withTitle(String title) {
        this.title = title;
        return this;
    }


    public ContactData withCompanyName(String companyName) {
        this.companyName = companyName;
        return this;
    }

    public ContactData withFirstAddress(String firstAddress) {
        this.firstAddress = firstAddress;
        return this;
    }

    public ContactData withTelHome(String telHome) {
        this.telHome = telHome;
        return this;
    }

    public ContactData withTelMobile(String telMobile) {
        this.telMobile = telMobile;
        return this;
    }

    public ContactData withTelWork(String telWork) {
        this.telWork = telWork;
        return this;
    }

    public ContactData withFirstEMail(String firstEMail) {
        this.firstEMail = firstEMail;
        return this;
    }

    public ContactData withSecondEMail(String secondEMail) {
        this.secondEMail = secondEMail;
        return this;
    }

    public ContactData withThirdEMail(String thirdEMail) {
        this.thirdEMail = thirdEMail;
        return this;
    }

    public ContactData withBDay(String BDay) {
        this.BDay = BDay;
        return this;
    }

    public ContactData withBMonth(String BMonth) {
        this.BMonth = BMonth;
        return this;
    }

    public ContactData withBYear(String BYear) {
        this.BYear = BYear;
        return this;
    }

    public ContactData withHomePage(String homePage) {
        this.homePage = homePage;
        return this;
    }

    public ContactData withSecondAddress(String secondAddress) {
        this.secondAddress = secondAddress;
        return this;
    }

    public ContactData withNotes(String notes) {
        this.notes = notes;
        return this;
    }

    public ContactData withAllEMails(){
        this.allEMails =  Arrays.asList(this.firstEMail, this.secondEMail, this.thirdEMail)
                .stream()
                .filter((s)-> (s!=null && !s.equals("")))
                .collect(joining("\n"));
        return this;
    }

    public ContactData withAllPhones(String allPhones) {
        this.allPhones = allPhones;
        return this;
    }

    public ContactData withAllEMails(String allEMails) {
        this.allEMails = allEMails;
        return this;
    }

    public ContactData withAllPhones(){
        this.allPhones = mergePhones(this);
        return this;
    }

    //-------- SETters
    public void setGroups(Set<GroupData> groups) {
        this.groups = groups;
    }

    // ------------  Utils -----------
    private String mergePhones(ContactData c){
        return Arrays.asList(c.getTelHome(),c.getTelMobile(),c.getTelWork())
                .stream()
                .filter((s)-> (s!=null && !s.equals("")))
                .map(this::cleanedSymPhone)
                .collect(joining("\n"));
    }

    private String cleanedSymPhone(String phone){
        return phone.replaceAll("\\s","").replaceAll("-+()","");
    }

    public ContactData withValuesToDbFormat() {
        // Convert JavaInstance to HibernateInstance
            this.photo = nullStringToEmpty(this.photo);
        return
                // -- nullStringToEmpty
                withFirstName(nullStringToEmpty(this.firstName))
                .withMiddleName(nullStringToEmpty(this.middleName))
                .withLastName(nullStringToEmpty(this.lastName))
                .withNickName(nullStringToEmpty(this.nickName))
                .withTitle(nullStringToEmpty(this.title))
                .withCompanyName(nullStringToEmpty(this.companyName))
                .withFirstAddress(nullStringToEmpty(this.firstAddress))
                .withTelHome(nullStringToEmpty(this.telHome))
                .withTelMobile(nullStringToEmpty(this.telMobile))
                .withTelWork(nullStringToEmpty(this.telWork))
                .withFirstEMail(nullStringToEmpty(this.firstEMail))
                .withSecondEMail(nullStringToEmpty(this.secondEMail))
                .withThirdEMail(nullStringToEmpty(this.thirdEMail))
                .withBYear(nullStringToEmpty(this.BYear))
                .withHomePage(nullStringToEmpty(this.homePage))
                .withSecondAddress(nullStringToEmpty(this.secondAddress))
                .withNotes(nullStringToEmpty(this.notes))
                // -- nullStringToZero
                .withBDay(nullStringToZero(this.BDay))
                // -- nullStringToDash
                .withBMonth(nullStringToDash(this.BMonth));
    }

    public String nullStringToEmpty(String s) {
        if (s == null) {
            return "";
        }
        return s;
    }

    public String nullStringToZero(String s) {
        if (s == null) {
            return "0";
        }
        return s;
    }

    public String nullStringToDash(String s) {
        if (s == null) {
            return "-";
        }
        return s;
    }

    public ContactData withValuesToJavaFormat() {
        // Convert HibernateInstance to JavaInstance
        this.photo = emptyStringToNull(this.photo);
        return
                // -- nullStringToEmpty
                withFirstName(emptyStringToNull(this.firstName))
                        .withMiddleName(emptyStringToNull(this.middleName))
                        .withLastName(emptyStringToNull(this.lastName))
                        .withNickName(emptyStringToNull(this.nickName))
                        .withTitle(emptyStringToNull(this.title))
                        .withCompanyName(emptyStringToNull(this.companyName))
                        .withFirstAddress(emptyStringToNull(this.firstAddress))
                        .withTelHome(emptyStringToNull(this.telHome))
                        .withTelMobile(emptyStringToNull(this.telMobile))
                        .withTelWork(emptyStringToNull(this.telWork))
                        .withFirstEMail(emptyStringToNull(this.firstEMail))
                        .withSecondEMail(emptyStringToNull(this.secondEMail))
                        .withThirdEMail(emptyStringToNull(this.thirdEMail))
                        .withBYear(emptyStringToNull(this.BYear))
                        .withHomePage(emptyStringToNull(this.homePage))
                        .withSecondAddress(emptyStringToNull(this.secondAddress))
                        .withNotes(emptyStringToNull(this.notes))
                        // -- nullStringToZero
                        .withBDay(zeroStringToNull(this.BDay))
                        // -- nullStringToDash
                        .withBMonth(dashStringToNull(this.BMonth));
    }

    public String emptyStringToNull(String s) {
        if (s != null && s.length() == 0) {
            return null;
        }
        return s;
    }
    public String zeroStringToNull(String s) {
        if (s != null && s.equals("0")) {
            return null;
        }
        return s;
    }

    public String dashStringToNull(String s) {
        if (s != null && s.equals("-")) {
            return null;
        }
        return s;
    }

    // ------- Override's Block ------------------
    @Override
    public String toString() {
        return "ContactData{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", telMobile='" + telMobile + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ContactData that = (ContactData) o;
        return (id == that.id) &&
                Objects.equals(firstName, that.firstName) &&
                Objects.equals(middleName, that.middleName) &&
                Objects.equals(lastName, that.lastName) &&
                Objects.equals(nickName, that.nickName) &&
                Objects.equals(title, that.title) &&
                Objects.equals(companyName, that.companyName) &&
                Objects.equals(firstAddress, that.firstAddress) &&
                Objects.equals(telHome, that.telHome) &&
                Objects.equals(telMobile, that.telMobile) &&
                Objects.equals(telWork, that.telWork) &&
                Objects.equals(firstEMail, that.firstEMail) &&
                Objects.equals(secondEMail, that.secondEMail) &&
                Objects.equals(thirdEMail, that.thirdEMail) &&
                Objects.equals(BDay, that.BDay) &&
                Objects.equals(BMonth, that.BMonth) &&
                Objects.equals(BYear, that.BYear) &&
                Objects.equals(homePage, that.homePage) &&
                Objects.equals(secondAddress, that.secondAddress) &&
                Objects.equals(notes, that.notes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, middleName, lastName, nickName, title, companyName, firstAddress, telHome, telMobile, telWork, firstEMail, secondEMail, thirdEMail, BDay, BMonth, BYear, homePage, secondAddress, notes);
    }

}
