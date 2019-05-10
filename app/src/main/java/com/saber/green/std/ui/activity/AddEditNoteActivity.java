package com.saber.green.std.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.saber.green.std.R;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Locale;

public class AddEditNoteActivity extends AppCompatActivity {

    public static final String EXTRA_OPTION = "com.saber.green.std.EXTRA_OPTION";
    public static final String EXTRA_ID = "com.saber.green.std.EXTRA_ID";

    public static final int RECOGNIZE_SPEECH_REQUEST = 201;

    private EditText editTextOption;
    private Button buttonSaveNote;
    private ImageButton buttonMicro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        editTextOption = findViewById(R.id.edit_text_option);
        buttonSaveNote = findViewById(R.id.button_save_note);
        buttonMicro = findViewById(R.id.button_micro);

        onButtonSaveClick();
        onButtonMicroClick();

        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_ID)) {
            editTextOption.setText(intent.getStringExtra(EXTRA_OPTION));
        }
    }

    public void onButtonMicroClick() {
        buttonMicro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSpeechInput();
            }
        });
    }

    private void getSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, Locale.getDefault());
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, RECOGNIZE_SPEECH_REQUEST);
        } else {
            Toast.makeText(this, "Your device doesn't support speech input", Toast.LENGTH_LONG).show();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RECOGNIZE_SPEECH_REQUEST && resultCode == RESULT_OK && data != null) {
            ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            editTextOption.setText(result.get(0));
        } else {
            Toast.makeText(this, "Failed to recognize speech", Toast.LENGTH_LONG).show();
        }

    }

    public void onButtonSaveClick() {
        buttonSaveNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveNote();
            }
        });
    }

    private void saveNote() {
        String option = editTextOption.getText().toString();
        if (option.trim().isEmpty()) {
            Toast.makeText(this, "Please insert your option", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent();
        intent.putExtra(EXTRA_OPTION, option);

        int id = getIntent().getIntExtra(EXTRA_ID, -1);
        if (id != -1) {
            intent.putExtra(EXTRA_ID, id);
        }

        setResult(RESULT_OK, intent);
        finish();
    }
}
