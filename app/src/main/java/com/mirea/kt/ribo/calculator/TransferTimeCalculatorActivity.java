package com.mirea.kt.ribo.calculator;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.mirea.kt.ribo.R;


// https://planetcalc.ru/8195/


public class TransferTimeCalculatorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer_time);

        EditText dataSizeInput = findViewById(R.id.input_data_size);
        Spinner dataUnitSpinner = findViewById(R.id.spinner_data_unit);
        EditText speedInput = findViewById(R.id.input_transfer_speed);
        Spinner speedUnitSpinner = findViewById(R.id.spinner_speed_unit);
        Button calculateButton = findViewById(R.id.calculate_button);
        TextView transferTimeOutput = findViewById(R.id.output_transfer_time);

        String[] dataUnits = getResources().getStringArray(R.array.data_units);
        String[] speedUnits = getResources().getStringArray(R.array.speed_units);
        Log.d("tag", "получены единицы измерения");

        String[] dataConversionFactors = getResources().getStringArray(R.array.data_conversion_factors);
        String[] speedConversionFactors = getResources().getStringArray(R.array.speed_conversion_factors);
        Log.d("tag", "получены числа для перевода из единиц измерения");

        ArrayAdapter<String> dataUnitAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, dataUnits);
        dataUnitAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataUnitSpinner.setAdapter(dataUnitAdapter);
        Log.d("tag", "установлен адаптер");

        ArrayAdapter<String> speedUnitAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, speedUnits);
        speedUnitAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        speedUnitSpinner.setAdapter(speedUnitAdapter);
        Log.d("tag", "установлен адаптер");

        calculateButton.setOnClickListener(v -> {
            String dataSizeStr = dataSizeInput.getText().toString();
            String speedStr = speedInput.getText().toString();

            if (dataSizeStr.isEmpty() || speedStr.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Введите объём данных и скорость передачи.", Toast.LENGTH_SHORT).show();
                return;
            }

            double dataSize = Double.parseDouble(dataSizeStr);
            double speed = Double.parseDouble(speedStr);

            int selectedDataUnitIndex = dataUnitSpinner.getSelectedItemPosition();
            int selectedSpeedUnitIndex = speedUnitSpinner.getSelectedItemPosition();

            double dataSizeInBits = dataSize * Long.parseLong(dataConversionFactors[selectedDataUnitIndex]);

            double speedInBps = speed * Long.parseLong(speedConversionFactors[selectedSpeedUnitIndex]);

            double transferTime = calculateTransferTime(dataSizeInBits, speedInBps);
            String result = String.format("Время передачи: %.2f сек", transferTime);
            transferTimeOutput.setText(result);
        });
    }

    private double calculateTransferTime(double dataSizeInBits, double speedBps) {
        return dataSizeInBits / speedBps;
    }
}
