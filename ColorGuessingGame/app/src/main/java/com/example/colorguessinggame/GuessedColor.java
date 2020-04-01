package com.example.colorguessinggame;

import android.content.IntentFilter;
import android.graphics.Color;

import java.util.Random;

public class GuessedColor {
    private double distance;
    private int red;
    private int green;
    private int blue;

    public static double average = -1;

    public GuessedColor() {
        Random random = new Random();
        this.red = random.nextInt(255);
        this.green = random.nextInt(255);
        this.blue = random.nextInt(255);
    }

    public GuessedColor( double distance, int red, int green, int blue) {
        this.distance = Math.floor(distance * 100) / 100;
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    public String getValidHex(int value) {
        String result = Integer.toHexString(value);
        if (result.length() < 2) {
            result = "0" + result;
        }
        return result;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public int getRed() {
        return red;
    }

    public void setRed(int red) {
        this.red = red;
    }

    public int getGreen() {
        return green;
    }

    public void setGreen(int green) {
        this.green = green;
    }

    public int getBlue() {
        return blue;
    }

    public void setBlue(int blue) {
        this.blue = blue;
    }

    public String toString() {
        String str = "";

        str += distance + " " + red + " " + green + " " + blue;

        return str;
    }

    public static GuessedColor parseString(String str) {
        String[] values = str.split(" ");
        double distance = Double.parseDouble(values[0]);
        int red = Integer.parseInt(values[1]);
        int green = Integer.parseInt(values[2]);
        int blue = Integer.parseInt(values[3]);
        return new GuessedColor(distance, red, green, blue);
    }

    public static double getAverage() {
        return Math.floor(average * 100) / 100;
    }

    public static void setAverage(double average) {
        GuessedColor.average = average;
    }

    public static void changeAverage(double newDistance) {
        if (average < 0) {
            GuessedColor.average = newDistance;
        }
        else {
        GuessedColor.average = (average + newDistance) / 2;
        }
    }
}