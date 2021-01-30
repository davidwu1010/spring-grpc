package com.grpcdemo.user.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.Data;
import lombok.ToString;

@Entity
@Data
@ToString
public class User {

    @Id
    private String login;
    private String name;
    private String genre;
}
