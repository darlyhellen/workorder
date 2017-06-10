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
import com.xiangxun.workorder.bean.Patrol;
import com.xiangxun.workorder.bean.WorkOrderData;
import com.xiangxun.workorder.ui.adapter.WorkOrderAdapter;
import com.xiangxun.workorder.ui.biz.WorkOrderListener;
import com.xiangxun.workorder.ui.fragment.SearchWorkOrderDialogFragment;
import com.xiangxun.workorder.ui.presenter.WorkOrderPresenter;
import com.xiangxun.workorder.widget.header.HeaderView;
import com.xiangxun.workorder.widget.xlistView.XListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zhangyuhui/Darly on 2017/5/19.
 * Copyright by [Zhangyuhui/Darly]
 * ©2017 XunXiang.Company. All rights reserved.
 *
 * @TODO:我的工单页面。传递给服务端用户ID，根据页码进行页面刷新加载。
 * @TODO:修改V1(根据首页传递进来的参数来判断到底是什么列表,当然传递为空表示全部工单列表)
 */
@ContentBinder(R.layout.activity_work_order)
public class WorkOrderActivity extends BaseActivity implements View.OnClickListener, XListView.IXListViewListener, WorkOrderListener.WorkOrderInterface, SearchWorkOrderDialogFragment.SearchListener {

    @ViewsBinder(R.id.id_work_order_header)
    private HeaderView header;
    @ViewsBinder(R.id.id_work_order_xlist)
    private XListView xlist;
    @ViewsBinder(R.id.id_work_order_text)
    private TextView textView;
    private String textDes;

    private WorkOrderAdapter adapter;

    private List<WorkOrderData> data;

    private WorkOrderPresenter presenter;
    //至关重要的一个参数
    private Patrol patrol;

    private int currentPage = 1;
    private int PageSize = 10;
    private int totalSize = 0;
    private String workorder;
    private String devicename;
    private String devicenum;
    private String deviceip;
    private int listState = AppEnum.LISTSTATEFIRST;


    /**
     * 是否是巡检的工单列表
     */
    private boolean isTour;

    @Override
    protected void initView(Bundle savedInstanceState) {
        presenter = new WorkOrderPresenter(this);
        //获取完成
        header.setLeftBackgroundResource(R.mipmap.ic_title_back);

        data = new ArrayList<WorkOrderData>();
        adapter = new WorkOrderAdapter(data, R.layout.item_activity_work_order, this);
        xlist.setAdapter(adapter);
        patrol = (Patrol) getIntent().getSerializableExtra("PATROL");

        if (patrol == null) {
            isTour = false;
            header.setTitle(R.string.main_work_order);
            presenter.getWorkOrderByPage(currentPage, workorder, devicename, devicenum, deviceip);
            header.setRightBackgroundResource(R.mipmap.ic_title_search);
            header.setRightOnClickListener(this);
        } else {
            //根据传递过来的参数,进行页面分类整理.请求不同的接口,
            if (patrol.getListId() == 20) {
                //巡检管理
                isTour = true;
                header.setRightBackgroundResource(R.mipmap.ic_title_add);
                header.setRightOnClickListener(this);
            } else if (patrol.getListId() == 40) {
                isTour = false;
                header.setRightBackgroundResource(0);
            } else {
                isTour = false;
                header.setRightBackgroundResource(R.mipmap.ic_title_search);
                header.setRightOnClickListener(this);
            }
            header.setTitle(patrol.getName());
            classifyRequest(patrol.getListId());


        }
    }

