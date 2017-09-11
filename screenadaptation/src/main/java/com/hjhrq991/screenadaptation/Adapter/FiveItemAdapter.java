package com.hjhrq991.screenadaptation.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.hjhrq991.screenadaptation.Model.AdapterType;
import com.hjhrq991.screenadaptation.R;

/**
 * @author hjhrq1991 created at 2017/9/11 14 48.
 * @Package com.hjhrq991.screenadaptation.Adapter
 * @Description:
 */

public class FiveItemAdapter extends SimplerRecyclerViewAdapter<AdapterType> {

    public FiveItemAdapter(Context context) {
        super(context);
    }

    @Override
    public int getItemViewType(int position) {
        return getItem(position).getType();
    }

    @Override
    public int getLayoutResource(int type) {
        switch (type) {
            case Type.TYPE_TRI_DP:
                return R.layout.item_five_model_right_layout_dp;
            case Type.TYPE_TRI_PT:
                return R.layout.item_five_model_right_layout_pt;
        }
        return 0;
    }

    @Override
    public RecyclerView.ViewHolder getLayoutHolder(View convertView, int type) {
        switch (type) {
            case Type.TYPE_TRI_DP:
                return new DpHolder(convertView);
            case Type.TYPE_TRI_PT:
                return new PtHolder(convertView);
        }
        return null;
    }

    @Override
    public RecyclerView.ViewHolder getView(RecyclerView.ViewHolder holder, int position) {
        return holder;
    }

    private static class DpHolder extends RecyclerView.ViewHolder {

        DpHolder(View itemView) {
            super(itemView);
        }
    }

    private static class PtHolder extends RecyclerView.ViewHolder {

        PtHolder(View itemView) {
            super(itemView);
        }
    }
}
