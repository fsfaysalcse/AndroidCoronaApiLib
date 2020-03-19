package com.faysal.coronaoutbreak.utils;

import com.faysal.coronaoutbreak.models.Response;

public interface TotalOutbreakListener {
  public void success(Response response);
  public void failed(String errorMessage);
}
