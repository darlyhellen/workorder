package com.xiangxun.workorder.ui.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.hellen.baseframe.binder.ContentBinder;
import com.hellen.baseframe.binder.ViewsBinder;
import com.hellen.baseframe.common.dlog.DLog;
import com.hellen.baseframe.common.obsinfo.ToastApp;
import com.hellen.baseframe.common.utiltools.SharePreferHelp;
import com.xiangxun.workorder.R;
import com.xiangxun.workorder.base.AppEnum;
import com.xiangxun.workorder.base.BaseActivity;
import com.xiangxun.workorder.base.ItemClickListenter;
import com.xiangxun.workorder.bean.EquipMenuChildData;
import com.xiangxun.workorder.bean.EquipmentInfo;
import com.xiangxun.workorder.common.LocationTools;
import com.xiangxun.workorder.ui.MaxLengthWatcher;
import com.xiangxun.workorder.ui.MaxLengthWatcher.MaxLengthUiListener;
import com.xiangxun.workorder.ui.adapter.TourImageAdapter;
import com.xiangxun.workorder.ui.adapter.TourImageAdapter.OnTourConsListener;
import com.xiangxun.workorder.ui.biz.TourListener.TourInterface;
import com.xiangxun.workorder.ui.login.edittext.ClearEditText;
import com.xiangxun.workorder.ui.presenter.TourPresenter;
import com.xiangxun.workorder.widget.camera.OwnerPhotoPop;
import com.xiangxun.workorder.widget.camera.PhotoPop;
import com.xiangxun.workorder.widget.dialog.TourSelectDialog;
import com.xiangxun.workorder.widget.dialog.TourSelectDialog.onSelectItemClick;
import com.xiangxun.workorder.widget.dialog.TourSelectListener;
import com.xiangxun.workorder.widget.grid.WholeGridView;
import com.xiangxun.workorder.widget.header.HeaderView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zhangyuhui/Darly on 2017/5/26.
 * Copyright by [Zhangyuhui/Darly]
 * ©2017 XunXiang.Company. All rights reserved.
 *
 * @TODO:新增巡检工单
 */
@ContentBinder(R.layout.activity_tour)
public class TourActivity extends BaseActivity implements OnClickListener, TourInterface, OnTourConsListener, onSelectItemClick, MaxLengthUiListener, LocationTools.LocationToolsListener {


    @ViewsBinder(R.id.id_tour_title)
    private HeaderView title;

    @ViewsBinder(R.id.id_order_equip_ip)
    private TextView id_order_equip_ip;
    @ViewsBinder(R.id.id_order_equip_type)
    private TextView id_order_equip_type;
    @ViewsBinder(R.id.id_order_equip_type_click)
    private RelativeLayout id_order_equip_type_click;
    @ViewsBinder(R.id.id_order_equip_position)
    private TextView id_order_equip_position;
    @ViewsBinder(R.id.id_order_equip_deptment)
    private TextView id_order_equip_deptment;
    @ViewsBinder(R.id.id_order_equip_image)
    private WholeGridView id_order_equip_image;
    @ViewsBinder(R.id.id_order_equip_declare)
    private EditText id_order_equip_declare;
    @ViewsBinder(R.id.id_order_equip_declare_num)
    private TextView id_order_equip_declare_num;


    @ViewsBinder(R.id.id_tour_code)
    private LinearLayout id_tour_code;

    @ViewsBinder(R.id.id_tour_code_code)
    private TextView id_tour_code_code;
    @ViewsBinder(R.id.id_tour_code_name)
    private ClearEditText id_tour_code_name;
    @ViewsBinder(R.id.id_tour_code_name_click)
    private TextView id_tour_code_name_click;//根据编码查询点击请求

    @ViewsBinder(R.id.id_tour_name)
    private LinearLayout id_tour_name;

    @ViewsBinder(R.id.id_tour_name_code)
    private TextView id_tour_name_code;//名称
    @ViewsBinder(R.id.id_tour_name_name)
    private ClearEditText id_tour_name_name;//编码
    @ViewsBinder(R.id.id_tour_name_name_click)
    private TextView id_tour_name_name_click;//根据名称查询点击请求


    //位置信息
    @ViewsBinder(R.id.id_order_adress)
    private TextView id_order_adress;//位置
    @ViewsBinder(R.id.id_order_longitude)
    private TextView id_order_longitude;//经度
    @ViewsBinder(R.id.id_order_latitude)
    private TextView id_order_latitude;//纬度

    private List<String> imageData;
    private TourImageAdapter adapter;
    private OwnerPhotoPop pop;

    private TourPresenter presenter;

    private boolean isCheck;

    @Override
    protected void initView(Bundle savedInstanceState) {
        presenter = new TourPresenter(this);
        LocationTools.getInstance().setLocationToolsListener(this);
        LocationTools.getInstance().start();
        String cach = SharePreferHelp.getValue(AppEnum.EQUIPMENTMATION.getDec(), null);
        if (TextUtils.isEmpty(cach)) {
            //没有缓存，进行网络请求接口。
            // presenter.getEquipment();
        } else {
            //有缓存，进行数据解析。
        }
        title.setTitle(R.string.st_tour_title);
        title.setLeftBackgroundResource(R.mipmap.ic_title_back);
        title.getTitleViewOperationText().setText(R.string.st_tour_commit);

        pop = new OwnerPhotoPop(this);
        //添加图片的功能模块
        imageData = new ArrayList<String>();
        imageData.add("添加图片");
        adapter = new TourImageAdapter(imageData, R.layout.item_main_detail_image_adapter, this, this);
        id_order_equip_image.setAdapter(adapter);

    }

