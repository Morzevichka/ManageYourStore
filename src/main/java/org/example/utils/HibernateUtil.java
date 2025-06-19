package org.example.utils;

import io.github.cdimascio.dotenv.Dotenv;
import org.example.entity.*;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.configuration.MutableConfiguration;
import javax.cache.expiry.CreatedExpiryPolicy;
import javax.cache.expiry.Duration;
import javax.cache.spi.CachingProvider;
import java.util.concurrent.TimeUnit;

public final class HibernateUtil {
    private final static SessionFactory sessionFactory;

    static {
        try {

            Dotenv dotenv = Dotenv.configure()
                    .directory("src/main/assets")
                    .filename("env")
                    .load();

            // Create configuration
            Configuration configuration = new Configuration();
            configuration.configure("hibernate.cfg.xml");
            configuration.setProperty("hibernate.connection.url", dotenv.get("DB_URL"));
            configuration.setProperty("hibernate.connection.username", dotenv.get("DB_USERNAME"));
            configuration.setProperty("hibernate.connection.password", dotenv.get("DB_PASSWORD"));

            // Create L2 Cache configuration
            CachingProvider provider = Caching.getCachingProvider();
            CacheManager cacheManager = provider.getCacheManager();

            MutableConfiguration<Object, Object> config = new MutableConfiguration<>()
                    .setStoreByValue(false)
                    .setStatisticsEnabled(true)
                    .setExpiryPolicyFactory(CreatedExpiryPolicy.factoryOf(Duration.TEN_MINUTES));

            cacheManager.createCache("org.example.entity.Card", config);
            cacheManager.createCache("org.example.entity.Card.clients", config);
            cacheManager.createCache("org.example.entity.Client", config);
            cacheManager.createCache("org.example.entity.Client.orders", config);
            cacheManager.createCache("org.example.entity.Order", config);
            cacheManager.createCache("org.example.entity.Category", config);
            cacheManager.createCache("org.example.entity.Category.products", config);
            cacheManager.createCache("org.example.entity.Product", config);
            cacheManager.createCache("org.example.entity.Product.orderItems", config);
            cacheManager.createCache("org.example.entity.Worker", config);
            cacheManager.createCache("org.example.entity.Worker.orders", config);
            cacheManager.createCache("default-query-results-region", config);
            cacheManager.createCache("default-update-timestamps-region", config);

            configuration.getProperties().put("hibernate.javax.cache.manager", cacheManager);

            configuration.addAnnotatedClass(Card.class);
            configuration.addAnnotatedClass(Category.class);
            configuration.addAnnotatedClass(Client.class);
            configuration.addAnnotatedClass(Order.class);
            configuration.addAnnotatedClass(OrderItem.class);
            configuration.addAnnotatedClass(Product.class);
            configuration.addAnnotatedClass(Worker.class);

            sessionFactory = configuration.buildSessionFactory();
        } catch (Exception e) {
            System.err.println("Initial SessionFactory creation failed: "+ e);
            throw new ExceptionInInitializerError(e);
        }
    }

    private HibernateUtil() {}

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void close() {
        sessionFactory.close();
    }
}
