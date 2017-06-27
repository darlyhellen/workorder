package com.xiangxun.workorder.ui.adapter;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.xiangxun.workorder.R;
import com.xiangxun.workorder.bean.ChildData;
import com.xiangxun.workorder.bean.EquipmentRoot;

import java.util.List;

/**
 * Created by Administrator on 2017/6/5.
 * 子列表
 */

public class ExpandableListViewAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<ChildData> groupData;

    public ExpandableListViewAdapter(List<ChildData> groupData, Context context) {
        this.context = context;
        this.groupData = groupData;
    }


    public void setData(List<ChildData> groupData) {
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
        int ret = 0;
        if (groupData != null && groupData.get(groupPosition).getData() != null) {
            ret = groupData.get(groupPosition).getData().size();
        }
        return ret;
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
        return true;
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
        ChildData groupData = this.groupData.get(groupPosition);
        //是否展开
//        if (isExpanded) {
//            holder.img.setImageResource(R.drawable.img_bottom);
//        } else {
//            holder.img.setImageResource(R.drawable.img_right);
//        }
        holder.tv_name.setText(groupData.getName());
        holder.tv_num.setText(groupData.getUrl());
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_child_expandablelistview, null);
            holder = new ChildViewHolder();
            holder.img = (ImageView) convertView.findViewById(R.id.id_item_equip_child_head);
            holder.tv_name = (TextView) convertView.findViewById(R.id.id_item_equip_child_name);
            holder.tv_content = (TextView) convertView.findViewById(R.id.id_item_equip_child_content);
            convertView.setTag(holder);
        } else {
            holder = (ChildViewHolder) convertView.getTag();
        }
        EquipmentRoot childData = groupData.get(groupPosition).getData().get(childPosition);
        holder.tv_name.setText(childData.getMessage());
        return convertView;
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

    class ChildViewHolder {
        ImageView img;
        TextView tv_name, tv_content;
    }
}
