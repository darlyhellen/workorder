package com.xiangxun.workorder.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;

import com.hellen.baseframe.binder.ContentBinder;
import com.hellen.baseframe.binder.ViewsBinder;
import com.hellen.baseframe.common.db.ThreadInfo;
import com.hellen.baseframe.common.dlog.DLog;
import com.hellen.baseframe.common.obsinfo.ToastApp;
import com.xiangxun.workorder.R;
import com.xiangxun.workorder.base.AppEnum;
import com.xiangxun.workorder.base.BaseActivity;
import com.xiangxun.workorder.base.StaticListener;
import com.xiangxun.workorder.bean.Patrol;
import com.xiangxun.workorder.service.WorkOrderNewService;
import com.xiangxun.workorder.ui.adapter.PatrolHomeAdapter;
import com.xiangxun.workorder.ui.biz.MainV0Listener;
import com.xiangxun.workorder.ui.main.WorkOrderActivity;
import com.xiangxun.workorder.ui.presenter.MainV0Presenter;
import com.xiangxun.workorder.widget.camera.PhotoPop;
import com.xiangxun.workorder.widget.header.HeaderView;

import java.io.File;
import java.util.Random;

/**
 * Created by Zhangyuhui/Darly on 2017/5/17.
 * Copyright by [Zhangyuhui/Darly]
 * ©2017 XunXiang.Company. All rights reserved.
 *
 * @TODO: 工单菜单页面。包括四个子宫能列表。
 */
@ContentBinder(R.layout.activity_main_v0)
public class WorkOrderMenuActivity extends BaseActivity implements View.OnClickListener, MainV0Listener.MainV0Interface, AdapterView.OnItemClickListener, StaticListener.RefreshMainUIListener {


    public static final String 工单管理 = "工单管理";
    @ViewsBinder(R.id.button)
    private Button button;
    @ViewsBinder(R.id.insert)
    private Button insert;
    @ViewsBinder(R.id.update)
    private Button update;
    @ViewsBinder(R.id.select)
    private Button select;
    @ViewsBinder(R.id.delete)
    private Button delete;
    @ViewsBinder(R.id.down)
    private Button down;
    @ViewsBinder(R.id.video)
    private Button video;
    /**
     * 上午9:29:04 TODO 调出选项的POP窗口，主要为相机，相册，取消
     */
    public PhotoPop pop;
    ThreadInfo bean;

    private MainV0Presenter presenter;
    //首页参数集合
    @ViewsBinder(R.id.comment_title)
    private HeaderView title;
    @ViewsBinder(R.id.gv_home)
    private GridView gridView;
    private PatrolHomeAdapter adapter;
    //首页参数集合

    @Override
    protected void initView(Bundle savedInstanceState) {
        pop = new PhotoPop(this);
        StaticListener.getInstance().setRefreshMainUIListener(this);
        Intent intent = new Intent(this, WorkOrderNewService.class);
        startService(intent);
        presenter = new MainV0Presenter(this);
        title.setTitle(R.string.maintenance_order);
        title.setLeftBackgroundResource(R.mipmap.ic_title_back);
        title.setRightBackgroundResource(0);

        adapter = new PatrolHomeAdapter(presenter.findMainV0(getIntent().getIntExtra("Num", 0)), R.layout.home_grideview_layout, this);
        gridView.setAdapter(adapter);

    }

    @Override
    protected void initListener() {
        title.setLeftBackOneListener(this);
        gridView.setOnItemClickListener(this);

        button.setOnClickListener(this);
        insert.setOnClickListener(this);
        update.setOnClickListener(this);
        select.setOnClickListener(this);
        delete.setOnClickListener(this);
        down.setOnClickListener(this);
        video.setOnClickListener(this);
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
        Intent intent = new Intent(this, WorkOrderActivity.class);
        intent.putExtra("PATROL", patrol);
        startActivity(intent);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        DLog.i("onActivityResult" + requestCode + resultCode);
        if (requestCode == AppEnum.REQUESTCODE_CUT) {
            // 裁剪
            if (data != null) {
                Bundle extras = data.getExtras();
                Bitmap head = extras.getParcelable("data");
            }
        } else if (requestCode == AppEnum.REQUESTCODE_CAM
                || requestCode == AppEnum.REQUESTCODE_CAP) {

            // 拍照或相册
            String head_path = null;
            if (data == null) {
                if (pop == null) {
                    head_path = AppEnum.capUri;
                } else {
                    head_path = pop.PopStringActivityResult(null,
                            AppEnum.REQUESTCODE_CAP);
                }
            } else {
                head_path = pop.PopStringActivityResult(data,
                        AppEnum.REQUESTCODE_CAM);
            }
            if (head_path == null) {
                return;
            }
            DLog.i(head_path);
            File temp = new File(head_path);
            pop.cropPhoto(Uri.fromFile(temp));// 裁剪图片
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (bean != null) {
            bean.close();
        }
    }


    @Override
    public PhotoPop getPop() {
        return pop;
    }

    @Override
    public ThreadInfo getBean() {
        int r = new Random().nextInt(3);
        bean = new ThreadInfo();
        bean.setFileName("qiangyu" + r);
        bean.setFilePath("male" + r);
        bean.setUrl("https://www.baidu.com/" + r);
        bean.setFinished(23 + r);
        bean.setStart(r);
        bean.setEnd(r);
        bean.setLenght(r);
        return bean;
    }

    @Override
    public void end() {
        Intent intent = new Intent();
        intent.setAction("com.xiangxun.workorder.ui.WorkOrderMenuActivity");
        sendBroadcast(intent);
        finish();
    }

    @Override
    public void setDisableClick() {

    }

    @Override
    public void setEnableClick() {

    }

    @Override
    public void refreshMainUI(int num) {
        DLog.i(getClass().getSimpleName() + "--->" + num);
        if (adapter != null) {
            adapter.setData(presenter.findMainV0(num));
        }
    }
}
