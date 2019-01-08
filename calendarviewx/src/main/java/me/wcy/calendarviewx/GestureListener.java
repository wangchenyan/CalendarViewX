package me.wcy.calendarviewx;

/**
 * Created by hzwangchenyan on 2016/10/20.
 */
interface GestureListener {
    /**
     * 点选一某一天
     */
    public void onTap(Month month, Day day);

    /**
     * 在某一天上按下
     */
    public void onPressDown(Month month, Day day);

    /**
     * 弹起
     */
    public void onReleaseUp(Month month, Day day);
}
