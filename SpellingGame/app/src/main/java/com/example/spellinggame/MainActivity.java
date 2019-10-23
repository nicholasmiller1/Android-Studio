package com.example.spellinggame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class MainActivity extends AppCompatActivity {

    Button beginButton;
    TextView countdown;
    int secondsLeft = 300;
    int score = 0;
    RadioGroup buttons;
    Switch confirm;
    List<String> words;
    Map<String, Boolean> wordsEvaluation;
    TextView word;
    TextView scoreField;
    TextView gameOver;
    TextView scoreDisplay;
    TextView splashScreenText;
    ImageView splashScreenImage;
    TextView confirmText;
    TextView scoreText;
    TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        confirmText = findViewById(R.id.textViewConfirm);
        scoreText = findViewById(R.id.textViewScore);
        title = findViewById(R.id.textViewTitle);

        createCountdown();
        createBeginButton();
        createRadioGroup();
        createConfirmSwitch();
        createWordTextView();
        createGameOver();
        createScoreField();
        createSplashScreen();
    }

    private void createBeginButton() {
        beginButton = findViewById(R.id.buttonBegin);
        beginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                beginButton.setText("");
                countdown.setVisibility(View.VISIBLE);
                beginButton.setEnabled(false);
                displayTime();
                runTimer();
            }
        });
    }

    private void createCountdown() {
        countdown = findViewById(R.id.textViewCountdown);
        countdown.setVisibility(View.INVISIBLE);
    }

    private void createRadioGroup() {
        buttons = findViewById(R.id.radioGroupButtons);
        for ( int i = 0; i < 3; i++ ) {
            buttons.getChildAt(i).setEnabled(false);
        }
    }

    private void beginRadioGroup() {
        for ( int i = 0; i < 3; i++ ) {
            buttons.getChildAt(i).setEnabled(true);
        }
    }

    private void createConfirmSwitch() {
        confirm = findViewById(R.id.switchConfirm);
        confirm.setEnabled(false);
    }

    private void beginConfirmSwitch() {
        confirm.setEnabled(true);
        confirm.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b) {
                    checkWord();
                }
            }
        });
    }

    private void createWordTextView() {
        word = findViewById(R.id.textViewWord);
    }

    private void beginWordTextView() {
        word.setText(words.get(0));
    }

    private void createWordMap() {
        wordsEvaluation = new TreeMap<>();
        wordsEvaluation.put("Vaccuum", false);
        wordsEvaluation.put("Congradulate", false);
        wordsEvaluation.put("Cemetery", true);
        wordsEvaluation.put("Prejudice", true);
        wordsEvaluation.put("Cemetary", false);
        wordsEvaluation.put("Rhythm", true);
        wordsEvaluation.put("Conceed", false);
        wordsEvaluation.put("Occassionally", false);
        wordsEvaluation.put("Secretery", false);
        wordsEvaluation.put("Succeed", true);
        wordsEvaluation.put("Inoculate", true);
        wordsEvaluation.put("Comaraderie", false);
        wordsEvaluation.put("Harrass", false);
        wordsEvaluation.put("Relevant", true);
        wordsEvaluation.put("Recieve", false);
        wordsEvaluation.put("Conceive", true);
        wordsEvaluation.put("Secretary", true);
        wordsEvaluation.put("Definite", true);
        wordsEvaluation.put("Hygiene", true);
        wordsEvaluation.put("Definately", false);
        words = new ArrayList<>(wordsEvaluation.keySet());
        Collections.shuffle(words);
    }

    private void createScoreField() {
        scoreField = findViewById(R.id.textViewScore);
    }

    private void runTimer() {
        beginProgram();
        new CountDownTimer(secondsLeft * 1000, 1000) {
            @Override
            public void onTick(long l) {
                secondsLeft--;
                displayTime();
            }

            @Override
            public void onFinish() {
                gameOver();
            }
        }.start();
    }

    private void displayTime() {
        int minutes = secondsLeft / 60;
        int seconds = secondsLeft % 60;
        String time = String.format("%d:%02d", minutes, seconds);
        countdown.setText(time);
    }

    private void checkWord() {
        int selected = buttons.getCheckedRadioButtonId();
        boolean answer = false;
        Log.i("Info", selected + "");
        if ( selected == -1 ) {
            confirm.setChecked(false);
            return;
        }
        else if ( selected == R.id.radioButtonCorrect ) {
            answer = true;
        }
        else if ( selected == R.id.radioButtonIncorrect ) {
            answer = false;
        }
        else if ( selected == R.id.radioButtonSkip ) {
            nextWord();
            return;
        }
        if ( wordsEvaluation.get(words.get(0)) == answer ) {
            score++;
            nextWord();
        }
        else {
            gameOver();
        }
    }

    private void nextWord() {
        words.remove(0);
        if ( words.isEmpty() ) {
            gameOver();
            return;
        }
        updateScore();
        word.setText(words.get(0));
        confirm.setChecked(false);
        buttons.clearCheck();
    }

    private void gameOver() {
        beginButton.animate().alpha(0.0f).setDuration(500);
        countdown.animate().alpha(0.0f).setDuration(500);
        buttons.animate().alpha(0.0f).setDuration(500);
        confirm.animate().alpha(0.0f).setDuration(500);
        word.animate().alpha(0.0f).setDuration(500);
        scoreField.animate().alpha(0.0f).setDuration(500);
        confirmText.animate().alpha(0.0f).setDuration(500);
        scoreText.animate().alpha(0.0f).setDuration(500);
        title.animate().alpha(0.0f).setDuration(500);

        scoreDisplay.setText(score + "/20");
        gameOver.animate().alpha(1.0f).setDuration(500);
        scoreDisplay.animate().alpha(1.0f).setDuration(500);
    }

    private void updateScore() {
        String scoreString = score + "/20";
        scoreField.setText(scoreString);
    }

    private void beginProgram() {
        beginRadioGroup();
        beginConfirmSwitch();
        createWordMap();
        beginWordTextView();
    }

    private void createGameOver() {
        gameOver = findViewById(R.id.textViewGameOver);
        scoreDisplay = findViewById(R.id.textViewScoreDisplay);
        gameOver.setAlpha(0.0f);
        scoreDisplay.setAlpha(0.0f);
    }

    private void createSplashScreen() {
        splashScreenText = findViewById(R.id.textViewSplashScreen);
        splashScreenImage = findViewById(R.id.imageViewSplashScreen);

        splashScreenText.setAlpha(1.0f);
        splashScreenImage.setAlpha(1.0f);

        beginButton.setAlpha(0.0f);
        countdown.setAlpha(0.0f);
        buttons.setAlpha(0.0f);
        confirm.setAlpha(0.0f);
        word.setAlpha(0.0f);
        scoreField.setAlpha(0.0f);
        confirmText.setAlpha(0.0f);
        scoreText.setAlpha(0.0f);
        title.setAlpha(0.0f);

        new CountDownTimer(1500, 1500) {
            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {
                splashScreenImage.animate().alpha(0.0f).setDuration(500);
                splashScreenText.animate().alpha(0.0f).setDuration(500);
                beginButton.animate().alpha(1.0f).setDuration(500);
                countdown.animate().alpha(1.0f).setDuration(500);
                buttons.animate().alpha(1.0f).setDuration(500);
                confirm.animate().alpha(1.0f).setDuration(500);
                word.animate().alpha(1.0f).setDuration(500);
                title.animate().alpha(1.0f).setDuration(500);
                scoreField.animate().alpha(1.0f).setDuration(500);
                confirmText.animate().alpha(1.0f).setDuration(500);
                scoreText.animate().alpha(1.0f).setDuration(500);
            }
        }.start();
    }
}
