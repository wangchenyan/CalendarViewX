# CalendarViewX
Android日历控件，可用于选择机票往返日期。

![]()

## 使用
暂无

## 属性
| 属性 | 描述 |
| ---- | ---- |
| cvSingleMode | 是否为单选模式 |
| cvDayHeight | Day高度 |
| cvWeekdayBackground | Weekday背景 |
| cvWeekdayTextSize | Weekday文字大小 |
| cvWeekdayTextColor | Weekday文字颜色 |
| cvMonthTextSize | 月份文字大小 |
| cvMonthTextColor | 月份文字颜色 |
| cvDayMargin | Day边距 |
| cvDayTextSize | Day文字大小 |
| cvDayTextColorNormal | Day普通文字颜色 |
| cvDayTextColorDisabled | Day过期文字颜色 |
| cvDayTextColorToday | Day今天文字颜色 |
| cvDayTextColorWeekend | Day周末文字颜色 |
| cvDayTextColorEndpoint | Day选中端点文字颜色 |
| cvDayTextColorMiddle | Day选中非端点文字颜色 |
| cvDayColorEndpoint | Day选中端点背景颜色 |
| cvDayColorMiddle | Day选中非端点背景颜色 |

## 方法
| 方法 | 描述 |
| ---- | ---- |
| setDateRange(int, int, int) | 设置开始年月日 |
| setDateRange(int, int, int, int, int, int) | 设置开始和结束年月日 |
| setCalendarListener(CalendarListener) | 设置日期选择监听器 |
| reset() | 将日历重置为初始状态 |
