package com.example.databetweenactivitiesquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button termsButton;
    private Button loginButton;

    private boolean status;
    private String inputtedCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createButtons();
    }

    private void createButtons() {
        termsButton = findViewById(R.id.buttonTerms);
        loginButton = findViewById(R.id.buttonLogin);

        termsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startTermsActivity();
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login(status, inputtedCode);
            }
        });

        loginButton.setEnabled(false);
    }

    private void startTermsActivity() {
        Intent intent = new Intent(this, TermsActivity.class);
        startActivityForResult(intent, 1);
    }

    private void login(boolean b, String code) {
        if (b) {
            Toast.makeText(this, "Success! Your code of " + (code.equals("") ? "<blank>" : code) + " was correct!", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this, "Fail! Your code of " + (code.equals("") ? "<blank>" : code) + " was incorrect!", Toast.LENGTH_SHORT).show();
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_CANCELED) {
            return;
        }

        if (resultCode == 1) {
            Log.i("FGSDFGDS", "FDSFDS");
            inputtedCode = data.getStringExtra(TermsActivity.CODE);
            status = data.getBooleanExtra(TermsActivity.GOOD, false);

            loginButton.setEnabled(true);
        }
    }
}
