package com.xiangxun.workorder.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.hellen.baseframe.binder.ContentBinder;
import com.hellen.baseframe.binder.ViewsBinder;
import com.hellen.baseframe.common.dlog.DLog;
import com.hellen.baseframe.common.obsinfo.ToastApp;
import com.xiangxun.workorder.R;
import com.xiangxun.workorder.base.AppEnum;
import com.xiangxun.workorder.base.BaseActivity;
import com.xiangxun.workorder.bean.WorkOrderData;
import com.xiangxun.workorder.ui.adapter.WorkOrderAdapter;
import com.xiangxun.workorder.ui.biz.WorkOrderListener;
import com.xiangxun.workorder.ui.presenter.WorkOrderPresenter;
import com.xiangxun.workorder.widget.header.HeaderView;
import com.xiangxun.workorder.widget.xlistView.XListView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Created by Zhangyuhui/Darly on 2017/5/19.
 * Copyright by [Zhangyuhui/Darly]
 * ©2017 XunXiang.Company. All rights reserved.
 *
 * @TODO:我的工单页面。传递给服务端用户ID，根据页码进行页面刷新加载。
 */
@ContentBinder(R.layout.activity_work_order)
public class WorkOrderActivity extends BaseActivity implements View.OnClickListener, XListView.IXListViewListener, AdapterView.OnItemClickListener, WorkOrderListener.WorkOrderInterface {

    @ViewsBinder(R.id.id_work_order_header)
    private HeaderView header;
    @ViewsBinder(R.id.id_work_order_xlist)
    private XListView xlist;

    private WorkOrderAdapter adapter;

    private List<WorkOrderData> data;

    private WorkOrderPresenter presenter;

    private int currentPage = 1;
    private int PageSize = 10;
    private int totalSize = 0;
    private int listState = AppEnum.LISTSTATEFIRST;


    @Override
    protected void initView(Bundle savedInstanceState) {
        presenter = new WorkOrderPresenter(this);
        header.setTitle(R.string.main_work_order);
        header.setLeftBackgroundResource(R.mipmap.back_image);
        header.setRightBackgroundResource(R.mipmap.xw_title_search);
        data = new ArrayList<>();
        adapter = new WorkOrderAdapter(data, R.layout.item_activity_work_order, this);
        xlist.setAdapter(adapter);
        presenter.getWorkOrderByPage(currentPage, null);
    }

    @Override
    protected void initListener() {
        header.setLeftBackOneListener(this);
        header.setRightOnClickListener(this);
        xlist.setPullLoadEnable(true);
        xlist.setXListViewListener(this);
        xlist.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View v) {
        presenter.onClickDown(this, v);
    }


    //开始XList的刷新
    @Override
    public void onRefresh(View v) {
        currentPage = 1;
        listState = AppEnum.LISTSTATEREFRESH;
        presenter.getWorkOrderByPage(currentPage, null);
    }

    @Override
    public void onLoadMore(View v) {
        if (totalSize < PageSize) {
            ToastApp.showToast("已经是最后一页");
            xlist.removeFooterView(xlist.mFooterView);
        } else {
            currentPage++;
            listState = AppEnum.LISTSTATELOADMORE;
            presenter.getWorkOrderByPage(currentPage, null);
        }
    }

    // xLisView 停止
    private void stopXListView() {
        xlist.stopRefresh();
        xlist.stopLoadMore();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        DLog.i("onItemClick--" + position);
        WorkOrderData ds = (WorkOrderData) parent.getItemAtPosition(position);
        Intent intent = new Intent(this, LbsAmapActivity.class);
        intent.putExtra("data", ds);
        startActivity(intent);
    }


