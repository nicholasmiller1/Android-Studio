package com.example.colorguessinggame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button beginButton;
    private Button resultsButton;
    private List<String> newResults;

    public static final String RESULTS_LIST = "results";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        newResults = new ArrayList<>();

        createBeginButton();
        createResultsButton();
        GuessedColor.setAverage(-1);
    }

    private void createBeginButton() {
        beginButton = findViewById(R.id.buttonBegin);

        beginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, GameActivity.class);
                startActivityForResult(intent, 1);
            }
        });
    }

    private void createResultsButton() {
        resultsButton = findViewById(R.id.buttonResults);

        resultsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ResultsActivity.class);

                String[] inputResults = new String[newResults.size()];

                for (int i = 0; i < newResults.size(); i++) {
                    inputResults[i] = newResults.get(i);
                }

                intent.putExtra(RESULTS_LIST, inputResults);
                startActivity(intent);
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_CANCELED) {
            return;
        }

        Log.i("tast", data.toString());

        if (resultCode == 1) {
            newResults.add( data.getStringExtra(GameActivity.RESULT) );
            if (data.getBooleanExtra(GameActivity.NEXT, false)) {
                Intent intent = new Intent(MainActivity.this, GameActivity.class);
                startActivityForResult(intent, 1);
            }
        }
    }
}
