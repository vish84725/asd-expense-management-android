package com.cube365.asdexpensemanagement.models.transactions;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

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

    @SerializedName("transactionType")
    @Expose
    private String transactionType;

    @SerializedName("createDate")
    @Expose
    private String createDate;

    public Integer getId() {
        return id;
    }

    public Double getAmount() {
        return amount;
    }

    public String getRecurringType() {
        return recurringType;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public String getCreateDate() {
        return createDate;
    }
}
