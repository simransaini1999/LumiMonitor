package com.example.lumimonitor;

public class DataStructure {

    private String username;
    private String temperature;
    private String humidity;
    private String lightValue;
    private String awakenTime;

    public DataStructure() {
    }

    public DataStructure(String username, String temperature,String humidity,
                         String lightValue, String awakenTime) {

        this.username = username;
        this.temperature = temperature;
        this.humidity = humidity;
        this.lightValue = lightValue;
        this.awakenTime = awakenTime;

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getLightValue() {
        return lightValue;
    }

    public void setLightValue(String lightValue) {
        this.lightValue = lightValue;
    }

    public String getAwakenTime() {
        return awakenTime;
    }

    public void setAwakenTime(String awakenTime) {
        this.awakenTime = awakenTime;
    }
}
