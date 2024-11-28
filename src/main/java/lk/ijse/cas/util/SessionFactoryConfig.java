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
        try {
            Properties properties = new Properties();
            properties.load(ClassLoader.getSystemClassLoader().getResourceAsStream("hibernate.properties"));

            Configuration configuration = new Configuration();
            // Add properties before adding annotated classes
            configuration.setProperties(properties);

            // Add annotated classes
            configuration.addAnnotatedClass(Student.class);
            configuration.addAnnotatedClass(Course.class);
            configuration.addAnnotatedClass(Payment.class);
            configuration.addAnnotatedClass(CourseDetails.class);
            configuration.addAnnotatedClass(PaymentDetails.class);
            configuration.addAnnotatedClass(User.class);

            sessionFactory = configuration.buildSessionFactory();
        } catch (IOException e) {
            System.out.println("Configuration Error: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public static SessionFactoryConfig getInstance() {
        return (sessionFactoryConfig == null) ?
                sessionFactoryConfig = new SessionFactoryConfig() :
                sessionFactoryConfig;
    }

    public Session getSession() {
        return sessionFactory.openSession();
    }
}