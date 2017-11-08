package com.xiangxun.workorder.ui.main;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.hellen.baseframe.binder.ContentBinder;
import com.hellen.baseframe.binder.ViewsBinder;
import com.xiangxun.workorder.R;
import com.xiangxun.workorder.base.BaseActivity;
import com.xiangxun.workorder.bean.NotifactionData;
import com.xiangxun.workorder.ui.adapter.NotificationAdapter;
import com.xiangxun.workorder.ui.biz.NotificationListener;
import com.xiangxun.workorder.ui.presenter.NotificationPresenter;
import com.xiangxun.workorder.widget.header.HeaderView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zhangyuhui/Darly on 2017/5/19.
 * Copyright by [Zhangyuhui/Darly]
 * ©2017 XunXiang.Company. All rights reserved.
 *
 * @TODO:通知公告展示页面
 */
@ContentBinder(R.layout.activity_notification)
public class NotifactionActivity extends BaseActivity implements View.OnClickListener,NotificationListener.NotificationInterface {

    @ViewsBinder(R.id.id_notification_header)
    private HeaderView header;
    @ViewsBinder(R.id.id_notification_list)
    private ListView list;
    @ViewsBinder(R.id.id_notification_text)
    private TextView textView;
    private String textDes;

    private NotificationAdapter adapter;

    private List<NotifactionData> data;

    private NotificationPresenter presenter;
    @Override
    protected void initView(Bundle savedInstanceState) {
        //获取完成
        header.setLeftBackgroundResource(R.mipmap.ic_title_back);
        header.setRightBackgroundResource(0);
        header.setTitle(R.string.maintenance_notification);
        presenter  = new NotificationPresenter(this);
        data = new ArrayList<NotifactionData>();
        adapter = new NotificationAdapter(data, R.layout.item_activity_notification, this);
        list.setAdapter(adapter);
        //通知公告页面列表
        textDes = "没有通知公告！";
        presenter.getNotification();
    }


    @Override
    protected void initListener() {
        header.setLeftBackOneListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_view_back_llayout:
                finish();
                break;
        }
    }


    @Override
    public void setDisableClick() {

    }

    @Override
    public void setEnableClick() {

    }

    @Override
    public void onNotificationSuccess(List<NotifactionData> data) {
        if (data!=null&&data.size() > 0) {
            adapter.setData(data);
            textView.setVisibility(View.GONE);
        } else {
            textView.setVisibility(View.VISIBLE);
            textView.setText(textDes);
        }
    }

    @Override
    public void onNotificationFailed() {
        textView.setVisibility(View.VISIBLE);
        textView.setText(textDes);
    }
}
