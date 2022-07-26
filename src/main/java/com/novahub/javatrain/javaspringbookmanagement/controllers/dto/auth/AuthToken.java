package com.novahub.javatrain.javaspringbookmanagement.controllers.dto.auth;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class AuthToken {
    private String token;
}