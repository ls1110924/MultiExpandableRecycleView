package com.yunxian.recycleview.multiexpandable.provider;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.ViewGroup;

import com.yunxian.recycleview.multiexpandable.viewholder.AbsMultiExpandableItemViewHolder;

/**
 * 可多级展开的RecycleView中单个ItemView对应的ViewHolder生成器
 *
 * @author A Shuai
 * @email ls1110924@gmail.com
 * @date 17/5/19 下午5:38
 */
public interface IMultiExpandableItemViewProvider {

    /**
     * 创建一个合适的ViewHolder对象
     *
     * @param context   Context上下文对象
     * @param viewGroup 该ViewHolder中的ItemView应用到的父容器视图
     * @return ViewHolder对象，不可为空
     */
    @NonNull
    AbsMultiExpandableItemViewHolder createViewHolder(@NonNull Context context, @NonNull ViewGroup viewGroup);

}
