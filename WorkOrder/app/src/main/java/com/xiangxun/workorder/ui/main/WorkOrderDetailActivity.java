package com.xiangxun.workorder.ui.main;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.hellen.baseframe.binder.ContentBinder;
import com.hellen.baseframe.binder.ViewsBinder;
import com.hellen.baseframe.common.dlog.DLog;
import com.xiangxun.workorder.R;
import com.xiangxun.workorder.base.BaseActivity;
import com.xiangxun.workorder.bean.WorkOrderData;
import com.xiangxun.workorder.ui.biz.WorkOrderDetailListener;
import com.xiangxun.workorder.ui.biz.WorkOrderDetailListener.WorkOrderDetailInterface;
import com.xiangxun.workorder.ui.presenter.TourPresenter;
import com.xiangxun.workorder.ui.presenter.WorkOrderDetailPresenter;
import com.xiangxun.workorder.widget.header.HeaderView;

/**
 * Created by Zhangyuhui/Darly on 2017/5/26.
 * Copyright by [Zhangyuhui/Darly]
 * ©2017 XunXiang.Company. All rights reserved.
 *
 * @TODO: 工单详情页面，点击每个工单，都会进入到详情页面。不过是根据不同参数。展示效果不同而已。
 */
@ContentBinder(R.layout.activity_detail_work_order)
public class WorkOrderDetailActivity extends BaseActivity implements OnClickListener, WorkOrderDetailInterface {

    private WorkOrderDetailPresenter presenter;
    @ViewsBinder(R.id.id_detail_title)
    private HeaderView header;

    @Override
    protected void initView(Bundle savedInstanceState) {
        presenter = new WorkOrderDetailPresenter(this);
        header.setTitle(R.string.st_tour_title);
        header.setLeftBackgroundResource(R.mipmap.ic_title_back);
        header.getTitleViewOperationText().setText(R.string.st_tour_commit);
        boolean isTour = getIntent().getBooleanExtra("isTour", false);
        int id = getIntent().getIntExtra("des", 0);
        WorkOrderData data = (WorkOrderData) getIntent().getSerializableExtra("data");
        DLog.i(getClass().getSimpleName(), isTour + "\r\n" + id + "\r\n" + data);
    }

    @Override
    protected void initListener() {
        header.setLeftBackOneListener(this);
        header.setRightImageTextFlipper(this);
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
}
