// File: EditNoteActivity.java
package com.example.sdp_lab;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class EditNoteActivity extends AppCompatActivity {

    private TextView titleTextView;
    private EditText contentEditText;
    private Button saveButton, cancelButton;
    private String noteTitle;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);

        // Initialize views
        titleTextView = findViewById(R.id.titleTextView);
        contentEditText = findViewById(R.id.contentEditText);
        saveButton = findViewById(R.id.saveButton);
        cancelButton = findViewById(R.id.cancelButton);

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences("NotesPrefs", MODE_PRIVATE);

        // Get the note title from the intent
        noteTitle = getIntent().getStringExtra("noteTitle");

        if (noteTitle != null) {
            // Set the title text
            titleTextView.setText(noteTitle);

            // Get and set the content text for editing
            String content = sharedPreferences.getString("note_" + noteTitle, "");
            contentEditText.setText(content);
        }

        // Set click listener for Save button
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = contentEditText.getText().toString().trim();

                // Save the updated content
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("note_" + noteTitle, content);
                editor.apply();

                Toast.makeText(EditNoteActivity.this, "Note updated", Toast.LENGTH_SHORT).show();
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