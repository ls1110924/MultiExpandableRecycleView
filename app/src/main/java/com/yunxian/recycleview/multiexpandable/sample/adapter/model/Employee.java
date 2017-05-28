package com.yunxian.recycleview.multiexpandable.sample.adapter.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.yunxian.recycleview.multiexpandable.model.ExpandableNodeCoordinateModel;
import com.yunxian.recycleview.multiexpandable.model.IExpandableItemModel;

import java.util.ArrayList;
import java.util.List;

/**
 * @author A Shuai
 * @email ls1110924@gmail.com
 * @date 2017/5/29 1:13
 */
public class Employee implements IExpandableItemModel {

    // 姓名
    private String name;
    // 下属
    private List<Employee> subordinate = null;

    public Employee() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Employee> getSubordinate() {
        return subordinate;
    }

    public void setSubordinate(List<Employee> subordinate) {
        this.subordinate = subordinate;
    }

    @Override
    public boolean isGroup() {
        return subordinate != null && subordinate.size() > 0;
    }

    @Nullable
    @Override
    public List<IExpandableItemModel> getChildren() {
        if (subordinate != null && subordinate.size() > 0) {
            List<IExpandableItemModel> resultList = new ArrayList<>();
            resultList.addAll(subordinate);
            return resultList;
        } else {
            return null;
        }
    }

    private boolean expandable = false;

    @Override
    public boolean isExpanded() {
        return expandable;
    }

    @Override
    public void setExpanded(boolean expanded) {
        expandable = expanded;
    }

    @NonNull
    @Override
    public String getItemViewType() {
        return Employee.class.getSimpleName();
    }

    private ExpandableNodeCoordinateModel coordinateModel = null;

    @Override
    public void setCoordinateInExpandableTree(ExpandableNodeCoordinateModel coordinate) {
        coordinateModel = coordinate;
    }

    @Override
    public ExpandableNodeCoordinateModel getCoordinateInExpandableTree() {
        return coordinateModel;
    }

    private int childIndex = -1;

    @Override
    public void setRecycleViewChildrenIndex(int position) {
        childIndex = position;
    }

    @Override
    public int getRecycleViewChildrenIndex() {
        return childIndex;
    }
}
