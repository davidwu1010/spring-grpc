package com.grpcdemo.movie.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.Data;
import lombok.ToString;

@Entity
@Data
@ToString
public class Movie {

    @Id
    private int id;
    private String title;
    private int year;
    private double rating;
    private String genre;
}
