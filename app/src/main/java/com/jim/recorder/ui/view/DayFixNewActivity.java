package com.jim.recorder.ui.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.codbking.calendar.CaledarAdapter;
import com.codbking.calendar.CalendarBean;
import com.codbking.calendar.CalendarDateView;
import com.codbking.calendar.CalendarLayout;
import com.codbking.calendar.CalendarView;
import com.jim.common.BaseMvpActivity;
import com.jim.common.adapter.listview.CommonLVAdapter;
import com.jim.common.adapter.listview.LVViewHolder;
import com.jim.common.adapter.recyclerview.MultiItemTypeAdapter;
import com.jim.common.adapter.recyclerview.base.ItemViewDelegate;
import com.jim.common.adapter.recyclerview.base.ViewHolder;
import com.jim.recorder.R;
import com.jim.recorder.model.Constants;
import com.jim.recorder.model.EventType;
import com.jim.recorder.ui.callback.DayFixView;
import com.jim.recorder.ui.custom.AutoSelectAdapter;
import com.jim.recorder.ui.custom.DayFixContentHeaderDelegate;
import com.jim.recorder.ui.custom.DragSelectTouchListener;
import com.jim.recorder.ui.custom.DragSelectionProcessor;
import com.jim.recorder.ui.model.SingleModel;
import com.jim.recorder.ui.pressenter.DayFixNewPressenter;
import com.jim.recorder.ui.pressenter.DayFixPressenter;
import com.jim.recorder.utils.CalendarUtil;
import com.jim.recorder.utils.DensityUtil;
import com.jim.recorder.utils.TemplateColor;

import java.util.Calendar;
import java.util.HashSet;
import java.util.List;

/**
 * Created by Tauren on 2018/5/19.
 */

public class DayFixNewActivity extends BaseMvpActivity<DayFixView, DayFixNewPressenter> implements DayFixView, NavigationView.OnNavigationItemSelectedListener {

    ListView mEventsView;
    RecyclerView mContent;
    CommonLVAdapter<EventType> rightAdapter;
    AutoSelectAdapter<SingleModel> leftAdapter;
    CalendarDateView mCalendarDateView;
    TextView mTitle;
    DragSelectTouchListener mDragSelectTouchListener;
    private boolean mIntercept = false;

