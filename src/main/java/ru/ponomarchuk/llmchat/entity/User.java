package ru.ponomarchuk.llmchat.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_login", nullable = false, length = 100)
    private String userLogin;

    @Column(name = "user_encoded_password", nullable = false, columnDefinition = "TEXT")
    private String userEncodedPassword;

    public User() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public String getUserEncodedPassword() {
        return userEncodedPassword;
    }

    public void setUserEncodedPassword(String userEncodedPassword) {
        this.userEncodedPassword = userEncodedPassword;
    }
}
