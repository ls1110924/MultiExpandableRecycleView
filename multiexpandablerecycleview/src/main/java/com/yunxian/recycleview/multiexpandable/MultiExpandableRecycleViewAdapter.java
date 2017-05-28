package com.yunxian.recycleview.multiexpandable;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.ViewGroup;

import com.yunxian.recycleview.multiexpandable.model.IExpandableItemModel;
import com.yunxian.recycleview.multiexpandable.provider.IMultiExpandableItemViewProvider;
import com.yunxian.recycleview.multiexpandable.viewholder.AbsMultiExpandableItemViewHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 可任意多级展开子项的RecycleView的适配器
 *
 * @author A Shuai
 * @email ls1110924@gmail.com
 * @date 17/5/19 下午3:41
 */
public class MultiExpandableRecycleViewAdapter extends RecyclerView.Adapter<AbsMultiExpandableItemViewHolder>implements AbsMultiExpandableItemViewHolder.OnBtnClickListener {

    private static final String TAG = MultiExpandableRecycleViewAdapter.class.getSimpleName();

    private final Context mContext;
    private final RecyclerView mRecyclerView;

    private final List<IExpandableItemModel> mTotalDataSet = new ArrayList<>();
    private final List<IExpandableItemModel> mVisibleDataSet = new ArrayList<>();

    private final List<String> mItemViewTypeIdMap = new ArrayList<>();
    private final Map<String, IMultiExpandableItemViewProvider> mViewHolderProviders = new HashMap<>();
    private final Map<String, Class<? extends IMultiExpandableItemViewProvider>> mViewHolderProviderClasses = new HashMap<>();

    public MultiExpandableRecycleViewAdapter(@NonNull Context context, @NonNull RecyclerView recyclerView) {
        mContext = context;
        mRecyclerView = recyclerView;

        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        mRecyclerView.setAdapter(this);
    }

    public final void setData(@NonNull List<IExpandableItemModel> dataSet) {
        mTotalDataSet.addAll(dataSet);
        rebuildVisibleDataSet();
    }

    private void rebuildVisibleDataSet() {
        // TODO 当有节点状态发生变化时，重建可见Item的数据集
    }

    @Override
    public AbsMultiExpandableItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        String itemViewType = mItemViewTypeIdMap.get(viewType);
        if (TextUtils.isEmpty(itemViewType)) {
            throw new IllegalStateException("Plz register itemView provider first!");
        }
        IMultiExpandableItemViewProvider viewProvider = getItemViewProvider(itemViewType);
        AbsMultiExpandableItemViewHolder viewHolder = viewProvider.createViewHolder(mContext, parent);
        viewHolder.addOnBtnClickListener(this);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(AbsMultiExpandableItemViewHolder holder, int position) {
        holder.bindData(position, new ArrayList<Integer>(), mVisibleDataSet.get(position));
    }

    @Override
    public int getItemViewType(int position) {
        String itemViewTypeStr = mVisibleDataSet.get(position).getItemViewType();
        int index = mItemViewTypeIdMap.indexOf(itemViewTypeStr);
        if (index >= 0) {
            return index;
        } else {
            mItemViewTypeIdMap.add(itemViewTypeStr);
            return mItemViewTypeIdMap.size() - 1;
        }
    }

    @Override
    public int getItemCount() {
        return mVisibleDataSet.size();
    }

    /**
     * 给指定的ItemView类型标示符注册对应的ViewHolder生成器
     *
     * @param itemViewType        ItemView类型标示符
     * @param itemViewProviderClz ViewHolder生成器
     */
    public final void registerItemViewProvider(@NonNull String itemViewType, @NonNull Class<? extends IMultiExpandableItemViewProvider> itemViewProviderClz) {
        if (mViewHolderProviderClasses.containsKey(itemViewType)) {
            Class<? extends IMultiExpandableItemViewProvider> oldClz = mViewHolderProviderClasses.get(itemViewType);
            if (oldClz == null) {
                mViewHolderProviderClasses.put(itemViewType, itemViewProviderClz);
            } else if (oldClz != itemViewProviderClz) {
                throw new IllegalStateException("the same itemViewType has been register, the registered clz is " + oldClz.getSimpleName() + " and the new clz is " + itemViewProviderClz.getSimpleName());
            }
        } else {
            mViewHolderProviderClasses.put(itemViewType, itemViewProviderClz);
        }
    }

    /**
     * 反注册指定的ItemView类型标示符对应的ViewHolder生成器
     *
     * @param itemViewType ItemView类型标示符
     */
    public final void unregisterItemViewProvider(@NonNull String itemViewType) {
        mViewHolderProviderClasses.remove(itemViewType);
    }

    /**
     * 获取指定ItemView类型标示符对应的ViewProvider对象
     *
     * @param itemViewType ItemView类型标示符
     * @return ViewProvider对象
     */
    @NonNull
    private IMultiExpandableItemViewProvider getItemViewProvider(@NonNull String itemViewType) {
        IMultiExpandableItemViewProvider itemViewProvider = mViewHolderProviders.get(itemViewType);
        if (itemViewProvider == null) {
            Class<? extends IMultiExpandableItemViewProvider> itemViewProviderClz = mViewHolderProviderClasses.get(itemViewType);
            if (itemViewProviderClz == null) {
                throw new IllegalStateException("plz register the corresponding item view provider of item view type " + itemViewType);
            }
            try {
                itemViewProvider = itemViewProviderClz.newInstance();
            } catch (Exception e) {
                Log.d(TAG, "instantiation the provider fail, the provider class is " + itemViewProviderClz.getSimpleName(), e);
                throw new IllegalStateException("instantiation the provider fail, the provider class is " + itemViewProviderClz.getSimpleName(), e);
            }
            mViewHolderProviders.put(itemViewType, itemViewProvider);
        }
        return itemViewProvider;
    }

    @Override
    public boolean onExpandedBtnClick(@NonNull IExpandableItemModel dataModel) {
        if (dataModel.isGroup()) {
            if (dataModel.isExpanded()) {
                List<IExpandableItemModel> childrenDataModel = new ArrayList<>();
                for (int i = dataModel.getRecycleViewChildrenIndex() + 1, size = mVisibleDataSet.size(); i < size; i++) {
                    IExpandableItemModel childDataModel = mVisibleDataSet.get(i);
                    if (dataModel.getCoordinateInExpandableTree().isChild(childDataModel.getCoordinateInExpandableTree())) {
                        childrenDataModel.add(childDataModel);
                    } else {
                        break;
                    }
                }
                mVisibleDataSet.removeAll(childrenDataModel);
                notifyItemRangeRemoved(dataModel.getRecycleViewChildrenIndex() + 1, childrenDataModel.size());
            } else {
                List<? extends IExpandableItemModel> childrenDataModel = dataModel.getChildren();
                if (childrenDataModel != null && childrenDataModel.size() > 0) {
                    mVisibleDataSet.addAll(dataModel.getRecycleViewChildrenIndex() + 1, childrenDataModel);
                    notifyItemRangeInserted(dataModel.getRecycleViewChildrenIndex() + 1, childrenDataModel.size());
                } else {
                    Log.d(TAG, "the leaf dataModel has clicked by expanded btn");
                }
            }
        } else {
            Log.d(TAG, "the leaf dataModel has clicked by expanded btn");
        }
        return false;
    }

    @Override
    public boolean onSelectedBtnClick(@NonNull IExpandableItemModel dataModel) {
        return false;
    }
}
