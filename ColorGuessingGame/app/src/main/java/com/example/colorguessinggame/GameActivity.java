package com.example.colorguessinggame;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.text.Editable;
import android.text.Layout;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class GameActivity extends AppCompatActivity {

    private GuessedColor currentColor;
    private View background;
    private EditText redInput;
    private EditText greenInput;
    private EditText blueInput;
    private Button submitButton;
    private Button returnButton;
    private Button nextButton;
    private ImageView arrowRed, arrowGreen, arrowBlue;
    private TextView answerRed, answerGreen, answerBlue;
    private boolean completed = false;

    public static final String RESULT = "result";
    public static final String NEXT = "next";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        initializeColor();
        createEditTexts();
        createAnswerElements();
        createSubmit();
        createReturn();
        createNext();
    }

    @Override
    public void onBackPressed() {
        endActivity(false);

        super.onBackPressed();
    }

    private void initializeColor() {
        currentColor = new GuessedColor();

        background = this.getWindow().getDecorView();
        background.setBackgroundColor(Color.rgb(currentColor.getRed(), currentColor.getGreen(), currentColor.getBlue()));
    }

    private void createEditTexts() {
        redInput = findViewById(R.id.editTextRed);
        greenInput = findViewById(R.id.editTextGreen);
        blueInput = findViewById(R.id.editTextBlue);

        redInput.addTextChangedListener(new TextWatcher() {
                @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                restrictEditTexts(redInput);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        greenInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                restrictEditTexts(greenInput);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        blueInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                restrictEditTexts(blueInput);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void createSubmit() {
          submitButton = findViewById(R.id.buttonSubmit);

          submitButton.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {
                  if (!redInput.getText().toString().equals("") && !greenInput.getText().toString().equals("") && !blueInput.getText().toString().equals("")) {
                      completed = true;

                      findDistance();

                      checkColor();
                  }
              }
          });
    }

    private void createReturn() {
        returnButton = findViewById(R.id.buttonReturn);

        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                endActivity(false);
            }
        });
    }

    private void createNext() {
        nextButton = findViewById(R.id.buttonNextColor);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                endActivity(true);
            }
        });
    }

    private void endActivity(boolean next) {
        Intent intent = new Intent();

        intent.putExtra(NEXT, next);

        if (completed == true) {
            intent.putExtra(RESULT, currentColor.toString());

            if (GuessedColor.getAverage() < 0) {
                GuessedColor.setAverage(currentColor.getDistance());
            } else {
                GuessedColor.changeAverage(currentColor.getDistance());
            }
        }

        setResult(1, intent);
        finish();
    }

    private void createAnswerElements() {
        arrowRed = findViewById(R.id.imageViewArrowRed);
        arrowGreen = findViewById(R.id.imageViewArrowGreen);
        arrowBlue = findViewById(R.id.imageViewArrowBlue);

        answerRed = findViewById(R.id.textViewRealRed);
        answerGreen = findViewById(R.id.textViewRealGreen);
        answerBlue = findViewById(R.id.textViewRealBlue);

        arrowRed.setVisibility(View.INVISIBLE);
        arrowGreen.setVisibility(View.INVISIBLE);
        arrowBlue.setVisibility(View.INVISIBLE);
        answerRed.setVisibility(View.INVISIBLE);
        answerGreen.setVisibility(View.INVISIBLE);
        answerBlue.setVisibility(View.INVISIBLE);
    }

    private void restrictEditTexts(EditText view) {
        String valueString = view.getText().toString();
        if (valueString == null || valueString.equals("")) {
            return;
        }
        int valueInt = Integer.parseInt(valueString);

        if (valueInt > 255) {
            String newString = valueString.substring(0, valueString.length()-1);
            view.setText(newString);
            view.setSelection(newString.length());
        }
    }

    private void findDistance() {
        String redInputString = redInput.getText().toString();
        String greenInputString = greenInput.getText().toString();
        String blueInputString = blueInput.getText().toString();

        int inputedRed = Integer.parseInt(redInputString);
        int inputedGreen = Integer.parseInt(greenInputString);
        int inputedBlue = Integer.parseInt(blueInputString);

        int realRed = currentColor.getRed();
        int realGreen = currentColor.getGreen();
        int realBlue = currentColor.getBlue();

        double distance = Math.sqrt( Math.pow(realRed - inputedRed, 2) + Math.pow(realGreen - inputedGreen, 2) + Math.pow(realBlue - inputedBlue, 2) );

        currentColor.setDistance(distance);

        TextView averageSentence = findViewById(R.id.textViewReport);
        averageSentence.setText("Your guess was a distance of " + Math.floor(distance * 100) / 100 + " off");

        TextView averageComparison = findViewById(R.id.textViewAverageComparison);
        ImageView face = findViewById(R.id.imageViewFace);
        if (GuessedColor.getAverage() < 0) {
            averageComparison.setText("This is your first score!");
            face.setImageResource(R.drawable.smile);
        }
        else if (distance > GuessedColor.getAverage()) {
            averageComparison.setText("Worse than your average");
            face.setImageResource(R.drawable.frown);
        }
        else if (distance < GuessedColor.getAverage()) {
            averageComparison.setText("Better than your average!");
            face.setImageResource(R.drawable.smile);
        }
        else {
            averageComparison.setText("At your average");
            face.setImageResource(R.drawable.smile);
        }
    }

    private void checkColor() {
        redInput.setFocusable(false);
        redInput.setClickable(false);

        greenInput.setFocusable(false);
        greenInput.setClickable(false);

        blueInput.setFocusable(false);
        blueInput.setClickable(false);

        submitButton.setVisibility(View.INVISIBLE);

        arrowRed.setVisibility(View.VISIBLE);
        arrowGreen.setVisibility(View.VISIBLE);
        arrowBlue.setVisibility(View.VISIBLE);

        answerRed.setText(String.valueOf(currentColor.getRed()));
        answerGreen.setText(String.valueOf(currentColor.getGreen()));
        answerBlue.setText(String.valueOf(currentColor.getBlue()));

        answerRed.setVisibility(View.VISIBLE);
        answerGreen.setVisibility(View.VISIBLE);
        answerBlue.setVisibility(View.VISIBLE);
    }
}
