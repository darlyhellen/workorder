package com.xiangxun.workorder.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.hellen.baseframe.binder.ContentBinder;
import com.hellen.baseframe.binder.ViewsBinder;
import com.hellen.baseframe.common.dlog.DLog;
import com.hellen.baseframe.common.obsinfo.ToastApp;
import com.xiangxun.workorder.R;
import com.xiangxun.workorder.base.AppEnum;
import com.xiangxun.workorder.base.BaseActivity;
import com.xiangxun.workorder.base.ItemClickListenter;
import com.xiangxun.workorder.bean.EquipMenuChildData;
import com.xiangxun.workorder.bean.EquipmentInfo;
import com.xiangxun.workorder.ui.adapter.EquipmentListAdapter;
import com.xiangxun.workorder.ui.biz.EquipmentListListener.EquipmentListInterface;
import com.xiangxun.workorder.ui.presenter.EquipmentListPresenter;
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
public class EquipmentListActivity extends BaseActivity implements View.OnClickListener, XListView.IXListViewListener, EquipmentListInterface {
    @ViewsBinder(R.id.id_work_order_header)
    private HeaderView header;
    @ViewsBinder(R.id.id_work_order_xlist)
    private XListView xlist;
    @ViewsBinder(R.id.id_work_order_text)
    private TextView textView;
    private String textDes;

    private EquipmentListAdapter adapter;

    private List<EquipmentInfo> data;

    private EquipmentListPresenter presenter;
    //至关重要的一个参数
    private EquipMenuChildData patrol;

    private int currentPage = 1;
    private int PageSize = 10;
    private int totalSize = 0;
    private String devicename;
    private String devicenum;
    private String deviceip;
    private int listState = AppEnum.LISTSTATEFIRST;


    @Override
    protected void initView(Bundle savedInstanceState) {
        //获取完成
        header.setLeftBackgroundResource(R.mipmap.ic_title_back);
        presenter = new EquipmentListPresenter(this);
        textDes = "没有设备信息！";
        data = new ArrayList<EquipmentInfo>();
        adapter = new EquipmentListAdapter(data, R.layout.item_activity_work_order, this);
        xlist.setAdapter(adapter);
        patrol = (EquipMenuChildData) getIntent().getSerializableExtra("EquipMenuChildData");
        if (patrol != null) {
            header.setTitle(patrol.getName());
            presenter.getEquipment(currentPage, patrol.getType(), devicename, devicenum, deviceip);
        }
    }

    @Override
    protected void initListener() {
        header.setLeftBackOneListener(this);
        xlist.setPullLoadEnable(true);
        xlist.setXListViewListener(this);
        xlist.setOnItemClickListener(new ItemClickListenter() {
            @Override
            public void NoDoubleItemClickListener(AdapterView<?> parent, View view, int position, long id) {
                DLog.i(getClass().getSimpleName(), position);

                DLog.i("onItemClick--" + position);
                EquipmentInfo ds = (EquipmentInfo) parent.getItemAtPosition(position);
                Intent intent = new Intent(EquipmentListActivity.this, WorkOrderDetailActivity.class);
                intent.putExtra("EquipmentInfo", ds);
                startActivityForResult(intent, 700);
            }
        });
    }

    @Override
    public void onRefresh(View v) {
        currentPage = 1;
        listState = AppEnum.LISTSTATEREFRESH;
        presenter.getEquipment(currentPage, patrol.getType(), devicename, devicenum, deviceip);
    }

    @Override
    public void onLoadMore(View v) {
        if (totalSize < PageSize) {
            ToastApp.showToast("已经是最后一页");
            xlist.removeFooterView(xlist.mFooterView);
        } else {
            currentPage++;
            listState = AppEnum.LISTSTATELOADMORE;
            presenter.getEquipment(currentPage, patrol.getType(), devicename, devicenum, deviceip);
        }
    }


    private void stopXListView() {
        xlist.stopRefresh();
        xlist.stopLoadMore();
    }

    protected void setEquipmentInfo(List<EquipmentInfo> orderBeans) {
        xlist.removeFooterView(xlist.mFooterView);
        if (orderBeans.size() > PageSize - 1) {
            xlist.addFooterView(xlist.mFooterView);
        }
        switch (listState) {
            case AppEnum.LISTSTATEFIRST:
                data.clear();
                data.addAll(orderBeans);
                adapter.setData(data);
                break;
            case AppEnum.LISTSTATEREFRESH:
                data.clear();
                data.addAll(orderBeans);
                adapter.setData(data);
                break;
            case AppEnum.LISTSTATELOADMORE:
                data.addAll(orderBeans);
                adapter.setData(data);
                break;
        }
        totalSize = orderBeans.size();
    }


    //监听类实现方法
    @Override
    public void onWorkOrderSuccess(List<EquipmentInfo> datas) {
        stopXListView();
        DLog.i("onWorkOrderSuccess" + datas);
        setEquipmentInfo(datas);
        if (xlist.getCount() > 1) {
            textView.setVisibility(View.GONE);
        } else {
            textView.setVisibility(View.VISIBLE);
            textView.setText(textDes);
        }
    }

    @Override
    public void onWorkOrderFailed() {
        stopXListView();
        DLog.i("onWorkOrderFailed");
    }

    @Override
    public String getDevicename() {
        return null;
    }

    @Override
    public String getDevicenum() {
        return null;
    }

    @Override
    public String getDeviceip() {
        return null;
    }

    @Override
    public void onClick(View v) {
        presenter.onClickDown(this, v);
    }

    @Override
    public void end() {
        finish();
    }

    @Override
    public void setDisableClick() {

    }

    @Override
    public void setEnableClick() {

    }
}
