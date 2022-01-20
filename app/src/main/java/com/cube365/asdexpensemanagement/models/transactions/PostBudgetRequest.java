package com.cube365.asdexpensemanagement.models.transactions;

import com.cube365.asdexpensemanagement.models.categories.GetCategoryResponse;
import com.cube365.asdexpensemanagement.models.users.GetUserResponse;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class PostBudgetRequest  implements Serializable {
    @SerializedName("amount")
    @Expose
    private Double amount;

    @SerializedName("date")
    @Expose
    private String date;

    @SerializedName("category")
    @Expose
    private GetCategoryResponse category;

    @SerializedName("user")
    @Expose
    private GetUserResponse user;

    public PostBudgetRequest(Double amount, String date, GetCategoryResponse category, GetUserResponse user) {
        this.amount = amount;
        this.date = date;
        this.category = category;
        this.user = user;
    }
}

