package il.elpisor.lodging.tests;

import il.elpisor.lodging.appmanager.AppManager;
import il.elpisor.lodging.model.ContactData;
import il.elpisor.lodging.model.GroupData;
import org.openqa.selenium.remote.BrowserType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestContext;
import org.testng.annotations.*;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

@Listeners(ListenerScreenShots.class)
public class TestBase {

    private Logger logger = LoggerFactory.getLogger(TestBase.class);
    //protected static final AppManager app = new AppManager(System.getProperty("browser",BrowserType.FIREFOX));
    private static ThreadLocal<AppManager> thread = new ThreadLocal<AppManager>();
    //private AppManager app;

    public static ThreadLocal<AppManager> getThreadAppManager() {
        return thread;
    }

    public static AppManager app() {
        return thread.get();
    }

    //@BeforeMethod(alwaysRun = true)
    @BeforeClass(alwaysRun = true)
    public void setUp(ITestContext context) throws Exception {
        //System.out.println("!!!!!!!!!!!!!!!!!!!!! " + Thread.currentThread().getName());
        thread.set(new AppManager(System.getProperty("browser",BrowserType.FIREFOX)));
        app().init();
        context.setAttribute("app",app());
    }

    //@AfterMethod(alwaysRun = true)
    @AfterClass(alwaysRun = true)
    public void tearDown() throws Exception {
        app().stop();
    }

    @BeforeMethod(alwaysRun = true)
    public void logTestStart(Method m, Object[] p){
        logger.info("Strated '" + m.getName() + "' with parameters: " + Arrays.asList(p));
    }

    @AfterMethod(alwaysRun = true)
    public void logTestFinish(Method m, Object[] p){
        logger.info("Finished '" + m.getName() + "' with parameters: " + Arrays.asList(p));
    }

    protected void verifyGroupListInUI() {
        if(Boolean.getBoolean("verifyUI")){
            logger.info("Started verifyGroupListInUI");
            Set<GroupData> groupsUI = app().group().all();
            Set<GroupData> groupsDB =  app().db().groups().stream()
                    .map(
                            (g)->
                                    new GroupData()
                                            .withId(g.getId())
                                            .withName(g.getName())
                    )
                    .collect(Collectors.toSet());

            assertThat(groupsUI,equalTo(groupsDB));
            logger.info("Finished verifyGroupListInUI");
        }
    }

    protected void verifyContactListInUI() {
        if(Boolean.getBoolean("verifyUI")){
            logger.info("Started verifyContactListInUI");
            Set<ContactData> groupsUI = app().contact().all();
            Set<ContactData> groupsDB = app().db().contacts().stream()
                    .map(
                            (g)->
                                    new ContactData()
                                            .withId(g.getId())
                                            .withLastName(g.getLastName())
                                            .withFirstName(g.getFirstName())
                                            .withFirstAddress(g.getFirstAddress())
                                            .withAllEMails()
                                            .withAllPhones()
                    )
                    .collect(Collectors.toSet());
            assertThat(groupsUI, equalTo(groupsDB));
            logger.info("Started verifyContactListInUI");
        }
    }
}
