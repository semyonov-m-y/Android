package ru.semenovmy.learning.animation;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class SwingsProgressView extends View {

    private int angle;
    private int maxAngle;
    private int minAngleColor;
    private int middleAngleColor;
    private int maxAngleColor;
    private float radius;
    private Paint circlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private RectF progressRect = new RectF(0, 0, 700, 700);

    public SwingsProgressView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(200, 200);
        canvas.drawArc(progressRect, 90f, angle * 360 / maxAngle, false, circlePaint);
        configureCirclePaint();

        radius = progressRect.height() / 2;

        double angle = (2 * Math.PI) / maxAngle * this.angle + Math.PI / 2;
        float x = (float) (radius * Math.cos(angle));
        float y = (float) (radius * Math.sin(angle));

        canvas.drawText("Max: " + maxAngle + " Current: " + this.angle, progressRect.width() / 2 - 250f, progressRect.height() + 150f, textPaint);
    }

    private void configureCirclePaint() {
        circlePaint.setStrokeWidth(64f);
        circlePaint.setStyle(Paint.Style.STROKE);

        if (angle <= 50) {
            circlePaint.setColor(minAngleColor);
        } else if (angle > 50 && angle <= 100) {
            circlePaint.setColor(middleAngleColor);
        } else if (angle > 100) {
            circlePaint.setColor(maxAngleColor);
        }

        invalidate();
    }

    private void init(@NonNull Context context, @Nullable AttributeSet attrs) {
        final Resources.Theme theme = context.getTheme();
        final TypedArray typedArray = theme.obtainStyledAttributes(attrs, R.styleable.SwingsProgressView, R.attr.angle_progress_style, 0);

        try {
            angle = typedArray.getInteger(R.styleable.SwingsProgressView_angle, 0);
            maxAngle = typedArray.getInteger(R.styleable.SwingsProgressView_maxAngle, 0);
            minAngleColor = typedArray.getColor(R.styleable.SwingsProgressView_minAngleColor, 0);
            middleAngleColor = typedArray.getColor(R.styleable.SwingsProgressView_middleAngleColor, 0);
            maxAngleColor = typedArray.getColor(R.styleable.SwingsProgressView_maxAngleColor, 0);
        } finally {
            typedArray.recycle();
        }

        textPaint.setTextSize(50f);
        textPaint.setColor(Color.RED);
    }

    public int getAngle() {
        return angle;
    }

    public void setAngle(int angle) {
        this.angle = angle;
    }

    public int getMinAngleColor() {
        return minAngleColor;
    }

    public void setMinAngleColor(int minAngleColor) {
        this.minAngleColor = minAngleColor;
    }

    public int getMiddleAngleColor() {
        return middleAngleColor;
    }

    public void setMiddleAngleColor(int middleAngleColor) {
        this.middleAngleColor = middleAngleColor;
    }

    public int getMaxAngle() {
        return maxAngle;
    }

    public void setMaxAngle(int maxAngle) {
        this.maxAngle = maxAngle;
    }

    public int getMaxAngleColor() {
        return maxAngleColor;
    }

    public void setMaxAngleColor(int maxAngleColor) {
        this.maxAngleColor = maxAngleColor;
    }
}
