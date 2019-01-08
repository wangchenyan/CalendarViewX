package me.wcy.calendarviewx;

import java.util.Calendar;

/**
 * Created by hzwangchenyan on 2016/10/21.
 */
public interface CalendarListener {
    void onFirstDaySelected(Calendar firstDay);

    void onSecondDaySelected(Calendar secondDay);
}
