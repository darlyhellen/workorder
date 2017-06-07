package com.xiangxun.workorder.ui.main;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.hellen.baseframe.binder.ContentBinder;
import com.hellen.baseframe.binder.ViewsBinder;
import com.xiangxun.workorder.R;
import com.xiangxun.workorder.base.AppEnum;
import com.xiangxun.workorder.base.BaseActivity;
import com.xiangxun.workorder.bean.EquipMenuChildData;
import com.xiangxun.workorder.bean.Patrol;
import com.xiangxun.workorder.bean.WorkOrderData;
import com.xiangxun.workorder.ui.adapter.WorkOrderAdapter;
import com.xiangxun.workorder.ui.presenter.EquipmentListPresenter;
import com.xiangxun.workorder.ui.presenter.WorkOrderPresenter;
import com.xiangxun.workorder.widget.header.HeaderView;
import com.xiangxun.workorder.widget.xlistView.XListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zhangyuhui/Darly on 2017/6/7.
 * Copyright by [Zhangyuhui/Darly]
 * ©2017 XunXiang.Company. All rights reserved.
 *
 * @TODO: 对应设备列表
 */
@ContentBinder(R.layout.activity_work_order)
public class EquipmentListActivity extends BaseActivity implements View.OnClickListener, XListView.IXListViewListener, AdapterView.OnItemClickListener {
    @ViewsBinder(R.id.id_work_order_header)
    private HeaderView header;
    @ViewsBinder(R.id.id_work_order_xlist)
    private XListView xlist;
    @ViewsBinder(R.id.id_work_order_text)
    private TextView textView;
    private String textDes;

    private WorkOrderAdapter adapter;

    private List<WorkOrderData> data;

    private EquipmentListPresenter presenter;
    //至关重要的一个参数
    private EquipMenuChildData patrol;

    private int currentPage = 1;
    private int PageSize = 10;
    private int totalSize = 0;
    private String workorder;
    private String devicename;
    private String devicenum;
    private String deviceip;
    private int listState = AppEnum.LISTSTATEFIRST;


    @Override
    protected void initView(Bundle savedInstanceState) {
        //获取完成
        header.setLeftBackgroundResource(R.mipmap.ic_title_back);

        adapter = new WorkOrderAdapter(data, R.layout.item_activity_work_order, this);
        xlist.setAdapter(adapter);
        patrol = (EquipMenuChildData) getIntent().getSerializableExtra("EquipMenuChildData");
        if (patrol != null) {
            header.setTitle(patrol.getName());
            //presenter.getEquipment(currentPage, patrol.getType(), devicename, devicenum, deviceip);
        }
    }

    @Override
    protected void initListener() {

    }

    @Override
    public void onRefresh(View v) {

    }

    @Override
    public void onLoadMore(View v) {

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