    //监听事件类型变更 及时刷新数据
    BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            getPresenter().refreshEventList();
            getPresenter().refreshContent();
        }
    };

    @NonNull
    @Override
    public DayFixNewPressenter createPresenter() {
        return new DayFixNewPressenter();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getPresenter().updateTimeCalendar();
        setContentView(R.layout.activity_day_fix_main);
        IntentFilter itf = new IntentFilter(Constants.TIME_RECORDER_EVENT_UPDATE);
        registerReceiver(mReceiver, itf);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getPresenter().updateTimeCalendar();
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(mReceiver);
        super.onDestroy();
    }

    @Override
    protected void initView() {
        mEventsView = findViewById(R.id.content_label);
        mContent = findViewById(R.id.content_rv);
        mCalendarDateView = findViewById(R.id.calendarDateView);
        CalendarLayout calendarLayout = findViewById(R.id.calendar_container);
        calendarLayout.setInterceptListener(new CalendarLayout.onInterceptListener() {
            @Override
            public boolean onIntercept() {
                return mIntercept;
            }
        });
        initCalendar();
        getPresenter().refreshData();
        //lb底部标签管理器
        View view = LayoutInflater.from(this).inflate(R.layout.layout_label_footer, mEventsView,false);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), EventManagerActivity.class));
            }
        });
        mEventsView.addFooterView(view);
        getPresenter().updateTitle();
    }

    private void initToggle(Toolbar toolbar) {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void initCalendar() {
        mCalendarDateView.setAdapter(new CaledarAdapter() {
            @Override
            public View getView(View convertView, ViewGroup parentView, CalendarBean bean) {
                TextView view;
                if (convertView == null) {
                    convertView = LayoutInflater.from(parentView.getContext()).inflate(R.layout.layout_item_calendar, null);
                    ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(px(48), px(48));
                    convertView.setLayoutParams(params);
                }
                view = convertView.findViewById(R.id.text);
                view.setText("" + bean.day);
                if (bean.mothFlag != 0) {
                    view.setTextColor(0xff9299a1);
                } else {
                    view.setTextColor(0xffffffff);
                }
                return convertView;
            }
        });
        mCalendarDateView.setOnItemClickListener(new CalendarView.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int postion, CalendarBean bean) {
                Calendar calendar = Calendar.getInstance();
                calendar = CalendarUtil.getCalendarDayStart(calendar);
                calendar.set(bean.year, bean.moth-1, bean.day);
                getPresenter().selectTime(calendar);
            }
        });
    }

    public int px(float dipValue) {
        Resources r=Resources.getSystem();
        final float scale =r.getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }


    public void updateContent(List<SingleModel> viewData) {
        leftAdapter = new AutoSelectAdapter<SingleModel>(this, viewData);
        leftAdapter.addItemViewDelegate(new ItemViewDelegate<SingleModel>() {
            @Override
            public int getItemViewLayoutId() {
                return R.layout.layout_day_fix_left_item;
            }
            @Override
            public boolean isForViewType(SingleModel item, int position) {
                return position %5 != 0;
            }
            @Override
            public void convert(ViewHolder holder, SingleModel singleModel, int position) {
                View view = holder.getConvertView();
                updateLeftCell(view, singleModel, position);
            }
        });
        leftAdapter.addItemViewDelegate(new DayFixContentHeaderDelegate());
        leftAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                if (position % 5==0)
                    return;
                leftAdapter.toggleSelection(position);
                getPresenter().handleLeftClick(view, position);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                if (mDragSelectTouchListener != null)
                    mDragSelectTouchListener.startDragSelection(position);
                mIntercept = true;
                return true;
            }
        });
        DragSelectionProcessor mDragSelectionProcessor = new DragSelectionProcessor(new DragSelectionProcessor.ISelectionHandler() {
            @Override
            public HashSet<Integer> getSelection() {
                return leftAdapter.getSelection();
            }

            @Override
            public boolean isSelected(int index) {
                return leftAdapter.getSelection().contains(index);
            }

            @Override
            public void updateSelection(int start, int end, boolean isSelected, boolean calledFromOnStart) {
                leftAdapter.selectRange(start, end, isSelected);
            }
        }).withMode(DragSelectionProcessor.Mode.ToggleAndUndo);
        leftAdapter.setDatas(viewData);
        mContent.setAdapter(leftAdapter);
        GridLayoutManager layoutManager = new GridLayoutManager(this,5);
        mContent.setLayoutManager(layoutManager);
        if (mDragSelectTouchListener == null) {
            mDragSelectTouchListener = new DragSelectTouchListener().withSelectListener(mDragSelectionProcessor);
            mDragSelectionProcessor.withStartFinishedListener(new DragSelectionProcessor.ISelectionStartFinishedListener(){

                @Override
                public void onSelectionStarted(int start, boolean originalSelectionState) {
                    mIntercept = true;
                }

                @Override
                public void onSelectionFinished(int end) {
                    mIntercept = false;
                }
            });
        }
        mContent.addOnItemTouchListener(mDragSelectTouchListener);
    }

    public void updateLeftCell(View v, SingleModel singleModel, int position) {
        TextView textView = v.findViewById(R.id.single_fix_left_cell);
        if (leftAdapter != null) {
            if (leftAdapter.getSelection().contains(position)) {
                Drawable bg = getResources().getDrawable(R.mipmap.day_fix_selected);
                if (bg != null)
                    textView.setBackground(bg);
                textView.setText("");
            } else {
                textView.setBackgroundColor(singleModel.getColor());
                textView.setText(singleModel.getName());
            }
        }
        getPresenter().updateSelectedIndicator(leftAdapter.getSelection());
    }

    public void updateEventList(final List<EventType> eventTypeList) {
        if (mEventsView == null)
            return;
         //右边标签列表设置
        rightAdapter = new CommonLVAdapter<EventType>(this,R.layout.layout_event_item, eventTypeList) {
            @Override
            protected void convert(LVViewHolder LVViewHolder, EventType item, int position) {
                TextView tv = LVViewHolder.getView(R.id.event_name);
                tv.setText(item.getName());
                GradientDrawable bg = new GradientDrawable();
                bg.setColor(TemplateColor.getColor(item.getType()));
                bg.setCornerRadius(DensityUtil.dip2px(getContext(),13));
                tv.setBackground(bg);
            }
        };
        mEventsView.setAdapter(rightAdapter);
        //标签列表点击事件
        mEventsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
                getPresenter().judgeStatus(leftAdapter.getSelection(), eventTypeList.get(pos));
            }
        });
        rightAdapter.notifyDataSetChanged();
    }

    @Override
    public void labelCoverWarning(final EventType eventType) {
        showCustomDialog(null, "原本标记的内容将被覆盖\n\n是否继续？", "继续", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                getPresenter().fixSelected(leftAdapter.getSelection(), eventType);
                dialog.dismiss();
            }
        }, false);
    }

    @Override
    public void updateAfterFix() {
        leftAdapter.deselectAll();
    }

    @Override
    public void updateTitle(String text) {
        if (mTitle == null) {
            mTitle = findViewById(R.id.tool_bar_title);
        }
        mTitle.setText(text);
    }

    @Override
    protected void setToolBar(Toolbar toolBar) {
        super.setToolBar(toolBar);
        hideToolBarTitle();
        setStatusBarColor(getResources().getColor(R.color.tool_bar_bg));
        initToggle(toolBar);
        //设置content高度
        View view = findViewById(R.id.content_container);
        DrawerLayout.LayoutParams lp = (DrawerLayout.LayoutParams) view.getLayoutParams();
        lp.setMargins(0, getStatusBarHeight(), 0,0);
        view.setLayoutParams(lp);
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
            Intent it = new Intent(this, MainActivity.class);
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

    private Snackbar selectIndicator;
    @Override
    public void updateSelectedIndicator(int count) {
        if (selectIndicator == null) {
            selectIndicator = getSnackbar("");
            selectIndicator.setAction("抹除", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getPresenter().wipeData(leftAdapter.getSelection());
                }
            });
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
}