    /**
     * @param listId
     * @TODO:根据传递过来的参数,进行页面分类整理.请求不同的接口
     */
    private void classifyRequest(int listId) {
        currentPage = 1;
        switch (listId) {
            case 1:
                // 新工單(工单接收)根據接口規則。传递第二个参数为字符串0；
                isTour = false;
                workorder = "0";
                textDes = "没有新的工单！";
                presenter.getWorkOrderByPage(currentPage, workorder, devicename, devicenum, deviceip);
                break;
            case 2:
                //完成的工单
                isTour = false;
                workorder = "6";
                textDes = "没有完成的工单！";
                presenter.getWorkOrderByPage(currentPage, workorder, devicename, devicenum, deviceip);
                break;
            case 3:
                //未完成的工单
                isTour = false;
                workorder = "-6";
                textDes = "没有未完成的工单！";
                presenter.getWorkOrderByPage(currentPage, workorder, devicename, devicenum, deviceip);
                break;
            case 5:
                //请求全部的接口
                isTour = false;
                workorder = "";
                textDes = "没有工单！";
                presenter.getWorkOrderByPage(currentPage, workorder, devicename, devicenum, deviceip);
                break;
            case 20:
                //巡检页面工单列表
                isTour = true;
                workorder = "6";
                textDes = "没有巡检工单！";
                presenter.getWorkOrderByPage(currentPage, workorder, devicename, devicenum, deviceip);
                break;
            case 40:
                //通知公告页面列表
                isTour = false;
                workorder = "";
                textDes = "没有通知公告！";
                textView.setVisibility(View.VISIBLE);
                textView.setText(textDes);
                break;
            default:
                break;
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
                DLog.i("onItemClick--" + position);
                WorkOrderData ds = (WorkOrderData) parent.getItemAtPosition(position);
                Intent intent = new Intent(WorkOrderActivity.this, WorkOrderDetailActivity.class);
                //工单详细信息
                intent.putExtra("WorkOrderData", ds);
                startActivityForResult(intent, 700);
            }
        });
    }

    @Override
    public void onClick(View v) {

        if (patrol == null) {
            presenter.onClickDown(this, 0, v, workorder);
        } else {
            presenter.onClickDown(this, patrol.getListId(), v, workorder);
        }

    }


    //开始XList的刷新
    @Override
    public void onRefresh(View v) {
        currentPage = 1;
        listState = AppEnum.LISTSTATEREFRESH;
        if (isTour) {

        } else {
            presenter.getWorkOrderByPage(currentPage, workorder, devicename, devicenum, deviceip);
        }
    }

    @Override
    public void onLoadMore(View v) {
        if (totalSize < PageSize) {
            ToastApp.showToast("已经是最后一页");
            xlist.removeFooterView(xlist.mFooterView);
        } else {
            currentPage++;
            listState = AppEnum.LISTSTATELOADMORE;
            if (isTour) {

            } else {
                presenter.getWorkOrderByPage(currentPage, workorder, devicename, devicenum, deviceip);
            }
        }
    }

    // xLisView 停止
    private void stopXListView() {
        xlist.stopRefresh();
        xlist.stopLoadMore();
    }


    protected void setWorkOrderData(List<WorkOrderData> orderBeans) {
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
    public void onWorkOrderSuccess(List<WorkOrderData> datas) {
        stopXListView();
        DLog.i("onWorkOrderSuccess" + datas);
        setWorkOrderData(datas);
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
        return devicename;
    }

    @Override
    public String getDevicenum() {
        return devicenum;
    }

    @Override
    public String getDeviceip() {
        return deviceip;
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


    @Override
    public void findParamers(String stat, String name, String num, String ip) {
        DLog.i("name = " + name + ";num = " + num + ";ip = " + ip);
        textDes = "没有搜索到工单！";
        currentPage = 1;
        devicename = name;
        devicenum = num;
        deviceip = ip;
        workorder = stat;
        //搜索的时候，清理以前的数据。
        data.clear();
        if (isTour) {
            //测试
            presenter.getWorkOrderByPage(currentPage, workorder, devicename, devicenum, deviceip);
        } else {
            presenter.getWorkOrderByPage(currentPage, workorder, devicename, devicenum, deviceip);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        DLog.i(getClass().getSimpleName(), requestCode + "---" + resultCode + "---" + data);

        if (resultCode == 701) {
            currentPage = 1;
            presenter.getWorkOrderByPage(currentPage, workorder, devicename, devicenum, deviceip);
        }
    }
}
