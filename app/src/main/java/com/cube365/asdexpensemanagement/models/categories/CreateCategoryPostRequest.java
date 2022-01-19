package com.cube365.asdexpensemanagement.models.categories;

import com.cube365.asdexpensemanagement.models.users.GetUserResponse;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CreateCategoryPostRequest implements Serializable {
    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("icon")
    @Expose
    private String icon;

    @SerializedName("user")
    @Expose
    private GetUserResponse user;

    public CreateCategoryPostRequest(String name, String icon, GetUserResponse user) {
        this.name = name;
        this.icon = icon;
        this.user = user;
    }
}
