package com.xiangxun.workorder.ui.adapter;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.hellen.baseframe.common.dlog.DLog;
import com.xiangxun.workorder.R;
import com.xiangxun.workorder.bean.ChildData;
import com.xiangxun.workorder.bean.EquipmentRoot;
import com.xiangxun.workorder.bean.GroupData;
import com.xiangxun.workorder.common.image.ImageLoaderUtil;
import com.xiangxun.workorder.widget.scroll.CustomExpandableListView;

import java.util.List;

/**
 * Created by Administrator on 2017/6/5.
 * 在外层Expand中，他的所有二级条目都是一个，为什么，因为他具体的显示都交给了子ExpandableListView，二级条目的目的是为了把子ExpandableListView显示出来。
 */

public class RootExpandableListViewAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<GroupData> groupData;

    public RootExpandableListViewAdapter(Context context, List<GroupData> groupData) {
        this.context = context;
        this.groupData = groupData;
    }


    public void setData(List<GroupData> groupData) {
        this.groupData = groupData;
        this.notifyDataSetChanged();
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public int getGroupCount() {
        int ret = 0;
        if (groupData != null) {
            ret = groupData.size();
        }
        return ret;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        // 很关键，，一定要返回  1
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groupData.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return groupData.get(groupPosition).getData().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_group_expandablelistview, null);
            holder = new GroupViewHolder();
            holder.img = (ImageView) convertView.findViewById(R.id.id_item_equip_iv);
            holder.tv_name = (TextView) convertView.findViewById(R.id.id_item_equip_name);
            holder.tv_num = (TextView) convertView.findViewById(R.id.id_item_equip_num);
            convertView.setTag(holder);
        } else {
            holder = (GroupViewHolder) convertView.getTag();
        }
        GroupData groupData = this.groupData.get(groupPosition);
        //是否展开
//        if (isExpanded) {
//            holder.img.setImageResource(R.drawable.img_bottom);
//        } else {
//            holder.img.setImageResource(R.drawable.img_right);
//        }
        holder.tv_name.setText(groupData.getName());
        holder.tv_num.setText(groupData.getNum());
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        AbsListView.LayoutParams layoutParams = new AbsListView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);

        CustomExpandableListView view = new CustomExpandableListView(context);
        // 加载班级的适配器
        final ExpandableListViewAdapter adapter = new ExpandableListViewAdapter(groupData.get(groupPosition).getData(), context);
        view.setAdapter(adapter);
        view.setPadding(20, 0, 0, 0);
        //重写OnGroupClickListener，实现当展开时，ExpandableListView不自动滚动
        view.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                if (parent.isGroupExpanded(groupPosition)) {
                    parent.collapseGroup(groupPosition);
                } else {
                    //第二个参数false表示展开时是否触发默认滚动动画
                    parent.expandGroup(groupPosition, false);
                }
                //telling the listView we have handled the group click, and don't want the default actions.
                return true;
            }
        });

        return view;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public void onGroupExpanded(int groupPosition) {

    }

    @Override
    public void onGroupCollapsed(int groupPosition) {

    }

    @Override
    public long getCombinedChildId(long groupId, long childId) {
        return 0;
    }

    @Override
    public long getCombinedGroupId(long groupId) {
        return 0;
    }

    class GroupViewHolder {
        ImageView img;
        TextView tv_name, tv_num;
    }

}