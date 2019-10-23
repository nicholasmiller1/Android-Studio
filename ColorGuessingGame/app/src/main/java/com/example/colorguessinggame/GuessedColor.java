package com.example.colorguessinggame;

import android.graphics.Color;

public class GuessedColor {
    private int distance;
    private String hexColor;

    public GuessedColor( int distance, String hex ) {
        this.distance = distance;
        this.hexColor = hex;
    }

    public GuessedColor( int distance, int red, int green, int blue) {
        this.distance = distance;
        this.hexColor = "#" + getValidHex(red) + getValidHex(green) + getValidHex(blue);
    }

    public String getValidHex(int value) {
        String result = Integer.toHexString(value);
        if (result.length() < 2) {
            result = "0" + result;
        }
        return result;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public String getHexColor() {
        return hexColor;
    }

    public void setHexColor(String hexColor) {
        this.hexColor = hexColor;
    }
}