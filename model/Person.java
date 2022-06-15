package model;

import java.io.Serializable;

public class Person implements Serializable {

    protected String name;
    protected String lastName;
    protected String username;
    protected String password;
    protected String phoneNumber;

    public Person(String name, String lastName, String username, String password, String phoneNumber) {
        this.name = name;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

}
