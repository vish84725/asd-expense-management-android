package com.cube365.asdexpensemanagement.services;

import com.cube365.asdexpensemanagement.models.categories.CreateCategoryPostRequest;
import com.cube365.asdexpensemanagement.models.categories.GetCategoryResponse;
import com.cube365.asdexpensemanagement.models.common.CommonResponse;
import com.cube365.asdexpensemanagement.models.users.CreateUserPostRequest;
import com.cube365.asdexpensemanagement.models.users.LoginUserPostRequest;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface IUserService {
    @POST("/user/login")
    Call<CommonResponse> login(@Body() LoginUserPostRequest request);

    @POST("/user/add-user")
    Call<CommonResponse> addUser(@Body() CreateUserPostRequest request);

}
