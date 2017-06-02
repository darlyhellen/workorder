package com.xiangxun.workorder.ui.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.hellen.baseframe.common.dlog.DLog;

import java.util.List;

/**
 * Created by Zhangyuhui/Darly on 2017/5/25.
 * Copyright by [Zhangyuhui/Darly]
 * ©2017 XunXiang.Company. All rights reserved.
 *
 * @TODO:
 */
public class ViewPagerAdapter extends FragmentPagerAdapter {


    private List<Fragment> viewList;

    private int[] title;

    private Context context;

    public ViewPagerAdapter(Context context, FragmentManager fm, List<Fragment> viewList, int[] titles) {
        super(fm);
        this.context = context;
        this.viewList = viewList;
        title = titles;
    }

    @Override
    public int getCount() {
        return viewList.size();
    }


    @Override
    public Fragment getItem(int position) {
        Fragment fragment = viewList.get(position);
        Bundle bundle = fragment.getArguments();
        bundle.putInt("TITLE", title[position]);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public CharSequence getPageTitle(int position) {
        String name = context.getResources().getString(title[position]);
        DLog.i(getClass().getSimpleName(), name);
        return name;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        //super.destroyItem(container, position, object);
    }

    @Override
    public void destroyItem(View container, int position, Object object) {
        //super.destroyItem(container, position, object);
    }
}
