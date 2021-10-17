package com.example.webik.controller.vm;

public class UsernamePasswordVM {//dton a
    private String username;
    private String password;

    public UsernamePasswordVM() {
    }

    public UsernamePasswordVM(String username, String password) {
        this.username = username;
        this.password = password;
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


}
