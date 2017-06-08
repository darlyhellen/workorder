package com.xiangxun.workorder.ui.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hellen.baseframe.binder.InitBinder;
import com.hellen.baseframe.binder.ViewsBinder;
import com.hellen.baseframe.common.dlog.DLog;
import com.hellen.baseframe.common.obsinfo.ToastApp;
import com.xiangxun.workorder.R;
import com.xiangxun.workorder.base.AppEnum;
import com.xiangxun.workorder.bean.EquipmentInfo;
import com.xiangxun.workorder.bean.WorkOrderData;
import com.xiangxun.workorder.common.WorkOrderUtils;
import com.xiangxun.workorder.ui.adapter.DetailOrderImageAdapter;
import com.xiangxun.workorder.ui.adapter.DetailOrderImageAdapter.OnDetailOrderConsListener;
import com.xiangxun.workorder.ui.biz.DetailOrderFragmentListener.DetailOrderFragmentInterface;
import com.xiangxun.workorder.ui.main.ShowImageViewActivity;
import com.xiangxun.workorder.ui.presenter.DetailOrderFragmentPresenter;
import com.xiangxun.workorder.widget.camera.PhotoPop;
import com.xiangxun.workorder.widget.grid.WholeGridView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zhangyuhui/Darly on 2017/5/27.
 * Copyright by [Zhangyuhui/Darly]
 * ©2017 XunXiang.Company. All rights reserved.
 *
 * @TODO: 固态详情展示页面。
 */
public class DetailOrderFragment extends Fragment implements OnClickListener, DetailOrderFragmentInterface, OnItemClickListener, OnDetailOrderConsListener {
    private View root;


    @ViewsBinder(R.id.id_detail_tv_title)
    private TextView tv_title;//展示名称。
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
    @ViewsBinder(R.id.id_detail_fragment_inimage)
    private WholeGridView images;
    private List<String> imageData;
    private DetailOrderImageAdapter adapter;

    /**
     * 上午9:29:04 TODO 调出选项的POP窗口，主要为相机，相册，取消
     */
    public PhotoPop pop;
    private DetailOrderFragmentPresenter presenter;

