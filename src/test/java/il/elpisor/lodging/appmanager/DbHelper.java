package il.elpisor.lodging.appmanager;

import il.elpisor.lodging.model.ContactData;
import il.elpisor.lodging.model.Contacts;
import il.elpisor.lodging.model.GroupData;
import il.elpisor.lodging.model.Groups;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.List;
import java.util.Properties;

public class DbHelper {
    private final SessionFactory sessionFactory;

    public DbHelper(Properties properties) {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure() // configures settings from hibernate.cfg.xml
                .applySetting("hibernate.connection.url", properties.getProperty("hibernate.connection.url"))
                .applySetting("hibernate.connection.username", properties.getProperty("hibernate.connection.username"))
                .applySetting("hibernate.connection.password", properties.getProperty("hibernate.connection.password"))
                .applySetting("hibernate.jdbc.time_zone", properties.getProperty("hibernate.jdbc.time_zone"))
                .build();
        sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
    }

    public Groups groups() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List<GroupData> results = session.createQuery("from GroupData").list();
        session.getTransaction().commit();
        session.close();
        return new Groups(results);
    }

    public Contacts contacts() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List<ContactData> results = session.createQuery("from ContactData where deprecated = '0000-00-00'").list();
        session.getTransaction().commit();
        session.close();
        return new Contacts(results);
    }

    public ContactData contactById(int id){
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        ContactData result = (ContactData) session.createQuery("from ContactData where id='" + id + "'").uniqueResult();
        session.getTransaction().commit();
        session.close();
        return result;
    }

    public void stop(){
        try{
            sessionFactory.close();
        }catch (Exception e){}

    }
}
