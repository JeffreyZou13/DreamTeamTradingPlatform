package com.citi.dream.strategies;

import org.json.JSONException;

public interface Strategy {
    void performStrategy() throws JSONException;
    String getType();
    void setType(String type);
    String getState();
    void setState(String state);
}
