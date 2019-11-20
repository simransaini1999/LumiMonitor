package com.example.lumimonitor;

import android.renderscript.Sampler;

public class DataStructure implements Comparable<DataStructure> {

    private String temperature;
    private String humidity;
    private String lightValue;
    private String awakenTime;

    public DataStructure() {
    }

    public DataStructure(String temperature,String humidity,
                         String lightValue, String awakenTime) {

        this.temperature = temperature;
        this.humidity = humidity;
        this.lightValue = lightValue;
        this.awakenTime = awakenTime;

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

    public int compareTo(DataStructure st){
        if (Long.parseLong(awakenTime) > Long.parseLong(st.awakenTime))
            return 1;
        else
            return -1;
    }

}
