package com.xiangxun.workorder.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.design.widget.TabLayout.OnTabSelectedListener;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;

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
import com.xiangxun.workorder.ui.adapter.ViewPagerAdapter;
import com.xiangxun.workorder.ui.biz.MainListener.MainInterface;
import com.xiangxun.workorder.ui.main.FragmentWorkOrder;
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
public class MainActivity extends BaseActivity implements View.OnClickListener, MainInterface, StaticListener.RefreshMainUIListener, OnPageChangeListener {


    /**
     * 上午9:29:04 TODO 调出选项的POP窗口，主要为相机，相册，取消
     */

    private MainPresenter presenter;
    //首页参数集合
    @ViewsBinder(R.id.comment_title)
    private HeaderView title;
    @ViewsBinder(R.id.comment_table)
    private TabLayout tab;
    @ViewsBinder(R.id.comment_viewpager)
    private ViewPager vp;

    private ViewPagerAdapter adapter;
    private List<Fragment> list = new ArrayList<Fragment>();


    private int[] titles = new int[]{R.string.main_work_order_new, R.string.main_work_order_undown, R.string.main_work_order_all};

    @Override
    protected void initView(Bundle savedInstanceState) {
        presenter = new MainPresenter(this);
        title.setTitle(R.string.main_work_order);
        title.setRightBackgroundResource(R.mipmap.set);
        //tab.setTabMode(TabLayout.MODE_SCROLLABLE);
        for (int i = 0; i < titles.length; i++) {
            //设置未选中和选中时字体的颜色
            tab.setTabTextColors(R.color.blue_btn_bg_color, R.color.text_color);
            tab.addTab(tab.newTab().setText(titles[i]));
            list.add(new FragmentWorkOrder());
        }
        //创建适配器(这个适配器是自定义的，我用的是FragmentPagerAdapger，根据需求自定义吧)
        adapter = new ViewPagerAdapter(this, getSupportFragmentManager(), list, titles);
        vp.setAdapter(adapter);
    }

    @Override
    protected void initListener() {
        title.setRightOnClickListener(this);
        tab.setupWithViewPager(vp);
        vp.setOnPageChangeListener(this);
        DLog.i(getClass().getSimpleName(), tab);
    }

    @Override
    public void onClick(View v) {
        presenter.onClickDown(this, v);
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

    @Override
    public void refreshMainUI(int num) {
        DLog.i(getClass().getSimpleName() + "--->" + num);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
