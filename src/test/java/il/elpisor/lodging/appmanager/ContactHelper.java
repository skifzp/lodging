package il.elpisor.lodging.appmanager;

import il.elpisor.lodging.model.ContactData;
import il.elpisor.lodging.model.Contacts;
import il.elpisor.lodging.model.GroupData;
import il.elpisor.lodging.model.Groups;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import java.util.List;

public class ContactHelper extends HelperBase {

    public ContactHelper(WebDriver wd) {
        super(wd);
    }

    public void goToContactList() {
        click(By.linkText("home"));
    }

    public void submitContactAdd() {
        click(By.xpath("(//input[@name='submit'])[2]"));
    }

    public void fillContactAdd(ContactData contact, boolean creation) {
        type(By.name("firstname"), contact.getFirstName());
        type(By.name("middlename"), contact.getMiddleName());
        type(By.name("lastname"), contact.getLastName());
        type(By.name("nickname"), contact.getNickName());
        type(By.name("title"), contact.getTitle());
        type(By.name("company"), contact.getCompanyName());
        type(By.name("address"), contact.getFirstAddress());
        type(By.name("home"), contact.getTelHome());
        type(By.name("mobile"), contact.getTelMobile());
        type(By.name("work"), contact.getTelWork());
        type(By.name("email"), contact.getFirstEMail());
        type(By.name("email2"), contact.getSecondEMail());
        attach(By.name("photo"), contact.getPhoto());

        if (contact.getBDay() != null && !contact.getBDay().equals("") && !contact.getBDay().equals("0")) {
            new Select(wd.findElement(By.name("bday"))).selectByVisibleText(contact.getBDay());
        }

        if (contact.getBMonth() != null && !contact.getBMonth().equals("") && !contact.getBMonth().equals("-")) {
            new Select(wd.findElement(By.name("bmonth"))).selectByVisibleText(contact.getBMonth());
        }

        type(By.name("byear"), contact.getBYear());
        type(By.name("homepage"), contact.getHomePage());
        type(By.name("address2"), contact.getSecondAddress());
        type(By.name("notes"), contact.getNotes());

        if (creation) { //form for creation contact
            /*if (contact.getGroup() != null) {
                if (isThereGroup(By.xpath("//select[@name='new_group'][.='" + contact.getGroup() + "']"))) {
                    new Select(wd.findElement(By.name("new_group"))).selectByVisibleText(contact.getGroup());
                } else {
                    System.out.println("Attention! No match group='" + contact.getGroup() + "' in SelectElement");
                }
            }
            */
        } else { //form for modification contact, that is, there should not be this tag NEW_GROUP
            Assert.assertFalse(isElementPresent(By.name("new_group")));
        }

    }

    private boolean isThereGroup(By locator) {
        return isElementPresent(locator);
    }

    public void initContactAdd() {
        click(By.linkText("add new"));
    }

    public void deleteContact() {
        click(By.xpath("//input[@value='Delete']"));
        closeAlertWindow();
        goToContactList();
    }

    public void delete(ContactData contact) {
        goToContactList();
        selectContactById(contact.getId());
        click(By.xpath("//input[@value='Delete']"));
        closeAlertWindow();
        goToContactList();
    }

    public void initContactModify() {
        click(By.xpath("//img[@alt='Edit']"));
    }

    public void initContactModify(int index) {
        click(By.xpath("(//img[@alt='Edit'])[" + (index + 1) + "]"));
        //wd.findElements(By.name("entry")).get(index).findElement(By.xpath("td[8]/a")).click();
    }

    private void initContactModifyById(int id) {
        click(By.xpath("//a[@href='edit.php?id=" + id + "']"));
    }

    public void submitContactModify() {
        click(By.xpath("(//input[@name='update'])[2]"));
    }

    /*
    public boolean isThereContact() {
        return isElementPresent(By.name("selected[]"));
    }
    */
    public void create(ContactData contact) {
        goToContactList();
        initContactAdd();
        fillContactAdd(contact, true);
        submitContactAdd();
        goToContactList();
    }

