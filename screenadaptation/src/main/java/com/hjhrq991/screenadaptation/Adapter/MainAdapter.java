package com.hjhrq991.screenadaptation.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.hjhrq991.screenadaptation.Activity.FiveActivity;
import com.hjhrq991.screenadaptation.Activity.TriActivity;
import com.hjhrq991.screenadaptation.Model.AdapterType;
import com.hjhrq991.screenadaptation.R;

/**
 * @author hjhrq1991 created at 2017/9/11 14 48.
 * @Package com.hjhrq991.screenadaptation.Adapter
 * @Description:
 */

public class MainAdapter extends SimplerRecyclerViewAdapter<AdapterType> {

    public MainAdapter(Context context) {
        super(context);
    }

    @Override
    public int getLayoutResource(int type) {
        return R.layout.item_main;
    }

    @Override
    public RecyclerView.ViewHolder getLayoutHolder(View convertView, int type) {
        return new DpHolder(convertView);
    }

    @Override
    public RecyclerView.ViewHolder getView(RecyclerView.ViewHolder holder, final int position) {
        BaseHolder mHolder = (BaseHolder) holder;
        String title = getItem(position).getTitle();
        mHolder.mTitleView.setText(!TextUtils.isEmpty(title) ? title : "");
        mHolder.mTitleView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int type = getItem(position).getType();
                switch (type) {
                    case Type.MAIN_TRI:
                        TriActivity.launch(mContext);
                        break;
                    case Type.MAIN_FIVE:
                        FiveActivity.launch(mContext);
                        break;
                }
            }
        });
        return holder;
    }

     static class DpHolder extends BaseHolder {

        DpHolder(View itemView) {
            super(itemView);
        }
    }
}
