package com.novahub.javatrain.javaspringbookmanagement;

import com.novahub.javatrain.javaspringbookmanagement.controllers.dto.auth.SignUpDTO;
import com.novahub.javatrain.javaspringbookmanagement.repositories.RoleRepository;
import com.novahub.javatrain.javaspringbookmanagement.repositories.UserRepository;
import com.novahub.javatrain.javaspringbookmanagement.repositories.entities.Role;
import com.novahub.javatrain.javaspringbookmanagement.repositories.entities.User;
import com.novahub.javatrain.javaspringbookmanagement.services.AuthService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Configuration
public class DataSeedingListener implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthService authService;

    @Autowired
    private RoleRepository roleRepository;

    @Value("${jwt-key}")
    private String signingKey;

    private void addRoleIfMissing(String name, User user){
        if (roleRepository.findRoleByName(name) == null) {
            roleRepository.save(new Role(0, name, List.of()));
        }
    }

    private void addAdmin(String email,String password){
        Role admin = roleRepository.findRoleByName("ROLE_ADMIN");
        SignUpDTO addAdmin = SignUpDTO.builder().email(email).password(password).build();
        if(userRepository.findUserByEmail(email) == null){
            authService.signUp(addAdmin);
            User newAdmin = userRepository.findUserByEmail(email);
            newAdmin.setRole(admin);
            userRepository.save(newAdmin);
        }
    }
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        addRoleIfMissing("ADMIN",null);
        addRoleIfMissing("USER",null);
        addAdmin("admin@gmail.com","123");

        if(signingKey == null || signingKey.length() ==0){
            String jws = Jwts.builder()
                    .setSubject("BookStore")
                    .signWith(SignatureAlgorithm.HS256, "BookStoreApi").compact();

            System.out.println("Use this jwt key:");
            System.out.println("jwt-key=" + jws);
        }
    }
}
