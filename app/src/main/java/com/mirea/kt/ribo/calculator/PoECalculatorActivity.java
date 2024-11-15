package com.mirea.kt.ribo.calculator;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.mirea.kt.ribo.R;

public class PoECalculatorActivity extends AppCompatActivity {

    private EditText devicePowerInput, powerPairsInput;
    private Spinner cableTypeSpinner;
    private EditText voltageInput, cableLengthInput, deviceCountInput;
    private Button calculateButton;
    private TextView resultText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poe_calculator);

        cableTypeSpinner = findViewById(R.id.cable_type_spinner);
        voltageInput = findViewById(R.id.voltage_input);
        cableLengthInput = findViewById(R.id.cable_length_input);
        deviceCountInput = findViewById(R.id.device_count_input);
        devicePowerInput = findViewById(R.id.device_power_input);
        powerPairsInput = findViewById(R.id.power_pairs_input);
        calculateButton = findViewById(R.id.calculate_button);
        resultText = findViewById(R.id.result_text);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getApplicationContext(),
                R.array.cable_types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cableTypeSpinner.setAdapter(adapter);
        Log.d("tag", "установлен адаптер");

        calculateButton.setOnClickListener(view -> calculatePoE());
    }

    private void calculatePoE() {
        try {
            String cableType = cableTypeSpinner.getSelectedItem().toString();
            double voltage = Double.parseDouble(voltageInput.getText().toString());
            double cableLength = Double.parseDouble(cableLengthInput.getText().toString());
            int deviceCount = Integer.parseInt(deviceCountInput.getText().toString());
            double devicePower = Double.parseDouble(devicePowerInput.getText().toString());
            int powerPairs = Integer.parseInt(powerPairsInput.getText().toString());

            double resistancePerMeter;
            switch (cableType) {
                case "Cat6":
                    resistancePerMeter = 0.08;
                    break;
                case "Cat6a":
                    resistancePerMeter = 0.06;
                    break;
                default:
                    resistancePerMeter = 0.1;
            }

            double current = devicePower / (voltage / powerPairs);
            Log.d("tag", "посчитана мощность устройства");

            double powerLoss = resistancePerMeter * cableLength * Math.pow(current, 2);
            Log.d("tag", "посчитаны потери");

            double totalPower = deviceCount * (devicePower + powerLoss);
            Log.d("tag", "посчитана общая мощность");

            resultText.setText(String.format("Требуемая мощность: %.2f Вт", totalPower));
        } catch (NumberFormatException e) {
            Toast.makeText(getApplicationContext(), "Пожалуйста, введите все данные.", Toast.LENGTH_SHORT).show();
        }
    }
}
