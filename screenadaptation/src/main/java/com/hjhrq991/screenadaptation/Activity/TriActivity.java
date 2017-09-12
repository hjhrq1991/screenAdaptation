package com.hjhrq991.screenadaptation.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.hjhrq991.screenadaptation.Adapter.TriAdapter;
import com.hjhrq991.screenadaptation.Adapter.Type;
import com.hjhrq991.screenadaptation.Model.AdapterType;
import com.hjhrq991.screenadaptation.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class TriActivity extends AppCompatActivity {

    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;

    private Unbinder unbinder;
    private TriAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        unbinder = ButterKnife.bind(this);

        initView();
        initData();
    }

    private void initView() {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(manager);
        mAdapter = new TriAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void initData() {
        List<AdapterType> list = new ArrayList<>();
        list.add(new AdapterType("The dp way", Type.TYPE_TRI_DP));
        list.add(new AdapterType("The weight way", Type.TYPE_TRI_WEIGHT));
        list.add(new AdapterType("The adaptation way", Type.TYPE_TRI_PT));
        mAdapter.replaceAll(list);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (unbinder != null) unbinder.unbind();
    }

    public static void launch(Context context) {
        context.startActivity(new Intent(context, TriActivity.class));
    }
}
