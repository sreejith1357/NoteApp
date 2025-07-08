// File: CreateNoteActivity.java
package com.example.sdp_lab;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.HashSet;
import java.util.Set;

public class CreateNoteActivity extends AppCompatActivity {

    private EditText titleEditText, contentEditText;
    private Button saveButton, cancelButton;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_note);

        // Initialize views
        titleEditText = findViewById(R.id.titleEditText);
        contentEditText = findViewById(R.id.contentEditText);
        saveButton = findViewById(R.id.saveButton);
        cancelButton = findViewById(R.id.cancelButton);

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences("NotesPrefs", MODE_PRIVATE);

        // Set click listener for Save button
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = titleEditText.getText().toString().trim();
                String content = contentEditText.getText().toString().trim();

                if (title.isEmpty()) {
                    Toast.makeText(CreateNoteActivity.this, "Please enter a title", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Save the note
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("note_" + title, content);

                // Add the title to the set of all note titles
                Set<String> titles = new HashSet<>(sharedPreferences.getStringSet("noteTitles", new HashSet<>()));
                titles.add(title);
                editor.putStringSet("noteTitles", titles);
                editor.apply();

                Toast.makeText(CreateNoteActivity.this, "Note saved", Toast.LENGTH_SHORT).show();
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
