package com.xiangxun.workorder.ui.main;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

import com.hellen.baseframe.binder.ContentBinder;
import com.hellen.baseframe.binder.ViewsBinder;
import com.hellen.baseframe.common.dlog.DLog;
import com.xiangxun.workorder.R;
import com.xiangxun.workorder.base.BaseActivity;
import com.xiangxun.workorder.bean.BaseModel;
import com.xiangxun.workorder.ui.biz.TourListener.TourInterface;
import com.xiangxun.workorder.ui.presenter.TourPresenter;
import com.xiangxun.workorder.widget.dialog.TourSelectDialog;
import com.xiangxun.workorder.widget.grid.WholeGridView;
import com.xiangxun.workorder.widget.header.HeaderView;

import java.util.ArrayList;

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
    @ViewsBinder(R.id.id_order_equip_code)
    private TextView id_order_equip_code;
    @ViewsBinder(R.id.id_order_equip_ip)
    private TextView id_order_equip_ip;
    @ViewsBinder(R.id.id_order_equip_type)
    private TextView id_order_equip_type;
    @ViewsBinder(R.id.id_order_equip_position)
    private TextView id_order_equip_position;
    @ViewsBinder(R.id.id_order_equip_deptment)
    private TextView id_order_equip_deptment;
    @ViewsBinder(R.id.id_order_equip_image)
    private WholeGridView id_order_equip_image;
    @ViewsBinder(R.id.id_order_equip_declare)
    private EditText id_order_equip_declare;
    @ViewsBinder(R.id.tv_order_name)
    private TextView tv_order_name;

    private TourSelectDialog nameType;

    private TourPresenter presenter;

    @Override
    protected void initView(Bundle savedInstanceState) {
        presenter = new TourPresenter(this);
        title.setTitle(R.string.st_tour_title);
        title.setLeftBackgroundResource(R.mipmap.ic_title_back);
        title.getTitleViewOperationText().setText(R.string.st_tour_commit);
        nameType = new TourSelectDialog(this, new ArrayList<BaseModel.Type>(), tv_order_name, "请选择工单类型");
    }

    @Override
    protected void initListener() {
        title.setLeftBackOneListener(this);
        title.setRightImageTextFlipper(this);
    }

    @Override
    public void onClick(View v) {
        presenter.onClickDown(this, v);
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
