package com.yunxian.recycleview.multiexpandable.model;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

/**
 * 扩展树上的结点坐标对应的数据模型表示
 *
 * @author A Shuai
 * @email ls1110924@gmail.com
 * @date 17/5/22 下午5:02
 */
public final class ExpandableNodeCoordinateModel {

    private final List<Integer> mCoordinates = new ArrayList<>();

    public ExpandableNodeCoordinateModel() {
    }

    /**
     * 追加一维坐标
     *
     * @param coordinate 一维坐标
     */
    public void appendCoordinate(int coordinate) {
        mCoordinates.add(coordinate);
    }

    /**
     * 判断指定参数的节点坐标是否为当前节点的子节点
     *
     * @param childCoordinateModel 待判断的节点坐标
     * @return true为参数指定节点为当前节点的子节点，否则不是
     */
    public boolean isChild(@NonNull ExpandableNodeCoordinateModel childCoordinateModel) {
        if (mCoordinates.size() <= childCoordinateModel.mCoordinates.size()) {
            for (int i = 0, size = mCoordinates.size(); i < size; i++) {
                if (!mCoordinates.get(i).equals(childCoordinateModel.mCoordinates.get(i))) {
                    return false;
                }
            }
            return true;
        } else {
            return false;
        }
    }

}
