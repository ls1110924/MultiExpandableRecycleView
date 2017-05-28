package com.yunxian.recycleview.multiexpandable.sample.adapter.viewholder;

import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yunxian.recycleview.multiexpandable.enumeration.ExpandableItemViewNodeType;
import com.yunxian.recycleview.multiexpandable.model.IExpandableItemModel;
import com.yunxian.recycleview.multiexpandable.sample.R;
import com.yunxian.recycleview.multiexpandable.sample.adapter.model.Employee;
import com.yunxian.recycleview.multiexpandable.viewholder.AbsMultiExpandableItemViewHolder;

import java.util.List;

/**
 * @author A Shuai
 * @email ls1110924@gmail.com
 * @date 2017/5/29 1:34
 */
public class EmployeeViewHolder extends AbsMultiExpandableItemViewHolder {

    private final int emptyUnit;

    private final View emptyView;
    private final ImageView flagImgView;
    private final TextView nameTxtView;
    private final ImageView expandImgView;

    public EmployeeViewHolder(View itemView) {
        super(itemView);

        emptyUnit = itemView.getContext().getResources().getDimensionPixelSize(R.dimen.empty_unit);

        emptyView = itemView.findViewById(R.id.empty);
        flagImgView = (ImageView) itemView.findViewById(R.id.flag);
        nameTxtView = (TextView) itemView.findViewById(R.id.name);
        expandImgView = (ImageView) itemView.findViewById(R.id.expand_btn);
    }

    @Override
    protected int getItemViewNodeType() {
        return ExpandableItemViewNodeType.ALL_ITEM;
    }

    @Override
    protected int getRootViewIdRes() {
        return R.id.selected_btn;
    }

    @Override
    protected int getExpandBtnViewIdRes() {
        return R.id.expand_btn;
    }

    @Override
    protected void bindData(int position, @NonNull IExpandableItemModel dataModel) {
        if (dataModel instanceof Employee) {
            Employee employee = (Employee) dataModel;

            List<Integer> coor = employee.getCoordinateInExpandableTree().getCoordinates();
            ViewGroup.LayoutParams layoutParams = emptyView.getLayoutParams();
            if (layoutParams.width != emptyUnit * coor.size()) {
                layoutParams.width = emptyUnit * coor.size();
                emptyView.setLayoutParams(layoutParams);
            }

            flagImgView.setImageResource(employee.isExpanded() ? R.mipmap.flag_expand : R.mipmap.flag_fold);

            nameTxtView.setText(employee.getName());

            expandImgView.setVisibility(employee.isGroup() ? View.VISIBLE : View.INVISIBLE);
        }
    }
}
