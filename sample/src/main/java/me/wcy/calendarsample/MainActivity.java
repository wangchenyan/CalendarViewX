package me.wcy.calendarsample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Calendar;

import me.wcy.calendarviewx.CalendarListener;
import me.wcy.calendarviewx.CalendarViewX;

public class MainActivity extends AppCompatActivity implements CalendarListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final CalendarViewX calendarView = (CalendarViewX) findViewById(R.id.calendar_view);
        Button btnReset = (Button) findViewById(R.id.btn_reset);

        calendarView.setCalendarListener(this);
        calendarView.setDateRange(2015, 9, 1);
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendarView.reset();
            }
        });
    }

    @Override
    public void onFirstDaySelected(Calendar firstDay) {
        int year = firstDay.get(Calendar.YEAR);
        int month = firstDay.get(Calendar.MONTH) + 1;
        int day = firstDay.get(Calendar.DAY_OF_MONTH);
        Toast.makeText(this, "第一天 " + year + "." + month + "." + day, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSecondDaySelected(Calendar secondDay) {
        int year = secondDay.get(Calendar.YEAR);
        int month = secondDay.get(Calendar.MONTH) + 1;
        int day = secondDay.get(Calendar.DAY_OF_MONTH);
        Toast.makeText(this, "第二天 " + year + "." + month + "." + day, Toast.LENGTH_SHORT).show();
    }
}
