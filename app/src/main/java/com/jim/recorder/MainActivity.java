package com.jim.recorder;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
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

public class MainActivity extends AppCompatActivity {

    long one_min = 60000;
    long one_s = 1000;
    long one_hour = 3600000;
    long one_day = 86400000;
    long timezone = 28800000;

    ListView lv;
    ListView lb;
    ArrayList<Data> data1 = new ArrayList<>();
    ArrayList<EventType> data2 = new ArrayList<>();
    TextView content_res;
    int position = 0;
    Calendar now;
    long now_start;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        now = Calendar.getInstance();
        now_start = getCalendarDayStart(now).getTimeInMillis();
        setContentView(R.layout.activity_main);
        init();
    }

    @Override
    protected void onResume() {
        super.onResume();
        now = Calendar.getInstance();
        now_start = getCalendarDayStart(now).getTimeInMillis();

    }

    private void init() {
        lv = findViewById(R.id.content_lv);
        lb = findViewById(R.id.content_label);
        content_res = findViewById(R.id.content_res);
        preData();
        final Calendar calendar = Calendar.getInstance();
        lv.setAdapter(new CommonAdapter<Data>(this, R.layout.layout_main_lv, data1) {
            @Override
            protected void convert(ViewHolder viewHolder, Data item, int position) {
                View view = null;
                LinearLayout content = viewHolder.getView(R.id.day_content);
                if (content.getChildCount() == 0) {
                    for (int i = 0; i < 24; i++) {
                        view = LayoutInflater.from(MainActivity.this).inflate(R.layout.layout_hour_item, content,false);
                        ((TextView)view.findViewById(R.id.day_time)).setText(i+":00");
                        content.addView(view);
                    }
                }
                long date = item.getTime();
                if (now_start == date) {
                    viewHolder.getView(R.id.day_divider).setVisibility(View.VISIBLE);
                } else {
                    viewHolder.getView(R.id.day_divider).setVisibility(View.GONE);
                };
                calendar.setTimeInMillis(date);
                viewHolder.setText(R.id.main_mon, MONTH_NAME[calendar.get(Calendar.MONTH)]);
                viewHolder.setText(R.id.main_date, calendar.get(Calendar.DATE)+"");
                viewHolder.setText(R.id.main_day, WEEK_NAME[calendar.get(Calendar.DAY_OF_WEEK)]);
            }
        });
        lv.setSelection(position);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (content_res != null) {
                }
            }
        });

        lb.setAdapter(new CommonAdapter<EventType>(this,R.layout.layout_event_item, data2) {
            @Override
            protected void convert(ViewHolder viewHolder, EventType item, int position) {
            }
        });
        lb.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
                lv.setSelection(position);
            }
        });

    }

    private void preData() {
        Calendar start = Calendar.getInstance();
        start.set(start.get(Calendar.YEAR)-2, 0, 1);
        start = getCalendarDayStart(start);

        Calendar end = Calendar.getInstance();
        end.set(end.get(Calendar.YEAR)+1, 0, 1);
        end = getCalendarDayStart(end);

        for (long i=start.getTimeInMillis();i <= end.getTimeInMillis(); i+=one_day) {
            data1.add(new Data(i));
        }
        long now_time = getCalendarDayStart(now).getTimeInMillis();
        position = Long.valueOf((now_time - start.getTimeInMillis())/one_day).intValue()+1;

        data2.add(new EventType("吃", 1, R.color.blue));
        data2.add(new EventType("喝",2, R.color.orange_color));
        data2.add(new EventType("玩",3,R.color.yellow));
        data2.add(new EventType("乐",4, R.color.colorPrimary));
        data2.add(new EventType("睡觉",5, R.color.colorAccent));
    }

    private Calendar getCalendarDayStart(Calendar param) {
        param.setTimeInMillis(param.getTimeInMillis() - param.getTimeInMillis()%one_day-timezone);
        return param;
    }


}
