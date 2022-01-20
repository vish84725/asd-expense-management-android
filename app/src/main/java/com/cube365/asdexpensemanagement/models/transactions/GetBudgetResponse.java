package com.cube365.asdexpensemanagement.models.transactions;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class GetBudgetResponse  implements Serializable {
    @SerializedName("categoryId")
    @Expose
    private Integer categoryId;

    @SerializedName("categoryName")
    @Expose
    private String categoryName;

    @SerializedName("budgetId")
    @Expose
    private Integer budgetId;

    @SerializedName("budgetAmount")
    @Expose
    private Double budgetAmount;

    @SerializedName("transactionAmount")
    @Expose
    private Double transactionAmount;

    public Integer getCategoryId() {
        return categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public Integer getBudgetId() {
        return budgetId;
    }

    public Double getBudgetAmount() {
        return budgetAmount;
    }

    public Double getTransactionAmount() {
        return transactionAmount;
    }
}


