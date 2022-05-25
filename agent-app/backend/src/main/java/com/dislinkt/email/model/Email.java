package com.dislinkt.email.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class Email {

    private String recipient;
    private String subject;
    private String content;
}
