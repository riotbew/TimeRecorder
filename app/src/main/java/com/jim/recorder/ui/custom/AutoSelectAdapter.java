package com.jim.recorder.ui.custom;

import android.content.Context;

import com.jim.common.adapter.recyclerview.MultiItemTypeAdapter;

import java.util.HashSet;
import java.util.List;

/**
 * Created by Tauren on 2018/5/20.
 */

public class AutoSelectAdapter<T> extends MultiItemTypeAdapter<T>
{


    private HashSet<Integer> mSelected;

    public AutoSelectAdapter(Context context, List<T> datas)
    {
        super(context, datas);
        mSelected = new HashSet<>();
    }

    // ----------------------
    // Selection
    // ----------------------

    public void toggleSelection(int pos)
    {
        if (mSelected.contains(pos))
            mSelected.remove(pos);
        else
            mSelected.add(pos);
        notifyItemChanged(pos);
    }

    public void select(int pos, boolean selected)
    {
        if (selected)
            mSelected.add(pos);
        else
            mSelected.remove(pos);
        notifyItemChanged(pos);
    }

    public void selectRange(int start, int end, boolean selected)
    {
        for (int i = start; i <= end; i++)
        {
            if (selected)
                mSelected.add(i);
            else
                mSelected.remove(i);
        }
        notifyItemRangeChanged(start, end - start + 1);
    }

    public void deselectAll()
    {
        // this is not beautiful...
        mSelected.clear();
        notifyDataSetChanged();
    }

    public void selectAll()
    {
        for (int i = 0; i < mDatas.size(); i++)
            mSelected.add(i);
        notifyDataSetChanged();
    }

    public void setDatas(List<T> datas) {
        this.mDatas = datas;
    }

    public int getCountSelected()
    {
        return mSelected.size();
    }

    public HashSet<Integer> getSelection()
    {
        return mSelected;
    }

}
