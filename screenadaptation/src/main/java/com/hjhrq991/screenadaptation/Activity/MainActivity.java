package com.hjhrq991.screenadaptation.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.hjhrq991.screenadaptation.Adapter.MainAdapter;
import com.hjhrq991.screenadaptation.Adapter.Type;
import com.hjhrq991.screenadaptation.Model.AdapterType;
import com.hjhrq991.screenadaptation.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;

    private Unbinder unbinder;
    private MainAdapter mAdapter;

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
        mAdapter = new MainAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void initData() {
        List<AdapterType> list = new ArrayList<>();
        list.add(new AdapterType("Example 1", Type.MAIN_FIVE));
        list.add(new AdapterType("Example 2", Type.MAIN_TRI));
        mAdapter.replaceAll(list);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (unbinder != null) unbinder.unbind();
    }
}
