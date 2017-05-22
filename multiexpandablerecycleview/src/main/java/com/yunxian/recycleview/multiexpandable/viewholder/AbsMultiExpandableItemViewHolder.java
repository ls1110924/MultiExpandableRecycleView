package com.yunxian.recycleview.multiexpandable.viewholder;

import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.yunxian.recycleview.multiexpandable.model.IExpandableItemModel;

import java.util.List;

/**
 * 支持多级展开类型的RecycleView上单条ItemView对应的ViewHolder
 *
 * @author A Shuai
 * @email ls1110924@gmail.com
 * @date 17/5/19 下午4:46
 */
public abstract class AbsMultiExpandableItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private final int mRootViewId;
    private final int mExpandBtnViewId;

    public AbsMultiExpandableItemViewHolder(View itemView) {
        super(itemView);

        mRootViewId = getRootViewIdRes();
        if (!initView(mRootViewId)) {
            throw new IllegalStateException("Plz set correct root view id!");
        }
        mExpandBtnViewId = getExpandBtnViewIdRes();
        if (!initView(mExpandBtnViewId)) {
            throw new IllegalStateException("Plz set correct expand btn view id!");
        }
    }

    private boolean initView(int viewId) {
        if (viewId <= 0) {
            return false;
        }
        View view = itemView.findViewById(viewId);
        if (view == null) {
            return false;
        }
        view.setOnClickListener(this);
        return true;
    }

    /**
     * 获取根视图的Id
     *
     * @return 根视图Id
     */
    @IdRes
    public abstract int getRootViewIdRes();

    /**
     * 获取展开子项的开关按钮视图对应的Id
     *
     * @return 展开收起开关视图对应的Id
     */
    @IdRes
    public abstract int getExpandBtnViewIdRes();

    /**
     * 获取根视图
     *
     * @return 返回根视图
     */
    @NonNull
    public final View getRootView() {
        return itemView;
    }

    /**
     * 绑定数据
     *
     * @param position    当前数据模型处于总可见视图的索引值
     * @param coordinates 当前数据模型处于总数据集中的坐标
     * @param dataModel   当前数据模型值
     */
    public abstract void bindData(int position, @NonNull List<Integer> coordinates, @NonNull IExpandableItemModel dataModel);

}
