package com.yunxian.recycleview.multiexpandable.sample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import com.yunxian.recycleview.multiexpandable.MultiExpandableRecycleViewAdapter;

/**
 * 测试RecyclerView的多级展开功能页面
 *
 * @author A Shuai
 * @email ls1110924@gmail.com
 * @date 2017/5/29 0:51
 */
public class TestMultiExpandableActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_multiexpand);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        MultiExpandableRecycleViewAdapter recycleViewAdapter = new MultiExpandableRecycleViewAdapter(this, recyclerView);
    }
}
