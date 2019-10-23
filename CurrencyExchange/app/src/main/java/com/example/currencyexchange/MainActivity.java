package com.example.currencyexchange;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private EditText input;
    private TextView output;
    private String valueString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createInput();
        createOutput();
    }

    private void createInput() {
        input = findViewById(R.id.editTextUSD);
        input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!input.getText().toString().matches("")) {
                    valueString = input.getText().toString();
                }
                else {
                    valueString = "0.00";
                }
                ensureDecimal(valueString);
                updateTotal(valueString);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void createOutput() {
        output = findViewById(R.id.textViewEuroResult);
    }

    private void updateTotal(String valueString) {
        double value = Double.parseDouble(valueString);
        double result = value * 0.913334;
        result = Math.floor(result * 100) / 100;
        output.setText("$" + String.valueOf(result));
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
                int selection = input.getSelectionStart() - 1;
                String valueStrTest = String.valueOf(value);
                valueStrTest = String.format("%d.%02d", (int) Math.floor(value), Integer.parseInt(valueStrTest.substring(valueStrTest.indexOf(".")+1)));
                input.setText(valueStrTest);
                input.setSelection(selection == valueStrTest.length() ? selection : selection + 1);
            }
        }
        if (value > 9999999.99) {
            valueStr = valueString.substring(0, 7);
            if (valueString.contains(".")) {
                valueStr += valueString.substring(valueString.indexOf("."));
            }
            valueString = valueStr;
            value = Math.floor(value * 100) / 100;
            int selection = input.getSelectionStart() - 1;
            input.setText(valueStr);
            input.setSelection(selection);
        }
    }
}
