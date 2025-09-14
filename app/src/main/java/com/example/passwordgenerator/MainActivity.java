package com.example.passwordgenerator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    EditText length_input, password_output;
    CheckBox lower, upper, Number, Symbol;
    Button copy_button, generate_button;
    Random random = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        length_input = findViewById(R.id.input);
        password_output = findViewById(R.id.output_box);
        lower = findViewById(R.id.LowercaseButton);
        upper = findViewById(R.id.UppercaseButton);
        Number = findViewById(R.id.NumberButton);
        Symbol = findViewById(R.id.symbolsButton);
        copy_button = findViewById(R.id.copy_button);
        generate_button = findViewById(R.id.generate_btn);

        // Generate Button Click
        generate_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    String lengthStr = length_input.getText().toString().trim();

                    if (lengthStr.isEmpty()) {
                        Toast.makeText(MainActivity.this, "Enter Password length", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    int length = Integer.parseInt(lengthStr);

                    if (length <= 0) {
                        Toast.makeText(MainActivity.this, "Length must be greater than 0", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    // Character sets
                    String Lower = "abcdefghijklmnopqrstuvwxyz";
                    String Upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
                    String number = "0123456789";
                    String symbol = "!#@$%^&*()_+-/*?<>";

                    // Allowed chars
                    StringBuilder allowedChars = new StringBuilder();

                    if (lower.isChecked()) allowedChars.append(Lower);
                    if (upper.isChecked()) allowedChars.append(Upper);
                    if (Number.isChecked()) allowedChars.append(number);
                    if (Symbol.isChecked()) allowedChars.append(symbol);

                    if (allowedChars.length() == 0) {
                        Toast.makeText(MainActivity.this, "Select at least one option", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    // Generate password
                    StringBuilder password = new StringBuilder();
                    for (int i = 0; i < length; i++) {
                        int index = random.nextInt(allowedChars.length());
                        password.append(allowedChars.charAt(index));
                    }

                    password_output.setText(password.toString());

                } catch (NumberFormatException e) {
                    Toast.makeText(MainActivity.this, "Invalid number format!", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, "Something went wrong: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });

        // Copy Button Click
        copy_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String password = password_output.getText().toString();

                    if (password.isEmpty()) {
                        Toast.makeText(MainActivity.this, "No password to copy", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData clip = ClipData.newPlainText("password", password);
                    clipboard.setPrimaryClip(clip);

                    Toast.makeText(MainActivity.this, "Password copied", Toast.LENGTH_SHORT).show();

                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, "Error copying password: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
