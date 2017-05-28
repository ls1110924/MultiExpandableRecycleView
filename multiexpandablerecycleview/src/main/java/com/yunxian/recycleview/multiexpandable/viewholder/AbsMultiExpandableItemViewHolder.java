package com.yunxian.recycleview.multiexpandable.viewholder;

import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.yunxian.recycleview.multiexpandable.R;
import com.yunxian.recycleview.multiexpandable.enumeration.ExpandableItemViewNodeType;
import com.yunxian.recycleview.multiexpandable.model.IExpandableItemModel;

import java.util.ArrayList;
import java.util.List;

/**
 * 支持多级展开类型的RecycleView上单条ItemView对应的ViewHolder
 *
 * @author A Shuai
 * @email ls1110924@gmail.com
 * @date 17/5/19 下午4:46
 */
public abstract class AbsMultiExpandableItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    @IdRes
    private final int mRootViewId;
    @ExpandableItemViewNodeType
    private final int mItemViewNodeType;
    @IdRes
    private final int mExpandBtnViewId;

    private final View mRootView;
    @Nullable
    private final View mExpandBtnView;

    private final List<OnBtnClickListener> mClickListeners = new ArrayList<>();

    public AbsMultiExpandableItemViewHolder(View itemView) {
        super(itemView);

        mRootViewId = getRootViewIdRes();
        mRootView = initView(mRootViewId);
        if (mRootView == null) {
            throw new IllegalStateException("Plz set correct root view id!");
        }

        mItemViewNodeType = getItemViewNodeType();
        if (mItemViewNodeType == ExpandableItemViewNodeType.ALL_ITEM) {
            mExpandBtnViewId = getExpandBtnViewIdRes();
            mExpandBtnView = initView(mExpandBtnViewId);
            if (mExpandBtnView == null) {
                throw new IllegalStateException("Plz set correct expand btn view id!");
            }
        } else if (mItemViewNodeType == ExpandableItemViewNodeType.GROUP_ITEM) {
            mExpandBtnViewId = getExpandBtnViewIdRes();
            mExpandBtnView = initView(mExpandBtnViewId);
            if (mExpandBtnView == null) {
                throw new IllegalStateException("Plz set correct expand btn view id!");
            }
        } else if (mItemViewNodeType == ExpandableItemViewNodeType.LEAF_ITEM) {
            mExpandBtnViewId = -1;
            mExpandBtnView = null;
        } else {
            throw new IllegalStateException("the itemViewNodeType is illegal, and the nodeType is " + mItemViewNodeType);
        }

        mRootView.setOnClickListener(this);
        if (mExpandBtnView != null) {
            mExpandBtnView.setOnClickListener(this);
        }

    }

    @Nullable
    private View initView(int viewId) {
        if (viewId <= 0) {
            return null;
        }
        return itemView.findViewById(viewId);
    }

    @ExpandableItemViewNodeType
    protected abstract int getItemViewNodeType();

    /**
     * 获取根视图的Id
     *
     * @return 根视图Id
     */
    @IdRes
    protected abstract int getRootViewIdRes();

    /**
     * 获取展开子项的开关按钮视图对应的Id
     *
     * @return 展开收起开关视图对应的Id
     */
    @IdRes
    protected abstract int getExpandBtnViewIdRes();

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
    public final void bindData(int position, @NonNull List<Integer> coordinates, @NonNull IExpandableItemModel dataModel) {
        mRootView.setTag(R.id.multiexpand_recycleview_viewholder_tag, dataModel);
        if (mExpandBtnView != null) {
            mExpandBtnView.setTag(R.id.multiexpand_recycleview_viewholder_tag, null);
        }

        if (mItemViewNodeType == ExpandableItemViewNodeType.ALL_ITEM) {
            if (dataModel.isGroup()) {
                if (mExpandBtnView == null) {
                    throw new NullPointerException("Plz check code, and the current dataModel isn't a groupModel");
                }
                // TODO
            } else {
                // TODO
            }
        } else if (mItemViewNodeType == ExpandableItemViewNodeType.GROUP_ITEM) {
            if (dataModel.isGroup()) {
                if (mExpandBtnView == null) {
                    throw new NullPointerException();
                }
                // TODO
            } else {
                throw new IllegalStateException("Plz set correct dataModel with viewHolder");
            }
        } else if (mItemViewNodeType == ExpandableItemViewNodeType.LEAF_ITEM) {
            if (dataModel.isGroup()) {
                throw new IllegalStateException("Plz set correct dataModel with viewHolder");
            } else {
                // TODO
            }
        }
    }

    @Override
    public final void onClick(View v) {
        final int id = v.getId();
        final IExpandableItemModel dataModel = (IExpandableItemModel) v.getTag(R.id.multiexpand_recycleview_viewholder_tag);

        if (mItemViewNodeType == ExpandableItemViewNodeType.ALL_ITEM || mItemViewNodeType == ExpandableItemViewNodeType.GROUP_ITEM) {
            if (dataModel.isGroup() && id == mExpandBtnViewId) {
                performExpandedBtnClick(dataModel);
            }
            if (id == mRootViewId) {
                performSelectedBtnClick(dataModel);
            }
        } else if (mItemViewNodeType == ExpandableItemViewNodeType.LEAF_ITEM) {
            if (id == mRootViewId) {
                performSelectedBtnClick(dataModel);
            }
        }
    }

    private void performExpandedBtnClick(@NonNull IExpandableItemModel dataModel) {
        for (OnBtnClickListener listener : mClickListeners) {
            listener.onExpandedBtnClick(dataModel);
        }
    }

    private void performSelectedBtnClick(@NonNull IExpandableItemModel dataModel) {
        for (OnBtnClickListener listener : mClickListeners) {
            listener.onSelectedBtnClick(dataModel);
        }
    }

    public final void addOnBtnClickListener(@Nullable OnBtnClickListener listener) {
        if (listener != null && !mClickListeners.contains(listener)) {
            mClickListeners.add(listener);
        }
    }

    public final void removeOnBtnClickListener(@Nullable OnBtnClickListener listener) {
        if (listener != null && mClickListeners.contains(listener)) {
            mClickListeners.remove(listener);
        }
    }

    /**
     *
     * 多级展开Item上的事件监听器，包括选择监听和展开监听
     */
    public interface OnBtnClickListener {

        /**
         * 展开监听
         *
         * @param dataModel
         * @return
         */
        boolean onExpandedBtnClick(@NonNull IExpandableItemModel dataModel);

        boolean onSelectedBtnClick(@NonNull IExpandableItemModel dataModel);

    }

}
