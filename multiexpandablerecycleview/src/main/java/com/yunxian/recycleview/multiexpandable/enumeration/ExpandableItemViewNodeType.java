package com.yunxian.recycleview.multiexpandable.enumeration;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 可展开Item项的节点类型，即是否为父节点或者叶子节点
 *
 * @author A Shuai
 * @email ls1110924@gmail.com
 * @date 17/5/22 上午10:11
 */
@IntDef({ExpandableItemViewNodeType.ALL_ITEM, ExpandableItemViewNodeType.GROUP_ITEM, ExpandableItemViewNodeType.LEAF_ITEM})
@Retention(RetentionPolicy.CLASS)
public @interface ExpandableItemViewNodeType {

    /**
     * 既可以用作组项，也可以用作叶子项
     */
    int ALL_ITEM = 0;

    /**
     * 包含子项的组项，即当前项可展开。适用于那些父子节点明确的情况
     */
    int GROUP_ITEM = 1;

    /**
     * 叶子项，无子项，不可展开
     */
    int LEAF_ITEM = 2;

}
