package utils;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HibernateUtils {

    private final SessionFactory sessionFactory;

    public HibernateUtils() {
        sessionFactory = new MetadataSources(
                new StandardServiceRegistryBuilder()
                        .configure() // .configure() -> without param by default load configuration from file hibernate.cfg.xml
                        .build())
                .buildMetadata()
                .buildSessionFactory();
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
