package com.hjhrq991.screenadaptation.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hjhrq1991 on 15/4/17.
 */
public abstract class SimplerRecyclerViewAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    protected List<T> mList;
    protected Context mContext;

    public SimplerRecyclerViewAdapter(Context context) {
        super();
        this.mContext = context;
        mList = new ArrayList<>();
    }

    public void addAll(List<T> elem) {
        mList.addAll(elem);
        notifyDataSetChanged();
    }

    public void add(T elem) {
        mList.add(elem);
        notifyItemInserted(mList.size());
    }

    public void add(T elem, int index) {
        mList.add(index, elem);
        notifyItemInserted(index);
    }

    public void remove(T elem) {
        mList.remove(elem);
        notifyDataSetChanged();
    }

    public void remove(int index) {
        mList.remove(index);
        notifyItemRemoved(index);
    }

    public void replace(T elem, int index) {
        mList.set(index, elem);
        notifyItemChanged(index);
    }

    public void replaceAll(List<T> elem) {
        mList.clear();
        mList.addAll(elem);
        notifyDataSetChanged();
    }

    public T getItem(int position) {
        return mList.get(position);
    }

    public List<T> getList() {
        return mList;
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    /**
     * 创建ViewHolder
     */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(getLayoutResource(viewType), parent, false);
        RecyclerView.ViewHolder holer = getLayoutHolder(view, viewType);

        return holer;
    }

    /**
     * 返回对应的layout
     *
     * @param type
     * @return
     */
    public abstract int getLayoutResource(int type);

    /**
     * 返回对应的ViewHolder
     *
     * @param convertView
     * @param type
     * @return
     */
    public abstract RecyclerView.ViewHolder getLayoutHolder(View convertView, int type);

    /**
     * 绑定viewholder
     */
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        getView(holder, position);
    }

    /**
     * 如果想做一些通用模板的事，可以实现此方法
     *
     * @param holder
     * @param position
     * @return
     */
    public abstract RecyclerView.ViewHolder getView(RecyclerView.ViewHolder holder, int position);

}