package com.example.myapp.ipc.other;

import java.io.Serializable;

public class User implements Serializable {
    private static final long serialVersionUID = 34343434342343L;
    public int userId;
    public String userName;
    public int userSex;



    @Override
    public String toString() {
        return "[User: " + userId + " " + userName +" " + userSex + "]";
    }
}
