package com.devplant.wallapp.model;

import lombok.Data;

import javax.validation.constraints.Size;

@Data
public class ReplyRequest {

    @Size(min = 10)
    private String content;
}
