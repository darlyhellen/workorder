package com.xiangxun.workorder.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;

import com.amap.api.maps.model.LatLng;
import com.hellen.baseframe.binder.ContentBinder;
import com.hellen.baseframe.binder.ViewsBinder;
import com.hellen.baseframe.common.dlog.DLog;
import com.hellen.baseframe.common.obsinfo.ToastApp;
import com.xiangxun.workorder.R;
import com.xiangxun.workorder.base.AppEnum;
import com.xiangxun.workorder.base.BaseActivity;
import com.xiangxun.workorder.bean.WorkOrderData;
import com.xiangxun.workorder.ui.adapter.ViewPagerAdapter;
import com.xiangxun.workorder.ui.biz.WorkOrderDetailListener.WorkOrderDetailInterface;
import com.xiangxun.workorder.ui.fragment.DetailImageFragment;
import com.xiangxun.workorder.ui.fragment.DetailLbsAmapFragment;
import com.xiangxun.workorder.ui.fragment.DetailOrderFragment;
import com.xiangxun.workorder.ui.presenter.WorkOrderDetailPresenter;
import com.xiangxun.workorder.widget.header.HeaderView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zhangyuhui/Darly on 2017/5/26.
 * Copyright by [Zhangyuhui/Darly]
 * ©2017 XunXiang.Company. All rights reserved.
 *
 * @TODO: 工单详情页面，点击每个工单，都会进入到详情页面。不过是根据不同参数。展示效果不同而已。
 */
@ContentBinder(R.layout.activity_detail_work_order)
public class WorkOrderDetailActivity extends BaseActivity implements OnClickListener, WorkOrderDetailInterface, OnPageChangeListener {

    private WorkOrderDetailPresenter presenter;
    @ViewsBinder(R.id.id_detail_title)
    private HeaderView header;
    @ViewsBinder(R.id.id_detail_table)
    private TabLayout tab;
    @ViewsBinder(R.id.id_detail_viewpager)
    private ViewPager vp;

    private ViewPagerAdapter adapter;
    private List<Fragment> list = new ArrayList<Fragment>();
    private int[] titles = new int[]{R.string.st_detail_detal, R.string.st_detail_image, R.string.st_detail_position};
    private int[] titles_no = new int[]{R.string.st_detail_detal, R.string.st_detail_image};

    private WorkOrderData data;
    private DetailOrderFragment order;

    @Override
    protected void initView(Bundle savedInstanceState) {
        presenter = new WorkOrderDetailPresenter(this);
        boolean isTour = getIntent().getBooleanExtra("isTour", false);
        int id = getIntent().getIntExtra("des", 0);
        data = getIntent().getParcelableExtra("data");
        if (AppEnum.TEST) {
            //单独的测试数据,假数据.
            data = AppEnum.getData();
            header.getTitleViewOperationText().setText(R.string.st_detail_position);
<<<<<<< HEAD
        }
        DLog.i(getClass().getSimpleName(), isTour + "--" + id + "---" + data);

=======
            header.setRightImageTextFlipper(this);
        }else {
            header.setRightBackgroundResource(0);
        }
>>>>>>> f9757719696d8632a6d88228ebda0cd326936f4d
        tab.setTabMode(TabLayout.MODE_FIXED);
        tab.setTabGravity(TabLayout.GRAVITY_FILL);
        tab.setTabTextColors(R.color.text_color, R.color.blue_btn_bg_color);
        if (data == null) {
            ToastApp.showToast("程序数据传递异常");
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putParcelable("data", data);
<<<<<<< HEAD
        DetailOrderFragment order = new DetailOrderFragment();
=======
        order = new DetailOrderFragment();
>>>>>>> f9757719696d8632a6d88228ebda0cd326936f4d
        order.setArguments(bundle);
        list.add(order);
        DetailImageFragment image = new DetailImageFragment();
        image.setArguments(bundle);
        list.add(image);
        if (!TextUtils.isEmpty(data.mapx) && !TextUtils.isEmpty(data.mapy)) {
            //有坐标
            for (int i = 0; i < titles.length; i++) {
                //设置未选中和选中时字体的颜色
                tab.addTab(tab.newTab().setText(titles[i]));
            }
            DetailLbsAmapFragment lbs = new DetailLbsAmapFragment();
            lbs.setArguments(bundle);
            list.add(lbs);
        } else {
            //无坐标
            for (int i = 0; i < titles_no.length; i++) {
                //设置未选中和选中时字体的颜色
                tab.addTab(tab.newTab().setText(titles[i]));
            }
        }
        if (isTour) {
            header.setTitle(R.string.st_tour_title_order);
            header.setLeftBackgroundResource(R.mipmap.ic_title_back);
        } else {
            header.setTitle(R.string.st_detail_title);
            header.setLeftBackgroundResource(R.mipmap.ic_title_back);
        }
        //创建适配器(这个适配器是自定义的，我用的是FragmentPagerAdapger，根据需求自定义吧)
        adapter = new ViewPagerAdapter(this, getSupportFragmentManager(), list, titles);
        vp.setAdapter(adapter);
    }

    @Override
    protected void initListener() {
        header.setLeftBackOneListener(this);


        tab.setupWithViewPager(vp);
        vp.setOnPageChangeListener(this);
    }

    @Override
    public void onClick(View v) {
        presenter.onClickDown(this, v);
    }

    @Override
    public WorkOrderData getOrderData() {
        return data;
    }

    @Override
    public void end() {
        finish();
    }

    @Override
    public void setDisableClick() {

    }

    @Override
    public void setEnableClick() {

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        order.onActivityResult(requestCode,resultCode,data);
    }
}
