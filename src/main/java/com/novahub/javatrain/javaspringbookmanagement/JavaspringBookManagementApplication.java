package com.novahub.javatrain.javaspringbookmanagement;

import org.hibernate.Session;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JavaspringBookManagementApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(JavaspringBookManagementApplication.class, args);
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.getTransaction().commit();
        }
    }
}
