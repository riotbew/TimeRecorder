package com.jim.recorder.common.adapter.listview.base;

import com.jim.recorder.common.adapter.listview.LVViewHolder;

public interface ItemViewDelegate<T>
{

    public abstract int getItemViewLayoutId();

    public abstract boolean isForViewType(T item, int position);

    public abstract void convert(LVViewHolder holder, T t, int position);

}