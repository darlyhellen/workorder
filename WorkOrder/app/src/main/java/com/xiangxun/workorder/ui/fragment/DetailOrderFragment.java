package com.xiangxun.workorder.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hellen.baseframe.binder.InitBinder;
import com.hellen.baseframe.binder.ViewsBinder;
import com.hellen.baseframe.common.obsinfo.ToastApp;
import com.xiangxun.workorder.R;
import com.xiangxun.workorder.bean.WorkOrderData;
import com.xiangxun.workorder.common.WorkOrderUtils;
import com.xiangxun.workorder.ui.biz.DetailOrderFragmentListener.DetailOrderFragmentInterface;
import com.xiangxun.workorder.ui.presenter.DetailOrderFragmentPresenter;

/**
 * Created by Zhangyuhui/Darly on 2017/5/27.
 * Copyright by [Zhangyuhui/Darly]
 * ©2017 XunXiang.Company. All rights reserved.
 *
 * @TODO: 固态详情展示页面。
 */
public class DetailOrderFragment extends Fragment implements OnClickListener, DetailOrderFragmentInterface {
    private View root;

    private WorkOrderData data;

    @ViewsBinder(R.id.tv_content01)
    private TextView tvContent01;//工单编号
    @ViewsBinder(R.id.tv_content02)
    private TextView tvContent02;//设备名称
    @ViewsBinder(R.id.tv_content03)
    private TextView tvContent03;//设备编号
    @ViewsBinder(R.id.tv_content04)
    private TextView tvContent04;//设备IP
    @ViewsBinder(R.id.tv_content05)
    private TextView tvContent05;//设备类型
    @ViewsBinder(R.id.tv_content06)
    private TextView tvContent06;//位置信息
    @ViewsBinder(R.id.tv_content07)
    private TextView tvContent07;//所属部门
    @ViewsBinder(R.id.tv_content08)
    private TextView tvContent08;//派发人
    @ViewsBinder(R.id.tv_content09)
    private TextView tvContent09;//派发时间
    @ViewsBinder(R.id.tv_content10)
    private TextView tvContent10;//工单状态
    @ViewsBinder(R.id.tv_content11)
    private TextView tvContent11;//是否场内
    @ViewsBinder(R.id.tv_content12)
    private TextView tvContent12;//是否转派
    @ViewsBinder(R.id.tv_content13)
    private TextView tvContent13;//是否遗留
    @ViewsBinder(R.id.tv_content14)
    private TextView tvContent14;//短信内容
    @ViewsBinder(R.id.id_detail_fragment_exception)
    private LinearLayout except;
    @ViewsBinder(R.id.tv_content15)
    private TextView tvContent15;//上报人
    @ViewsBinder(R.id.tv_content16)
    private TextView tvContent16;//上报时间
    @ViewsBinder(R.id.tv_content17)
    private TextView tvContent17;//上报内容
    @ViewsBinder(R.id.id_detail_fragment_close)
    private LinearLayout close;
    @ViewsBinder(R.id.tv_content18)
    private TextView tvContent18;//关闭人
    @ViewsBinder(R.id.tv_content19)
    private TextView tvContent19;//关闭时间
    @ViewsBinder(R.id.tv_content20)
    private TextView tvContent20;   //
    @ViewsBinder(R.id.tv_content21)
    private TextView tvContent21;   //
    @ViewsBinder(R.id.tv_declare)
    private EditText reason;   //
    @ViewsBinder(R.id.id_detail_fragment_button)
    private LinearLayout button;   //
    @ViewsBinder(R.id.id_detail_fragment_config)
    private Button commit;   //
    @ViewsBinder(R.id.id_detail_fragment_consel)
    private Button consel;   //

    private DetailOrderFragmentPresenter presenter;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_detail_order, null);
        InitBinder.InitAll(this, root);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        presenter = new DetailOrderFragmentPresenter(this);
        initView();
        initListener();
    }


    private void initView() {
        data = getArguments().getParcelable("data");
        if (data == null) {
            ToastApp.showToast("页面数据错误");
            return;
        }
        switch (data.status) {
            case 0:
                button.setVisibility(View.VISIBLE);
                close.setVisibility(View.GONE);
                except.setVisibility(View.GONE);
                break;
            case 5:
                button.setVisibility(View.GONE);
                except.setVisibility(View.VISIBLE);
                close.setVisibility(View.GONE);
                break;
            case 6:
                button.setVisibility(View.GONE);
                except.setVisibility(View.GONE);
                close.setVisibility(View.VISIBLE);
                break;
            case 1:
            case 2:
            case 3:
            case 4:
            case 7:
                button.setVisibility(View.GONE);
                except.setVisibility(View.GONE);
                close.setVisibility(View.GONE);
                break;
            default:
                button.setVisibility(View.GONE);
                except.setVisibility(View.GONE);
                close.setVisibility(View.GONE);
                break;
        }
        initData();
    }

    private void initData() {
        tvContent01.setText(data.id);
        tvContent02.setText(data.devicename);
        tvContent03.setText(data.devicecode);
        tvContent04.setText(data.deviceip);
        tvContent05.setText(data.devicetype);
        tvContent06.setText(data.position);
        tvContent07.setText(data.orgname);
        tvContent08.setText(data.contactname);
        tvContent09.setText(data.assigntime);
        tvContent10.setText(WorkOrderUtils.findStatus(data.status));
        if (0 == data.isouter) {
            tvContent11.setText("否");
        } else {
            tvContent11.setText("是");
        }
        tvContent12.setText(data.isreassign);
        tvContent13.setText(data.isleave);
        tvContent14.setText(data.messages);

        //异常状态
        tvContent15.setText("");
        tvContent16.setText("");
        tvContent17.setText("");
        // 关闭情况
        tvContent18.setText("");
        tvContent19.setText("");
        tvContent20.setText("");
        tvContent21.setText("");
    }

    private void initListener() {
        commit.setOnClickListener(this);
        consel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        presenter.onClickDown(getActivity(), v);
    }

    @Override
    public void onLoginSuccess() {
        //修改状态成功后，退出页面
        getActivity().setResult(701);
        getActivity().finish();
    }

    @Override
    public void onLoginFailed() {

    }

    @Override
    public String getDataID() {
        return data.id;
    }

    @Override
    public String getReason() {
        return reason.getText().toString().trim();
    }

    @Override
    public void setDisableClick() {
        commit.setClickable(false);
        consel.setClickable(false);
    }

    @Override
    public void setEnableClick() {
        commit.setClickable(true);
        consel.setClickable(true);
    }
}
