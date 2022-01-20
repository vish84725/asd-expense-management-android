package com.cube365.asdexpensemanagement.services;

import com.cube365.asdexpensemanagement.models.common.CommonResponse;
import com.cube365.asdexpensemanagement.models.transactions.CreateTransactionPostRequest;
import com.cube365.asdexpensemanagement.models.transactions.GetBudgetResponse;
import com.cube365.asdexpensemanagement.models.transactions.PostBudgetRequest;
import com.cube365.asdexpensemanagement.models.transactions.TransactionResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ITransactionService {
    @GET("/transaction/get-transactions")
    Call<List<TransactionResponse>> getAllTransactions(@Header("Authorization") String token,
                                                    @Query("userId") Integer userId, @Query("type") String type);

    @POST("/transaction/save-transaction-details")
    Call<CommonResponse> saveTransaction(@Header("Authorization") String token,
                                         @Body() CreateTransactionPostRequest request);

    @GET("/budget/get-budget")
    Call<List<GetBudgetResponse>> getBudgetsForCategories(@Header("Authorization") String token,
                                                          @Query("userId") Integer userId);

    @POST("/budget/save-budget-details")
    Call<CommonResponse> saveBudget(@Header("Authorization") String token,
                                         @Body() PostBudgetRequest request);

}
