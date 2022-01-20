package com.cube365.asdexpensemanagement.models.categories;

import com.cube365.asdexpensemanagement.models.users.GetUserResponse;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class GetCategoryResponse implements Serializable {
    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("icon")
    @Expose
    private String icon;

    @SerializedName("user")
    @Expose
    private GetUserResponse user;

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getIcon() {
        return icon;
    }

    public GetUserResponse getUser() {
        return user;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