    @Override
    protected void initListener() {
        id_order_equip_declare.addTextChangedListener(new MaxLengthWatcher(100, id_order_equip_declare, this));
        id_order_equip_declare_num.setText("0/100");
        title.setLeftBackOneListener(this);
        title.setRightImageTextFlipper(this);
        id_order_equip_image.setOnItemClickListener(new ItemClickListenter() {
            @Override
            public void NoDoubleItemClickListener(AdapterView<?> parent, View view, int position, long id) {
                String st = (String) parent.getItemAtPosition(position);
                if (position == (imageData.size() - 1)) {
                    pop.show(view, imageData.size(), AppEnum.IMAGE.concat("tour"));
                } else {
                    Intent intent = new Intent(TourActivity.this, ShowImageViewActivity.class);
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
        });
        id_order_equip_type_click.setOnClickListener(this);
        id_tour_code_name_click.setOnClickListener(this);
        id_tour_name_name_click.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.id_order_equip_type_click:
                new TourSelectDialog(this, presenter.getType(), "请选择设备类型", this).show();
                break;
            default:
                presenter.onClickDown(this, v, isCheck);
                break;
        }

    }

    @Override
    public void end() {
        finish();
    }

    @Override
    public String getCode() {
        return id_tour_code_name.getText().toString().trim();
    }

    @Override
    public void onNameCodeSuccess(List<EquipmentInfo> info) {
        //填充数据
        isCheck = true;
        List<TourSelectListener> datas = new ArrayList<TourSelectListener>();
        datas.addAll(info);
        new TourSelectDialog(this, datas, "请选择设备类型", this).show();
    }

    @Override
    public void onNameCodeFailed() {
        isCheck = false;
    }


    @Override
    public String getName() {
        return id_tour_name_name.getText().toString().trim();
    }


    @Override
    public String getDeclare() {
        return id_order_equip_declare.getText().toString().trim();
    }

    @Override
    public List<String> getImageData() {
        return imageData;
    }

    @Override
    public String getAddress() {
        return id_order_adress.getText().toString().trim();
    }

    @Override
    public String getlongitude() {
        return id_order_longitude.getText().toString().trim();
    }

    @Override
    public String getlatitude() {
        return id_order_latitude.getText().toString().trim();
    }

    @Override
    public void onTourSuccess(String s) {
        ToastApp.showToast(s);
        setResult(701);
        finish();
    }

    @Override
    public void onTourFailed() {

    }

    @Override
    public void setDisableClick() {
        title.getTitleViewOperationText().setClickable(false);
    }

    @Override
    public void setEnableClick() {
        title.getTitleViewOperationText().setClickable(true);
    }

    @Override
    public void onConsListener(View v, int position) {
        imageData.remove(position);
        adapter.setData(imageData);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        DLog.i("onActivityResult" + requestCode + resultCode);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case 1:
                    if (data != null) {
                        List<String> photos = (List<String>) data.getSerializableExtra("camera_picture");
                        imageData.addAll(imageData.size() - 1, photos);
                        adapter.setData(imageData);
                    }
                    break;
                case 99:
                    if (data != null) {
                        List<String> photos = (List<String>) data.getSerializableExtra("album_picture");
                        imageData.addAll(imageData.size() - 1, photos);
                        adapter.setData(imageData);
                    }
                    break;
            }
        }

    }

    @Override
    public void changeState(TourSelectListener type) {
        if (type instanceof EquipMenuChildData) {
            EquipMenuChildData datat = (EquipMenuChildData) type;
            //点击选中的设备信息。
            id_order_equip_type.setText(datat.getName());
//            if ("icabinef".equals(datat.getType()) || "device".equals(datat.getType()) || "server".equals(datat.getType())) {
//                //这些有设备编号。根据编号查询
//                id_tour_code.setVisibility(View.VISIBLE);
//                id_tour_name.setVisibility(View.GONE);
//            } else {
            //剩余没有设备编号。更加名称查询
            id_tour_code.setVisibility(View.GONE);
            id_tour_name.setVisibility(View.VISIBLE);
//            }
            presenter.setType(datat.getType());

            id_order_equip_ip.setText("");
            id_order_equip_position.setText("");
            id_order_equip_deptment.setText("");
            id_tour_code_code.setText("");
            id_tour_code_name.setText("");
            id_tour_name_code.setText("");
            id_tour_name_name.setText("");
        }
        if (type instanceof EquipmentInfo) {
            EquipmentInfo infoed = (EquipmentInfo) type;
            id_order_equip_ip.setText(infoed.ip);
            id_order_equip_position.setText(infoed.installplace);
            id_order_equip_deptment.setText(infoed.orgname);
            id_tour_code_code.setText(infoed.assetname);
            id_tour_code_name.setText(infoed.code);
            id_tour_name_code.setText(infoed.code);
            id_tour_name_name.setText(infoed.assetname);
            presenter.setDevice(infoed);
        }
    }

    @Override
    public void onUiChanged(int num) {
        id_order_equip_declare_num.setText(num + "/100");
    }

    @Override
    public void locationSuccess(AMapLocation amapLocation) {
        if (amapLocation != null) {
            id_order_adress.setText(amapLocation.getAddress());
            id_order_longitude.setText(String.valueOf(amapLocation.getLongitude()));
            id_order_latitude.setText(String.valueOf(amapLocation.getLatitude()));
        } else {
            LocationTools.getInstance().reStart();
        }
    }

    @Override
    public void locationFail() {

    }
}
