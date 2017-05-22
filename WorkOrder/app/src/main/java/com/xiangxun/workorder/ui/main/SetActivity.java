package com.xiangxun.workorder.ui.main;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.hellen.baseframe.binder.ContentBinder;
import com.hellen.baseframe.binder.ViewsBinder;
import com.xiangxun.workorder.R;
import com.xiangxun.workorder.base.BaseActivity;
import com.xiangxun.workorder.bean.SetModel;
import com.xiangxun.workorder.ui.adapter.SetAdapter;
import com.xiangxun.workorder.ui.biz.SetListener;
import com.xiangxun.workorder.ui.presenter.SetPresenter;
import com.xiangxun.workorder.widget.header.HeaderView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zhangyuhui/Darly on 2017/5/22.
 * Copyright by [Zhangyuhui/Darly]
 * ©2017 XunXiang.Company. All rights reserved.
 *
 * @TODO:首页设置页面。
 */
@ContentBinder(R.layout.activity_set)
public class SetActivity extends BaseActivity implements SetListener.SetInterface, View.OnClickListener {

    @ViewsBinder(R.id.id_set_title)
    private HeaderView header;
    @ViewsBinder(R.id.id_set_list)
    private ListView listView;
    private SetAdapter adapter;

    private SetPresenter presenter;

    @Override
    protected void initView(Bundle savedInstanceState) {
        header.setTitle(R.string.main_work_set);
        header.setLeftBackgroundResource(R.mipmap.back_image);

        presenter = new SetPresenter(this);
        presenter.findFileSize();


        adapter = new SetAdapter(null, R.layout.item_activity_set, this);
        listView.setAdapter(adapter);
    }


    @Override
    protected void initListener() {
        header.setLeftBackOneListener(this);
    }

    @Override
    public void onClick(View v) {
        presenter.onClickDown(this, v);
    }

    @Override
    public void getUserDate(List<SetModel> data) {
        adapter.setData(data);
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
}
