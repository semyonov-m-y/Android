package ru.semenovmy.learning.animation;

import androidx.annotation.IdRes;
import androidx.appcompat.app.AppCompatActivity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        handleClick(R.id.animation_drawable, AnimationDrawableActivity.class);
        handleClick(R.id.view_animations, ViewAnimationActivity.class);
        handleClick(R.id.value_animations, ValueAnimationActivity.class);
        handleClick(R.id.object_animations, ObjectAnimationActivity.class);
        handleClick(R.id.custom_view_animations, CustomAnimationActivity.class);
    }

    private void handleClick(@IdRes int viewId, Class<? extends Activity> activityClass) {
        findViewById(viewId).setOnClickListener(
            v -> startActivity(new Intent(MainActivity.this, activityClass))
        );
    }
}
