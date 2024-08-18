package ru.semenovmy.learning.animation;

import androidx.appcompat.app.AppCompatActivity;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.widget.ImageView;

public class AnimationDrawableActivity extends AppCompatActivity {

    private AnimationDrawable animationDrawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation_drawable);

        final ImageView imageViewHorse = findViewById(R.id.horse);

        animationDrawable = (AnimationDrawable) imageViewHorse.getDrawable();
    }

    @Override
    protected void onStart() {
        super.onStart();
        animationDrawable.start();
    }
}
