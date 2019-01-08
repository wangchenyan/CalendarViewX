package me.wcy.calendarviewx;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Calendar;

/**
 * Created by hzwangchenyan on 2016/10/26.
 */
public class MonthLayout extends LinearLayout {
    private Month month;
    private MonthView monthView;
    private TextView tvMonth;

    public MonthLayout(Context context) {
        super(context);
        setOrientation(LinearLayout.VERTICAL);

        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);

        tvMonth = new TextView(getContext());
        addView(tvMonth, layoutParams);

        monthView = new MonthView(getContext());
        addView(monthView, layoutParams);
    }

    public void setMonth(Month month) {
        this.month = month;
        setMonthText();
        monthView.setMonth(month);
    }

    private void setMonthText() {
        int horizontalPadding = (int) (month.getConfig().getDayWidth() / 3);
        int verticalPadding = (int) (month.getConfig().getMonthTextSize() / 2);
        tvMonth.setPadding(horizontalPadding, verticalPadding, horizontalPadding, verticalPadding);
        tvMonth.setTextSize(CalendarUtils.px2sp(getContext(), month.getConfig().getMonthTextSize()));
        tvMonth.setTextColor(month.getConfig().getMonthTextColor());
        tvMonth.setMaxLines(1);

        Calendar calendar = month.getDay(0).getCalendar();
        Calendar today = Calendar.getInstance();
        String title;
        if (calendar.get(Calendar.YEAR) == today.get(Calendar.YEAR)) {
            title = getResources().getString(R.string.calendar_month, calendar.get(Calendar.MONTH) + 1);
        } else {
            title = getResources().getString(R.string.calendar_year_month, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1);
        }
        tvMonth.setText(title);
    }
}
