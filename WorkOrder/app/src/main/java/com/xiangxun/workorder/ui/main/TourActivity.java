package com.xiangxun.workorder.ui.main;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.hellen.baseframe.binder.ContentBinder;
import com.hellen.baseframe.binder.ViewsBinder;
import com.hellen.baseframe.common.dlog.DLog;
import com.xiangxun.workorder.R;
import com.xiangxun.workorder.base.BaseActivity;
import com.xiangxun.workorder.ui.biz.TourListener.TourInterface;
import com.xiangxun.workorder.ui.presenter.TourPresenter;
import com.xiangxun.workorder.widget.header.HeaderView;

/**
 * Created by Zhangyuhui/Darly on 2017/5/26.
 * Copyright by [Zhangyuhui/Darly]
 * ©2017 XunXiang.Company. All rights reserved.
 *
 * @TODO:新增巡检工单
 */
@ContentBinder(R.layout.activity_tour)
public class TourActivity extends BaseActivity implements OnClickListener, TourInterface {


    @ViewsBinder(R.id.id_tour_title)
    private HeaderView title;

    private TourPresenter presenter;

    @Override
    protected void initView(Bundle savedInstanceState) {
        presenter = new TourPresenter(this);
        title.setTitle(R.string.st_tour_title);
        title.setLeftBackgroundResource(R.mipmap.ic_title_back);
        title.getTitleViewOperationText().setText(R.string.st_tour_commit);
    }

    @Override
    protected void initListener() {
        title.setLeftBackOneListener(this);
        title.setRightImageTextFlipper(this);
    }

    @Override
    public void onClick(View v) {
        presenter.onClickDown(this,v);
    }

    @Override
    public void end() {
        finish();
    }

    @Override
    public void setDisableClick() {
        title.getTitleViewOperationText().setClickable(false);
    }

    @Override
    public void setEnableClick() {
        title.getTitleViewOperationText().setClickable(true);
    }
}
