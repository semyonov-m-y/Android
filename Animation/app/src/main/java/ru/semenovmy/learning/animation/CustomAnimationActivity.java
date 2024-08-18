package ru.semenovmy.learning.animation;

import android.animation.AnimatorInflater;
import android.animation.ObjectAnimator;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class CustomAnimationActivity extends AppCompatActivity {

    private ObjectAnimator mAnimator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_animation);
        setTitle(R.string.custom_view_title);

        SwingsProgressView swingsProgressView = findViewById(R.id.swings_progress_view);
        mAnimator = (ObjectAnimator) AnimatorInflater.loadAnimator(this, R.animator.custom_view_animator);
        mAnimator.setTarget(swingsProgressView);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAnimator.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAnimator.end();
    }
}

