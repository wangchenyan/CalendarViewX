package me.wcy.calendarviewx;

import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by hzwangchenyan on 2016/10/20.
 */
class MonthView extends View {
    private Month month;
    private Day touchingDay;

    public MonthView(Context context) {
        super(context);
    }

    public void setMonth(Month month) {
        this.month = month;
        requestLayout();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        for (Day day : month.getDayList()) {
            day.onMeasure();
        }
        int width = MeasureSpec.getSize(widthMeasureSpec);
        setMeasuredDimension(width, (int) Math.ceil(month.getLastDay().getBound().bottom));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        for (Day day : month.getDayList()) {
            day.draw(canvas);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                for (Day day : month.getDayList()) {
                    if (day.isTouched(event.getX(), event.getY())) {
                        if (day.isExistFlag(Day.FLAG_DISABLED)) {
                            return false;
                        }
                        touchingDay = day;
                        month.getGestureListener().onPressDown(month, touchingDay);
                        break;
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                if (touchingDay != null) {
                    if (touchingDay.isTouched(event.getX(), event.getY())) {
                        month.getGestureListener().onTap(month, touchingDay);
                    } else {
                        month.getGestureListener().onReleaseUp(month, touchingDay);
                    }
                    touchingDay = null;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_OUTSIDE:
                month.getGestureListener().onReleaseUp(month, touchingDay);
                touchingDay = null;
                break;
        }
        return true;
    }
}
