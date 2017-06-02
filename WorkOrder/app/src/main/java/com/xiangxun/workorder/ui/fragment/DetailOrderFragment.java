package com.xiangxun.workorder.ui.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hellen.baseframe.binder.InitBinder;
import com.hellen.baseframe.binder.ViewsBinder;
<<<<<<< HEAD
=======
import com.hellen.baseframe.common.dlog.DLog;
>>>>>>> f9757719696d8632a6d88228ebda0cd326936f4d
import com.hellen.baseframe.common.obsinfo.ToastApp;
import com.xiangxun.workorder.R;
import com.xiangxun.workorder.base.AppEnum;
import com.xiangxun.workorder.bean.WorkOrderData;
<<<<<<< HEAD
import com.xiangxun.workorder.ui.biz.DetailOrderFragmentListener.DetailOrderFragmentInterface;
import com.xiangxun.workorder.ui.presenter.DetailOrderFragmentPresenter;
=======
import com.xiangxun.workorder.common.WorkOrderUtils;
import com.xiangxun.workorder.ui.adapter.DetailOrderImageAdapter;
import com.xiangxun.workorder.ui.biz.DetailOrderFragmentListener.DetailOrderFragmentInterface;
import com.xiangxun.workorder.ui.main.ShowImageViewActivity;
import com.xiangxun.workorder.ui.presenter.DetailOrderFragmentPresenter;
import com.xiangxun.workorder.widget.camera.PhotoPop;
import com.xiangxun.workorder.widget.grid.WholeGridView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
>>>>>>> f9757719696d8632a6d88228ebda0cd326936f4d

/**
 * Created by Zhangyuhui/Darly on 2017/5/27.
 * Copyright by [Zhangyuhui/Darly]
 * ©2017 XunXiang.Company. All rights reserved.
 *
 * @TODO: 固态详情展示页面。
 */
<<<<<<< HEAD
public class DetailOrderFragment extends Fragment implements OnClickListener, DetailOrderFragmentInterface {
=======
public class DetailOrderFragment extends Fragment implements OnClickListener, DetailOrderFragmentInterface, OnItemClickListener {
>>>>>>> f9757719696d8632a6d88228ebda0cd326936f4d
    private View root;

    private WorkOrderData data;

    @ViewsBinder(R.id.tv_content01)
<<<<<<< HEAD
    private TextView tvContent01;
    @ViewsBinder(R.id.tv_content02)
    private TextView tvContent02;
    @ViewsBinder(R.id.tv_content03)
    private TextView tvContent03;
    @ViewsBinder(R.id.tv_content04)
    private TextView tvContent04;
    @ViewsBinder(R.id.tv_content05)
    private TextView tvContent05;
    @ViewsBinder(R.id.tv_content06)
    private TextView tvContent06;
    @ViewsBinder(R.id.tv_content07)
    private TextView tvContent07;
    @ViewsBinder(R.id.tv_content08)
    private TextView tvContent08;
    @ViewsBinder(R.id.tv_content09)
    private TextView tvContent09;
    @ViewsBinder(R.id.tv_content10)
    private TextView tvContent10;
    @ViewsBinder(R.id.tv_content11)
    private TextView tvContent11;
    @ViewsBinder(R.id.tv_content12)
    private TextView tvContent12;
    @ViewsBinder(R.id.tv_content13)
    private TextView tvContent13;
    @ViewsBinder(R.id.tv_content14)
    private TextView tvContent14;
    @ViewsBinder(R.id.tv_content15)
    private TextView tvContent15;
    @ViewsBinder(R.id.tv_content16)
    private TextView tvContent16;
    @ViewsBinder(R.id.tv_content17)
    private TextView tvContent17;
    @ViewsBinder(R.id.tv_content18)
    private TextView tvContent18;
    @ViewsBinder(R.id.tv_content19)
    private TextView tvContent19;
    @ViewsBinder(R.id.tv_content20)
    private TextView tvContent20;
    @ViewsBinder(R.id.tv_content21)
    private TextView tvContent21;
    @ViewsBinder(R.id.tv_content22)
    private TextView tvContent22;
    @ViewsBinder(R.id.id_detail_fragment_button)
    private LinearLayout button;
    @ViewsBinder(R.id.id_detail_fragment_config)
    private Button commit;
    @ViewsBinder(R.id.id_detail_fragment_consel)
    private Button consel;
=======
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
    @ViewsBinder(R.id.id_detail_fragment_inimage)
    private WholeGridView images;
    private List<String> imageData;
    private DetailOrderImageAdapter adapter;


