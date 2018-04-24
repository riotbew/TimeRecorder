package com.jim.recorder;

import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.LongSparseArray;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.jim.recorder.abslistview.CommonAdapter;
import com.jim.recorder.abslistview.ViewHolder;
import com.jim.recorder.bean.Cell;
import com.jim.recorder.bean.Data;
import com.jim.recorder.bean.EventType;

import java.util.ArrayList;
import java.util.Calendar;

import static com.jim.recorder.bean.Constants.MONTH_NAME;
import static com.jim.recorder.bean.Constants.WEEK_NAME;
import static com.jim.recorder.bean.Constants.one_day;
import static com.jim.recorder.bean.Constants.one_hour;
import static com.jim.recorder.bean.Constants.one_min;
import static com.jim.recorder.bean.Constants.timezone;

public class MainActivity extends AppCompatActivity {

    private final String TAG = MainActivity.class.getSimpleName();

    ListView lv;
    ListView lb;
    ArrayList<Data> data1 = new ArrayList<>();
    ArrayList<EventType> data2 = new ArrayList<>();
    LongSparseArray<SparseArray<Cell>> mStorage = new LongSparseArray<>();

    TextView content_res;
    int mPosition = 0;
    Calendar now;
    long now_start;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setTimeZone();
        now_start = getCalendarDayStart(Calendar.getInstance()).getTimeInMillis();
        now = Calendar.getInstance();
    }

    private void setTimeZone() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(0);
        timezone = calendar.get(Calendar.HOUR_OF_DAY) * one_hour + calendar.get(Calendar.MINUTE) * one_min;
        if (calendar.get(Calendar.YEAR) != 1970) {
            timezone = one_day - timezone;
        }
    }

    //处理点击cell点击事件
    private void handleCell(View v, Data item, Object position) {
        if (position == null) {
            return;
        }
        int pos = (Integer) position;
        long timeOfDay = item.getTime();
        SparseArray<Cell> cells = mStorage.get(timeOfDay);
        Cell cell = null;
        if (cells == null) {
            cells = new SparseArray<>();
            cell = new Cell(true, pos);
            cells.put(pos, cell);
            mStorage.put(timeOfDay, cells);
        } else {
            cell = cells.get(pos);
            if (cell == null) {
                cell = new Cell(true, pos);
                cells.put(pos, cell);
            } else {
                cell.setSelected(!cell.isSelected());
            }
        }
        if (cell.isSelected()) {
            v.setBackground(getResources().getDrawable(R.mipmap.cell_selected));
        } else {
            v.setBackgroundColor(getResources().getColor(R.color.cell_origin));
        }
    }

    //重置每一天的数据
    private void resetDayItem(View parent, final Data item, int position) {

        ViewGroup content = parent.findViewById(R.id.day_content);
        ViewGroup labelContainer = null;
        if (content.getChildCount() == 0) {
            for (int i = 0; i < 24; i++) {
                labelContainer = (ViewGroup) LayoutInflater.from(MainActivity.this).inflate(R.layout.layout_hour_item, content,false);
                TextView tv = labelContainer.findViewById(R.id.day_time);
                tv.setText(i+":00");
                View child;
                for (int j=1; j < labelContainer.getChildCount(); j++) {
                    child = labelContainer.getChildAt(j);
                    child.setTag((i) * 4 + j);
                    child.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            handleCell(v, item, v.getTag());
                        }
                    });
                }
                content.addView(labelContainer);
            }
        } else {
            for (int i = 0; i < 24; i++) {
                labelContainer = (ViewGroup) content.getChildAt(i);
                View child;
                for (int j = 1; labelContainer!= null && j < labelContainer.getChildCount(); j++) {
                    child = labelContainer.getChildAt(j);
                    child.setBackgroundColor(MainActivity.this.getResources().getColor(R.color.cell_origin));
                    //每次更新数据都需要重置点击事件，否则点击的时候拿到数据就有问题
                    child.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            handleCell(v, item, v.getTag());
                        }
                    });
                }
            }
        }
    }

    //更新数据
    private void updateDayItem(ViewGroup parent, final Data item) {
        ViewGroup content = parent.findViewById(R.id.day_content);
        SparseArray<Cell> cells = mStorage.get(item.getTime());
        Cell cell;
        ViewGroup labelContainer;
        View label;
        if (content != null) {
            for (int i=0; i< 24; i++) {
                labelContainer = (ViewGroup) content.getChildAt(i);
                for (int j = 1; j < 5; j++) {
                    cell = cells.get((i) * 4 + j);
                    label = labelContainer.getChildAt(j);
                    if (label != null) {
                        if (cell != null) {
                            if (cell.isSelected()){
                                label.setBackground(getResources().getDrawable(R.mipmap.cell_selected));
                            } else {
                                if (cell.getType() == -1) {
                                    label.setBackgroundColor(getResources().getColor(R.color.cell_origin));
                                } else {
                                    //TODO 填充颜色
                                }
                            }
                        } else {
                            label.setBackgroundColor(getResources().getColor(R.color.cell_origin));
                        }
                        //每次更新数据都需要重置点击事件，否则点击的时候拿到数据就有问题
                        label.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                handleCell(v, item, v.getTag());
                            }
                        });
                    }
                }
            }
        }
    }

    private void init() {
        setTimeZone();
        now = Calendar.getInstance();
        now_start = getCalendarDayStart(Calendar.getInstance()).getTimeInMillis();

        lv = findViewById(R.id.content_lv);
        lb = findViewById(R.id.content_label);
        content_res = findViewById(R.id.content_res);
        preData();
        final Calendar calendar = Calendar.getInstance();
        lv.setAdapter(new CommonAdapter<Data>(this, R.layout.layout_main_lv, data1) {
            @Override
            protected void convert(ViewHolder viewHolder, final Data item, int position) {
                ViewGroup container = (ViewGroup)viewHolder.getConvertView();
                ViewGroup content = container.findViewById(R.id.day_content);
                if (mStorage.get(item.getTime()) == null) {
                    resetDayItem(container, data1.get(position), position);
                } else {
                    if (content.getChildCount() == 0) {
                        resetDayItem(container, data1.get(position), position);
                    }
                    updateDayItem((ViewGroup) viewHolder.getConvertView(), data1.get(position));
                }
                // 左边title
                long date = item.getTime();
                if (now_start == date) {
                    viewHolder.getView(R.id.day_divider).setVisibility(View.VISIBLE);
                } else {
                    viewHolder.getView(R.id.day_divider).setVisibility(View.GONE);
                }
                calendar.setTimeInMillis(date);
                TextView text_mon = viewHolder.getView(R.id.main_mon);
                if (calendar.get(Calendar.MONTH) > 9) {
                    text_mon.setTextSize(11);
                } else {
                    text_mon.setTextSize(16);
                }
                text_mon.setText(MONTH_NAME[calendar.get(Calendar.MONTH)]);
                viewHolder.setText(R.id.main_date, calendar.get(Calendar.DATE)+"");
                viewHolder.setText(R.id.main_day, WEEK_NAME[calendar.get(Calendar.DAY_OF_WEEK)]);
            }
        });
        lv.setSelection(mPosition);
        lv.setVerticalScrollBarEnabled(false);
        lb.setAdapter(new CommonAdapter<EventType>(this,R.layout.layout_event_item, data2) {
            @Override
            protected void convert(ViewHolder viewHolder, EventType item, int position) {
                TextView tv = viewHolder.getView(R.id.event_name);
                tv.setText(item.getName());
                GradientDrawable bg = new GradientDrawable();
                bg.setColor(getResources().getColor(item.getColor()));
                bg.setCornerRadius(DensityUtil.dip2px(MainActivity.this,13));
                tv.setBackground(bg);
            }
        });
        lb.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
                lv.setSelection(mPosition);
            }
        });
        View view = LayoutInflater.from(this).inflate(R.layout.layout_label_footer, lb,false);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, LabelManagerActivity.class));
            }
        });
        lb.addFooterView(view);
    }

    private void preData() {
        Calendar start = Calendar.getInstance();
        start.set(start.get(Calendar.YEAR)-2, 0, 1);
        start = getCalendarDayStart(start);

        Calendar end = Calendar.getInstance();
        end.set(end.get(Calendar.YEAR)+1, 0, 1);
        end = getCalendarDayStart(end);

        for (long i=start.getTimeInMillis();i < end.getTimeInMillis(); i+=one_day) {
            data1.add(new Data(i));
        }
        long now_time = getCalendarDayStart(now).getTimeInMillis();
        mPosition = Long.valueOf((now_time - start.getTimeInMillis()+timezone)/one_day).intValue();

        data2.add(new EventType("吃", 1, R.color.blue));
        data2.add(new EventType("喝",2, R.color.orange_color));
        data2.add(new EventType("玩",3,R.color.colorPrimaryDark));
        data2.add(new EventType("乐",4, R.color.colorPrimary));
        data2.add(new EventType("睡觉",5, R.color.colorAccent));
        data2.add(new EventType("吃", 1, R.color.blue));
        data2.add(new EventType("喝",2, R.color.orange_color));
        data2.add(new EventType("玩",3,R.color.colorPrimaryDark));
        data2.add(new EventType("乐",4, R.color.colorPrimary));
        data2.add(new EventType("睡觉",5, R.color.colorAccent));
        data2.add(new EventType("吃", 1, R.color.blue));
        data2.add(new EventType("喝",2, R.color.orange_color));
        data2.add(new EventType("玩",3,R.color.colorPrimaryDark));
        data2.add(new EventType("乐",4, R.color.colorPrimary));
        data2.add(new EventType("睡觉",5, R.color.colorAccent));
        data2.add(new EventType("吃", 1, R.color.blue));
        data2.add(new EventType("喝",2, R.color.orange_color));
        data2.add(new EventType("玩",3,R.color.colorPrimaryDark));
        data2.add(new EventType("乐",4, R.color.colorPrimary));
        data2.add(new EventType("睡觉",5, R.color.colorAccent));
        data2.add(new EventType("吃", 1, R.color.blue));
        data2.add(new EventType("喝",2, R.color.orange_color));
        data2.add(new EventType("玩",3,R.color.colorPrimaryDark));
        data2.add(new EventType("乐",4, R.color.colorPrimary));
        data2.add(new EventType("睡觉",5, R.color.colorAccent));
        data2.add(new EventType("吃", 1, R.color.blue));
        data2.add(new EventType("喝",2, R.color.orange_color));
        data2.add(new EventType("玩",3,R.color.colorPrimaryDark));
        data2.add(new EventType("乐",4, R.color.colorPrimary));
        data2.add(new EventType("睡觉",5, R.color.colorAccent));
    }

    private Calendar getCalendarDayStart(Calendar param) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis((param.getTimeInMillis() - param.getTimeInMillis()%one_day)-timezone);
        return calendar;
    }
}
