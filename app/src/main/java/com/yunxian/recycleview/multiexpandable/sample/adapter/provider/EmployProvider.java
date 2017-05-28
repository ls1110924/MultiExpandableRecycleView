package com.yunxian.recycleview.multiexpandable.sample.adapter.provider;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.yunxian.recycleview.multiexpandable.provider.IMultiExpandableItemViewProvider;
import com.yunxian.recycleview.multiexpandable.sample.R;
import com.yunxian.recycleview.multiexpandable.sample.adapter.viewholder.EmployeeViewHolder;
import com.yunxian.recycleview.multiexpandable.viewholder.AbsMultiExpandableItemViewHolder;

/**
 * @author A Shuai
 * @email ls1110924@gmail.com
 * @date 2017/5/29 2:07
 */
public class EmployProvider implements IMultiExpandableItemViewProvider {

    @NonNull
    @Override
    public AbsMultiExpandableItemViewHolder createViewHolder(@NonNull Context context,
                                                             @NonNull ViewGroup viewGroup) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        return new EmployeeViewHolder(layoutInflater.inflate(R.layout.item_test_multiexpand_recyclerview, viewGroup, false));
    }
}
