package com.devplant.wallapp.model;

import lombok.Data;

import javax.validation.constraints.Size;

@Data
public class ChangePasswordRequest {


    private String oldPassword;

    @Size(min = 6)
    private String newPassword;

}
