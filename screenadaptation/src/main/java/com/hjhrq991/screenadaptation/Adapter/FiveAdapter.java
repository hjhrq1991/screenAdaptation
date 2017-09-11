package com.hjhrq991.screenadaptation.Adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.hjhrq991.screenadaptation.Model.AdapterType;
import com.hjhrq991.screenadaptation.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author hjhrq1991 created at 2017/9/11 14 48.
 * @Package com.hjhrq991.screenadaptation.Adapter
 * @Description:
 */

public class FiveAdapter extends SimplerRecyclerViewAdapter<AdapterType> {

    private List<AdapterType> listPt = new ArrayList<>();
    private List<AdapterType> listDp = new ArrayList<>();

    public FiveAdapter(Context context) {
        super(context);
        for (int i = 0; i < 4; i++) {
            listPt.add(new AdapterType(Type.TYPE_TRI_PT));
            listDp.add(new AdapterType(Type.TYPE_TRI_DP));
        }
    }

    @Override
    public int getItemViewType(int position) {
        return getItem(position).getType();
    }

    @Override
    public int getLayoutResource(int type) {
        switch (type) {
            case Type.TYPE_FIVE_DP:
                return R.layout.item_index_five_model_dp;
            case Type.TYPE_FIVE_PT:
                return R.layout.item_index_five_model_pt;
            case Type.TYPE_FIVE_WEIGHT:
                return R.layout.item_index_five_model_weight;
        }
        return 0;
    }

    @Override
    public RecyclerView.ViewHolder getLayoutHolder(View convertView, int type) {
        switch (type) {
            case Type.TYPE_FIVE_DP:
                return new DpHolder(convertView, listDp);
            case Type.TYPE_FIVE_PT:
                return new PtHolder(convertView, listPt);
            case Type.TYPE_FIVE_WEIGHT:
                return new WeigthHolder(convertView, listDp);
        }
        return null;
    }

    @Override
    public RecyclerView.ViewHolder getView(RecyclerView.ViewHolder holder, int position) {
        BaseHolder mHolder = (BaseHolder) holder;
        String title = getItem(position).getTitle();
        mHolder.mTitleView.setText(!TextUtils.isEmpty(title) ? title : "");
        return holder;
    }

     static class DpHolder extends BaseHolder {
        @BindView(R.id.gridview)
        RecyclerView recyclerView;
        FiveItemAdapter mAdapter;

        DpHolder(View itemView, List<AdapterType> list) {
            super(itemView);
            recyclerView.setLayoutManager(new GridLayoutManager(itemView.getContext(), 2));
            mAdapter = new FiveItemAdapter(itemView.getContext());
            recyclerView.setAdapter(mAdapter);
            mAdapter.replaceAll(list);
        }
    }

     static class PtHolder extends BaseHolder {
        @BindView(R.id.gridview)
        RecyclerView recyclerView;
        FiveItemAdapter mAdapter;

        PtHolder(View itemView, List<AdapterType> list) {
            super(itemView);
            recyclerView.setLayoutManager(new GridLayoutManager(itemView.getContext(), 2));
            mAdapter = new FiveItemAdapter(itemView.getContext());
            recyclerView.setAdapter(mAdapter);
            mAdapter.replaceAll(list);
        }
    }

     static class WeigthHolder extends BaseHolder {
        @BindView(R.id.gridview)
        RecyclerView recyclerView;
        FiveItemAdapter mAdapter;

        WeigthHolder(View itemView, List<AdapterType> list) {
            super(itemView);
            recyclerView.setLayoutManager(new GridLayoutManager(itemView.getContext(), 2));
            mAdapter = new FiveItemAdapter(itemView.getContext());
            recyclerView.setAdapter(mAdapter);
            mAdapter.replaceAll(list);
        }
    }
}
