package me.wcy.calendarviewx;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by hzwangchenyan on 2016/10/21.
 */
public class CalendarViewX extends LinearLayout implements GestureListener {
    private static final int STATE_RESET = 0;
    private static final int STATE_SINGLE_TAP = 1;
    private static final int STATE_DOUBLE_TAP = 2;

    private Calendar startDay = Calendar.getInstance();
    private Calendar endDay = Calendar.getInstance();
    private Calendar today = Calendar.getInstance();

    private ListView lvMonth;
    private MonthAdapter monthAdapter;
    private Config config = new Config();
    private CalendarListener calendarListener;

    private int state = STATE_RESET;

    private int indexCurrentMonth;
    private int indexFirstMonth;
    private int indexFirstDay;
    private int indexSecondMonth;
    private int indexSecondDay;

    public CalendarViewX(Context context) {
        this(context, null);
    }

    public CalendarViewX(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CalendarViewX(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        TypedArray ta = getResources().obtainAttributes(attrs, R.styleable.CalendarViewX);
        config.setSingleMode(ta.getBoolean(R.styleable.CalendarViewX_cvSingleMode, false));

        config.setDayHeight(ta.getDimension(R.styleable.CalendarViewX_cvDayHeight, 0));

        config.setWeekdayBackground(ta.getDrawable(R.styleable.CalendarViewX_cvWeekdayBackground));
        config.setWeekdayTextSize(ta.getDimension(R.styleable.CalendarViewX_cvWeekdayTextSize, CalendarUtils.sp2px(getContext(), 14)));
        config.setWeekdayTextColor(ta.getColor(R.styleable.CalendarViewX_cvWeekdayTextColor, Color.WHITE));

        config.setMonthTextSize(ta.getDimension(R.styleable.CalendarViewX_cvMonthTextSize, CalendarUtils.dp2px(getContext(), 20)));
        config.setMonthTextColor(ta.getColor(R.styleable.CalendarViewX_cvMonthTextColor, Color.BLACK));

        config.setDayMargin(ta.getDimension(R.styleable.CalendarViewX_cvDayMargin, 0));
        config.setDayTextSize(ta.getDimension(R.styleable.CalendarViewX_cvDayTextSize, CalendarUtils.dp2px(getContext(), 14)));

        config.setDayTextColorNormal(ta.getColor(R.styleable.CalendarViewX_cvDayTextColorNormal, Color.BLACK));
        config.setDayTextColorDisabled(ta.getColor(R.styleable.CalendarViewX_cvDayTextColorDisabled, Color.LTGRAY));
        config.setDayTextColorToday(ta.getColor(R.styleable.CalendarViewX_cvDayTextColorToday, getResources().getColor(R.color.blue)));
        config.setDayTextColorWeekend(ta.getColor(R.styleable.CalendarViewX_cvDayTextColorWeekend, Color.GRAY));
        config.setDayTextColorEndpoint(ta.getColor(R.styleable.CalendarViewX_cvDayTextColorEndpoint, Color.WHITE));
        config.setDayTextColorMiddle(ta.getColor(R.styleable.CalendarViewX_cvDayTextColorMiddle, Color.BLACK));

        config.setDayColorEndpoint(ta.getColor(R.styleable.CalendarViewX_cvDayColorEndpoint, getResources().getColor(R.color.blue)));
        config.setDayColorMiddle(ta.getColor(R.styleable.CalendarViewX_cvDayColorMiddle, getResources().getColor(R.color.blue_100)));
        ta.recycle();

        setOrientation(VERTICAL);

        addWeekday();

        clearTime(startDay);
        clearTime(endDay);
        clearTime(today);
    }

    /**
     * @param startMonth 从0开始
     */
    public void setDateRange(int startYear, int startMonth, int startDay) {
        setDateRange(startYear, startMonth, startDay, today.get(Calendar.YEAR),
                today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH));
    }

    /**
     * @param startMonth 从0开始
     * @param endMonth   从0开始
     */
    public void setDateRange(int startYear, int startMonth, int startDay, int endYear, int endMonth, int endDay) {
        this.startDay.set(startYear, startMonth, startDay);
        this.endDay.set(endYear, endMonth, endDay);

        initCalendar();
    }

    public void setCalendarListener(CalendarListener listener) {
        calendarListener = listener;
    }

