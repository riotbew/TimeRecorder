package com.jim.recorder.ui.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.jim.recorder.R;
import com.jim.recorder.common.BaseMvpActivity;
import com.jim.recorder.common.adapter.listview.CommonLVAdapter;
import com.jim.recorder.common.adapter.listview.LVViewHolder;
import com.jim.recorder.common.adapter.recyclerview.MultiItemTypeAdapter;
import com.jim.recorder.common.adapter.recyclerview.base.ItemViewDelegate;
import com.jim.recorder.common.adapter.recyclerview.base.ViewHolder;
import com.jim.recorder.model.Constants;
import com.jim.recorder.model.EventType;
import com.jim.recorder.ui.model.SingleModel;
import com.jim.recorder.ui.callback.DayFixView;
import com.jim.recorder.ui.custom.AutoSelectAdapter;
import com.jim.recorder.ui.custom.DragSelectTouchListener;
import com.jim.recorder.ui.custom.DayFixContentHeaderDelegate;
import com.jim.recorder.ui.custom.DragSelectionProcessor;
import com.jim.recorder.ui.pressenter.DayFixPressenter;
import com.jim.recorder.utils.DensityUtil;
import com.jim.recorder.utils.TemplateColor;

import java.util.HashSet;
import java.util.List;

/**
 * Created by Tauren on 2018/5/19.
 */

public class DayFixActivity extends BaseMvpActivity<DayFixView, DayFixPressenter> implements DayFixView {

    ListView mEventsView;
    RecyclerView mContent;
    CommonLVAdapter<EventType> rightAdapter;
    AutoSelectAdapter<SingleModel> leftAdapter;
    TextView mTitle;
    DragSelectTouchListener mDragSelectTouchListener;
    private DragSelectionProcessor mDragSelectionProcessor;

    BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            getPresenter().refreshEventList();
            getPresenter().refreshContent();
        }
    };

    @NonNull
    @Override
    public DayFixPressenter createPresenter() {
        return new DayFixPressenter();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getPresenter().updateTimeCalendar();
        setContentView(R.layout.activity_day_fix);
        IntentFilter itf = new IntentFilter(Constants.TIME_RECORDER_EVENT_UPDATE);
        registerReceiver(mReceiver, itf);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getPresenter().updateTimeCalendar();
        getPresenter().updateTitle();
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
                mDragSelectTouchListener.startDragSelection(position);
                return true;
            }
        });
        mDragSelectionProcessor = new DragSelectionProcessor(new DragSelectionProcessor.ISelectionHandler() {
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
        }).withMode(DragSelectionProcessor.Mode.FirstItemDependentToggleAndUndo);
        leftAdapter.setDatas(viewData);
        mContent.setAdapter(leftAdapter);
        GridLayoutManager layoutManager = new GridLayoutManager(this,5);
        mContent.setLayoutManager(layoutManager);
        if (mDragSelectTouchListener == null)
            mDragSelectTouchListener = new DragSelectTouchListener().withSelectListener(mDragSelectionProcessor);
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
    }
}