    public void modifyContact(ContactData contact) {
        goToContactList();
        //thereIsNoContact();
        selectContactById(contact.getId());
        initContactModifyById(contact.getId());
        fillContactAdd(contact, false);
        submitContactModify();
        goToContactList();
    }


    public Contacts all() {
        int id;
        String firstName, lastName, phones, addressFirst, emails;

        Contacts contacts = new Contacts();

        List<WebElement> elements = wd.findElements(By.name("entry"));
        for (WebElement element : elements) {
            id = Integer.parseInt(element.findElement(By.tagName("input")).getAttribute("value"));
            lastName = element.findElement(By.xpath("td[2]")).getText();
            firstName = element.findElement(By.xpath("td[3]")).getText();
            addressFirst = element.findElement(By.xpath("td[4]")).getText();
            //phones = element.findElement(By.xpath("td[6]")).getText().split("\n");
            phones = element.findElement(By.xpath("td[6]")).getText();
            emails = element.findElement(By.xpath("td[5]")).getText();
            contacts.add(new ContactData().withId(id).withFirstName(firstName).withLastName(lastName)
                    .withAllPhones(phones).withFirstAddress(addressFirst).withAllEMails(emails));
        }
        return contacts;
    }

    public void selectContact() {
        click(By.name("selected[]"));
    }

    public void selectContactByIndex(int index) {
        wd.findElements(By.name("selected[]")).get(index).click();
    }

    public void selectContactById(int id) {
        click(By.cssSelector("input[value='" + id + "']"));
    }

    public void selectGroupById(int id){
        click(By.cssSelector("select[name='to_group']>option[value='" +id+ "']"));
    }

    public ContactData infoFromEditForm(ContactData contact) {
        String firstName, lastName, homeTel, mobileTel, workTel, addressFirst, emailFirst, emailSecond, emailThird;
        initContactModifyById(contact.getId());
        firstName = wd.findElement(By.name("firstname")).getAttribute("value");
        lastName = wd.findElement(By.name("lastname")).getAttribute("value");
        homeTel = wd.findElement(By.name("home")).getAttribute("value");
        mobileTel = wd.findElement(By.name("mobile")).getAttribute("value");
        workTel = wd.findElement(By.name("work")).getAttribute("value");
        addressFirst = wd.findElement(By.cssSelector("textarea[name='address']")).getText();
        emailFirst = wd.findElement(By.cssSelector("input[name='email']")).getAttribute("value");
        emailSecond = wd.findElement(By.cssSelector("input[name='email2']")).getAttribute("value");
        emailThird = wd.findElement(By.cssSelector("input[name='email3']")).getAttribute("value");
        wd.navigate().back();
        return new ContactData().withId(contact.getId()).withFirstName(firstName).withLastName(lastName)
            .withTelHome(homeTel).withTelMobile(mobileTel).withTelWork(workTel).withFirstAddress(addressFirst)
                .withFirstEMail(emailFirst).withSecondEMail(emailSecond).withThirdEMail(emailThird);
    }

    public Groups groupsFromHomePage(){
        Groups groups = new Groups();
        List<WebElement> elements = wd.findElements(By.cssSelector("select[name='to_group']>option"));
        for (WebElement element : elements) {
            int id = Integer.parseInt(element.getAttribute("value"));
            String name = element.getText();
            groups.add(new GroupData().withId(id).withName(name).withValuesToDbFormat());
        }
        return new Groups(groups);
    }

    public void joinContactToGroup(ContactData contact, GroupData group) {
        selectContactById(contact.getId());
        selectGroupById(group.getId());
        click(By.cssSelector("input[name='add']"));
        goToContactList();
    }

    public void unJoinGroup(GroupData group, ContactData contact) {
        if(group == null || group.getContacts() == null){
            throw new Error("group == null || group.getContacts() == null");
        }
        // set group filter
        click(By.cssSelector("select[name='group']>option[value='" + group.getId() + "']"));
        //select the contact
        selectContactById(contact.getId());
        //unJoin the contact from group that we set on filter
        click(By.cssSelector("input[name='remove']"));

        goToContactList();
    }
}
