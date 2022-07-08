package com.novahub.javatrain.javaspringbookmanagement.fakes;

import com.novahub.javatrain.javaspringbookmanagement.controllers.dto.auth.SignInDTO;
import com.novahub.javatrain.javaspringbookmanagement.controllers.dto.auth.SignUpDTO;
import com.novahub.javatrain.javaspringbookmanagement.repositories.entities.Role;
import com.novahub.javatrain.javaspringbookmanagement.repositories.entities.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class UserFaker {
    public static final Role mockRole = Role.builder().id(1).name("ROLE_USER").build();
    
    public static User createUser() {
        return User.builder()
                .id(1)
                .email("test@gmail.com")
                .enabled(true)
                .password(new BCryptPasswordEncoder().encode("test"))
                .role(mockRole)
                .build();
    }
   
    
    public static final SignUpDTO signUpDTO = SignUpDTO.builder()
            .email("test@gmail.com")
            .password(new BCryptPasswordEncoder().encode("test"))
            .firstName("test")
            .build();
    
    public static final User mockUserExisted = User.builder()
            .email("kien@gmail.com")
            .enabled(true)
            .password(new BCryptPasswordEncoder().encode("test"))
            .role(mockRole)
            .build();
    
    public static final SignInDTO signInDTO = SignInDTO.builder()
            .email("test@gmail.com")
            .password("test")
            .build();
}
