package com.mirea.kt.ribo.calculator;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.mirea.kt.ribo.R;

import java.security.SecureRandom;

public class PasswordGeneratorActivity extends AppCompatActivity {

    private static final String UPPER_CASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String LOWER_CASE = "abcdefghijklmnopqrstuvwxyz";
    private static final String DIGITS = "0123456789";
    private static final String SPECIAL_CHARACTERS = "!@#$%^&*()-_=+<>?";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_generator);

        EditText passwordOutput = findViewById(R.id.password_output);
        EditText passwordLengthOutput = findViewById(R.id.password_length_output);
        Button generateButton = findViewById(R.id.generate_button);
        Button copyButton = findViewById(R.id.copy_button);

        generateButton.setOnClickListener(v -> {
            Log.d("tag", "Нажата кнопка");
            String lengthStr = passwordLengthOutput.getText().toString();
            String password;
            if (!lengthStr.isEmpty()) {
                password = generatePassword(Integer.parseInt(lengthStr));
            } else {
                password = generatePassword(12);
            }
            Log.d("tag", "Сгенерирован пароль");
            passwordOutput.setText(password);
        });

        copyButton.setOnClickListener(v -> {
            String password = passwordOutput.getText().toString();
            if (!password.isEmpty()) {
                copyToClipboard(password);
                Toast.makeText(getApplicationContext(), "Пароль скопирован в буфер обмена", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), "Сначала сгенерируйте пароль", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String generatePassword(int length) {
        String characterSet = UPPER_CASE + LOWER_CASE + DIGITS + SPECIAL_CHARACTERS;
        StringBuilder password = new StringBuilder(length);
        SecureRandom random = new SecureRandom();

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characterSet.length());
            password.append(characterSet.charAt(index));
        }

        return password.toString();
    }

    private void copyToClipboard(String text) {
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("Generated Password", text);
        clipboard.setPrimaryClip(clip);
        Log.d("tag", "Пароль скопирован");
    }
}
