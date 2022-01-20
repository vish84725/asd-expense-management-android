package com.cube365.asdexpensemanagement.models.transactions;

import com.cube365.asdexpensemanagement.models.categories.GetCategoryResponse;
import com.cube365.asdexpensemanagement.models.users.GetUserResponse;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

public class CreateTransactionPostRequest implements Serializable {
    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("note")
    @Expose
    private String note;

    @SerializedName("amount")
    @Expose
    private Double amount;

    @SerializedName("recurringType")
    @Expose
    private String recurringType;

    @SerializedName("transactionType")
    @Expose
    private String transactionType;

    @SerializedName("createDate")
    @Expose
    private String createDate;

    @SerializedName("category")
    @Expose
    private GetCategoryResponse category;

    @SerializedName("user")
    @Expose
    private GetUserResponse user;

    public CreateTransactionPostRequest(String description, String note, Double amount, String recurringType, String transactionType, String createDate, GetCategoryResponse category, GetUserResponse user) {
        this.description = description;
        this.note = note;
        this.amount = amount;
        this.recurringType = recurringType;
        this.transactionType = transactionType;
        this.createDate = createDate;
        this.category = category;
        this.user = user;
    }
}
