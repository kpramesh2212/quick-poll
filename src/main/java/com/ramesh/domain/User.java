package com.ramesh.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "USERS")
public class User {
    @Id
    @GeneratedValue
    @Column(name="USER_ID")
    private Long id;
    @Column(name="USERNAME")
    @NotBlank
    private String username;
    @Column(name = "PASSWORD")
    @JsonIgnore
    @NotBlank
    private String password;
    @Column(name="FIRST_NAME")
    @NotBlank
    private String firstName;
    @Column(name="LAST_NAME")
    @NotBlank
    private String lastName;
    @Column(name="ADMIN", columnDefinition = "char(3)")
    @Type(type = "yes_no")
    @NotBlank
    private boolean admin;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }
}
