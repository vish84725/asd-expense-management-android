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
}
