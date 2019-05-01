package com.saber.green.decisionshake.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.saber.green.decisionshake.R;

import androidx.appcompat.app.AppCompatActivity;

public class ShakeActivity extends AppCompatActivity {

    TextView shakeText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shake);
        shakeText.findViewById(R.id.shake_text_view);
    }


}
