package com.cube365.asdexpensemanagement.models.users;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class LoginUserPostRequest implements Serializable {
    @SerializedName("userName")
    @Expose
    private String userName;

    @SerializedName("password")
    @Expose
    private String password;

    public LoginUserPostRequest(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }
}
