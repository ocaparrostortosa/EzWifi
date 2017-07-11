package com.github.ocaparrostortosa.ezwifi.pojo;

/**
 * Created by Oscar on 30/06/2017.
 * Usuario is a POJO class to create new users objects.
 * @author Oscar Caparros
 * @version 1.0
 */

public class Usuario {

    private String email;
    private String username;
    private String password;

    /**
     * Usuario() is the default constructor.
     */
    public Usuario(){}

    /**
     * Usuario() is the main constructor. It contains getters and setters for all the attributes and override the toString method.
     * @param email User email
     * @param username User name
     * @param password User password
     */
    public Usuario(String email, String username, String password) {
        this.email = email;
        this.username = username;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
