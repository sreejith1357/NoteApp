// File: MainActivity.java
package com.example.sdp_lab;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private ArrayList<String> noteTitles;
    private ArrayAdapter<String> adapter;
    private Button createNoteButton, settingsButton;
    private ListView notesListView;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize views
        createNoteButton = findViewById(R.id.createNoteButton);
        settingsButton = findViewById(R.id.settingsButton);
        notesListView = findViewById(R.id.notesListView);

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences("NotesPrefs", MODE_PRIVATE);

        // Initialize notes list
        noteTitles = new ArrayList<>();
        Set<String> savedTitles = sharedPreferences.getStringSet("noteTitles", new HashSet<>());
        noteTitles.addAll(savedTitles);

        // Set up adapter for ListView
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, noteTitles);
        notesListView.setAdapter(adapter);

        // Set click listener for Create Note button
        createNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CreateNoteActivity.class);
                startActivity(intent);
            }
        });

        // Set click listener for Settings button
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });

        // Set click listener for notes in the list
        notesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedTitle = noteTitles.get(position);
                Intent intent = new Intent(MainActivity.this, ViewNoteActivity.class);
                intent.putExtra("noteTitle", selectedTitle);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Refresh the notes list when returning to this activity
        noteTitles.clear();
        Set<String> savedTitles = sharedPreferences.getStringSet("noteTitles", new HashSet<>());
        noteTitles.addAll(savedTitles);
        adapter.notifyDataSetChanged();
    }
}