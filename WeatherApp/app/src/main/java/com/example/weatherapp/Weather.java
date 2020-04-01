package com.example.weatherapp;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Weather extends AppCompatActivity {

    private ImageView image;
    private TextView type;
    private TextView high;
    private TextView low;
    private Button previous;
    private Button next;
    private Button today;
    private List<Day> days = new ArrayList<>();
    int index;
    private String[] WEATHER_TYPES;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        WEATHER_TYPES = getResources().getStringArray(R.array.weather_types);

        createForecastItems();
        createInitialType();
        createNextButton();
        createPreviousButton();
        createTodayButton();
    }

    private void createForecastItems() {
        type = findViewById(R.id.textViewType);
        high = findViewById(R.id.textViewHighValue);
        low = findViewById(R.id.textViewLowValue);
        image = findViewById(R.id.imageViewType);
    }

    private void createInitialType() {
        Day today = new Day(WEATHER_TYPES);

        days.add(today);
        index = 0;

        setForecast(today);
    }

    private void createNextButton() {
        next = findViewById(R.id.buttonNext);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextDay();
            }
        });
    }

    private void createPreviousButton() {
        previous = findViewById(R.id.buttonPrevious);
        previous.setEnabled(false);

        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                previousDay();
            }
        });
    }

    private void createTodayButton() {
        today = findViewById(R.id.buttonToday);
        today.setEnabled(false);

        today.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toToday();
            }
        });
    }

    private void nextDay() {
        index += 1;

        if (index == 0) {
            today.setEnabled(false);
            previous.setEnabled(false);
        }

        if (index > 0) {
            today.setEnabled(true);
            previous.setEnabled(true);
        }

        if (days.size() > index) {
            setForecast(days.get(index));
        }
        else {
            Day newDay = new Day(WEATHER_TYPES);
            days.add(newDay);

            setForecast(newDay);
        }
    }

    private void previousDay() {
        index -= 1;
        if (index == 0) {
            today.setEnabled(false);
            previous.setEnabled(false);
        }
        setForecast(days.get(index));
    }

    private void toToday() {
        index = 0;
        setForecast(days.get(index));
        today.setEnabled(false);
        previous.setEnabled(false);
    }

    private void setForecast(Day day) {
        type.setText(day.getWeatherType());
        high.setText(String.valueOf(day.getHighTemperature()) + "°F");
        low.setText(String.valueOf(day.getLowTemperature()) + "°F");

        int imageRes = R.drawable.sunny;
        String weatherType = day.getWeatherType();

        switch (weatherType) {
            case "Sunny":
                imageRes = R.drawable.sunny;
                break;
            case "Cloudy":
                imageRes = R.drawable.cloudy;
                break;
            case "Rainy":
                imageRes = R.drawable.rainy;
                break;
            case "Snowy":
                imageRes = R.drawable.snowy;
                break;
            case "Stormy":
                imageRes = R.drawable.stormy;
                break;
        }

        image.setImageResource(imageRes);
    }
}
