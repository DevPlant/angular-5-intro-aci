package com.devplant.wallapp.model;

import lombok.Data;

import javax.validation.constraints.Size;

@Data
public class AccountRegistrationRequest {

    private String username;

    @Size(min = 6)
    private String password;
}
