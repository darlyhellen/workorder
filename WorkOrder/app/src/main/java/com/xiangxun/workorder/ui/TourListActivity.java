package com.xiangxun.workorder.ui;

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
import com.xiangxun.workorder.bean.TourInfo;
import com.xiangxun.workorder.ui.adapter.TourAdapter;
import com.xiangxun.workorder.ui.biz.TourListListener.TourListInterface;
import com.xiangxun.workorder.ui.fragment.SearchWorkOrderDialogFragment;
import com.xiangxun.workorder.ui.main.WorkOrderDetailActivity;
import com.xiangxun.workorder.ui.presenter.TourListPresenter;
import com.xiangxun.workorder.widget.header.HeaderView;
import com.xiangxun.workorder.widget.xlistView.XListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zhangyuhui/Darly on 2017/5/19.
 * Copyright by [Zhangyuhui/Darly]
 * ©2017 XunXiang.Company. All rights reserved.
 *
 * @TODO:巡检列表和其他列表不能共用。单独提取出来使用。
 */
@ContentBinder(R.layout.activity_work_order)
public class TourListActivity extends BaseActivity implements View.OnClickListener, XListView.IXListViewListener, TourListInterface, SearchWorkOrderDialogFragment.SearchListener {

    @ViewsBinder(R.id.id_work_order_header)
    private HeaderView header;
    @ViewsBinder(R.id.id_work_order_xlist)
    private XListView xlist;
    @ViewsBinder(R.id.id_work_order_text)
    private TextView textView;
    private String textDes;

    private TourAdapter adapter;

    private List<TourInfo> data;

    private TourListPresenter presenter;
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


    @Override
    protected void initView(Bundle savedInstanceState) {
        presenter = new TourListPresenter(this);
        //获取完成
        header.setLeftBackgroundResource(R.mipmap.ic_title_back);

        data = new ArrayList<TourInfo>();
        adapter = new TourAdapter(data, R.layout.item_activity_equipment, this);
        xlist.setAdapter(adapter);
        patrol = (Patrol) getIntent().getSerializableExtra("PATROL");

        if (patrol == null) {
            header.setTitle(R.string.main_work_order);
            presenter.getWorkOrderByPage(currentPage, workorder, devicename, devicenum, deviceip);
            header.setRightBackgroundResource(R.mipmap.ic_title_search);
        } else {
            //巡检管理
            header.getTitleViewOperationText().setText("新建");
            header.setRightImageTextFlipper(this);
            //header.setRightBackgroundResource(R.mipmap.ic_title_add);
            header.setTitle(patrol.getName());
            textDes = "没有巡检工单！";
            presenter.getWorkOrderByPage(currentPage, workorder, devicename, devicenum, deviceip);
        }
    }


    @Override
    protected void initListener() {
        header.setLeftBackOneListener(this);
        header.setRightOnClickListener(this);
        xlist.setPullLoadEnable(true);
        xlist.setXListViewListener(this);
        xlist.setOnItemClickListener(new ItemClickListenter() {
            @Override
            public void NoDoubleItemClickListener(AdapterView<?> parent, View view, int position, long id) {
                DLog.i("onItemClick--" + position);
                TourInfo ds = (TourInfo) parent.getItemAtPosition(position);
                Intent intent = new Intent(TourListActivity.this, WorkOrderDetailActivity.class);
                //工单详细信息
                intent.putExtra("TourInfo", ds);
                startActivity(intent);
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
        presenter.getWorkOrderByPage(currentPage, workorder, devicename, devicenum, deviceip);
    }

    @Override
    public void onLoadMore(View v) {
        if (totalSize < PageSize) {
            ToastApp.showToast("已经是最后一页");
            xlist.removeFooterView(xlist.mFooterView);
        } else {
            currentPage++;
            listState = AppEnum.LISTSTATELOADMORE;
            presenter.getWorkOrderByPage(currentPage, workorder, devicename, devicenum, deviceip);
        }
    }

    // xLisView 停止
    private void stopXListView() {
        xlist.stopRefresh();
        xlist.stopLoadMore();
    }


    protected void setWorkOrderData(List<TourInfo> orderBeans) {
        xlist.removeFooterView(xlist.mFooterView);
        if (orderBeans.size() > PageSize - 1) {
            xlist.addFooterView(xlist.mFooterView);
        }
        switch (listState) {
            case AppEnum.LISTSTATEFIRST:
                data.clear();
                data.addAll(orderBeans);
                adapter.setData(data);
                xlist.smoothScrollToPosition(1);
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
    public void onWorkOrderSuccess(List<TourInfo> datas) {
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
        presenter.getWorkOrderByPage(currentPage, workorder, devicename, devicenum, deviceip);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        DLog.i(getClass().getSimpleName(), requestCode + "---" + resultCode + "---" + data);

        if (resultCode == 701) {
            currentPage = 1;
            listState = AppEnum.LISTSTATEFIRST;
            presenter.getWorkOrderByPage(currentPage, workorder, devicename, devicenum, deviceip);
        }
    }
}
