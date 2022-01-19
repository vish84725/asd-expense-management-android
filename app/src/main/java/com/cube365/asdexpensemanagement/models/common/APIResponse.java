package com.cube365.asdexpensemanagement.models.common;

import java.util.List;

public class APIResponse<T> {
    public List<T> data;
    private Throwable error;

    public APIResponse(List<T> data) {
        this.data = data;
        this.error = null;
    }

    public APIResponse(Throwable error) {
        this.error = error;
        this.data = null;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public Throwable getError() {
        return error;
    }

    public void setError(Throwable error) {
        this.error = error;
    }
}
