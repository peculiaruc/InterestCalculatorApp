package com.pecpaker.interestcalculatorapp.calculator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.pecpaker.interestcalculatorapp.R;

import java.util.Locale;

public class CalculatorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);

        final CalculatorViewModel calculatorViewModel = new ViewModelProvider(this).get(CalculatorViewModel.class);

        TextInputEditText amount = findViewById(R.id.amount_edit_text);
        TextInputEditText months = findViewById(R.id.month_edit_text);
        months.setText("1");

        final TextView calculatedInterest = findViewById(R.id.calculated_interest);

        calculatorViewModel.getCalculatedInterest().observe(this, new Observer<Double>() {
            @Override
            public void onChanged(Double value) {
                String formattedText = String.format(Locale.ROOT, "Your expected interest is: ₦%.2f", value);
                calculatedInterest.setText(formattedText);
            }
        });

        months.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().isEmpty()) {
                    calculatorViewModel.updateYears(s.toString());
                }
            }
        });

        amount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().isEmpty()) {
                    calculatorViewModel.updateSavingsAmount(s.toString());
                }
            }
        });

        Spinner spinner = findViewById(R.id.products_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.product_type,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = (String) parent.getItemAtPosition(position);
                calculatorViewModel.updateSelectedPackage(selectedItem);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}