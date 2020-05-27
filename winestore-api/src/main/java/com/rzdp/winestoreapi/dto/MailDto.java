package com.rzdp.winestoreapi.dto;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class MailDto {

    private String sender;
    private String receiver;
    private String subject;
    private Map<String, Object> props = new HashMap<>();
}
