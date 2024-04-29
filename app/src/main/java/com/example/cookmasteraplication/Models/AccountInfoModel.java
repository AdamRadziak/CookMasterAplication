package com.example.cookmasteraplication.Models;

import com.example.cookmasteraplication.Controlers.LoginPageControler;

import java.util.ArrayList;

public class AccountInfoModel {

    public String email;
    public String password;
    LoginPageControler controler;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int VerifyEmailPassword() {
        int statusCode = 0;
        // in this method send post request to server with  password and email headers
        // if server return status code 200 string status is Succesed login
        // if server status code is 400 status is login failed

        return statusCode;
    }

    public int SendPasswordReminder() {
        int statusCode = 0;
        // in this method send post request with email password reminder
        // if server return status code 200 string status is Succesed login
        // if server status code is 400 status is login failed

        return statusCode;
    }

    public int RegisterUser() {
        int statusCode = 0;
        // in this method send post request with email and password to register user
        // next it send get request to check that user is verified
        // if server return status code 200 string status is Succesed login
        // if server status code is 400 status is login failed

        return statusCode;
    }




}
