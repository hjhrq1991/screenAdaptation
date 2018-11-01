package com.hjhrq991.screenadaptation.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.hjhrq991.screenadaptation.Model.AdapterType;
import com.hjhrq991.screenadaptation.R;
import com.hjhrq991.screenadapter.ScreenAdaperHelper;

/**
 * @author hjhrq1991 created at 2017/9/11 14 48.
 * @Package com.hjhrq991.screenadaptation.Adapter
 * @Description:
 */

public class TriAdapter extends SimplerRecyclerViewAdapter<AdapterType> {

    public TriAdapter(Context context) {
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
                return R.layout.item_tri_dp;
            case Type.TYPE_TRI_PT:
                return R.layout.item_tri_pt;
            case Type.TYPE_TRI_WEIGHT:
                return R.layout.item_tri_weight;
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
            case Type.TYPE_TRI_WEIGHT:
                return new WeigthHolder(convertView);
        }
        return null;
    }

    @Override
    public RecyclerView.ViewHolder getView(RecyclerView.ViewHolder holder, int position) {
        BaseHolder mHolder = (BaseHolder) holder;
        String title = getItem(position).getTitle();
        mHolder.mTitleView.setText(!TextUtils.isEmpty(title) ? title : "");
        if (getItemViewType(position) == Type.TYPE_TRI_PT) {
            mHolder.mTitleView.getLayoutParams().height = (int) ScreenAdaperHelper.ptTopx(mContext, 210f);
        } else {
            mHolder.mTitleView.getLayoutParams().height = (int) ScreenAdaperHelper.dpTopx(mContext, 210f);
        }
        return holder;
    }

    static class DpHolder extends BaseHolder {

        DpHolder(View itemView) {
            super(itemView);
        }
    }

    static class PtHolder extends BaseHolder {

        PtHolder(View itemView) {
            super(itemView);
        }
    }

    static class WeigthHolder extends BaseHolder {

        WeigthHolder(View itemView) {
            super(itemView);
        }
    }
}
