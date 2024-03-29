package com.example.loginandsignup.helpers;

public class User {
    private int id;
    private String fullName;
    private String gender;
    private int yearOfBirth;
    private String username;
    private String password;

    public User() {
        // Default constructor required for Firebase
    }

    public User(String fullName, String gender, int yearOfBirth, String username, String password) {
        this.fullName = fullName;
        this.gender = gender;
        this.yearOfBirth = yearOfBirth;
        this.username = username;
        this.password = password;
    }

    // Getters and setters

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }
    public void setYearOfBirth(int yearOfBirth) {
        this.yearOfBirth = yearOfBirth;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getFullName() {
        return fullName;
    }
    public String getGender() {
        return gender;
    }
    public int getYearOfBirth() {
        return yearOfBirth;
    }
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
    // Setter method for id
    public void setId(int id) {
        this.id = id;
    }

    // Getter method for id
    public int getId() {
        return id;
    }
}

