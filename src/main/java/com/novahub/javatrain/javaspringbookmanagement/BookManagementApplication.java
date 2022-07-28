package com.novahub.javatrain.javaspringbookmanagement;

import org.hibernate.Session;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BookManagementApplication {
    public static void main(String[] args) {
        SpringApplication.run(BookManagementApplication.class, args);
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.getTransaction().commit();
        }
    }
}
