package com.example.splashscreenproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    TextView header;
    EditText editText;
    SeekBar seekBar;
    TextView result;
    ImageView splashScreen;
    String[] words;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createEditText();
        createSeekBar();
        createTextViews();
        createImageView();
        runTimer();
    }

    private void createEditText() {
        editText = findViewById(R.id.editTextSentence);
        editText.setAlpha(0.0f);
        editText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                words = editText.getText().toString().trim().split("\\s+");
                updateSeekBar();
                calculateWords();
                return false;
            }
        });
    }

    private void createSeekBar() {
        seekBar = findViewById(R.id.seekBarSlider);
        seekBar.setAlpha(0.0f);
        seekBar.setMax(1);
        seekBar.setProgress(0);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                calculateWords();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void createTextViews() {
        result = findViewById(R.id.textViewResult);
        result.setAlpha(0.0f);
        header = findViewById(R.id.textViewHeader);
        header.setAlpha(0.0f);
    }

    private void createImageView() {
        splashScreen = findViewById(R.id.imageViewSplashScreen);
        splashScreen.setAlpha(1.0f);
    }

    private void calculateWords() {
        int i = seekBar.getProgress();
        String word;
        if (editText.getText().toString().trim().length() == 0) {
            word = "";
        }
        else {
            word = "Word " + i + ": " + words[i];
        }
        result.setText(word);
    }

    private void updateSeekBar() {
        int progress = seekBar.getProgress();
        seekBar.setMax(words.length-1);
    }

    public void runTimer() {
        new CountDownTimer(1500, 500) {

            @Override
            public void onTick(long l) {
                splashScreen.animate().alpha(0.0f).setDuration(1500);
            }

            @Override
            public void onFinish() {
                editText.animate().alpha(1.0f).setDuration(500);
                seekBar.animate().alpha(1.0f).setDuration(500);
                result.animate().alpha(1.0f).setDuration(500);
                header.animate().alpha(1.0f).setDuration(500);
            }
        }.start();
    }
}
