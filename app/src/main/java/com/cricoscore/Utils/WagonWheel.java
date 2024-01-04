package com.cricoscore.Utils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.cricoscore.Activity.WagonWheelActivity;

public class WagonWheel extends View {

    private static final int SEGMENTS = 8;
    private Paint paint, paint1, paint2, paint3;
    public int selectedSegment = -1;

    private Paint segmentPaint;
    private Paint selectedSegmentPaint;
    //private Paint textPaint;
    private TextPaint textPaint;

    private int GROUND_COLOR = Color.parseColor("#658A00");
    private int PITCH_COLOR = Color.parseColor("#EFC88D");
    private int BOUNDARY_COLOR = Color.parseColor("#FFFFFF");
    private int INNER_BOUNDARY_COLOR = Color.parseColor("#a2b081");
    private int TEXT_COLOR = Color.parseColor("#FFFFFF");
    private int LINE_COLOR = Color.parseColor("#759718");
    private int SELECTED_SIGEMENT_COLOR = Color.parseColor("#456103");

    private OnSegmentClickListener onSegmentClickListener;

    private String[] segmentTexts = {"Deep\n Mid Wicket", "Long On", "Long Off", "Deep Cover", "Deep Point", "Third Man", "Deep\n Fine Leg","Deep\n Square Leg"};


    public WagonWheel(Context context) {
        super(context);
        init();
    }

    public WagonWheel(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(false);

        paint1 = new Paint();
        paint1.setColor(Color.GRAY);
        paint1.setStyle(Paint.Style.STROKE);
        paint1.setStrokeWidth(3);
        paint1.setAntiAlias(true);

        paint2 = new Paint();
        paint2.setColor(Color.GRAY);
        paint2.setStyle(Paint.Style.STROKE);
        paint2.setStrokeWidth(1);
        paint2.setAntiAlias(true);

        paint3 = new Paint();
        paint3.setColor(Color.GRAY);
        paint3.setStyle(Paint.Style.STROKE);
        paint3.setStrokeWidth(1);
        paint3.setAntiAlias(true);

        segmentPaint = new Paint();
        segmentPaint.setStyle(Paint.Style.STROKE);
        segmentPaint.setStrokeWidth(20);
        segmentPaint.setColor(Color.rgb(139, 69, 19)); // Brown

        selectedSegmentPaint = new Paint();
        selectedSegmentPaint.setStyle(Paint.Style.STROKE);
        selectedSegmentPaint.setColor(Color.BLUE);

        textPaint = new TextPaint();
        textPaint.setColor(TEXT_COLOR);
        textPaint.setTextSize(30);
        textPaint.setTextAlign(Paint.Align.CENTER);


        setOnTouchListener((v, event) -> {
            handleTouch(event.getX(), event.getY());
            return true;
        });
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int w = getWidth();
        int h = getHeight();

        int pl = getPaddingLeft();
        int pr = getPaddingRight();
        int pt = getPaddingTop();
        int pb = getPaddingBottom();

        int usableWidth = w - (pl + pr);
        int usableHeight = h - (pt + pb);

        int radiuss = Math.min(usableWidth, usableHeight) / 2;
        int cx = pl + (usableWidth / 2);
        int cy = pt + (usableHeight / 2);


        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;
        int radius = Math.min(centerX, centerY);

        float angle = 360f / SEGMENTS;



        for (int i = 0; i < SEGMENTS; i++) {
            float startAngle = i * angle;
            float endAngle = startAngle + angle;

            if (i == selectedSegment) {
                paint.setColor(SELECTED_SIGEMENT_COLOR);
            } else {
                paint.setColor(GROUND_COLOR);
                paint.setStyle(Paint.Style.FILL_AND_STROKE);

            }
            paint1.setColor(LINE_COLOR);
            paint1.setStyle(Paint.Style.STROKE);


            canvas.drawArc(centerX - radius, centerY - radius, centerX + radius, centerY + radius, startAngle, angle, true, paint);
            canvas.drawArc(centerX - radius, centerY - radius, centerX + radius, centerY + radius, startAngle, angle, true, paint1);

            // Draw text in the center of each segment
            float textX = (float) (centerX + (radius - 150) * Math.cos(Math.toRadians(startAngle + angle / 2)));
            float textY = (float) (centerY + (radius - 150) * Math.sin(Math.toRadians(startAngle + angle / 2)));

            //canvas.drawText(segmentTexts[i], textX, textY, textPaint);
            drawString(canvas,segmentTexts[i],textX,textY,textPaint);


        }

        //pitch on wagon wheel
        Point centerOfCanvas = new Point(getWidth() / 2, getHeight() / 2);
        int rectW = 60;//pitch width
        int rectH = 180;//pitch height
        int left = centerOfCanvas.x - (rectW / 2);
        int top = centerOfCanvas.y - (rectH / 2);
        int right = centerOfCanvas.x + (rectW / 2);
        int bottom = centerOfCanvas.y + (rectH / 2);
        Rect rect = new Rect(left, top, right, bottom);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(PITCH_COLOR);
        canvas.drawRect(rect, paint);

        //boundary
        paint2.setColor(BOUNDARY_COLOR);
        paint2.setStyle(Paint.Style.STROKE);
        paint2.setStrokeWidth(5);
        canvas.drawCircle(cx, cy, (float) (radius * 0.9), paint2);

        paint3.setColor(INNER_BOUNDARY_COLOR);
        paint3.setStyle(Paint.Style.STROKE);
        paint3.setStrokeWidth(2);
        canvas.drawCircle(cx, cy, (float) (radius / 2), paint3);



    }

