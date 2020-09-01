package il.elpisor.lodging.tests;

import il.elpisor.lodging.model.ContactData;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

public class HBConnectionTest {

    SessionFactory sessionFactory;

    @BeforeMethod
    protected void setUp() throws Exception {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure() // configures settings from hibernate.cfg.xml
                .applySetting("hibernate.connection.url", "jdbc:mysql://localhost:3306/addressbook")
                .applySetting("hibernate.connection.username", "root")
                .applySetting("hibernate.connection.password", "")
                .build();
        try {
            sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        } catch (Exception e) {
            e.printStackTrace();
            // The registry would be destroyed by the SessionFactory, but we had trouble building the SessionFactory
            // so destroy it manually.
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }

    @Test
    public void testHBConnection() {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        List<ContactData> results = null;
        try {
            tx = session.beginTransaction();
            results = session.createQuery("from ContactData where deprecated = '0000-00-00'").list();
            //List<GroupData> results = session.createQuery("FROM GroupData").list();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }

        for (ContactData res : results) {
            //for (GroupData res : results) {
            System.out.println(res);
            System.out.println(res.getGroups());
        }
    }
}
