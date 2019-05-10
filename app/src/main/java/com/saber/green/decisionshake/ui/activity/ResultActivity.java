package com.saber.green.decisionshake.ui.activity;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.saber.green.decisionshake.R;
import com.saber.green.decisionshake.entity.Note;
import com.saber.green.decisionshake.utils.VibrationUtils;
import com.saber.green.decisionshake.viewmodel.ResultViewModel;

public class ResultActivity extends AppCompatActivity {

    ResultViewModel resultViewModel;
    VibrationUtils vibrationUtils;
    TextView resultText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        resultViewModel = ViewModelProviders.of(this).get(ResultViewModel.class);
        resultText = findViewById(R.id.result);

        vibrationUtils = new VibrationUtils(this);
        vibrationUtils.vibrate();

        observeRandomNoteChange();
    }

    public void observeRandomNoteChange(){
        resultViewModel.getRandomNote().observe(this, new Observer<Note>() {
            @Override
            public void onChanged(Note note) {
                resultText.setText(note.getTitle());
            }
        });
    }

}
