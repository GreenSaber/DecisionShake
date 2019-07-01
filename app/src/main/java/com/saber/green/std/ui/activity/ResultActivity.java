package com.saber.green.std.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.saber.green.std.R;
import com.saber.green.std.entity.Note;
import com.saber.green.std.utils.VibrationUtils;
import com.saber.green.std.viewmodel.ResultViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ResultActivity extends AppCompatActivity {

    ResultViewModel resultViewModel;
    VibrationUtils vibrationUtils;

    @BindView(R.id.result) TextView resultText;
    @BindView(R.id.button_shake_again) ImageButton shakeAgainButton;
    @BindView(R.id.button_restart) Button restartButton;
    @BindView(R.id.adView) AdView adView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        ButterKnife.bind(this);

        AdRequest adRequest = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build();
        adView.loadAd(adRequest);

        resultViewModel = ViewModelProviders.of(this).get(ResultViewModel.class);
        vibrationUtils = new VibrationUtils(this);

        vibrationUtils.vibrate();

        observeRandomNoteChange();
        onShakeAgainButtonClick();
        onRestartButtonClick();
    }

    public void observeRandomNoteChange() {
        resultViewModel.getRandomNote().observe(this, new Observer<Note>() {
            @Override
            public void onChanged(Note note) {
                resultText.setText(note.getTitle());
            }
        });
    }

    public void onShakeAgainButtonClick() {
        shakeAgainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ResultActivity.this, ShakeActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_right);
            }
        });
    }

    public void onRestartButtonClick() {
        restartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ResultActivity.this, MainActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(ResultActivity.this, ShakeActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_right);
    }

}
