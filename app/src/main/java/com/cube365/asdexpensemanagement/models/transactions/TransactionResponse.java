package com.cube365.asdexpensemanagement.models.transactions;

import com.cube365.asdexpensemanagement.models.categories.GetCategoryResponse;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

public class TransactionResponse  implements Serializable {
    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("amount")
    @Expose
    private Double amount;

    @SerializedName("recurringType")
    @Expose
    private String recurringType;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("note")
    @Expose
    private String note;

    @SerializedName("category")
    @Expose
    private GetCategoryResponse category;

    @SerializedName("transactionType")
    @Expose
    private String transactionType;

    @SerializedName("createDate")
    @Expose
    private Date createDate;

    public Integer getId() {
        return id;
    }

    private Double getAmount() {
        return amount;
    }

    public String getAmountString() {
        return "Rs. " + amount.toString();
    }

    public String getRecurringType() {
        return recurringType;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public GetCategoryResponse getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public String getNote() {
        return note;
    }
}
