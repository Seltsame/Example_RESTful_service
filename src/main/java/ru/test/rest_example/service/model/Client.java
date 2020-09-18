package ru.test.rest_example.service.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Client {

    private Integer id;
    private String name;
    private String email;
    private String phone;

}
