package com.fittracker.model;

import java.sql.*;

public class User {
    private int id;
    private String username;
    private String email;
    private String sex;
    private double weight;
    private String password;
    private Date createdAt;

    public User(){}

    public User(int id, String username, String email, String sex, double weight, String password, Date createdAt){
        this.id = id;
        this.username = username;
        this.email = email;
        this.sex = sex;
        this.weight = weight;
        this.password = password;
        this.createdAt = createdAt;
    }

    public int getUserId() {
        return this.id;
    }

    public String getUserUsername(){
        return this.username;
    }

    // public String getUserPassword(){
    //     return this.password;
    // }
}
