package com.novahub.javatrain.javaspringbookmanagement.controllers.dto.auth;

import com.sun.istack.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequestSignInDto {
    @NotNull
    private String email;

    @NotNull
    private String password;
}
