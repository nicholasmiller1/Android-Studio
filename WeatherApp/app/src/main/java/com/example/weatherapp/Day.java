package com.example.weatherapp;

import android.util.Log;

import java.util.Random;

public class Day {
    private int highTemperature;
    private int lowTemperature;
    private String weatherType;

    public Day(String[] array) {
        Random random = new Random();
        highTemperature = random.nextInt(100);
        lowTemperature = random.nextInt(highTemperature) - 1;
        weatherType = array[random.nextInt(4)];
    }

    public int getHighTemperature() {
        return highTemperature;
    }

    public void setHighTemperature(int highTemperature) {
        this.highTemperature = highTemperature;
    }

    public int getLowTemperature() {
        return lowTemperature;
    }

    public void setLowTemperature(int lowTemperature) {
        this.lowTemperature = lowTemperature;
    }

    public String getWeatherType() {
        return weatherType;
    }

    public void setWeatherType(String weatherType) {
        this.weatherType = weatherType;
    }
}
