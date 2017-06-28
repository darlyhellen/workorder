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
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.hellen.baseframe.binder.InitBinder;
import com.hellen.baseframe.binder.ViewsBinder;
import com.hellen.baseframe.common.dlog.DLog;
import com.xiangxun.workorder.R;
import com.xiangxun.workorder.base.AppEnum;
import com.xiangxun.workorder.base.ItemClickListenter;
import com.xiangxun.workorder.bean.EquipmentInfo;
import com.xiangxun.workorder.bean.TourInfo;
import com.xiangxun.workorder.bean.WorkOrderData;
import com.xiangxun.workorder.common.WorkOrderUtils;
import com.xiangxun.workorder.ui.adapter.DetailOrderImageAdapter;
import com.xiangxun.workorder.ui.adapter.DetailOrderImageAdapter.OnDetailOrderConsListener;
import com.xiangxun.workorder.ui.biz.DetailOrderFragmentListener.DetailOrderFragmentInterface;
import com.xiangxun.workorder.ui.main.ShowImageViewActivity;
import com.xiangxun.workorder.ui.presenter.DetailOrderFragmentPresenter;
import com.xiangxun.workorder.widget.camera.PhotoPop;
import com.xiangxun.workorder.widget.grid.DetailView;
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
public class DetailOrderFragment extends Fragment implements OnClickListener, DetailOrderFragmentInterface, OnDetailOrderConsListener {
    private View root;


    @ViewsBinder(R.id.id_detail_tv_title)
    private TextView tv_title;//展示名称。
    @ViewsBinder(R.id.id_detail_tv_linear)
    private LinearLayout tv_linear;//展示名称。

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
    @ViewsBinder(R.id.tv_declare)
    private EditText reason;   //
    @ViewsBinder(R.id.tv_declare_title)
    private TextView reason_title;   //
    @ViewsBinder(R.id.id_detail_fragment_button)
    private LinearLayout button;   //
    @ViewsBinder(R.id.id_detail_fragment_config)
    private Button commit;   //
    @ViewsBinder(R.id.id_detail_fragment_consel)
    private Button consel;   //
    @ViewsBinder(R.id.id_detail_fragment_inimage)
    private WholeGridView images;
    @ViewsBinder(R.id.id_detail_radio_group)
    private RadioGroup group;//二选一列表
    @ViewsBinder(R.id.id_detail_radio_down)
    private RadioButton down;//用户处理完成选择
    @ViewsBinder(R.id.id_detail_radio_undown)
    private RadioButton undown;//用户未处理完成选择


    private List<String> imageData;
    private DetailOrderImageAdapter adapter;

    /**
     * 上午9:29:04 TODO 调出选项的POP窗口，主要为相机，相册，取消
     */
    public PhotoPop pop;
    private DetailOrderFragmentPresenter presenter;

