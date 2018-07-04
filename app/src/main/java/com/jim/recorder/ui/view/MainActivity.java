package com.jim.recorder.ui.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.jim.recorder.R;
import com.jim.common.BaseMvpActivity;
import com.jim.common.adapter.listview.CommonLVAdapter;
import com.jim.common.adapter.listview.LVViewHolder;
import com.jim.recorder.ui.model.ViewCell;
import com.jim.recorder.ui.model.MainViewData;
import com.jim.recorder.model.DayCell;
import com.jim.recorder.model.EventType;
import com.jim.recorder.ui.callback.MainView;
import com.jim.recorder.ui.pressenter.MainPressenter;
import com.jim.recorder.utils.CalendarUtil;
import com.jim.recorder.utils.TemplateColor;
import com.jim.recorder.utils.DensityUtil;

import java.util.Calendar;

import static com.jim.recorder.model.Constants.MONTH_NAME;
import static com.jim.recorder.model.Constants.WEEK_NAME;

public class MainActivity extends BaseMvpActivity<MainView, MainPressenter> implements MainView, NavigationView.OnNavigationItemSelectedListener {

    private final String TAG = MainActivity.class.getSimpleName();

    ListView lv;
    ListView lb;

    //当前时间的位置
    int mPosition = 0;
    Calendar now;
    long now_start;
    CommonLVAdapter leftAdapter;
    CommonLVAdapter rightAdapter;

    BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            getPresenter().refreshRecordData();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getPresenter().updateTime();
        setTimeZone();
        now = Calendar.getInstance();
        now_start = getCalendarDayStart(Calendar.getInstance()).getTimeInMillis();
        preData( );
        setContentView(R.layout.activity_main_drawer);
        registerReceiver(mReceiver, new IntentFilter("TIME_RECORDER_DEL_EVENT_TYPE"));
        Intent it = getIntent();
        if (it == null) {
            Log.i(TAG, "it is null");
        }
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(mReceiver);
        super.onDestroy();
    }

    @Override
    protected void setToolBar(Toolbar toolBar) {
        super.setToolBar(toolBar);
        setStatusBarColor(getResources().getColor(R.color.tool_bar_bg));
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        initDrawer(toolBar);

        //设置content高度
        View view = findViewById(R.id.content_container);
        DrawerLayout.LayoutParams lp = (DrawerLayout.LayoutParams) view.getLayoutParams();
        lp.setMargins(0, getStatusBarHeight(), 0,0);
        view.setLayoutParams(lp);
    }

    private void initDrawer(Toolbar toolbar) {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setTimeZone();
        getPresenter().updateTime();
        rightAdapter.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MainPressenter createPresenter() {
        return new MainPressenter();
    }

    private void setTimeZone() {
        getPresenter().setTimeZone();
        now_start = getCalendarDayStart(Calendar.getInstance()).getTimeInMillis();
        now = Calendar.getInstance();
    }

    protected void initView() {
        lv = findViewById(R.id.content_lv);
        lb = findViewById(R.id.content_label);
        //左边数据设置
        leftAdapter = new CommonLVAdapter<MainViewData>(this, R.layout.layout_main_lv, getPresenter().getViewData()) {
            @Override
            protected void convert(LVViewHolder LVViewHolder, final MainViewData item, int position) {
                ViewGroup container = (ViewGroup) LVViewHolder.getConvertView();
                ViewGroup content = container.findViewById(R.id.day_content);
                getPresenter().handleDayCellRender(content,item,position);
                // 左边title页面绘制
                renderLeftTitle(LVViewHolder, item);
            }
        };
        lv.setAdapter(leftAdapter);
        lv.setSelection(mPosition);
        lv.setVerticalScrollBarEnabled(false);
        //右边标签列表设置
        rightAdapter = new CommonLVAdapter<EventType>(this,R.layout.layout_event_item, getPresenter().getLabelData()) {
            @Override
            protected void convert(LVViewHolder LVViewHolder, EventType item, int position) {
                TextView tv = LVViewHolder.getView(R.id.event_name);
                tv.setText(item.getName());
                GradientDrawable bg = new GradientDrawable();
                bg.setColor(TemplateColor.getColor(item.getType()));
                bg.setCornerRadius(DensityUtil.dip2px(MainActivity.this,13));
                tv.setBackground(bg);
            }
        };
        lb.setAdapter(rightAdapter);
        //标签列表点击事件
        lb.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
            getPresenter().judgeStatus(getPresenter().getEventType(pos));
            }
        });
        //lb底部标签管理器
        View view = LayoutInflater.from(this).inflate(R.layout.layout_label_footer, null,false);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, EventManagerActivity.class));
            }
        });
        lb.addFooterView(view);
    }

    private void renderLeftTitle(LVViewHolder LVViewHolder, MainViewData item) {
        final Calendar calendar = Calendar.getInstance();
        long date = item.getTime();
        if (now_start == date) {
            LVViewHolder.getView(R.id.day_divider).setVisibility(View.VISIBLE);
        } else {
            LVViewHolder.getView(R.id.day_divider).setVisibility(View.GONE);
        }
        calendar.setTimeInMillis(date);
        TextView text_mon = LVViewHolder.getView(R.id.main_mon);
        if (calendar.get(Calendar.MONTH) > 9) {
            text_mon.setTextSize(11);
        } else {
            text_mon.setTextSize(16);
        }
        text_mon.setText(MONTH_NAME[calendar.get(Calendar.MONTH)]);
        LVViewHolder.setText(R.id.main_date, calendar.get(Calendar.DATE)+"");
        LVViewHolder.setText(R.id.main_day, WEEK_NAME[calendar.get(Calendar.DAY_OF_WEEK)]);
    }

    private void preData() {
        getPresenter().initViewData(this);
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
     * @param viewCell
     */
    @Override
    public void updateCellAfterClick(View v, ViewCell viewCell) {
        if (viewCell.isSelected()) {
            v.setBackground(getResources().getDrawable(R.mipmap.cell_selected));
        } else {
            //TODO 填充颜色
             if (viewCell.getType() != -1)
                v.setBackgroundColor(TemplateColor.getColor(viewCell.getType()));
            else
                setCellOriginBg(v);
        }
    }

    private void setCellOriginBg(View v) {
        v.setBackground(getResources().getDrawable(R.drawable.cell_min_bg));
    }

    /**
     * 重置每天的内容
     * @param content
     * @param item
     */
    @Override
    public void resetLeftDayCellView(ViewGroup content, final MainViewData item) {
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
    public void updateLeftDayCellView(ViewGroup content, final MainViewData item, DayCell cells) {
        ViewCell viewCell;
        ViewGroup labelContainer;
        View label;
        if (content != null) {
            for (int i=0; i< 24; i++) {
                labelContainer = (ViewGroup) content.getChildAt(i);
                for (int j = 1; j < 5; j++) {
                    //数据更新存在延迟，需要即时判断
                    viewCell = getPresenter().cellAvailJudge(cells,(i) * 4 + j);
                    label = labelContainer.getChildAt(j);
                    if (label != null) {
                        if (viewCell != null) {
                            if (viewCell.isSelected()){
                                label.setBackground(getResources().getDrawable(R.mipmap.cell_selected));
                            } else {
                                if (viewCell.getType() == -1) {
                                    setCellOriginBg(label);
                                } else { //TODO 填充颜色
                                    label.setBackgroundColor(TemplateColor.getColor(viewCell.getType()));
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
        if (leftAdapter != null)
            leftAdapter.notifyDataSetChanged();
        if (selectIndicator != null)
            selectIndicator.dismiss();
    }

    @Override
    public void labelCoverWarning(final EventType eventType) {
        showCustomDialog(null, "原本标记的内容将被覆盖\n\n是否继续？", "继续", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                getPresenter().fixSelected(eventType);
                dialog.dismiss();
            }
        }, false);
    }

    @Override
    public void labelWipeWarning() {
        showCustomDialog(null, "原本标记的内容将被清除\n\n是否继续？", "继续", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                getPresenter().wipeData(false);
                if (selectIndicator != null)
                    selectIndicator.dismiss();
            }
        }, false);
    }

    private Snackbar selectIndicator;
    @Override
    public void updateSelectIndicator(int count) {
        if (selectIndicator == null) {
            selectIndicator = getSnackbar("");
//            selectIndicator.setAction("抹除", new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    getPresenter().wipeData(true);
//                }
//            });
        }
        selectIndicator.setText(getPresenter().toFormatTime(count));
        if (count == 0) {
            selectIndicator.dismiss();
            return;
        }
        if (!selectIndicator.isShown()) {
            selectIndicator.show();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        int id = item.getItemId();
        if (id == R.id.nav_statistic) {
            getSnackbar("尽情期待", Snackbar.LENGTH_SHORT).show();
        } else if(id == R.id.nav_manager) {
            startActivity(new Intent(this, EventManagerActivity.class));
        } else if (id == R.id.nav_exchange) {
            Intent it = new Intent(this, DayFixNewActivity.class);
            it.putExtra("clear_other", true);
            startActivity(it);
            return true;
        }
        drawer.closeDrawer(GravityCompat.START);
        return false;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer == null)
            return super.onKeyDown(keyCode, event);
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
