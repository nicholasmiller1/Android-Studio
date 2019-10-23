package com.example.colorguessinggame;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class ResultsActivity extends AppCompatActivity {

    private ListView listView;
    private List<GuessedColor> colors;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        createListView();
    }

    private void createListView() {
        listView = findViewById(R.id.listViewScores);
        buildScoresList();
        ColorArrayAdapter adapter = new ColorArrayAdapter(this, colors);
        listView.setAdapter(adapter);
    }

    private void buildScoresList() {
        colors = new ArrayList<>();
        colors.add(new GuessedColor(0, 0, 0, 0));
        colors.add(new GuessedColor(255, 255, 255, 255));
        colors.add(new GuessedColor(24, 15, 104, 117));
    }
}
