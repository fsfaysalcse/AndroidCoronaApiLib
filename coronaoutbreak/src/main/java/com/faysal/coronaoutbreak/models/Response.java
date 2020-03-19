package com.faysal.coronaoutbreak.models;

public class Response {
    boolean isSuccess;
    String message;
    TotalOutbreak outbreak;

    public Response() {
    }

    public Response(boolean isSuccess, String message, TotalOutbreak outbreak) {
        this.isSuccess = isSuccess;
        this.message = message;
        this.outbreak = outbreak;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public TotalOutbreak getOutbreak() {
        return outbreak;
    }

    public void setOutbreak(TotalOutbreak outbreak) {
        this.outbreak = outbreak;
    }
}