    public void drawString(Canvas canvas, String text, float x, float y, TextPaint paint) {
        if (text.contains("\n")) {
            String[] texts = text.split("\n");
            for (String txt : texts) {
                canvas.drawText(txt, x, y, paint);
                y += paint.getTextSize();
            }
        } else {
            canvas.drawText(text, x, y, paint);
        }
    }

    private void handleTouch(float x, float y) {
        double angle = Math.toDegrees(Math.atan2(y - getHeight() / 2, x - getWidth() / 2));
        if (angle < 0) {
            angle += 360;
        }

        int segment = (int) (angle / (360.0 / SEGMENTS));
        selectedSegment = segment;
        // Notify the listener with the selected segment text
        if (onSegmentClickListener != null) {
            onSegmentClickListener.onSegmentClick(segmentTexts[selectedSegment]);
        }
        invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                selectedSegment = getTouchedSegment(x, y);
                invalidate();
                return true;
            case MotionEvent.ACTION_UP:
                // Handle click event here
                if (selectedSegment != -1) {
                    // Do something when a segment is clicked
                    Toast.makeText(getContext(), "Segment " + selectedSegment + " clicked", Toast.LENGTH_SHORT).show();
                }
                selectedSegment = -1;
                invalidate();
                return true;
        }

        return super.onTouchEvent(event);
    }

    private int getTouchedSegment(float x, float y) {
        // Calculate the angle based on touch coordinates
        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;

        double angle = Math.toDegrees(Math.atan2(y - centerY, x - centerX));
        if (angle < 0) {
            angle += 360;
        }

        // Determine the segment based on the angle
        int segment = (int) (angle / (360f / SEGMENTS));

        return segment;
    }


    // Interface to handle segment clicks
    public interface OnSegmentClickListener {
        void onSegmentClick(String segmentText);
    }

    // Setter for the click listener
    public void setOnSegmentClickListener(OnSegmentClickListener listener) {
        this.onSegmentClickListener = listener;
    }

    // Method to programmatically set the selected segment
    public void setSelectedSegment(int segmentIndex) {
        if (segmentIndex >= 0 && segmentIndex < SEGMENTS) {
            selectedSegment = segmentIndex;
            invalidate(); // Redraw the view
        }
    }
}