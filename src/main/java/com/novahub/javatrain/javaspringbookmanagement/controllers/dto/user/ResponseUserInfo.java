package com.novahub.javatrain.javaspringbookmanagement.controllers.dto.user;

import lombok.*;

@Builder
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseUserInfo {
    private String email;
    private String firstName;
    private String lastName;
    private boolean enabled;
    private String avatar;
}
