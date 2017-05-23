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
import com.hellen.baseframe.common.dlog.DLog;
import com.hellen.baseframe.common.obsinfo.ToastApp;
import com.xiangxun.workorder.R;
import com.xiangxun.workorder.base.AppEnum;
import com.xiangxun.workorder.base.BaseActivity;
import com.xiangxun.workorder.bean.Patrol;
import com.xiangxun.workorder.db.TestBean;
import com.xiangxun.workorder.ui.adapter.PatrolHomeAdapter;
import com.xiangxun.workorder.ui.biz.MainListener;
import com.xiangxun.workorder.ui.main.WorkOrderActivity;
import com.xiangxun.workorder.ui.presenter.MainPresenter;
import com.xiangxun.workorder.widget.camera.PhotoPop;
import com.xiangxun.workorder.widget.header.HeaderView;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by Zhangyuhui/Darly on 2017/5/17.
 * Copyright by [Zhangyuhui/Darly]
 * ©2017 XunXiang.Company. All rights reserved.
 *
 * @TODO: 首页静态页面, 暂时没有接口网络请求。
 */
@ContentBinder(R.layout.activity_main)
public class MainActivity extends BaseActivity implements View.OnClickListener, MainListener.MainView, AdapterView.OnItemClickListener {


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
    /**
     * 上午9:29:04 TODO 调出选项的POP窗口，主要为相机，相册，取消
     */
    public PhotoPop pop;
    TestBean bean;

    private MainPresenter presenter;
    //首页参数集合
    @ViewsBinder(R.id.comment_title)
    private HeaderView title;
    @ViewsBinder(R.id.gv_home)
    private GridView gridView;
    private PatrolHomeAdapter adapter;
    private Map<String, Integer> map = new HashMap<String, Integer>();
    private List<Patrol> data;
    //首页参数集合

    @Override
    protected void initView(Bundle savedInstanceState) {
        pop = new PhotoPop(this);

        presenter = new MainPresenter(this);

        title.setTitle(R.string.main_titile);
        title.setRightBackgroundResource(R.mipmap.set);
        data = new ArrayList<>();
        data.add(new Patrol(1, R.string.main_work_order, R.mipmap.work_order_search));
        data.add(new Patrol(2, R.string.main_work_order, R.mipmap.man_user_manage));
        data.add(new Patrol(3, R.string.main_work_order, R.mipmap.work_order_search));
        data.add(new Patrol(4, R.string.main_work_order, R.mipmap.work_order_repor));
        data.add(new Patrol(5, R.string.main_work_order, R.mipmap.contact_phone));

        adapter = new PatrolHomeAdapter(data, R.layout.home_grideview_layout, this);
        gridView.setAdapter(adapter);

    }

    @Override
    protected void initListener() {
        button.setOnClickListener(this);
        insert.setOnClickListener(this);
        update.setOnClickListener(this);
        select.setOnClickListener(this);
        delete.setOnClickListener(this);
        title.setRightOnClickListener(this);
        gridView.setOnItemClickListener(this);
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
            case 1:
                startActivity(new Intent(this, WorkOrderActivity.class));
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
            case 5:
                break;
            default:
                break;
        }
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
    public TestBean getBean() {
        int r = new Random().nextInt(3);
        bean = new TestBean();
        bean.setName("qiangyu" + r);
        bean.setGender("male" + r);
        bean.setUrl("http://www.baidu.com/" + r);
        bean.setAge(23 + r);
        return bean;
    }

    @Override
    public void end() {

    }

    @Override
    public void setDisableClick() {

    }

    @Override
    public void setEnableClick() {

    }
}
