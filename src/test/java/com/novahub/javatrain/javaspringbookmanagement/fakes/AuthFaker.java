package com.novahub.javatrain.javaspringbookmanagement.fakes;

import com.novahub.javatrain.javaspringbookmanagement.controllers.dto.auth.AuthToken;

public class AuthFaker {
    public static final AuthToken responseLogin = AuthToken.builder().token("test").build();
}
