package com.example.loginscreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;

public class AccountScreen extends AppCompatActivity {

    private Button logoutButton;
    private EditText journal;

    public static final String TEXT_RETURN_IDENTIFIER = "text_return";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_screen);

        createViews();
        getJournalText();
    }

    @Override
    protected void onPause() {
        Intent intent = new Intent();

        if (journal.getText() != null && !journal.getText().toString().equals("")) {
            intent.putExtra(TEXT_RETURN_IDENTIFIER, journal.getText().toString());
        }

        setResult(1, intent);

        super.onPause();
    }

    @Override
    public void onBackPressed() {
        endActivity();
    }

    private void getJournalText() {
        Intent intent = getIntent();
        String text = intent.getStringExtra(MainActivity.TEXT_IDENTIFIER);

        if (text != null) {
            journal.setText(text);
        }
    }

    private void createViews() {
        journal = findViewById(R.id.editTextJournal);
        logoutButton = findViewById(R.id.buttonLogout);

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                endActivity();
            }
        });
    }

    private void endActivity() {
        Intent intent = new Intent();

        if (journal.getText() != null && !journal.getText().toString().equals("")) {
            intent.putExtra(TEXT_RETURN_IDENTIFIER, journal.getText().toString());
        }

        setResult(1, intent);
        finish();
    }
}
