package com.novahub.javatrain.javaspringbookmanagement.controllers.dto.auth;

import com.sun.istack.NotNull;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RequestSignUpDto {
    @NotNull
    private String email;

    @NotNull
    private String password;

    private String firstName;

    private String lastName;

    private String avatar;
}
