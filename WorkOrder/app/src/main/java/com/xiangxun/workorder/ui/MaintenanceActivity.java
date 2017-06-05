package com.xiangxun.workorder.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
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
import com.xiangxun.workorder.ui.adapter.PatrolHomeAdapter;
import com.xiangxun.workorder.ui.adapter.WorkOrderAdapter;
import com.xiangxun.workorder.ui.biz.MaintenanceListener.MaintenanceInterface;
import com.xiangxun.workorder.ui.main.WorkOrderActivity;
import com.xiangxun.workorder.ui.main.WorkOrderDetailActivity;
import com.xiangxun.workorder.ui.presenter.MaintenancePresenter;
import com.xiangxun.workorder.widget.header.HeaderView;

import java.util.List;

/**
 * Created by Zhangyuhui/Darly on 2017/5/26.
 * Copyright by [Zhangyuhui/Darly]
 * ©2017 XunXiang.Company. All rights reserved.
 *
 * @TODO: 运维通的首页展示内容
 */

@ContentBinder(R.layout.activity_maintenance)
public class MaintenanceActivity extends BaseActivity implements AdapterView.OnItemClickListener, MaintenanceInterface, View.OnClickListener {
    private MaintenancePresenter presenter;
    //首页参数集合
    @ViewsBinder(R.id.id_maintenance_title)
    private HeaderView title;
    @ViewsBinder(R.id.id_maintenance_iv)
    private ImageView iv;
    @ViewsBinder(R.id.id_maintenance_username)
    private TextView name;
    @ViewsBinder(R.id.id_maintenance_flipper)
    private ViewFlipper flipper;


    @ViewsBinder(R.id.id_maintenance_gv)
    private GridView gridView;

    private PatrolHomeAdapter adapter;

    private List<Patrol> data;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void initView(Bundle savedInstanceState) {
        presenter = new MaintenancePresenter(this);
        presenter.getWorkOrderByPage(1, "0", null, null, null);
        title.setTitle(R.string.maintenance_title);
        title.setRightBackgroundResource(R.mipmap.set);
        iv.setLayoutParams(new LinearLayout.LayoutParams(AppEnum.WIDTH.getLen() / 5, AppEnum.WIDTH.getLen() / 5));
        name.setText("用户名称：" + SharePreferHelp.getValue(AppEnum.USERREALNAME.getDec(), null));
        adapter = new PatrolHomeAdapter(presenter.findMaintenance(), R.layout.home_grideview_layout, this);
        gridView.setAdapter(adapter);
    }

    @Override
    protected void initListener() {
        title.setRightOnClickListener(this);
        gridView.setOnItemClickListener(this);
    }


    @Override
    public void goBack() {
        finish();
    }

    @Override
    public void setDisableClick() {

    }

    @Override
    public void setEnableClick() {

    }

    @Override
    public void onClick(View v) {
        presenter.onClickDown(this, v);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Patrol patrol = (Patrol) parent.getItemAtPosition(position);
        if (patrol == null) {
            ToastApp.showToast("数据错误！");
            return;
        }
        switch (patrol.getListId()) {
            case 10:
                startActivity(new Intent(this, MainActivity_V0.class));
                break;
            case 20:
                Intent intent = new Intent(this, WorkOrderActivity.class);
                intent.putExtra("PATROL", patrol);
                startActivity(intent);
                break;
            case 30:
                break;
            case 40:
                Intent ask = new Intent(this, WorkOrderActivity.class);
                ask.putExtra("PATROL", patrol);
                startActivity(ask);
                break;
            default:
                break;
        }
    }


    private long time = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (KeyEvent.KEYCODE_BACK == keyCode) {
            long dely = System.currentTimeMillis();
            if (dely - time < 2000) {
                System.exit(0);
            } else {
                ToastApp.showToast("再次点击退出程序");
                time = dely;
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onWorkOrderSuccess(List<WorkOrderData> datas) {
        for (final WorkOrderData da : datas) {
            View view = LayoutInflater.from(this).inflate(R.layout.item_activity_flipper, null);
            ((TextView) view.findViewById(R.id.id_maintenance_flp_tv)).setText("工单编号：" + da.id + "  设备名称：" + da.devicename + "  请点击查看详情！");
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DLog.i(getClass().getSimpleName(), da.toString() + da.id);
                    Intent intent = new Intent(MaintenanceActivity.this, WorkOrderDetailActivity.class);
                    //是否是巡检工单
                    intent.putExtra("isTour", false);
                    //工单详细信息
                    intent.putExtra("data", da);
                    startActivityForResult(intent, 700);
                }
            });
            flipper.addView(view);
        }
    }

    @Override
    public void onWorkOrderFailed() {

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        DLog.i(getClass().getSimpleName(), requestCode + "---" + resultCode + "---" + data);

        if (resultCode == 701) {
            presenter.getWorkOrderByPage(1, "0", null, null, null);
        }
    }
}
