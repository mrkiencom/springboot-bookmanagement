package com.novahub.javatrain.javaspringbookmanagement;

import org.hibernate.Session;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BookManagementApplication {
    public static void main(String[] args) {
        SpringApplication.run(BookManagementApplication.class, args);
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.getTransaction().commit();
        }
    }
}
