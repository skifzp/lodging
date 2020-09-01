package il.elpisor.lodging.appmanager;

import il.elpisor.lodging.model.GroupData;
import il.elpisor.lodging.model.Groups;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class GroupHelper extends HelperBase {
    private Groups groupsCache;

    public GroupHelper(WebDriver wd) {
        super(wd);
    }

    public void returnGroupPage() {
        click(By.linkText("groups"));
    }

    public void submitGroupCreation() {
        click(By.name("submit"));
    }

    public void fillGroupForm(GroupData groupData) {
        type(By.name("group_name"), (groupData.getName() == null) ? "" : groupData.getName());
        type(By.name("group_header"), (groupData.getHeader() == null) ? "": groupData.getHeader());
        type(By.name("group_footer"), (groupData.getFooter() == null) ? "": groupData.getFooter());
    }

    public void initGroupCreation() {
        click(By.name("new"));
    }

    public void delete(GroupData group) {
        selectGroupById(group.getId());
        deleteSelectedGroups();
        groupsCache = null;
        returnGroupPage();
    }

    private void selectGroupById(int id) {
        wd.findElement(By.cssSelector("input[value='" + id + "']")).click();
    }

    public void deleteSelectedGroups() {
        click(By.name("delete"));
    }

    public void initGroupModify() {
        click(By.name("edit"));
    }

    public void submitGroupModify() {
        click(By.name("update"));
    }

    public void create(GroupData groupData) {
        initGroupCreation();
        fillGroupForm(groupData);
        submitGroupCreation();
        groupsCache = null;
        returnGroupPage();
    }

    public void modify(GroupData group) {
        selectGroupById(group.getId());
        initGroupModify();
        fillGroupForm(group);
        submitGroupModify();
        groupsCache = null;
        returnGroupPage();
    }

    public Groups all() {
        int id;
        String name;

        if (groupsCache != null){ return new Groups(groupsCache); }

        groupsCache = new Groups();
        List<WebElement> elements = wd.findElements(By.cssSelector("span.group"));
        for (WebElement element : elements) {
            id = Integer.parseInt(element.findElement(By.tagName("input")).getAttribute("value"));
            name = element.getText();
            groupsCache.add(new GroupData().withId(id).withName(name));
        }
        return new Groups(groupsCache);
    }

    public int count(){
        return wd.findElements(By.name("selected[]")).size();
    }

}
