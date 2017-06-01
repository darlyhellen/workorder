package com.xiangxun.workorder.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.hellen.baseframe.binder.InitBinder;
import com.hellen.baseframe.binder.ViewsBinder;
import com.hellen.baseframe.common.dlog.DLog;
import com.hellen.baseframe.common.obsinfo.ToastApp;
import com.xiangxun.workorder.R;
import com.xiangxun.workorder.base.AppEnum;
import com.xiangxun.workorder.bean.WorkOrderData;
import com.xiangxun.workorder.ui.adapter.WorkOrderAdapter;
import com.xiangxun.workorder.ui.biz.WorkOrderListener;
import com.xiangxun.workorder.ui.main.LbsAmapActivity;
import com.xiangxun.workorder.ui.presenter.WorkOrderFragmentPresenter;
import com.xiangxun.workorder.widget.header.HeaderView;
import com.xiangxun.workorder.widget.xlistView.XListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.xiangxun.workorder.ui.fragment.SearchWorkOrderDialogFragment.*;

/**
 * Created by Zhangyuhui/Darly on 2017/5/25.
 * Copyright by [Zhangyuhui/Darly]
 * ©2017 XunXiang.Company. All rights reserved.
 *
 * @TODO:
 */
public class FragmentWorkOrder extends Fragment implements View.OnClickListener, XListView.IXListViewListener, AdapterView.OnItemClickListener, WorkOrderListener.WorkOrderInterface, SearchListener {
    private View root;

    @ViewsBinder(R.id.id_work_order_header)
    private HeaderView header;
    @ViewsBinder(R.id.id_work_order_xlist)
    private XListView xlist;
    @ViewsBinder(R.id.id_work_order_text)
    private TextView textView;

    private WorkOrderAdapter adapter;

    private List<WorkOrderData> data;

    private WorkOrderFragmentPresenter presenter;
    //至关重要的一个参数

    private int currentPage = 1;
    private int PageSize = 10;
    private int totalSize = 0;
    private int listState = AppEnum.LISTSTATEFIRST;
    private String devicename;
    private String devicenum;
    private String deviceip;

    private Map<String, String> map;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.activity_work_order, null);
        InitBinder.InitAll(this, root);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        initListener();
    }

    protected void initView() {
        presenter = new WorkOrderFragmentPresenter(this);
        //获取完成
        header.setLeftBackgroundResource(R.mipmap.ic_title_back);
        header.setRightBackgroundResource(R.mipmap.ic_title_search);
        header.setVisibility(View.GONE);

        data = new ArrayList<>();
        adapter = new WorkOrderAdapter(data, R.layout.item_activity_work_order, getActivity());
        xlist.setAdapter(adapter);
        int titles = (int) getArguments().getInt("TITLE", 0);
        DLog.i(getClass().getSimpleName(), titles);
        map = new HashMap<String, String>();
        if (titles == 0) {
            header.setTitle(R.string.main_work_order);
            presenter.getWorkOrderByPage(currentPage, 100, null, null, null);
        } else {
            //根据传递过来的参数,进行页面分类整理.请求不同的接口,
            header.setTitle(titles);
            classifyRequest(titles);


        }
    }

    /**
     * @param listId
     * @TODO:根据传递过来的参数,进行页面分类整理.请求不同的接口
     */
    private void classifyRequest(int listId) {
        switch (listId) {
            case R.string.main_work_order_new:
                break;
            case R.string.main_work_order_undown:
                break;
            case R.string.main_work_order_all:
                //请求全部的接口
                presenter.getWorkOrderByPage(currentPage, 100, null, null, null);
                break;
            default:
                break;
        }

    }

    protected void initListener() {
        header.setLeftBackOneListener(this);
        header.setRightOnClickListener(this);
        xlist.setPullLoadEnable(true);
        xlist.setXListViewListener(this);
        xlist.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View v) {
        presenter.onClickDown(getActivity(), v);
    }


    //开始XList的刷新
    @Override
    public void onRefresh(View v) {
        currentPage = 1;
        listState = AppEnum.LISTSTATEREFRESH;
        presenter.getWorkOrderByPage(currentPage, 100, null, null, null);
    }

    @Override
    public void onLoadMore(View v) {
        if (totalSize < PageSize) {
            ToastApp.showToast("已经是最后一页");
            xlist.removeFooterView(xlist.mFooterView);
        } else {
            currentPage++;
            listState = AppEnum.LISTSTATELOADMORE;
            presenter.getWorkOrderByPage(currentPage, 100, null, null, null);
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
        Intent intent = new Intent(getActivity(), LbsAmapActivity.class);
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
    }


    @Override
    public void findParamers(String name, String num, String ip) {
        currentPage = 1;
        devicename = name;
        devicenum = num;
        deviceip = ip;
        presenter.getWorkOrderByPage(currentPage, 100, name, num, ip);
    }
}
