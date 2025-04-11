package com.example.projetnathanjilenkave;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.widget.FrameLayout;

public class ZoomLayout extends FrameLayout {
    private float scaleFactor = 1.0f;
    private ScaleGestureDetector scaleDetector;

    public ZoomLayout(Context context) {
        super(context);
        init(context);
    }

    public ZoomLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        scaleDetector = new ScaleGestureDetector(context, new ScaleListener());
        setWillNotDraw(false);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        scaleDetector.onTouchEvent(event);
        return true;
    }

    @Override
    protected void dispatchDraw(android.graphics.Canvas canvas) {
        canvas.save();
        canvas.scale(scaleFactor, scaleFactor);
        super.dispatchDraw(canvas);
        canvas.restore();
    }

    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            scaleFactor *= detector.getScaleFactor();
            // Limite le zoom entre 0.5x et 3.0x
            scaleFactor = Math.max(0.5f, Math.min(scaleFactor, 3.0f));
            invalidate();
            requestLayout();
            return true;
        }
    }
}