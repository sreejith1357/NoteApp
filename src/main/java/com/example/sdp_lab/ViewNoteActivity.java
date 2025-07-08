package com.example.sdp_lab;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ViewNoteActivity extends AppCompatActivity {

    private TextView titleTextView, contentTextView;
    private Button editButton, backButton;
    private String noteTitle;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_note);

        // Initialize views
        titleTextView = findViewById(R.id.titleTextView);
        contentTextView = findViewById(R.id.contentTextView);
        editButton = findViewById(R.id.editButton);
        backButton = findViewById(R.id.backButton);

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences("NotesPrefs", MODE_PRIVATE);

        // Get the note title from the intent
        noteTitle = getIntent().getStringExtra("noteTitle");

        if (noteTitle != null) {
            // Set the title text
            titleTextView.setText(noteTitle);

            // Get and set the content text
            String content = sharedPreferences.getString("note_" + noteTitle, "");
            contentTextView.setText(content);
        }

        // Set click listener for Edit button
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewNoteActivity.this, EditNoteActivity.class);
                intent.putExtra("noteTitle", noteTitle);
                startActivity(intent);
            }
        });

        // Set click listener for Back button
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Refresh the content when returning to this activity
        if (noteTitle != null) {
            String content = sharedPreferences.getString("note_" + noteTitle, "");
            contentTextView.setText(content);
        }
    }
}