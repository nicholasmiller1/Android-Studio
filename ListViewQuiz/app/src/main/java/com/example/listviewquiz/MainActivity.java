package com.example.listviewquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText nameInput;
    private Button addPlayer;
    private ListView listView;

    private List<String> names;
    private ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createListView();
        createViews();
    }

    private void createViews() {
        nameInput = findViewById(R.id.editTextInput);
        addPlayer = findViewById(R.id.buttonAdd);

        addPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addPlayerToList();
                hideKeyboardFrom(nameInput);
            }
        });
    }

    private void createListView() {
        listView = findViewById(R.id.listViewNames);

        names = new ArrayList<>();

        arrayAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, names);
        listView.setAdapter(arrayAdapter);
    }

    private void addPlayerToList() {
        if (nameInput.getText() != null && !nameInput.getText().toString().equals("") && names.size() < 8) {
            names.add(nameInput.getText().toString());
            arrayAdapter.notifyDataSetChanged();

            nameInput.setText("");
        }
    }

    private void hideKeyboardFrom(View view) {
        InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);
    }
}
