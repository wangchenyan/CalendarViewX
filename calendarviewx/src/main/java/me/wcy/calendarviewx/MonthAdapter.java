package me.wcy.calendarviewx;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hzwangchenyan on 2016/10/21.
 */
class MonthAdapter extends BaseAdapter {
    private List<Month> monthList = new ArrayList<>();

    public void addMonth(Month month) {
        monthList.add(month);
    }

    @Override
    public int getCount() {
        return monthList.size();
    }

    @Override
    public Month getItem(int position) {
        return monthList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = new MonthLayout(parent.getContext());
        }
        ((MonthLayout) convertView).setMonth(monthList.get(position));
        return convertView;
    }

    @Override
    public boolean isEnabled(int position) {
        return false;
    }

    public int getPosition(Month month) {
        return monthList.indexOf(month);
    }
}