    private WorkOrderData data;
    private EquipmentInfo info;
    private WorkOrderData tour;

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
        pop = new PhotoPop(getActivity());
        initView();
        initListener();
    }


    private void initView() {
        data = getArguments().getParcelable("WorkOrderData");
        info = getArguments().getParcelable("EquipmentInfo");
        tour = getArguments().getParcelable("WorkOrderData");
        if (data != null) {
            hasData();
        } else if (info != null) {
            hasInfo();
        } else if (tour != null) {
            hasTour();
        }

    }

    private void hasData() {
        tv_title.setText(R.string.st_detail_detail);
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
        }
        tvContent01.setText(data.id);
        tvContent02.setText(data.devicename);
        tvContent03.setText(data.devicecode);
        tvContent04.setText(data.deviceip);
        if ("device".equals(data.devicetype)) {
            tvContent05.setText("卡口");
        } else if ("ftp".equals(data.devicetype)) {
            tvContent05.setText("FTP");
        } else if ("project".equals(data.devicetype)) {
            tvContent05.setText("平台");
        } else if ("database".equals(data.devicetype)) {
            tvContent05.setText("数据库");
        } else {
            tvContent05.setText("机柜");
        }
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
        imageData.add("添加图片");
        adapter = new DetailOrderImageAdapter(imageData, R.layout.item_main_detail_image_adapter, getActivity(), this);
        images.setAdapter(adapter);
    }
    //设备信息
    private void hasInfo() {
//        tv_title.setText(R.string.st_detail_equip);
//        tvContent01.setText(info.id);
//        tvContent02.setText(info.devicename);
//        tvContent03.setText(info.devicecode);
//        tvContent04.setText(info.deviceip);
//        if ("device".equals(info.devicetype)) {
//            tvContent05.setText("卡口");
//        } else if ("ftp".equals(info.devicetype)) {
//            tvContent05.setText("FTP");
//        } else if ("project".equals(info.devicetype)) {
//            tvContent05.setText("平台");
//        } else if ("infobase".equals(info.devicetype)) {
//            tvContent05.setText("数据库");
//        } else {
//            tvContent05.setText("机柜");
//        }
//        tvContent06.setText(info.position);
//        tvContent07.setText(info.orgname);
//        tvContent08.setText(info.contactname);
//        tvContent09.setText(info.assigntime);
//        WorkOrderUtils.findStatus(info.status, tvContent10);
//        if (0 == info.isouter) {
//            tvContent11.setText("否");
//        } else {
//            tvContent11.setText("是");
//        }
//        if ("0".equals(info.isreassign)) {
//            tvContent12.setText("否");
//        } else {
//            tvContent12.setText("是");
//        }
//
//        if ("0".equals(info.isleave)) {
//            tvContent13.setText("否");
//        } else {
//            tvContent13.setText("是");
//        }
//        tvContent14.setText(info.messages);
//
//        //异常状态
//        tvContent15.setText(info.exceptionid);
//        tvContent16.setText("");
//        tvContent17.setText("");
//        // 关闭情况
//        tvContent18.setText(info.offaccount);
//        tvContent19.setText(info.offtime);
//        tvContent20.setText("");
//        tvContent21.setText("");
//        //添加图片的功能模块
//        imageData = new ArrayList<String>();
//        imageData.add("添加图片");
//        adapter = new DetailOrderImageAdapter(imageData, R.layout.item_main_detail_image_adapter, getActivity(), this);
//        images.setAdapter(adapter);
        
    }

    private void hasTour() {
        tv_title.setText(R.string.st_detail_tour);
        tvContent01.setText(tour.id);
        tvContent02.setText(tour.devicename);
        tvContent03.setText(tour.devicecode);
        tvContent04.setText(tour.deviceip);
        if ("device".equals(tour.devicetype)) {
            tvContent05.setText("卡口");
        } else if ("ftp".equals(tour.devicetype)) {
            tvContent05.setText("FTP");
        } else if ("project".equals(tour.devicetype)) {
            tvContent05.setText("平台");
        } else if ("database".equals(tour.devicetype)) {
            tvContent05.setText("数据库");
        } else {
            tvContent05.setText("机柜");
        }
        tvContent06.setText(tour.position);
        tvContent07.setText(tour.orgname);
        tvContent08.setText(tour.contactname);
        tvContent09.setText(tour.assigntime);
        WorkOrderUtils.findStatus(tour.status, tvContent10);
        if (0 == tour.isouter) {
            tvContent11.setText("否");
        } else {
            tvContent11.setText("是");
        }
        if ("0".equals(tour.isreassign)) {
            tvContent12.setText("否");
        } else {
            tvContent12.setText("是");
        }

        if ("0".equals(tour.isleave)) {
            tvContent13.setText("否");
        } else {
            tvContent13.setText("是");
        }
        tvContent14.setText(tour.messages);

        //异常状态
        tvContent15.setText(tour.exceptionid);
        tvContent16.setText("");
        tvContent17.setText("");
        // 关闭情况
        tvContent18.setText(tour.offaccount);
        tvContent19.setText(tour.offtime);
        tvContent20.setText("");
        tvContent21.setText("");
        //添加图片的功能模块
        imageData = new ArrayList<String>();
        imageData.add("添加图片");
        adapter = new DetailOrderImageAdapter(imageData, R.layout.item_main_detail_image_adapter, getActivity(), this);
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
    public int getStatus() {
        return data.status;
    }


    @Override
    public List<String> getUrls() {
        return imageData;
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
        if (position == (imageData.size() - 1)) {
            if (position == 3) {
                //上传图片
                DLog.i(getClass().getSimpleName(), position + "上传图片");
                presenter.upLoadImage(imageData);
            } else {
                pop.show(view);
            }
        } else {
            Intent intent = new Intent(getActivity(), ShowImageViewActivity.class);
            intent.putExtra("position", position);
            int[] location = new int[2];
            view.getLocationOnScreen(location);
            intent.putExtra("locationX", location[0]);//必须
            intent.putExtra("locationY", location[1]);//必须
            intent.putExtra("url", st);
            intent.putExtra("width", view.getWidth());//必须
            intent.putExtra("height", view.getHeight());//必须
            startActivity(intent);
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

            imageData.add(imageData.size() - 1, head_path);
            adapter.setData(imageData);
            //pop.cropPhoto(Uri.fromFile(temp));// 裁剪图片
            //这里不需要裁剪图片。

        }
    }

    //点击删除图片按钮，进行图片删除操作。
    @Override
    public void onConsListener(View v, int position) {
        imageData.remove(position);
        adapter.setData(imageData);
    }
}