    /**
     * 上午9:29:04 TODO 调出选项的POP窗口，主要为相机，相册，取消
     */
    public PhotoPop pop;
    private DetailOrderFragmentPresenter presenter;
>>>>>>> f9757719696d8632a6d88228ebda0cd326936f4d

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
<<<<<<< HEAD
=======
        pop = new PhotoPop(getActivity());
>>>>>>> f9757719696d8632a6d88228ebda0cd326936f4d
        initView();
        initListener();
    }


    private void initView() {
<<<<<<< HEAD
        data =  getArguments().getParcelable("data");
=======
        data = getArguments().getParcelable("data");
>>>>>>> f9757719696d8632a6d88228ebda0cd326936f4d
        if (data == null) {
            ToastApp.showToast("页面数据错误");
            return;
        }
<<<<<<< HEAD
//        tvContent01 = (TextView) root.findViewById(R.id.tv_content01);
//        tvContent02 = (TextView) root.findViewById(R.id.tv_content02);
//        tvContent03 = (TextView) root.findViewById(R.id.tv_content03);
//        tvContent04 = (TextView) root.findViewById(R.id.tv_content04);
//        tvContent05 = (TextView) root.findViewById(R.id.tv_content05);
//        tvContent06 = (TextView) root.findViewById(R.id.tv_content06);
//        tvContent07 = (TextView) root.findViewById(R.id.tv_content07);
//        tvContent08 = (TextView) root.findViewById(R.id.tv_content08);
//        tvContent09 = (TextView) root.findViewById(R.id.tv_content09);
//        tvContent10 = (TextView) root.findViewById(R.id.tv_content10);
//        tvContent11 = (TextView) root.findViewById(R.id.tv_content11);
//        tvContent12 = (TextView) root.findViewById(R.id.tv_content12);
//        tvContent13 = (TextView) root.findViewById(R.id.tv_content13);
//        tvContent14 = (TextView) root.findViewById(R.id.tv_content14);
//        tvContent15 = (TextView) root.findViewById(R.id.tv_content15);
//        tvContent16 = (TextView) root.findViewById(R.id.tv_content16);
//        tvContent17 = (TextView) root.findViewById(R.id.tv_content17);
//        tvContent18 = (TextView) root.findViewById(R.id.tv_content18);
//        tvContent19 = (TextView) root.findViewById(R.id.tv_content19);
//        tvContent20 = (TextView) root.findViewById(R.id.tv_content20);
//        tvContent21 = (TextView) root.findViewById(R.id.tv_content21);
//        tvContent22 = (TextView) root.findViewById(R.id.tv_content22);
//        button = (LinearLayout) root.findViewById(R.id.id_detail_fragment_button);
//        commit = (Button) root.findViewById(R.id.id_detail_fragment_config);
//        consel = (Button) root.findViewById(R.id.id_detail_fragment_consel);
        if ("0".equals(data.status)) {
            button.setVisibility(View.VISIBLE);
            commit.setOnClickListener(this);
            consel.setOnClickListener(this);
        } else {
            button.setVisibility(View.GONE);
=======
        switch (data.status) {
            case 0:
                //派工的状态(接收工单，退回工单)
                button.setVisibility(View.VISIBLE);
                close.setVisibility(View.GONE);
                except.setVisibility(View.GONE);
                images.setVisibility(View.GONE);
                commit.setText("接收工单");
                consel.setText("退回工单");
                break;
            case 1:
                //已接收的状态(异常上报,正常上报)
                button.setVisibility(View.VISIBLE);
                except.setVisibility(View.GONE);
                close.setVisibility(View.GONE);
                images.setVisibility(View.VISIBLE);
                commit.setText("正常上报");
                consel.setText("异常上报");
                break;
            case 5:
                //遗留的状态
                button.setVisibility(View.GONE);
                except.setVisibility(View.VISIBLE);
                close.setVisibility(View.GONE);
                images.setVisibility(View.GONE);
                break;
            case 6:
                //关闭的状态
                images.setVisibility(View.GONE);
                button.setVisibility(View.GONE);
                except.setVisibility(View.GONE);
                close.setVisibility(View.VISIBLE);
                break;
            case 2:
            case 3:
            case 4:
            case 7:
                //其他的状态
                images.setVisibility(View.GONE);
                button.setVisibility(View.GONE);
                except.setVisibility(View.GONE);
                close.setVisibility(View.GONE);
                break;
            default:
                button.setVisibility(View.GONE);
                images.setVisibility(View.GONE);
                except.setVisibility(View.GONE);
                close.setVisibility(View.GONE);
                break;
>>>>>>> f9757719696d8632a6d88228ebda0cd326936f4d
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
        WorkOrderUtils.findStatus(data.status, tvContent10);
        if (0 == data.isouter) {
            tvContent11.setText("否");
        } else {
            tvContent11.setText("是");
        }
        if ("0".equals(data.isreassign)) {
            tvContent12.setText("否");
        } else {
            tvContent12.setText("是");
        }

        if ("0".equals(data.isleave)) {
            tvContent13.setText("否");
        } else {
            tvContent13.setText("是");
        }
        tvContent14.setText(data.messages);

        //异常状态
        tvContent15.setText(data.exceptionid);
        tvContent16.setText("");
        tvContent17.setText("");
        // 关闭情况
        tvContent18.setText(data.offaccount);
        tvContent19.setText(data.offtime);
        tvContent20.setText("");
        tvContent21.setText("");
        //添加图片的功能模块
        imageData = new ArrayList<String>();
        imageData.add("add");
        adapter = new DetailOrderImageAdapter(imageData, R.layout.item_fragment_detail_image, getActivity());
        images.setAdapter(adapter);

    }

    private void initListener() {
        commit.setOnClickListener(this);
        consel.setOnClickListener(this);

        images.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View v) {
        presenter.onClickDown(getActivity(), v);
    }

    @Override
<<<<<<< HEAD
    public String getDataID() {
        return data.id;
    }

    @Override
    public void setDisableClick() {
=======
    public void onLoginSuccess() {
        //修改状态成功后，退出页面
        getActivity().setResult(701);
        getActivity().finish();
    }

    @Override
    public void onLoginFailed() {
>>>>>>> f9757719696d8632a6d88228ebda0cd326936f4d

    }

    @Override
<<<<<<< HEAD
    public void setEnableClick() {

=======
    public String getDataID() {
        return data.id;
    }

    @Override
    public String getReason() {
        return reason.getText().toString().trim();
>>>>>>> f9757719696d8632a6d88228ebda0cd326936f4d
    }

    @Override
    public int getStatus() {
        return data.status;
    }


    @Override
    public List<String> getUrls() {
        return null;
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

    //图片加载类
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String st = (String) parent.getItemAtPosition(position);
        if ("add".equals(st)) {
            pop.show(view);
        } else {
            Intent intent = new Intent(getActivity(), ShowImageViewActivity.class);
            intent.putExtra("position", position);
            int[] location = new int[2];
            view.getLocationOnScreen(location);
            intent.putExtra("locationX", location[0]);//必须
            intent.putExtra("locationY", location[1]);//必须
            intent.putExtra("url", (String) parent.getItemAtPosition(position));
            intent.putExtra("width", view.getWidth());//必须
            intent.putExtra("height", view.getHeight());//必须
            startActivity(intent);
            getActivity().overridePendingTransition(0, 0);
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
            imageData.add(imageData.size() - 1, head_path);
            adapter.setData(imageData);
            //pop.cropPhoto(Uri.fromFile(temp));// 裁剪图片
            //这里不需要裁剪图片。
        }
    }


}
