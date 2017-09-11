package com.hjhrq991.screenadaptation.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.hjhrq991.screenadaptation.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author hjhrq1991 created at 2017/9/11 16 27.
 * @Package com.hjhrq991.screenadaptation.Adapter
 * @Description:
 */

public class BaseHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.title)
    TextView mTitleView;

    public BaseHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
