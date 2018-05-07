package com.jim.recorder.ui.view;

import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.util.LongSparseArray;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.hannesdorfmann.mosby.mvp.MvpActivity;
import com.jim.recorder.ui.callback.MainView;
import com.jim.recorder.ui.pressenter.MainPressenter;
import com.jim.recorder.utils.CalendarUtil;
import com.jim.recorder.utils.ColorUtil;
import com.jim.recorder.utils.DensityUtil;
import com.jim.recorder.LabelManagerActivity;
import com.jim.recorder.R;
import com.jim.recorder.abslistview.CommonAdapter;
import com.jim.recorder.abslistview.ViewHolder;
import com.jim.recorder.model.Cell;
import com.jim.recorder.model.Data;
import com.jim.recorder.model.EventType;

import java.util.Calendar;

import static com.jim.recorder.model.Constants.MONTH_NAME;
import static com.jim.recorder.model.Constants.WEEK_NAME;

public class MainActivity extends MvpActivity<MainView, MainPressenter> implements MainView {

    private final String TAG = MainActivity.class.getSimpleName();

    ListView lv;
    ListView lb;

    //当前时间的位置
    int mPosition = 0;
    Calendar now;
    long now_start;
    CommonAdapter leftAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getPresenter().updateTime();
        setTimeZone();
        now = Calendar.getInstance();
        now_start = getCalendarDayStart(Calendar.getInstance()).getTimeInMillis();

        setContentView(R.layout.activity_main);
        preData();
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setTimeZone();
        getPresenter().updateTime();

        now_start = getCalendarDayStart(Calendar.getInstance()).getTimeInMillis();
        now = Calendar.getInstance();
    }

    @NonNull
    @Override
    public MainPressenter createPresenter() {
        return new MainPressenter();
    }

    private void setTimeZone() {
        getPresenter().setTimeZone();
    }

    private void initView() {
        lv = findViewById(R.id.content_lv);
        lb = findViewById(R.id.content_label);
        leftAdapter = new CommonAdapter<Data>(this, R.layout.layout_main_lv, getPresenter().getViewData()) {
            @Override
            protected void convert(ViewHolder viewHolder, final Data item, int position) {
                ViewGroup container = (ViewGroup)viewHolder.getConvertView();
                ViewGroup content = container.findViewById(R.id.day_content);
                getPresenter().handleDayCellRender(content,item,position);
                // 左边title页面绘制
                renderLeftTitle(viewHolder, item);
            }
        };
        lv.setAdapter(leftAdapter);
        lv.setSelection(mPosition);
        lv.setVerticalScrollBarEnabled(false);
        //
        lb.setAdapter(new CommonAdapter<EventType>(this,R.layout.layout_event_item, getPresenter().getLabelData()) {
            @Override
            protected void convert(ViewHolder viewHolder, EventType item, int position) {
                TextView tv = viewHolder.getView(R.id.event_name);
                tv.setText(item.getName());
                GradientDrawable bg = new GradientDrawable();
                bg.setColor(getColorByRes(ColorUtil.getColor(item.getType())));
                bg.setCornerRadius(DensityUtil.dip2px(MainActivity.this,13));
                tv.setBackground(bg);
            }
        });
        //标签列表点击事件
        lb.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
                if (pos == 0) {
                    getPresenter().fixSelected(-1);
                } else {
                    getPresenter().fixSelected(getPresenter().getEventType(pos).getType());
                }

            }
        });
        //lb底部标签管理器
        View view = LayoutInflater.from(this).inflate(R.layout.layout_label_footer, lb,false);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, LabelManagerActivity.class));
            }
        });
        lb.addFooterView(view);
    }

    private void renderLeftTitle(ViewHolder viewHolder, Data item) {
        final Calendar calendar = Calendar.getInstance();
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

    private void preData() {
        getPresenter().initViewData();
    }

    private Calendar getCalendarDayStart(Calendar param) {
        return CalendarUtil.getCalendarDayStart(param);
    }

    @Override
    public void todayPosition(int position) {
        mPosition = position;
    }

    /**
     * 处理左边列表点击事件
     * @param v
     * @param cell
     */
    @Override
    public void updateCellAfterClick(View v, Cell cell) {
        if (cell.isSelected()) {
            v.setBackground(getResources().getDrawable(R.mipmap.cell_selected));
        } else {
            //TODO 填充颜色
             if (cell.getType() != -1)
                v.setBackgroundColor(getColorByRes(ColorUtil.getColor(cell.getType())));
            else
                setCellOriginBg(v);
        }
    }

    private void setCellOriginBg(View v) {
        v.setBackground(getResources().getDrawable(R.drawable.cell_min_bg));
    }

    private int getColorByRes(int color) {
        return getResources().getColor(color);
    }

    /**
     * 重置每天的内容
     * @param content
     * @param item
     */
    @Override
    public void resetLeftDayCellView(ViewGroup content, final Data item) {
        ViewGroup labelContainer;
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
                            getPresenter().handleLeftClick(v, item, v.getTag());
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
                    setCellOriginBg(child);
                    //每次更新数据都需要重置点击事件，否则点击的时候拿到数据就有问题
                    child.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            getPresenter().handleLeftClick(v, item, v.getTag());
                        }
                    });
                }
            }
        }
    }

    @Override
    public void updateLeftDayCellView(ViewGroup content, final Data item, SparseArray<Cell> cells) {
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
                                    setCellOriginBg(label);
                                } else {
                                    //TODO 填充颜色
                                    label.setBackgroundColor(getColorByRes(ColorUtil.getColor(cell.getType())));
                                }
                            }
                        } else {
                            setCellOriginBg(label);
                        }
                        //每次更新数据都需要重置点击事件，否则点击的时候拿到数据就有问题
                        label.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                getPresenter().handleLeftClick(v, item, v.getTag());
                            }
                        });
                    }
                }
            }
        }
    }

    @Override
    public void refreshLeft() {
        leftAdapter.notifyDataSetChanged();
    }
}
