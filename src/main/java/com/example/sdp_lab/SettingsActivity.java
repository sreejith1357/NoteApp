// File: SettingsActivity.java
package com.example.sdp_lab;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

public class SettingsActivity extends AppCompatActivity {

    private RadioGroup themeRadioGroup, textSizeRadioGroup;
    private Button saveButton, cancelButton;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Initialize views
        themeRadioGroup = findViewById(R.id.themeRadioGroup);
        textSizeRadioGroup = findViewById(R.id.textSizeRadioGroup);
        saveButton = findViewById(R.id.saveButton);
        cancelButton = findViewById(R.id.cancelButton);

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences("NotesPrefs", MODE_PRIVATE);

        // Load current settings
        int theme = sharedPreferences.getInt("theme", 0); // 0 for light, 1 for dark
        int textSize = sharedPreferences.getInt("textSize", 1); // 0 for small, 1 for medium, 2 for large

        // Set the selected radio buttons
        if (theme == 0) {
            themeRadioGroup.check(R.id.lightThemeRadio);
        } else {
            themeRadioGroup.check(R.id.darkThemeRadio);
        }

        if (textSize == 0) {
            textSizeRadioGroup.check(R.id.smallTextRadio);
        } else if (textSize == 1) {
            textSizeRadioGroup.check(R.id.mediumTextRadio);
        } else {
            textSizeRadioGroup.check(R.id.largeTextRadio);
        }

        // Set click listener for Save button
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Save theme setting
                int themeId = themeRadioGroup.getCheckedRadioButtonId();
                int newTheme = (themeId == R.id.lightThemeRadio) ? 0 : 1;

                // Save text size setting
                int textSizeId = textSizeRadioGroup.getCheckedRadioButtonId();
                int newTextSize;
                if (textSizeId == R.id.smallTextRadio) {
                    newTextSize = 0;
                } else if (textSizeId == R.id.mediumTextRadio) {
                    newTextSize = 1;
                } else {
                    newTextSize = 2;
                }

                // Save settings to SharedPreferences
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("theme", newTheme);
                editor.putInt("textSize", newTextSize);
                editor.apply();

                // Apply theme change
                if (newTheme == 0) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                }

                Toast.makeText(SettingsActivity.this, "Settings saved", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        // Set click listener for Cancel button
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}