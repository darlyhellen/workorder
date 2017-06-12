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

import com.hellen.baseframe.binder.ContentBinder;
import com.hellen.baseframe.binder.ViewsBinder;
import com.xiangxun.workorder.R;
import com.xiangxun.workorder.base.AppEnum;
import com.xiangxun.workorder.base.BaseActivity;
import com.xiangxun.workorder.bean.EquipmentInfo;
import com.xiangxun.workorder.bean.TourInfo;
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
    private int[] titles = new int[]{R.string.st_detail_detal, R.string.st_detail_position, R.string.st_detail_image};
    private int[] titles_no = new int[]{R.string.st_detail_detal, R.string.st_detail_image};

    private WorkOrderData data;
    private EquipmentInfo info;
    private TourInfo tour;
    private DetailOrderFragment order;

    @Override
    protected void initView(Bundle savedInstanceState) {
        presenter = new WorkOrderDetailPresenter(this);
        //一下三个必须进来一个。否则就崩溃了。
        //正常工单详情
        data = getIntent().getParcelableExtra("WorkOrderData");
        //设备信息详情
        info = getIntent().getParcelableExtra("EquipmentInfo");
        //巡检工单详情
        tour = getIntent().getParcelableExtra("TourInfo");
        if (AppEnum.TEST) {
            //单独的测试数据,假数据.
            data = AppEnum.getData();
            header.getTitleViewOperationText().setText(R.string.st_detail_position);
            header.setRightImageTextFlipper(this);
        } else {
            header.setRightBackgroundResource(0);
        }
        tab.setTabMode(TabLayout.MODE_FIXED);
        tab.setTabGravity(TabLayout.GRAVITY_FILL);
        tab.setTabTextColors(R.color.edit_save_btn_color_disable, R.color.text_color);

        if (data != null) {
            hasData();
        } else if (info != null) {
            hasInfo();
        } else if (tour != null) {
            hasTour();
        }
    }

    /**
     * @TODO:详情信息
     */
    private void hasData() {

        Bundle bundle = new Bundle();
        bundle.putParcelable("WorkOrderData", data);
        order = new DetailOrderFragment();
        order.setArguments(bundle);
        list.add(order);
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
        DetailImageFragment image = new DetailImageFragment();
        image.setArguments(bundle);
        list.add(image);
        header.setTitle(R.string.st_detail_title);
        header.setLeftBackgroundResource(R.mipmap.ic_title_back);
        //创建适配器(这个适配器是自定义的，我用的是FragmentPagerAdapger，根据需求自定义吧)
        adapter = new ViewPagerAdapter(this, getSupportFragmentManager(), list, list.size() > 2 ? titles : titles_no);
        vp.setAdapter(adapter);
    }

    /**
     * @TODO:详情信息
     */
    private void hasInfo() {

        Bundle bundle = new Bundle();
        bundle.putParcelable("EquipmentInfo", info);
        order = new DetailOrderFragment();
        order.setArguments(bundle);
        list.add(order);
        if (!TextUtils.isEmpty(info.mapx) && !TextUtils.isEmpty(info.mapy)) {
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
        DetailImageFragment image = new DetailImageFragment();
        image.setArguments(bundle);
        list.add(image);
        header.setTitle(R.string.st_equip_title_order);
        header.setLeftBackgroundResource(R.mipmap.ic_title_back);
        //创建适配器(这个适配器是自定义的，我用的是FragmentPagerAdapger，根据需求自定义吧)
        adapter = new ViewPagerAdapter(this, getSupportFragmentManager(), list, list.size() > 2 ? titles : titles_no);
        vp.setAdapter(adapter);
    }

    /**
     * @TODO:详情信息
     */
    private void hasTour() {

        Bundle bundle = new Bundle();
        bundle.putParcelable("TourInfo", tour);
        order = new DetailOrderFragment();
        order.setArguments(bundle);
        list.add(order);
        if (!TextUtils.isEmpty(tour.mapx) && !TextUtils.isEmpty(tour.mapy)) {
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
        DetailImageFragment image = new DetailImageFragment();
        image.setArguments(bundle);
        list.add(image);
        header.setTitle(R.string.st_tour_title_order);
        header.setLeftBackgroundResource(R.mipmap.ic_title_back);
        //创建适配器(这个适配器是自定义的，我用的是FragmentPagerAdapger，根据需求自定义吧)
        adapter = new ViewPagerAdapter(this, getSupportFragmentManager(), list, list.size() > 2 ? titles : titles_no);
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
        order.onActivityResult(requestCode, resultCode, data);
    }
}
