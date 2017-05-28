package com.yunxian.recycleview.multiexpandable.utils;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.yunxian.recycleview.multiexpandable.model.ExpandableNodeCoordinateModel;
import com.yunxian.recycleview.multiexpandable.model.IExpandableItemModel;

import java.util.ArrayList;
import java.util.List;

/**
 * 简单方法工具类
 *
 * @author A Shuai
 * @email ls1110924@gmail.com
 * @date 17/5/28 下午6:49
 */
public final class SimpleUtils {

    private SimpleUtils() {
        throw new IllegalStateException("shouldn't init instance!");
    }

    /**
     * 为指定的可展开节点树梳理其节点坐标以及可见性（由父节点是否展开决定）
     *
     * @param rootNodes 待梳理树的根节点
     */
    public static void teaseExpandableTree(@NonNull List<IExpandableItemModel> rootNodes) {
        if (rootNodes.size() > 0) {
            for (int i = 0, size = rootNodes.size(); i < size; i++) {
                IExpandableItemModel node = rootNodes.get(i);
                // 初始所有节点不可见
                node.setRecycleViewChildrenIndex(-1);


                ExpandableNodeCoordinateModel coordinateModel = new ExpandableNodeCoordinateModel();
                coordinateModel.appendCoordinate(i);
                node.setCoordinateInExpandableTree(coordinateModel);

                teaseExpandableTree(node.getChildren(), coordinateModel, node.isExpanded());
            }
        }
    }

    /**
     * 为当前参数指定的节点创建对应的节点坐标
     *
     * @param nodes           待创建节点坐标的节点
     * @param parentCoorModel 父节点的坐标
     * @param isExpand        true为父节点可见，反之不可见，此时
     */
    private static void teaseExpandableTree(@Nullable List<IExpandableItemModel> nodes,
                                            @NonNull ExpandableNodeCoordinateModel parentCoorModel, boolean isExpand) {
        if (nodes != null && nodes.size() > 0) {
            for (int i = 0, size = nodes.size(); i < size; i++) {
                IExpandableItemModel node = nodes.get(i);
                // 如果父节点不可见，则子节点无论是否展开，都不可见
                if (!isExpand) {
                    node.setExpanded(false);
                }

                ExpandableNodeCoordinateModel coordinateModel = new ExpandableNodeCoordinateModel(parentCoorModel);
                coordinateModel.appendCoordinate(i);
                node.setCoordinateInExpandableTree(coordinateModel);

                teaseExpandableTree(node.getChildren(), coordinateModel, node.isExpanded());
            }
        }
    }

    /**
     * 为指定的可展开节点树创建对应的可见节点
     *
     * @param rootNodes 扩展树的根节点
     * @return 可见节点列表
     */
    public static List<IExpandableItemModel> buildVisibleExpandableNodeList(@NonNull List<IExpandableItemModel> rootNodes) {
        // 忽略掉根节点的可见性，根节点永远可见，由于根节点无父节点管理是否展开或关闭
        List<IExpandableItemModel> visibleNodes = new ArrayList<>();
        for (IExpandableItemModel node : rootNodes) {
            visibleNodes.add(node);
            node.setRecycleViewChildrenIndex(visibleNodes.size() - 1);

            if (node.isGroup() && node.isExpanded()) {
                List<IExpandableItemModel> childrenNodes = node.getChildren();
                if (childrenNodes == null || childrenNodes.size() == 0) {
                    throw new IllegalStateException("Plz insure group expandableNode has children, the group expandableNode is" + node);
                }
                fillExpandableNodeList(childrenNodes, visibleNodes);
            }

        }
        return visibleNodes;
    }

    /**
     * 对参数指定的一组节点填充到可见节点中去，并递归调用所有可见可展开的节点填充到可见节点列表中去
     *
     * @param nodes        带填充的一组节点
     * @param visibleNodes 可见节点列表，填充容器
     */
    private static void fillExpandableNodeList(@NonNull List<IExpandableItemModel> nodes, @NonNull List<IExpandableItemModel> visibleNodes) {
        for (IExpandableItemModel node : nodes) {
            visibleNodes.add(node);
            node.setRecycleViewChildrenIndex(visibleNodes.size() - 1);

            if (node.isGroup() && node.isExpanded()) {
                List<IExpandableItemModel> childrenNodes = node.getChildren();
                if (childrenNodes == null || childrenNodes.size() == 0) {
                    throw new IllegalStateException("Plz insure group expandableNode has children, the group expandableNode is" + node);
                }
                fillExpandableNodeList(childrenNodes, visibleNodes);
            }
        }
    }

    /**
     * 重置所有节点在RecyclerView上的索引至默认值（省事）
     *
     * @param rootNodes 扩展树的根节点
     */
    public static void restoreInvisibleForTotalTree(@Nullable List<IExpandableItemModel> rootNodes) {
        if (rootNodes != null && rootNodes.size() > 0) {
            for (IExpandableItemModel node : rootNodes) {
                node.setRecycleViewChildrenIndex(-1);

                restoreInvisibleForTotalTree(node.getChildren());
            }
        }
    }

    /**
     * 梳理可见节点在RecyclerView上的索引
     *
     * @param visibleNodes 可见节点列表
     */
    public static void teaseIndexOfVisibleNodeList(@NonNull List<IExpandableItemModel> visibleNodes) {
        for (int i = 0, size = visibleNodes.size(); i < size; i++) {
            visibleNodes.get(i).setRecycleViewChildrenIndex(i);
        }
    }

}
