package com.mirea.kt.ribo;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mirea.kt.ribo.calculator.PasswordGeneratorActivity;
import com.mirea.kt.ribo.calculator.PoECalculatorActivity;
import com.mirea.kt.ribo.calculator.SubnetCalculatorActivity;
import com.mirea.kt.ribo.calculator.TransferTimeCalculatorActivity;
import com.mirea.kt.ribo.model.CalculationAdapter;
import com.mirea.kt.ribo.model.CalculationItem;

import java.util.ArrayList;
import java.util.List;

public class OperationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operation);

        RecyclerView calculationRecyclerView = findViewById(R.id.calculation_recycler_view);
        calculationRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        List<CalculationItem> calculationItemList = new ArrayList<>();
        calculationItemList.add(new CalculationItem("Подсеть"));
        calculationItemList.add(new CalculationItem("Время передачи"));
        calculationItemList.add(new CalculationItem("Пароль"));
        calculationItemList.add(new CalculationItem("PoE"));

        CalculationAdapter adapter = new CalculationAdapter(getApplicationContext(), calculationItemList, position -> {
            Intent intent;
            switch (position) {
                case 0:
                    intent = new Intent(getApplicationContext(), SubnetCalculatorActivity.class);
                    break;
                case 1:
                    intent = new Intent(getApplicationContext(), TransferTimeCalculatorActivity.class);
                    break;
                case 2:
                    intent = new Intent(getApplicationContext(), PasswordGeneratorActivity.class);
                    break;
                case 3:
                    intent = new Intent(getApplicationContext(), PoECalculatorActivity.class);
                    break;
                default:
                    return;
            }
            startActivity(intent);
        });

        calculationRecyclerView.setAdapter(adapter);
    }
}