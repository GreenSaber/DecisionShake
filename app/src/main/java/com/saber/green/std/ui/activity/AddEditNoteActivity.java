package com.saber.green.std.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.muddzdev.styleabletoast.StyleableToast;
import com.saber.green.std.R;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddEditNoteActivity extends AppCompatActivity {

    public static final String EXTRA_OPTION = "com.saber.green.std.EXTRA_OPTION";
    public static final String EXTRA_ID = "com.saber.green.std.EXTRA_ID";

    public static final int RECOGNIZE_SPEECH_REQUEST = 201;

    @BindView(R.id.text_input_edit_text) TextInputEditText textInputEditText;
    @BindView(R.id.text_input_layout) TextInputLayout textInputLayout;
    @BindView(R.id.button_save_note) Button buttonSaveNote;
    @BindView(R.id.button_micro) Button buttonMicro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        ButterKnife.bind(this);

        onButtonSaveClick();
        onButtonMicroClick();

        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_ID)) {
            textInputEditText.setText(intent.getStringExtra(EXTRA_OPTION));
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
            StyleableToast.makeText(this, getString(R.string.toast_device_not_support), Toast.LENGTH_LONG, R.style.customToastTop).show();

        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RECOGNIZE_SPEECH_REQUEST && resultCode == RESULT_OK && data != null) {
            ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            textInputEditText.setText(result.get(0));
        } else {
            StyleableToast.makeText(this, getString(R.string.toast_failed_recognize), Toast.LENGTH_LONG, R.style.customToastTop).show();
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
        String option = textInputEditText.getText().toString();
        if (option.trim().isEmpty()) {
            StyleableToast.makeText(this, getString(R.string.toast_insert_option), Toast.LENGTH_LONG, R.style.customToastTop).show();
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
        overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_right);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_right);
    }
}
