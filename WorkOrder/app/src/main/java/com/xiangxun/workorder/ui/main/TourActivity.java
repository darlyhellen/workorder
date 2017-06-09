package com.xiangxun.workorder.ui.main;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hellen.baseframe.binder.ContentBinder;
import com.hellen.baseframe.binder.ViewsBinder;
import com.hellen.baseframe.common.dlog.DLog;
import com.hellen.baseframe.common.utiltools.SharePreferHelp;
import com.xiangxun.workorder.R;
import com.xiangxun.workorder.base.AppEnum;
import com.xiangxun.workorder.base.BaseActivity;
import com.xiangxun.workorder.base.ItemClickListenter;
import com.xiangxun.workorder.bean.EquipMenuChildData;
import com.xiangxun.workorder.bean.EquipmentInfo;
import com.xiangxun.workorder.ui.adapter.TourImageAdapter;
import com.xiangxun.workorder.ui.adapter.TourImageAdapter.OnTourConsListener;
import com.xiangxun.workorder.ui.biz.TourListener.TourInterface;
import com.xiangxun.workorder.ui.presenter.TourPresenter;
import com.xiangxun.workorder.widget.camera.PhotoPop;
import com.xiangxun.workorder.widget.dialog.TourSelectDialog;
import com.xiangxun.workorder.widget.dialog.TourSelectDialog.onSelectItemClick;
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
public class TourActivity extends BaseActivity implements OnClickListener, TourInterface, OnTourConsListener, onSelectItemClick {


    @ViewsBinder(R.id.id_tour_title)
    private HeaderView title;

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


    @ViewsBinder(R.id.id_tour_code)
    private LinearLayout id_tour_code;

    @ViewsBinder(R.id.id_tour_code_code)
    private TextView id_tour_code_code;
    @ViewsBinder(R.id.id_tour_code_name)
    private EditText id_tour_code_name;
    @ViewsBinder(R.id.id_tour_code_name_click)
    private TextView id_tour_code_name_click;//根据编码查询点击请求

    @ViewsBinder(R.id.id_tour_name)
    private LinearLayout id_tour_name;

    @ViewsBinder(R.id.id_tour_name_code)
    private TextView id_tour_name_code;//名称
    @ViewsBinder(R.id.id_tour_name_name)
    private EditText id_tour_name_name;//编码
    @ViewsBinder(R.id.id_tour_name_name_click)
    private TextView id_tour_name_name_click;//根据名称查询点击请求

    private List<String> imageData;
    private TourImageAdapter adapter;
    private PhotoPop pop;

    private TourPresenter presenter;

    private boolean isCheck;

    @Override
    protected void initView(Bundle savedInstanceState) {
        presenter = new TourPresenter(this);
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

        pop = new PhotoPop(this);
        //添加图片的功能模块
        imageData = new ArrayList<String>();
        imageData.add("添加图片");
        adapter = new TourImageAdapter(imageData, R.layout.item_main_detail_image_adapter, this, this);
        id_order_equip_image.setAdapter(adapter);

    }

    @Override
    protected void initListener() {
        title.setLeftBackOneListener(this);
        title.setRightImageTextFlipper(this);
        id_order_equip_image.setOnItemClickListener(new ItemClickListenter() {
            @Override
            public void NoDoubleItemClickListener(AdapterView<?> parent, View view, int position, long id) {
                String st = (String) parent.getItemAtPosition(position);
                if (position == (imageData.size() - 1)) {
                    pop.show(view);
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
        id_order_equip_type.setOnClickListener(this);

        id_tour_code_name_click.setOnClickListener(this);
        id_tour_name_name_click.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.id_order_equip_type:
                new TourSelectDialog(this, presenter.getType(), id_order_equip_type, "请选择设备类型", this).show();
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

    @Override
    public void changeState(EquipMenuChildData type) {
        //点击选中的设备信息。
        id_order_equip_type.setText(type.getName());
        if ("icabinef".equals(type.getType()) || "device".equals(type.getType()) || "server".equals(type.getType())) {
            //这些有设备编号。根据编号查询
            id_tour_code.setVisibility(View.VISIBLE);
            id_tour_name.setVisibility(View.GONE);
        } else {
            //剩余没有设备编号。更加名称查询
            id_tour_code.setVisibility(View.GONE);
            id_tour_name.setVisibility(View.VISIBLE);
        }
        presenter.setType(type.getType());
    }
}
