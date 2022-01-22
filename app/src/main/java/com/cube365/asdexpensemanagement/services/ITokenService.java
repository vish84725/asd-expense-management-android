package com.cube365.asdexpensemanagement.services;

import com.cube365.asdexpensemanagement.models.users.GetUserResponse;

public interface ITokenService {
    void setAccessToken(String token);
    void setLoggedInUser(String user);
    GetUserResponse getLoggedInUser();
    String getAccessToken();
}
