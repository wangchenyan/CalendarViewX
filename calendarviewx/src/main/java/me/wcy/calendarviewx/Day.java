package me.wcy.calendarviewx;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.text.TextPaint;

import java.util.Calendar;

/**
 * Created by hzwangchenyan on 2016/10/20.
 */
class Day {
    // 标记:周末
    public static final int FLAG_WEEKEND = 1;
    // 标记:已过期
    public static final int FLAG_DISABLED = 1 << 1;
    // 标记:正在被按下
    public static final int FLAG_TOUCHING = 1 << 2;
    // 标记:是否是今天
    public static final int FLAG_TODAY = 1 << 3;
    // 标记:是否是一周的第一天
    public static final int FLAG_FIRST_DAY_OF_WEEK = 1 << 4;
    // 标记:是否是一周的最后一天
    public static final int FLAG_LAST_DAY_OF_WEEK = 1 << 5;
    // 标记:是否是一月的第一天
    public static final int FLAG_FIRST_DAY_OF_MONTH = 1 << 6;
    // 标记:是否是一月的最后一天
    public static final int FLAG_LAST_DAY_OF_MONTH = 1 << 7;

    private static final int BITS_COUNT_PER_ATTR = 4;
    private static final int SHIFT_CHECK_STATE = 0xA;
    // 选中状态:普通
    public static final int CHECK_STATE_NORMAL = 0;
    // 选中状态:单选选中
    public static final int CHECK_STATE_SINGLE = 1;
    // 选中状态:开始
    public static final int CHECK_STATE_START = 2;
    // 选中状态:开始和结束之间的日子
    public static final int CHECK_STATE_MIDDLE = 3;
    // 选中状态:结束
    public static final int CHECK_STATE_END = 4;

    private static final TextPaint textPaint;
    private static final Paint endpointBgPaint;
    private static final Paint middleBgPaint;

    static {
        textPaint = new TextPaint();
        textPaint.setAntiAlias(true);
        textPaint.setTextAlign(Paint.Align.CENTER);

        endpointBgPaint = new Paint(textPaint);
        middleBgPaint = new Paint(textPaint);
    }

    private final RectF bound = new RectF();
    private final Calendar calendar;
    private final Config config;
    private final int rowOfMonth;
    private int flag;

    public Day(Calendar calendar, Config config, int rowOfMonth) {
        this.calendar = calendar;
        this.config = config;
        this.rowOfMonth = rowOfMonth;
    }

    public void onMeasure() {
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        float left = config.getDayWidth() * (dayOfWeek - 1);
        float right = config.getDayWidth() * dayOfWeek;
        float top = config.getDayHeight() * rowOfMonth;
        float bottom = top + config.getDayHeight();
        bound.set(left, top, right, bottom);
    }

    public void draw(Canvas canvas) {
        float minLength = Math.min(bound.width(), bound.height());
        float radius = minLength / 2 - config.getDayMargin();
        textPaint.setTextSize(config.getDayTextSize());
        endpointBgPaint.setColor(config.getDayColorEndpoint());
        middleBgPaint.setColor(config.getDayColorMiddle());
        switch (getCheckState()) {
            case CHECK_STATE_NORMAL:
                if (isExistFlag(FLAG_DISABLED)) {
                    textPaint.setColor(config.getDayTextColorDisabled());
                } else if (isExistFlag(FLAG_TODAY)) {
                    textPaint.setColor(config.getDayTextColorToday());
                } else if (isExistFlag(FLAG_WEEKEND)) {
                    textPaint.setColor(config.getDayTextColorWeekend());
                } else {
                    textPaint.setColor(config.getDayTextColorNormal());
                }
                break;
            case CHECK_STATE_SINGLE:
                textPaint.setColor(config.getDayTextColorEndpoint());
                canvas.drawCircle(bound.centerX(), bound.centerY(), radius, endpointBgPaint);
                break;
            case CHECK_STATE_START:
                textPaint.setColor(config.getDayTextColorEndpoint());
                canvas.drawRect(bound.centerX(), bound.centerY() - radius, bound.right, bound.centerY() + radius, middleBgPaint);
                canvas.drawCircle(bound.centerX(), bound.centerY(), radius, endpointBgPaint);
                break;
            case CHECK_STATE_MIDDLE:
                textPaint.setColor(config.getDayTextColorMiddle());
                canvas.drawRect(bound.left, bound.centerY() - radius, bound.right, bound.centerY() + radius, middleBgPaint);
                break;
            case CHECK_STATE_END:
                textPaint.setColor(config.getDayTextColorEndpoint());
                canvas.drawRect(bound.left, bound.centerY() - radius, bound.centerX(), bound.centerY() + radius, middleBgPaint);
                canvas.drawCircle(bound.centerX(), bound.centerY(), radius, endpointBgPaint);
                break;
        }
        Paint.FontMetrics fontMetrics = textPaint.getFontMetrics();
        float textHeight = fontMetrics.descent - fontMetrics.ascent;
        canvas.drawText(dayOfMonth(), bound.centerX(), bound.centerY() + textHeight / 2 - fontMetrics.descent, textPaint);
    }

    public RectF getBound() {
        return bound;
    }

    public Calendar getCalendar() {
        return calendar;
    }

    public void addFlag(int f) {
        flag |= f;
    }

    public void removeFlag(int f) {
        flag &= ~f;
    }

    public boolean isExistFlag(int f) {
        return (flag & f) != 0;
    }

    public void setCheckState(int state) {
        flag &= ~(getBitsPerAttr() << SHIFT_CHECK_STATE);
        flag |= state << SHIFT_CHECK_STATE;
    }

    public int getCheckState() {
        return flag >> SHIFT_CHECK_STATE & getBitsPerAttr();
    }

    public boolean isTouched(float localX, float localY) {
        return bound.contains(localX, localY);
    }

    private String dayOfMonth() {
        return String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
    }

    private int getBitsPerAttr() {
        return ~(-1 << BITS_COUNT_PER_ATTR);
    }
}
