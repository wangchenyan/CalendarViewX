package me.wcy.calendarviewx;

import android.graphics.drawable.Drawable;

/**
 * Created by hzwangchenyan on 2016/10/21.
 */
class Config {
    private boolean singleMode;

    // 根据控件宽度自适应，不可自定义
    private float dayWidth;
    private float dayHeight;

    private Drawable weekdayBackground;
    private float weekdayTextSize;
    private int weekdayTextColor;

    private float monthTextSize;
    private int monthTextColor;

    private float dayMargin;
    private float dayTextSize;

    private int dayTextColorNormal;
    private int dayTextColorDisabled;
    private int dayTextColorToday;
    private int dayTextColorWeekend;
    private int dayTextColorEndpoint;
    private int dayTextColorMiddle;

    private int dayColorEndpoint;
    private int dayColorMiddle;

    public boolean isSingleMode() {
        return singleMode;
    }

    public void setSingleMode(boolean singleMode) {
        this.singleMode = singleMode;
    }

    public float getDayWidth() {
        return dayWidth;
    }

    public void setDayWidth(float dayWidth) {
        this.dayWidth = dayWidth;
    }

    public float getDayHeight() {
        return dayHeight;
    }

    public void setDayHeight(float dayHeight) {
        this.dayHeight = dayHeight;
    }

    public Drawable getWeekdayBackground() {
        return weekdayBackground;
    }

    public void setWeekdayBackground(Drawable weekdayBackground) {
        this.weekdayBackground = weekdayBackground;
    }

    public float getWeekdayTextSize() {
        return weekdayTextSize;
    }

    public void setWeekdayTextSize(float weekdayTextSize) {
        this.weekdayTextSize = weekdayTextSize;
    }

    public int getWeekdayTextColor() {
        return weekdayTextColor;
    }

    public void setWeekdayTextColor(int weekdayTextColor) {
        this.weekdayTextColor = weekdayTextColor;
    }

    public float getMonthTextSize() {
        return monthTextSize;
    }

    public void setMonthTextSize(float monthTextSize) {
        this.monthTextSize = monthTextSize;
    }

    public int getMonthTextColor() {
        return monthTextColor;
    }

    public void setMonthTextColor(int monthTextColor) {
        this.monthTextColor = monthTextColor;
    }

    public float getDayMargin() {
        return dayMargin;
    }

    public void setDayMargin(float dayMargin) {
        this.dayMargin = dayMargin;
    }

    public float getDayTextSize() {
        return dayTextSize;
    }

    public void setDayTextSize(float dayTextSize) {
        this.dayTextSize = dayTextSize;
    }

    public int getDayTextColorNormal() {
        return dayTextColorNormal;
    }

    public void setDayTextColorNormal(int dayTextColorNormal) {
        this.dayTextColorNormal = dayTextColorNormal;
    }

    public int getDayTextColorDisabled() {
        return dayTextColorDisabled;
    }

    public void setDayTextColorDisabled(int dayTextColorDisabled) {
        this.dayTextColorDisabled = dayTextColorDisabled;
    }

    public int getDayTextColorToday() {
        return dayTextColorToday;
    }

    public void setDayTextColorToday(int dayTextColorToday) {
        this.dayTextColorToday = dayTextColorToday;
    }

    public int getDayTextColorWeekend() {
        return dayTextColorWeekend;
    }

    public void setDayTextColorWeekend(int dayTextColorWeekend) {
        this.dayTextColorWeekend = dayTextColorWeekend;
    }

    public int getDayTextColorEndpoint() {
        return dayTextColorEndpoint;
    }

    public void setDayTextColorEndpoint(int dayTextColorEndpoint) {
        this.dayTextColorEndpoint = dayTextColorEndpoint;
    }

    public int getDayTextColorMiddle() {
        return dayTextColorMiddle;
    }

    public void setDayTextColorMiddle(int dayTextColorMiddle) {
        this.dayTextColorMiddle = dayTextColorMiddle;
    }

    public int getDayColorEndpoint() {
        return dayColorEndpoint;
    }

    public void setDayColorEndpoint(int dayColorEndpoint) {
        this.dayColorEndpoint = dayColorEndpoint;
    }

    public int getDayColorMiddle() {
        return dayColorMiddle;
    }

    public void setDayColorMiddle(int dayColorMiddle) {
        this.dayColorMiddle = dayColorMiddle;
    }
}
