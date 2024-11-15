package com.mirea.kt.ribo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/*
Student46372
gSsuS7i
*/

public class MainActivity extends AppCompatActivity {

    private static final String ADDRESS = "https://android-for-students.ru/coursework/login.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText etLogin = findViewById(R.id.login);
        EditText etPassword = findViewById(R.id.password);
        Button button = findViewById(R.id.enter);

        button.setOnClickListener(v -> {
            String lgn = etLogin.getText().toString();
            String pwd = etPassword.getText().toString();
            if (lgn.equals("admin") && pwd.equals("root")) {
                startActivity(new Intent(getApplicationContext(), OperationActivity.class));
            } else if (!lgn.isEmpty() && !pwd.isEmpty()) {
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("lgn", lgn);
                hashMap.put("pwd", pwd);
                hashMap.put("g", "RIBO-04-22");
                HTTPRunnable httpRunnable = new HTTPRunnable(ADDRESS, hashMap);
                Thread th = new Thread(httpRunnable);
                th.start();
                try {
                    th.join();
                } catch (InterruptedException exception) {
                    throw new RuntimeException(exception);
                } finally {
                    try {
                        JSONObject jsonObject = new JSONObject(httpRunnable.getResponseBody());
                        int result = jsonObject.getInt("result_code");
                        if (result == 1) {
                            Log.i("my_task", "Title: " + jsonObject.getString("title"));
                            Log.i("my_task", "Task: " + jsonObject.getString("task"));
                            Log.i("my_task", "Variant: " + jsonObject.getString("variant"));
                            startActivity(new Intent(getApplicationContext(), OperationActivity.class));
                        } else if (result == -1) {
                            Toast.makeText(getApplicationContext(), R.string.incorrect_data, Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException | NullPointerException exception) {
                        Toast.makeText(getApplicationContext(), R.string.the_server_is_not_responding, Toast.LENGTH_SHORT).show();
                    }
                }
            } else {
                Toast.makeText(getApplicationContext(), R.string.fill_fields, Toast.LENGTH_SHORT).show();
            }
        });
    }
}