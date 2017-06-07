package com.xiangxun.workorder.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ExpandableListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hellen.baseframe.binder.ContentBinder;
import com.hellen.baseframe.binder.ViewsBinder;
import com.hellen.baseframe.common.dlog.DLog;
import com.hellen.baseframe.common.utiltools.SharePreferHelp;
import com.xiangxun.workorder.R;
import com.xiangxun.workorder.base.AppEnum;
import com.xiangxun.workorder.base.BaseActivity;
import com.xiangxun.workorder.bean.ChildData;
import com.xiangxun.workorder.bean.EquipMenuChildData;
import com.xiangxun.workorder.bean.EquipmentRoot;
import com.xiangxun.workorder.bean.GroupData;
import com.xiangxun.workorder.bean.ObjectData;
import com.xiangxun.workorder.ui.adapter.EquipmentMenuAdapter;
import com.xiangxun.workorder.ui.adapter.ExpandableListViewAdapter;
import com.xiangxun.workorder.ui.adapter.RootExpandableListViewAdapter;
import com.xiangxun.workorder.ui.biz.EquipmentMenuListener;
import com.xiangxun.workorder.ui.biz.EquipmentMenuListener.EquipmentMenuInterface;
import com.xiangxun.workorder.ui.presenter.EquipmentMenuPresenter;
import com.xiangxun.workorder.widget.header.HeaderView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/6/5.
 *
 * @TODO: 設備管理功能頁面，包括幾大點。下拉列表。點擊進設備列表。然後點擊進詳情。
 */
@ContentBinder(R.layout.activity_equipment)
public class EquipmentMenuAcitvity extends BaseActivity implements OnClickListener, EquipmentMenuInterface {
    @ViewsBinder(R.id.id_equipment_header)
    private HeaderView header;
    @ViewsBinder(R.id.id_equipment_listview)
    private ExpandableListView listView;
    private EquipmentMenuAdapter adapter;

    private EquipmentMenuPresenter presenter;

    private ObjectData data;

    @Override
    protected void initView(Bundle savedInstanceState) {
        presenter = new EquipmentMenuPresenter(this);
        adapter = new EquipmentMenuAdapter(this, presenter.findData());
        listView.setAdapter(adapter);

        //重写OnGroupClickListener，实现当展开时，ExpandableListView不自动滚动
        listView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                if (parent.isGroupExpanded(groupPosition)) {
                    parent.collapseGroup(groupPosition);
                } else {
                    //第二个参数false表示展开时是否触发默认滚动动画
                    parent.expandGroup(groupPosition, false);
                }
                return true;
            }
        });
        header.setTitle("设备列表");
        header.setLeftBackgroundResource(R.mipmap.ic_title_back);
        header.setRightBackgroundResource(0);
    }

    @Override
    protected void initListener() {
        header.setLeftBackOneListener(this);
        listView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                EquipMenuChildData data = (EquipMenuChildData) adapter.getChild(groupPosition, childPosition);
                DLog.i(getClass().getSimpleName(), groupPosition + "---" + childPosition + "---" + data.getName());
                return false;
            }
        });

    }


    @Override
    public void onClick(View v) {
        finish();
    }

    @Override
    public void end() {

    }

    @Override
    public void setDisableClick() {

    }

    @Override
    public void setEnableClick() {

    }
}
