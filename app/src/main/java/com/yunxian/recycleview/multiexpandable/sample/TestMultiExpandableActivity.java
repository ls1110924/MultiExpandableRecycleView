package com.yunxian.recycleview.multiexpandable.sample;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.yunxian.recycleview.multiexpandable.MultiExpandableRecycleViewAdapter;
import com.yunxian.recycleview.multiexpandable.model.IExpandableItemModel;
import com.yunxian.recycleview.multiexpandable.sample.adapter.model.Employee;
import com.yunxian.recycleview.multiexpandable.sample.adapter.provider.EmployProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.Stack;

/**
 * 测试RecyclerView的多级展开功能页面
 *
 * @author A Shuai
 * @email ls1110924@gmail.com
 * @date 2017/5/29 0:51
 */
public class TestMultiExpandableActivity extends AppCompatActivity {

    private static final String TAG = TestMultiExpandableActivity.class.getSimpleName();

    private final CommonCallbackListener mCommonListener = new CommonCallbackListener();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_multiexpand);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        MultiExpandableRecycleViewAdapter recycleViewAdapter = new MultiExpandableRecycleViewAdapter(this, recyclerView);
        recycleViewAdapter.registerItemViewProvider(Employee.class.getSimpleName(), EmployProvider.class);
        recycleViewAdapter.addOnExpandableItemClickListener(mCommonListener);

        recycleViewAdapter.setData(mockData());
    }

    private List<IExpandableItemModel> mockData() {

        final int maxLevel = 5;

        List<IExpandableItemModel> itemModels = new ArrayList<>();
        Stack<Integer> stack = new Stack<>();
        Random random = new Random();

        for (int i = 0, size = 5; i < size; i++) {
            stack.push(i);

            Employee employee = new Employee();
            employee.setName(String.format(Locale.getDefault(), "Name-->%d", i));
            employee.setExpanded(random.nextBoolean());
            List<Employee> children = randSubordinate(random, stack, 4);
            employee.setSubordinate(children);
            itemModels.add(employee);

            stack.pop();
        }
        return itemModels;

    }

    private static List<Employee> randSubordinate(Random random, Stack<Integer> indexStack, int level) {
        int nodesNum = Math.max(1, random.nextInt(5));
        List<Employee> itemModels = new ArrayList<>();
        for (int i = 0; i < nodesNum; i++) {
            indexStack.push(i);

            Employee employee = new Employee();
            employee.setName(buildNodeName(indexStack));
            employee.setExpanded(random.nextBoolean());

            // 递归生辰子节点
            if (level > 0) {
                employee.setSubordinate(randSubordinate(random, indexStack, level - 1));
            }

            itemModels.add(employee);

            indexStack.pop();
        }
        return itemModels;
    }

    private static String buildNodeName(Stack<Integer> integerStack) {
        StringBuilder stringBuilder = new StringBuilder("Name-->");
        Integer[] indexArray = integerStack.toArray(new Integer[0]);
        stringBuilder.append(indexArray[0]);
        for (int i = 1; i < indexArray.length; i++) {
            stringBuilder.append('-').append(indexArray[i]);
        }
        return stringBuilder.toString();
    }

    private class CommonCallbackListener implements MultiExpandableRecycleViewAdapter.OnExpandableItemClickListener {

        @Override
        public void onExpandableItemSelected(@NonNull IExpandableItemModel dataModel, @NonNull List<Integer> coordinate) {
            Employee employee = (Employee) dataModel;
            Log.d(TAG, "the " + employee.getName() + " has been selected!");
        }
    }

}