    public void reset() {
        if (state == STATE_SINGLE_TAP) {
            Month month = monthAdapter.getItem(indexFirstMonth);
            Day day = month.getDay(indexFirstDay);
            day.setCheckState(Day.CHECK_STATE_NORMAL);
        } else if (state == STATE_DOUBLE_TAP) {
            linkStartToEnd(true, false);
        }

        state = STATE_RESET;

        lvMonth.setAdapter(monthAdapter);
        lvMonth.setSelection(indexCurrentMonth);
    }

    private void initCalendar() {
        state = STATE_RESET;
        removeView(lvMonth);

        monthAdapter = new MonthAdapter();
        addMonths();

        lvMonth = new ListView(getContext());
        lvMonth.setCacheColorHint(Color.TRANSPARENT);
        lvMonth.setDivider(null);
        lvMonth.setAdapter(monthAdapter);
        lvMonth.setSelection(indexCurrentMonth);
        lvMonth.setVerticalScrollBarEnabled(false);
        lvMonth.setPadding(0, 0, 0, CalendarUtils.dp2px(getContext(), 10));
        lvMonth.setClipToPadding(false);

        addView(lvMonth, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
    }

    private void addWeekday() {
        LinearLayout llWeekday = new LinearLayout(getContext());
        llWeekday.setOrientation(HORIZONTAL);
        llWeekday.setBackgroundDrawable(config.getWeekdayBackground());
        int verticalPadding = (int) (config.getWeekdayTextSize() / 2);
        llWeekday.setPadding(0, verticalPadding, 0, verticalPadding);

        String[] texts = getResources().getStringArray(R.array.calendar_weekdays);
        float textSize = CalendarUtils.px2sp(getContext(), config.getWeekdayTextSize());
        LayoutParams layoutParams = new LayoutParams(0, LayoutParams.WRAP_CONTENT, 1);
        for (String text : texts) {
            TextView textView = new TextView(getContext());
            textView.setGravity(Gravity.CENTER);
            textView.setTextSize(textSize);
            textView.setTextColor(config.getWeekdayTextColor());
            textView.setText(text);
            llWeekday.addView(textView, layoutParams);
        }

        addView(llWeekday, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        float dayWidth = width / 7f;
        config.setDayWidth(dayWidth);
        if (config.getDayHeight() <= 0) {
            config.setDayHeight(config.getDayWidth());
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private void addMonths() {
        Calendar cursor = (Calendar) startDay.clone();
        cursor.set(Calendar.DAY_OF_MONTH, 1);
        Calendar realEnd = (Calendar) endDay.clone();
        realEnd.set(Calendar.DAY_OF_MONTH, endDay.getActualMaximum(Calendar.DAY_OF_MONTH));
        int rowOfMonth = 0;
        List<Day> dayList = null;
        while (cursor.before(realEnd) || cursor.equals(realEnd)) {
            int dayOfWeek = cursor.get(Calendar.DAY_OF_WEEK);
            int dayOfMonth = cursor.get(Calendar.DAY_OF_MONTH);
            if (dayOfMonth == 1) {
                rowOfMonth = 0;
                dayList = new ArrayList<>(31);
                monthAdapter.addMonth(new Month(dayList, config, this));

                if (cursor.get(Calendar.YEAR) == today.get(Calendar.YEAR) && cursor.get(Calendar.MONTH) == today.get(Calendar.MONTH)) {
                    indexCurrentMonth = monthAdapter.getCount() - 1;
                }
            } else if (dayOfWeek == Calendar.SUNDAY) {
                rowOfMonth++;
            }
            Day day = new Day((Calendar) cursor.clone(), config, rowOfMonth);
            if (dayList != null) {
                dayList.add(day);
            }

            if (cursor.equals(today)) {
                day.addFlag(Day.FLAG_TODAY);
            }
            if (cursor.before(startDay) || cursor.after(endDay)) {
                day.addFlag(Day.FLAG_DISABLED);
            }
            if (dayOfWeek == Calendar.SUNDAY) {
                day.addFlag(Day.FLAG_WEEKEND);
                day.addFlag(Day.FLAG_FIRST_DAY_OF_WEEK);
            } else if (dayOfWeek == Calendar.SATURDAY) {
                day.addFlag(Day.FLAG_WEEKEND);
                day.addFlag(Day.FLAG_LAST_DAY_OF_WEEK);
            }
            if (dayOfMonth == 1) {
                day.addFlag(Day.FLAG_FIRST_DAY_OF_MONTH);
            } else if (dayOfMonth == cursor.getActualMaximum(Calendar.DAY_OF_MONTH)) {
                day.addFlag(Day.FLAG_LAST_DAY_OF_MONTH);
            }

            cursor.add(Calendar.DAY_OF_MONTH, 1);
        }
    }

    @Override
    public void onTap(Month month, Day day) {
        if (state == STATE_RESET) {
            state = STATE_SINGLE_TAP;

            indexFirstMonth = monthAdapter.getPosition(month);
            indexFirstDay = day.getCalendar().get(Calendar.DAY_OF_MONTH) - 1;

            day.setCheckState(Day.CHECK_STATE_SINGLE);
            monthAdapter.notifyDataSetChanged();

            if (calendarListener != null) {
                calendarListener.onFirstDaySelected((Calendar) day.getCalendar().clone());
            }
        } else if (state == STATE_SINGLE_TAP) {
            if (config.isSingleMode()) {
                Month prevMonth = monthAdapter.getItem(indexFirstMonth);
                Day prevDay = prevMonth.getDay(indexFirstDay);

                if (day != prevDay) {
                    indexFirstMonth = monthAdapter.getPosition(month);
                    indexFirstDay = day.getCalendar().get(Calendar.DAY_OF_MONTH) - 1;

                    prevDay.setCheckState(Day.CHECK_STATE_NORMAL);
                    day.setCheckState(Day.CHECK_STATE_SINGLE);
                    monthAdapter.notifyDataSetChanged();
                }

                if (calendarListener != null) {
                    calendarListener.onFirstDaySelected((Calendar) day.getCalendar().clone());
                }
            } else {
                state = STATE_DOUBLE_TAP;

                indexSecondMonth = monthAdapter.getPosition(month);
                indexSecondDay = day.getCalendar().get(Calendar.DAY_OF_MONTH) - 1;

                if (indexFirstMonth != indexSecondMonth || indexFirstDay != indexSecondDay) {
                    linkStartToEnd(false, true);
                }

                if (calendarListener != null) {
                    calendarListener.onSecondDaySelected((Calendar) day.getCalendar().clone());
                }
            }
        } else if (state == STATE_DOUBLE_TAP) {
            state = STATE_SINGLE_TAP;

            linkStartToEnd(true, false);

            indexFirstMonth = monthAdapter.getPosition(month);
            indexFirstDay = day.getCalendar().get(Calendar.DAY_OF_MONTH) - 1;

            day.setCheckState(Day.CHECK_STATE_SINGLE);
            monthAdapter.notifyDataSetChanged();

            if (calendarListener != null) {
                calendarListener.onFirstDaySelected((Calendar) day.getCalendar().clone());
            }
        }
    }

    @Override
    public void onPressDown(Month month, Day day) {
    }

    @Override
    public void onReleaseUp(Month month, Day day) {
    }

    private void linkStartToEnd(boolean clear, boolean notify) {
        int startMonth = Math.min(indexFirstMonth, indexSecondMonth);
        int endMonth = Math.max(indexFirstMonth, indexSecondMonth);
        for (int i = startMonth; i <= endMonth; i++) {
            Month month = monthAdapter.getItem(i);
            int startDay = 0;
            int endDay = month.getCount() - 1;
            if (startMonth == endMonth) {
                startDay = Math.min(indexFirstDay, indexSecondDay);
                endDay = Math.max(indexFirstDay, indexSecondDay);
            } else if (i == startMonth) {
                startDay = (startMonth == indexFirstMonth) ? indexFirstDay : indexSecondDay;
            } else if (i == endMonth) {
                endDay = (endMonth == indexFirstMonth) ? indexFirstDay : indexSecondDay;
            }

            for (int j = startDay; j <= endDay; j++) {
                Day day = month.getDay(j);

                if (clear) {
                    day.setCheckState(Day.CHECK_STATE_NORMAL);
                } else {
                    if (i == startMonth && j == startDay) {
                        day.setCheckState(Day.CHECK_STATE_START);
                    } else if (i == endMonth && j == endDay) {
                        day.setCheckState(Day.CHECK_STATE_END);
                    } else {
                        day.setCheckState(Day.CHECK_STATE_MIDDLE);
                    }
                }
            }
        }

        if (notify) {
            monthAdapter.notifyDataSetChanged();
        }
    }

    private void clearTime(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
    }
}
