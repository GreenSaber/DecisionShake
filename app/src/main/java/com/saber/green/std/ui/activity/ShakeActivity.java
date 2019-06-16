package com.saber.green.std.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.saber.green.std.R;
import com.saber.green.std.listeners.DoubleClickListener;
import com.saber.green.std.utils.ShakeUtils;
import com.squareup.seismic.ShakeDetector;

import androidx.appcompat.app.AppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShakeActivity extends AppCompatActivity {

    @BindView(R.id.shake_text_view) TextView shakeText;
    @BindView(R.id.phoneView) ImageView phoneView;
    @BindView(R.id.shakeView) ImageView shakeView;
    ShakeUtils shakeUtils;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shake);
        ButterKnife.bind(this);

        initShakeUtils();
        onPhoneImageClick();
        onShakeImageClick();
        animatePhoneShake();
    }

    @Override
    public void onResume() {
        super.onResume();
        shakeUtils.startHearShake();
    }

    @Override
    public void onPause() {
        super.onPause();
        shakeUtils.stopHearShake();
    }

    public void onPhoneImageClick() {
        phoneView.setOnClickListener(new DoubleClickListener() {
            @Override
            public void onDoubleClick(View view) {
                startResultActivity();
            }
        });
    }

    public void onShakeImageClick() {
        shakeView.setOnClickListener(new DoubleClickListener() {
            @Override
            public void onDoubleClick(View view) {
                startResultActivity();
            }
        });
    }

    public void initShakeUtils() {
        shakeUtils = new ShakeUtils(this, new ShakeDetector.Listener() {
            @Override
            public void hearShake() {
                shakeUtils.stopHearShake();
                startResultActivity();
            }
        });
    }

    public void startResultActivity() {
        Intent intent = new Intent(ShakeActivity.this, ResultActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(ShakeActivity.this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_right);
    }

    public void animatePhoneShake() {
        Animation shakeAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_shake);
        phoneView.setAnimation(shakeAnimation);
    }
}
