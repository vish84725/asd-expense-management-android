package com.cube365.asdexpensemanagement.models.common;

public class APIObjectResponse<T> {
    public T data;
    private Throwable error;

    public APIObjectResponse(T data) {
        this.data = data;
        this.error = null;
    }

    public APIObjectResponse(Throwable error) {
        this.error = error;
        this.data = null;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Throwable getError() {
        return error;
    }

    public void setError(Throwable error) {
        this.error = error;
    }
}