    protected void setWorkOrderData(List<WorkOrderData> orderBeans) {
        xlist.removeFooterView(xlist.mFooterView);
        if (orderBeans.size() > 9) {
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
        totalSize += orderBeans.size();
    }

    //监听类实现方法
    @Override
    public void onWorkOrderSuccess(List<WorkOrderData> datas) {
        stopXListView();
        DLog.i("onWorkOrderSuccess" + datas);
        setWorkOrderData(datas);

    }

    @Override
    public void onWorkOrderFailed() {
        stopXListView();
        DLog.i("onWorkOrderFailed");
        //异常情况下，进入假数据环节。
        List<WorkOrderData> datas = new ArrayList<>();
        SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (int i = 0; i < 10; i++) {
            WorkOrderData dd = new WorkOrderData();
            dd.workDays = i;
            dd.serveBackText = i + "" + currentPage;
            dd.dutyOrgCode = i + "" + currentPage;
            dd.complaintAddress = i + "" + currentPage;
            dd.workAssess = i + "" + currentPage;
            dd.updateId = i + "" + currentPage;
            dd.workOrderText = i + "208省道22km北方向故障维护" + currentPage;
            dd.backVisitId = i + "" + currentPage;
            dd.employeeName = i + "" + currentPage;
            dd.issuedRegName = i + "" + currentPage;
            dd.annexNumber = i + currentPage;
            dd.orgName = i + "" + currentPage;
            dd.siteDescription = i + "" + currentPage;
            dd.workEventType = i + "" + currentPage;
            dd.disposePhotoId = i + "" + currentPage;
            dd.localPhotoNumber = i + currentPage;
            dd.localPhotoId = i + "" + currentPage;
            dd.dutyOrgType = i + "" + currentPage;
            dd.workEventPointLatlon = i + "" + currentPage;
            dd.workOrderId = i + "" + currentPage;
            dd.satisfactionDegree = i + "" + currentPage;
            dd.workEventPointExplain = i + "" + currentPage;
            dd.workIsChange = i + currentPage;
            dd.checkDescription = i + "" + currentPage;
            dd.workOrderCode = i + "17041008265709544d74" + currentPage;
            dd.backOrgId = i + "" + currentPage;
            dd.eventBackText = i + "" + currentPage;
            dd.workOrderRemark = i + "" + currentPage;
            dd.workEventSource = i + "" + currentPage;
            dd.complaintTel = i + "" + currentPage;
            dd.orgId = i + "" + currentPage;
            dd.checkStatus = i + "" + currentPage;
            dd.dealLimit = i + "" + currentPage;
            dd.orgCode = i + "" + currentPage;
            dd.complaintName = i + "" + currentPage;
            dd.backVisitName = i + "" + currentPage;
            dd.complaintEmail = i + "" + currentPage;
            dd.localPhotoExplain = i + "" + currentPage;
            dd.workEndTime = i + "" + currentPage;
            dd.backOrgCode = i + "" + currentPage;
            dd.dutyOrgName = i + "西安翔迅科技" + currentPage;
            if (new Random().nextBoolean()) {
                dd.workEventState = "闭合";
            } else {
                dd.workEventState = "打开";
            }
            dd.workIsNodus = i + "" + currentPage;
            dd.dutyOrgId = i + "160321194637834dc78d" + currentPage;
            dd.employeeId = i + "" + currentPage;
            dd.updateTime = i + "" + currentPage;
            dd.workIsIssued = i + "" + currentPage;
            dd.workBackExplain = i + "" + currentPage;
            dd.backOrgName = i + "" + currentPage;
            dd.sceneAnnex = i + "" + currentPage;
            dd.workIsBack = i + "" + currentPage;
            dd.workReportType = i + "" + currentPage;
            dd.createTime = i + "" + currentPage;
            dd.workOrderName = i + "" + currentPage;
            dd.workDisposeRequest = i + "" + currentPage;
            dd.issuedRegId = i + "" + currentPage;
            dd.workBeginTime = time.format(new Date());
            dd.longitude = 108.928822 + 0.000001 * i;
            dd.latitude = 34.273066 + 0.000001 * i;
            datas.add(dd);
        }
        setWorkOrderData(datas);
    }

    @Override
    public void setDisableClick() {

    }

    @Override
    public void setEnableClick() {

    }

    @Override
    public void end() {
        finish();
    }
}
