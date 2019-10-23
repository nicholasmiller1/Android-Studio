package com.example.demo1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Spinner spin;
    EditText editText;
    String valueString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createSpinner();
        createEditText();
    }

    private void createSpinner() {
        spin = findViewById(R.id.spinnerTip);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.tip_values, android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(adapter);
        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (!editText.getText().toString().matches("")) {
                    valueString = editText.getText().toString();
                }
                else {
                    valueString = "0.00";
                }
                ensureDecimal(valueString);
                updateTotal(Double.parseDouble(valueString));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void createEditText() {
        editText = findViewById(R.id.editTextBill);
        editText.setOnKeyListener(new View.OnKeyListener() {

            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (!editText.getText().toString().matches("")) {
                    valueString = editText.getText().toString();
                }
                else {
                    valueString = "0.00";
                }
                ensureDecimal(valueString);
                updateTotal(Double.parseDouble(valueString));
                return false;
            }
        });
    }

    public void updateTotal(Double bill) {
        String spinnerPercent = spin.getSelectedItem().toString();
        double percentage = Double.parseDouble(spinnerPercent.substring(0, spinnerPercent.length()-1)) / 100;
        double totalAmount = Math.floor(((percentage + 1) * bill) * 100) / 100;
        TextView totalBox = findViewById(R.id.textViewTotalAmount);
        totalBox.setText("$" + String.valueOf(totalAmount));
    }

    private void ensureDecimal(String valueStr) {
        if (valueStr.equals(".")) {
            valueString = "0.";
            valueStr = "0.";
        }
        double value = Double.parseDouble(valueString);
        value = Math.floor(value * 100) / 100;
        if (valueStr.contains(".")) {
            int numOfDecimalPlaces = valueString.length() - valueString.indexOf(".") - 1;
            if (numOfDecimalPlaces > 2) {
                int selection = editText.getSelectionStart() - 1;
                String valueStrTest;
                if (value > 0) {
                    valueStrTest = String.valueOf(value);
                }
                else {
                    valueStrTest = String.valueOf(value) + "0";
                }
                editText.setText(valueStrTest);
                editText.setSelection(selection == valueStrTest.length() ? selection : selection + 1);
            }
        }
    }
}
