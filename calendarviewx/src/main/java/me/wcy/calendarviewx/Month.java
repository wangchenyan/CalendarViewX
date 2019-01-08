package me.wcy.calendarviewx;

import java.util.List;

/**
 * Created by hzwangchenyan on 2016/10/20.
 */
class Month {
    private List<Day> dayList;
    private Config config;
    private GestureListener gestureListener;

    public Month(List<Day> dayList, Config config, GestureListener gestureListener) {
        this.dayList = dayList;
        this.config = config;
        this.gestureListener = gestureListener;
    }

    public List<Day> getDayList() {
        return dayList;
    }

    public Config getConfig() {
        return config;
    }

    public GestureListener getGestureListener() {
        return gestureListener;
    }

    public Day getDay(int position) {
        return dayList.get(position);
    }

    public Day getLastDay() {
        return dayList.get(dayList.size() - 1);
    }

    public int getCount() {
        return dayList.size();
    }
}
