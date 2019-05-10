package com.saber.green.decisionshake.ui.activity;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
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
        vibrationUtils = new VibrationUtils(this);
        vibrationUtils.vibrate();
        resultText = findViewById(R.id.result);
        resultViewModel = ViewModelProviders.of(this).get(ResultViewModel.class);
        resultViewModel.getRandomNote().observe(this, new Observer<Note>() {
            @Override
            public void onChanged(Note note) {
                resultText.setText(note.getTitle());
            }
        });
    }


}
