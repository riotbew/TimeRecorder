package com.jim.common.adapter.listview;


import android.content.Context;

import com.jim.common.adapter.listview.base.ItemViewDelegate;

import java.util.List;

public abstract class CommonLVAdapter<T> extends MultiItemTypeAdapter<T>
{

    public CommonLVAdapter(Context context, final int layoutId, List<T> datas)
    {
        super(context, datas);

        addItemViewDelegate(new ItemViewDelegate<T>()
        {
            @Override
            public int getItemViewLayoutId()
            {
                return layoutId;
            }

            @Override
            public boolean isForViewType(T item, int position)
            {
                return true;
            }

            @Override
            public void convert(LVViewHolder holder, T t, int position)
            {
                CommonLVAdapter.this.convert(holder, t, position);
            }
        });
    }

    protected abstract void convert(LVViewHolder LVViewHolder, T item, int position);

}
