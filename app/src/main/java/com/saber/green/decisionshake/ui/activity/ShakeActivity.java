package com.saber.green.decisionshake.ui.activity;

import android.content.Intent;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.saber.green.decisionshake.R;
import com.squareup.seismic.ShakeDetector;

import androidx.appcompat.app.AppCompatActivity;

public class ShakeActivity extends AppCompatActivity implements ShakeDetector.Listener {

    TextView shakeText;
    Button nextButton;

    SensorManager sensorManager;
    ShakeDetector shakeDetector;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shake);

        shakeText = findViewById(R.id.shake_text_view);
        nextButton = findViewById(R.id.next);
        onNextButtonClick();
        initShake();
    }

    @Override
    public void hearShake() {
        shakeDetector.stop();
        startResultActivity();
    }

    @Override
    public void onResume() {
        super.onResume();
        shakeDetector.start(sensorManager);
    }

    @Override
    public void onPause() {
        super.onPause();
        shakeDetector.stop();
    }

    public void initShake() {
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        shakeDetector = new ShakeDetector(this);
        shakeDetector.setSensitivity(ShakeDetector.SENSITIVITY_LIGHT);
    }

    //TODO remove for final build
    public void onNextButtonClick() {
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startResultActivity();
            }
        });
    }

    private void startResultActivity() {
        Intent intent = new Intent(ShakeActivity.this, ResultActivity.class);
        startActivity(intent);
    }

}
