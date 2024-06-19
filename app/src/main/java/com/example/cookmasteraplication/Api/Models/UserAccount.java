package com.example.cookmasteraplication.Api.Models;
import com.google.gson.annotations.SerializedName;
public class UserAccount {

    @SerializedName("id")
    private Integer Id;
    @SerializedName("emailHash")
    private String emailHash;
    @SerializedName("passwordHash")
    private String passwordHash;

    public UserAccount(String EmailHash, String PasswordHash) {
        this.passwordHash = PasswordHash;
        this.emailHash = EmailHash;
    }

    public Integer getId() {
        return Id;
    }

    public String getEmail() {
        return emailHash;
    }

    public void setEmail(String Email) {
        this.emailHash = Email;
    }

    public String getPassword() {
        return passwordHash;
    }

    public void setPassword(String passwHash) {
        this.passwordHash = passwHash;
    }
}
