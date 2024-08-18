package ru.semenovmy.learning.animation;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

public class ViewAnimationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_animation);

        final Animation animation = AnimationUtils.loadAnimation(this, R.animator.rotate_and_scale);
        final ImageView imageView = findViewById(R.id.view);

        imageView.setOnClickListener(v -> {
            imageView.clearAnimation();
            imageView.startAnimation(animation);
        });

        Toast.makeText(this, R.string.toast_text, Toast.LENGTH_LONG).show();
    }
}