    private WorkOrderData data;
    private EquipmentInfo info;
    private TourInfo tour;

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
        data = (WorkOrderData) getArguments().getSerializable("WorkOrderData");
        info = (EquipmentInfo) getArguments().getSerializable("EquipmentInfo");
        tour = (TourInfo) getArguments().getSerializable("TourInfo");
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
                reason_title.setText(R.string.st_detail_declare_t1);
                group.setVisibility(View.GONE);
                commit.setText("接收工单");
                consel.setText("退回工单");
                break;
            case 1:
                //已接收的状态(异常上报,正常上报)
                button.setVisibility(View.VISIBLE);
                except.setVisibility(View.GONE);
                close.setVisibility(View.GONE);
                images.setVisibility(View.VISIBLE);
                group.setVisibility(View.VISIBLE);
                group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        switch (checkedId) {
                            case R.id.id_detail_radio_down:
                                reason_title.setText(R.string.st_detail_declare_t2);
                                commit.setText("正常上报");
                                commit.setVisibility(View.VISIBLE);
                                consel.setVisibility(View.GONE);
                                break;
                            case R.id.id_detail_radio_undown:
                                reason_title.setText(R.string.st_detail_declare_t3);
                                consel.setText("异常上报");
                                commit.setVisibility(View.GONE);
                                consel.setVisibility(View.VISIBLE);
                                break;
                        }
                    }
                });
                down.setChecked(true);
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
        DetailView id = new DetailView(getActivity());
        id.setNameValue(R.string.st_detail_orderid, data.id);
        tv_linear.addView(id);

        DetailView devicename = new DetailView(getActivity());
        devicename.setNameValue(R.string.st_detail_complainant, data.devicename);
        tv_linear.addView(devicename);

        DetailView devicecode = new DetailView(getActivity());
        devicecode.setNameValue(R.string.st_detail_complainant_time, data.devicecode);
        tv_linear.addView(devicecode);

        DetailView deviceip = new DetailView(getActivity());
        deviceip.setNameValue(R.string.st_detail_complainant_tel, data.deviceip);
        tv_linear.addView(deviceip);


        DetailView devicetype = new DetailView(getActivity());
        if ("device".equals(data.devicetype)) {
            devicetype.setNameValue(R.string.st_detail_come, "卡口");
        } else if ("ftp".equals(data.devicetype)) {
            devicetype.setNameValue(R.string.st_detail_come, "FTP");
        } else if ("project".equals(data.devicetype)) {
            devicetype.setNameValue(R.string.st_detail_come, "平台");
        } else if ("database".equals(data.devicetype)) {
            devicetype.setNameValue(R.string.st_detail_come, "数据库");
        } else {
            devicetype.setNameValue(R.string.st_detail_come, "机柜");
        }
        tv_linear.addView(devicetype);

        DetailView position = new DetailView(getActivity());
        position.setNameValue(R.string.st_detail_type, data.position);
        tv_linear.addView(position);

        DetailView orgname = new DetailView(getActivity());
        orgname.setNameValue(R.string.st_detail_inout, data.orgname);
        tv_linear.addView(orgname);

        DetailView contactname = new DetailView(getActivity());
        contactname.setNameValue(R.string.st_detail_company, data.contactname);
        tv_linear.addView(contactname);

        DetailView assigntime = new DetailView(getActivity());
        assigntime.setNameValue(R.string.st_detail_worktime, data.assigntime);
        tv_linear.addView(assigntime);


        DetailView de = new DetailView(getActivity());
        de.setName(R.string.st_detail_postion);
        WorkOrderUtils.findStatus(data.status, de.getValue());
        tv_linear.addView(de);

        DetailView isouter = new DetailView(getActivity());
        if (0 == data.isouter) {
            isouter.setNameValue(R.string.st_detail_ordertype, "否");
        } else {
            isouter.setNameValue(R.string.st_detail_ordertype, "是");
        }
        tv_linear.addView(isouter);
        DetailView isreassign = new DetailView(getActivity());
        if ("0".equals(data.isreassign)) {
            isreassign.setNameValue(R.string.st_detail_order_content, "否");
        } else {
            isreassign.setNameValue(R.string.st_detail_order_content, "是");
        }
        tv_linear.addView(isreassign);
        DetailView isleave = new DetailView(getActivity());
        if ("0".equals(data.isleave)) {
            isleave.setNameValue(R.string.st_detail_request, "否");
        } else {
            isleave.setNameValue(R.string.st_detail_request, "是");
        }
        tv_linear.addView(isleave);
        DetailView messages = new DetailView(getActivity());
        messages.setNameValue(R.string.st_detail_declare, data.messages);
        tv_linear.addView(messages);

        DetailView backreas = new DetailView(getActivity());
        backreas.setNameValue(R.string.st_detail_backreas, data.reason);
        tv_linear.addView(backreas);

        //异常状态
        tvContent15.setText(data.exceptionid);
        tvContent16.setText("");
        tvContent17.setText("");
        // 关闭情况
        tvContent18.setText(data.offaccount);
        tvContent19.setText(data.offtime);
        tvContent20.setText("");
        //添加图片的功能模块
        imageData = new ArrayList<String>();
        imageData.add("添加图片");
        adapter = new DetailOrderImageAdapter(imageData, R.layout.item_main_detail_image_adapter, getActivity(), this);
        images.setAdapter(adapter);
    }

    //设备信息
    private void hasInfo() {
        tv_title.setText(R.string.st_detail_equip);
        images.setVisibility(View.GONE);
        button.setVisibility(View.GONE);
        except.setVisibility(View.GONE);
        close.setVisibility(View.GONE);


        DetailView deviceid = new DetailView(getActivity());
        deviceid.setNameValue(R.string.st_equip_deviceid, info.deviceid);
        tv_linear.addView(deviceid);
        DetailView assetname = new DetailView(getActivity());
        assetname.setNameValue(R.string.st_equip_assetname, info.assetname);
        tv_linear.addView(assetname);
        DetailView assetcode = new DetailView(getActivity());
        assetcode.setNameValue(R.string.st_equip_assetcode, info.code);
        tv_linear.addView(assetcode);

        DetailView assetmodel = new DetailView(getActivity());
        assetmodel.setNameValue(R.string.st_equip_assetmodel, info.assetmodel);
        tv_linear.addView(assetmodel);

        DetailView assettype = new DetailView(getActivity());
        if ("device".equals(info.assettype)) {
            assettype.setNameValue(R.string.st_equip_assettype, "卡口");
        } else if ("ftp".equals(info.assettype)) {
            assettype.setNameValue(R.string.st_equip_assettype, "FTP");
        } else if ("project".equals(info.assettype)) {
            assettype.setNameValue(R.string.st_equip_assettype, "平台");
        } else if ("database".equals(info.assettype)) {
            assettype.setNameValue(R.string.st_equip_assettype, "数据库");
        } else if ("server".equals(info.assettype)) {
            assettype.setNameValue(R.string.st_equip_assettype, "服务器");
        } else {
            assettype.setNameValue(R.string.st_equip_assettype, "机柜");
        }
        tv_linear.addView(assettype);

        DetailView manufacturer = new DetailView(getActivity());
        manufacturer.setNameValue(R.string.st_equip_manufacturer, info.manufacturer);
        tv_linear.addView(manufacturer);

        DetailView factoryId = new DetailView(getActivity());
        factoryId.setNameValue(R.string.st_equip_factoryId, info.factoryId);
        tv_linear.addView(factoryId);
        DetailView purchasetime = new DetailView(getActivity());
        purchasetime.setNameValue(R.string.st_equip_purchasetime, info.purchasetime);
        tv_linear.addView(purchasetime);
        DetailView engineering = new DetailView(getActivity());
        engineering.setNameValue(R.string.st_equip_engineering, info.engineering);
        tv_linear.addView(engineering);
        DetailView installtime = new DetailView(getActivity());
        installtime.setNameValue(R.string.st_equip_installtime, info.installtime);
        tv_linear.addView(installtime);
        DetailView installplace = new DetailView(getActivity());
        installplace.setNameValue(R.string.st_equip_installplace, info.installplace);
        tv_linear.addView(installplace);
        DetailView serviceid = new DetailView(getActivity());
        serviceid.setNameValue(R.string.st_equip_serviceid, info.serviceid);
        tv_linear.addView(serviceid);

    }

    private void hasTour() {
        tv_title.setText(R.string.st_detail_tour);
        images.setVisibility(View.GONE);
        button.setVisibility(View.GONE);
        except.setVisibility(View.GONE);
        close.setVisibility(View.GONE);
        DetailView id = new DetailView(getActivity());
        id.setNameValue(R.string.st_tour_id, tour.id);
        tv_linear.addView(id);

        DetailView ip = new DetailView(getActivity());
        ip.setNameValue(R.string.st_detail_complainant_tel, tour.ip);
        tv_linear.addView(ip);
        DetailView installplace = new DetailView(getActivity());
        installplace.setNameValue(R.string.st_detail_type, tour.installplace);
        tv_linear.addView(installplace);
        DetailView orgnames = new DetailView(getActivity());
        orgnames.setNameValue(R.string.st_detail_inout, tour.orgname);
        tv_linear.addView(orgnames);
        DetailView assetname = new DetailView(getActivity());
        assetname.setNameValue(R.string.st_detail_complainant, tour.devicename);
        tv_linear.addView(assetname);
        DetailView code = new DetailView(getActivity());
        code.setNameValue(R.string.st_tour_code, tour.code);
        tv_linear.addView(code);

        DetailView Checkingpeople = new DetailView(getActivity());
        Checkingpeople.setNameValue(R.string.st_tour_Checkingpeople, tour.name);
        tv_linear.addView(Checkingpeople);


        DetailView time = new DetailView(getActivity());
        time.setNameValue(R.string.st_tour_time, tour.inserttime);
        tv_linear.addView(time);

        DetailView reason = new DetailView(getActivity());
        reason.setNameValue(R.string.st_tour_reason, tour.reason);
        tv_linear.addView(reason);

        DetailView note = new DetailView(getActivity());
        note.setNameValue(R.string.st_tour_note, tour.note);
        tv_linear.addView(note);

        DetailView orgname = new DetailView(getActivity());
        orgname.setNameValue(R.string.st_tour_Checkingpeople, tour.Checkingpeople);
        tv_linear.addView(orgname);


    }


    private void initListener() {
        commit.setOnClickListener(this);
        consel.setOnClickListener(this);
        images.setOnItemClickListener(new ItemClickListenter() {
            @Override
            public void NoDoubleItemClickListener(AdapterView<?> parent, View view, int position, long id) {
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
        });
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
