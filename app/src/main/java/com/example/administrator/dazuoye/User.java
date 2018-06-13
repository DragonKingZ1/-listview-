package com.example.administrator.dazuoye;

public class User {
    private int _id;
    private  String username;
    private String password;

    public User() {
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public User(String username, String password, int _id) {
        this.username = username;
        this.password = password;
        this._id=_id;

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
