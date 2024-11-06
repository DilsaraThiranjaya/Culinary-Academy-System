package lk.ijse.cas.util;

import lk.ijse.cas.entity.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.IOException;
import java.util.Properties;

public class SessionFactoryConfig {
    private static SessionFactoryConfig sessionFactoryConfig;
    private final SessionFactory sessionFactory;

    private SessionFactoryConfig() {
        Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(Student.class);
        configuration.addAnnotatedClass(Course.class);
        configuration.addAnnotatedClass(Payment.class);
        configuration.addAnnotatedClass(CourseDetails.class);
        configuration.addAnnotatedClass(PaymentDetails.class);
        configuration.addAnnotatedClass(User.class);

        Properties properties = new Properties();
        try {
            properties.load(ClassLoader.getSystemClassLoader().getResourceAsStream("hibernate.properties"));
        } catch (IOException e) {
            System.out.println("file not found");
        }
        configuration.mergeProperties(properties);

        sessionFactory = configuration.buildSessionFactory();
    }

    public static SessionFactoryConfig getInstance() {
        return (sessionFactoryConfig == null) ?
                sessionFactoryConfig = new SessionFactoryConfig() :
                sessionFactoryConfig;
    }

    public final Session getSession(){
        return sessionFactory.openSession();
    }

}
