package com.novahub.javatrain.javaspringbookmanagement.controllers.dto.auth;

import lombok.*;

@Builder
@NoArgsConstructor
@Setter
@Getter
@AllArgsConstructor
public class ResponseSignInDto {
    private String email;
    private String firstName;
    private String lastName;
    private String token;
}
