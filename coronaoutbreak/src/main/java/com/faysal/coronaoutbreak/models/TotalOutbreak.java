package com.faysal.coronaoutbreak.models;

public class TotalOutbreak {
    String totalCases;
    String totalDeaths;
    String totalRecovered;

    public TotalOutbreak() {
    }

    public TotalOutbreak(String totalCases, String totalDeaths, String totalRecovered) {
        this.totalCases = totalCases;
        this.totalDeaths = totalDeaths;
        this.totalRecovered = totalRecovered;
    }

    public String getTotalCases() {
        return totalCases;
    }

    public void setTotalCases(String totalCases) {
        this.totalCases = totalCases;
    }

    public String getTotalDeaths() {
        return totalDeaths;
    }

    public void setTotalDeaths(String totalDeaths) {
        this.totalDeaths = totalDeaths;
    }

    public String getTotalRecovered() {
        return totalRecovered;
    }

    public void setTotalRecovered(String totalRecovered) {
        this.totalRecovered = totalRecovered;
    }
}
