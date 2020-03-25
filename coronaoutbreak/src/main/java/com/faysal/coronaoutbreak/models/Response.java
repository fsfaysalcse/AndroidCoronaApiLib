package com.faysal.coronaoutbreak.models;

import java.util.List;

public class Response {
    boolean isSuccess;
    String message;
    TotalOutbreak outbreak;
    List<ReportByCountry> reportByCountry;

    public Response() {
    }

    public Response(boolean isSuccess, String message, TotalOutbreak outbreak) {
        this.isSuccess = isSuccess;
        this.message = message;
        this.outbreak = outbreak;
    }

    public Response(boolean isSuccess, String message, TotalOutbreak outbreak, List<ReportByCountry> reportByCountry) {
        this.isSuccess = isSuccess;
        this.message = message;
        this.outbreak = outbreak;
        this.reportByCountry = reportByCountry;
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

    public List<ReportByCountry> getReportByCountry() {
        return reportByCountry;
    }

    public void setReportByCountry(List<ReportByCountry> reportByCountry) {
        this.reportByCountry = reportByCountry;
    }
}
