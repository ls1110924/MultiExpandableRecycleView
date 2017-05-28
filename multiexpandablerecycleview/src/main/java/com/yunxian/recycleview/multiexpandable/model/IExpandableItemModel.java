package com.yunxian.recycleview.multiexpandable.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.yunxian.recycleview.multiexpandable.viewholder.AbsMultiExpandableItemViewHolder;

import java.util.List;

/**
 * 支持多级展开子项的Item 数据Model应该是想的接口
 *
 * @author A Shuai
 * @email ls1110924@gmail.com
 * @date 17/5/19 下午4:13
 */
public interface IExpandableItemModel {

    /**
     * 表明此数据项是否是组项，即是否有子项
     *
     * @return true为组项，否则为叶子项
     */
    boolean isGroup();

    /**
     * 如果当前是组项，则返回该组项下的所有子项列表，请务必保证当前为组项数据模型时，该方法有返回值；
     * 如果为叶子项，则不再具有子项，可返回null
     *
     * @return 组项对应的所有子项列表；叶子项返回null
     */
    @Nullable
    List<IExpandableItemModel> getChildren();

    /**
     * 如果是组项数据模型，则是否展开显示所有子项；
     * 如果当前是叶子项，该方法不会被触发调用
     *
     * @return true为展开，否则不展开
     */
    boolean isExpanded();

    /**
     * 设置当前组项显示所有子项；如果当前是叶子项，则该方法调用不产生任何效果
     *
     * @param expanded true为展开，否则不展开
     */
    void setExpanded(boolean expanded);

    /**
     * 获取当前数据模型对应的ItemView的类型表示字符串，与{@link AbsMultiExpandableItemViewHolder}相对应
     *
     * @return ItemView的类型表征字符串
     */
    @NonNull
    String getItemViewType();

    /**
     * 设置在扩展树上坐标
     *
     * @param coordinate 坐标
     */
    void setCoordinateInExpandableTree(ExpandableNodeCoordinateModel coordinate);

    ExpandableNodeCoordinateModel getCoordinateInExpandableTree();

    /**
     * 设置该数据模型在RecycleView上的索引值
     * 当设置为-1时，表示该项不可见，不具有索引值
     *
     * @param position 在RecycleView上的索引值，-1表示该项不可见
     */
    void setRecycleViewChildrenIndex(int position);

    /**
     * 获取该数据项在RecycleView上的索引值
     * 取值范围为{-1 ~ 正无穷}
     *
     * @return 在RecycleView上索引值，-1表示不可见
     */
    int getRecycleViewChildrenIndex();

}
