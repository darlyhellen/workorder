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
import com.hellen.baseframe.common.utiltools.SharePreferHelp;
import com.xiangxun.workorder.R;
import com.xiangxun.workorder.base.AppEnum;
import com.xiangxun.workorder.base.BaseActivity;
import com.xiangxun.workorder.bean.Patrol;
import com.xiangxun.workorder.bean.WorkOrderData;
import com.xiangxun.workorder.ui.adapter.WorkOrderAdapter;
import com.xiangxun.workorder.ui.biz.WorkOrderListener;
import com.xiangxun.workorder.ui.presenter.WorkOrderPresenter;
import com.xiangxun.workorder.widget.header.HeaderView;
import com.xiangxun.workorder.widget.xlistView.XListView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by Zhangyuhui/Darly on 2017/5/19.
 * Copyright by [Zhangyuhui/Darly]
 * ©2017 XunXiang.Company. All rights reserved.
 *
 * @TODO:我的工单页面。传递给服务端用户ID，根据页码进行页面刷新加载。
 * @TODO:修改V1(根据首页传递进来的参数来判断到底是什么列表,当然传递为空表示全部工单列表)
 */
@ContentBinder(R.layout.activity_work_order)
public class WorkOrderActivity extends BaseActivity implements View.OnClickListener, XListView.IXListViewListener, AdapterView.OnItemClickListener, WorkOrderListener.WorkOrderInterface, SearchWorkOrderDialogFragment.SearchListener {

    @ViewsBinder(R.id.id_work_order_header)
    private HeaderView header;
    @ViewsBinder(R.id.id_work_order_xlist)
    private XListView xlist;
    @ViewsBinder(R.id.id_work_order_text)
    private TextView textView;

    private WorkOrderAdapter adapter;

    private List<WorkOrderData> data;

    private WorkOrderPresenter presenter;
    //至关重要的一个参数
    private Patrol patrol;

    private int currentPage = 1;
    private int PageSize = 10;
    private int totalSize = 0;
    private int listState = AppEnum.LISTSTATEFIRST;

    private Map<String, String> map;


    @Override
    protected void initView(Bundle savedInstanceState) {
        presenter = new WorkOrderPresenter(this);
        //获取完成
        header.setLeftBackgroundResource(R.mipmap.ic_title_back);

        data = new ArrayList<WorkOrderData>();
        adapter = new WorkOrderAdapter(data, R.layout.item_activity_work_order, this);
        xlist.setAdapter(adapter);
        patrol = (Patrol) getIntent().getSerializableExtra("PATROL");
        map = new HashMap<String, String>();
        map.put("userId", SharePreferHelp.getValue(AppEnum.USERID.getDec(), null));

        if (patrol == null) {
            header.setTitle(R.string.main_work_order);
            presenter.getWorkOrderByPage(currentPage, null, null, null, null);
            header.setRightBackgroundResource(R.mipmap.ic_title_search);
        } else {
            //根据传递过来的参数,进行页面分类整理.请求不同的接口,
            if (patrol.getListId() == 20) {
                //巡检管理
                header.setRightBackgroundResource(R.mipmap.ic_title_add);
            } else {
                header.setRightBackgroundResource(R.mipmap.ic_title_search);
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
        switch (listId) {
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
            case 5:
                //请求全部的接口
                presenter.getWorkOrderByPage(currentPage, null, null, null, null);
                break;
            case 20:
                //巡检页面工单列表
                break;
            default:
                break;
        }

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

        if (patrol == null) {
            presenter.onClickDown(this, 0, v);
        } else {
            presenter.onClickDown(this, patrol.getListId(), v);
        }

    }


    //开始XList的刷新
    @Override
    public void onRefresh(View v) {
        currentPage = 1;
        listState = AppEnum.LISTSTATEREFRESH;
        presenter.getWorkOrderByPage(currentPage, null, null, null, null);
    }

    @Override
    public void onLoadMore(View v) {
        if (totalSize < PageSize) {
            ToastApp.showToast("已经是最后一页");
            xlist.removeFooterView(xlist.mFooterView);
        } else {
            currentPage++;
            listState = AppEnum.LISTSTATELOADMORE;
            presenter.getWorkOrderByPage(currentPage, null, null, null, null);
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
        if (xlist.getCount() > 0) {
            textView.setVisibility(View.GONE);
        } else {
            textView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onWorkOrderFailed() {
        stopXListView();
        DLog.i("onWorkOrderFailed");
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
    public void findParamers(Map<String, String> map) {
        DLog.i(map);
        currentPage = 1;
        this.map = map;
        presenter.getWorkOrderByPage(currentPage, null, null, null, null);

    }
}
