package com.cube365.asdexpensemanagement.services;

import com.cube365.asdexpensemanagement.models.categories.CreateCategoryPostRequest;
import com.cube365.asdexpensemanagement.models.categories.GetCategoryResponse;
import com.cube365.asdexpensemanagement.models.common.CommonResponse;
import com.cube365.asdexpensemanagement.models.transactions.CreateTransactionPostRequest;
import com.cube365.asdexpensemanagement.models.transactions.TransactionResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ICategoryService {
    @GET("/category/get-categories")
    Call<List<GetCategoryResponse>> getAllCategories(@Header("Authorization") String token,
                                                     @Query("userId") Integer userId);

    @POST("/category/save-category-details")
    Call<CommonResponse> saveCategory(@Header("Authorization") String token,
                                         @Body() CreateCategoryPostRequest request);
}
