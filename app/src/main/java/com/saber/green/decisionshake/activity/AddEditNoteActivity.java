package com.saber.green.decisionshake.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.saber.green.decisionshake.R;

import androidx.appcompat.app.AppCompatActivity;

public class AddEditNoteActivity extends AppCompatActivity {

    public static final String EXTRA_OPTION = "com.saber.green.decisionshake.EXTRA_OPTION";
    public static final String EXTRA_ID = "com.saber.green.decisionshake.EXTRA_ID";

    public EditText editTextOption;
    public Button buttonSaveNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);



        editTextOption = findViewById(R.id.edit_text_option);
        buttonSaveNote = findViewById(R.id.button_save_note);
        buttonSaveNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveNote();
            }
        });

        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_ID)) {
            editTextOption.setText(intent.getStringExtra(EXTRA_OPTION));
        }
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
