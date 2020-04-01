package com.example.colorguessinggame;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ResultsActivity extends AppCompatActivity {

    private ListView listView;
    private List<GuessedColor> colors;
    private TextView average;
    private Button returnButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        createListView();
        createAverageTextView();
        createButton();
    }

    private void createListView() {
        listView = findViewById(R.id.listViewScores);
        buildScoresList();
        ColorArrayAdapter adapter = new ColorArrayAdapter(this, colors);
        listView.setAdapter(adapter);
    }

    private void buildScoresList() {
        colors = new ArrayList<>();

        Intent intent = getIntent();
        String[] results = intent.getStringArrayExtra(MainActivity.RESULTS_LIST);

        if (results != null) {
            for (String i : results) {
                if (i != null) {
                    colors.add(GuessedColor.parseString(i));
                }
            }
        }
    }

    private void createAverageTextView() {
        average = findViewById(R.id.textViewAverageComparison);

        if (GuessedColor.getAverage() < 0) {
            average.setText(R.string.no_average);
        }
        else {
            average.setText("Average: " + GuessedColor.getAverage());
        }
    }

    private void createButton() {
        returnButton = findViewById(R.id.buttonReturn);

        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
