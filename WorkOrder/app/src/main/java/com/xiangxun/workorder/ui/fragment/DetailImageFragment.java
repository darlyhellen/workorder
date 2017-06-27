package com.xiangxun.workorder.ui.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
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
import com.xiangxun.workorder.common.image.BitmapChangeUtil;
import com.xiangxun.workorder.ui.adapter.DetailImageFragmentAdapter;
import com.xiangxun.workorder.ui.biz.DetailImageFragmentListener.DetailImageFragmentInterface;
import com.xiangxun.workorder.ui.main.ShowImageViewActivity;
import com.xiangxun.workorder.ui.presenter.DetailImageFragmentPresenter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zhangyuhui/Darly on 2017/5/27.
 * Copyright by [Zhangyuhui/Darly]
 * ©2017 XunXiang.Company. All rights reserved.
 *
 * @TODO:展示服务端传递图片的位置
 */
public class DetailImageFragment extends Fragment implements DetailImageFragmentInterface {

    private View root;

    @ViewsBinder(R.id.id_detail_fragment_grid)
    private GridView gridView;
    @ViewsBinder(R.id.id_detail_fragment_text)
    private TextView text;
    private DetailImageFragmentAdapter adapter;
    private List<String> urls;

    private DetailImageFragmentPresenter presenter;

    private WorkOrderData data;
    private EquipmentInfo info;
    private TourInfo tour;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_detail_image, null);
        InitBinder.InitAll(this, root);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        initListener();
    }


    private void initView() {
        presenter = new DetailImageFragmentPresenter(this);
        data = getArguments().getParcelable("WorkOrderData");
        info = getArguments().getParcelable("EquipmentInfo");
        tour = getArguments().getParcelable("TourInfo");
        if (data != null) {
            urls = new ArrayList<String>();
            if (TextUtils.isEmpty(data.id)) {
                return;
            }
            File f = new File(AppEnum.IMAGE, data.id);
            if (f.exists()) {
                File[] lis = f.listFiles();
                for (int i = 0; i < lis.length; i++) {
                    urls.add(lis[i].getAbsolutePath());
                }
            } else {
                presenter.getData();
            }
            adapter = new DetailImageFragmentAdapter(urls, R.layout.item_fragment_detail_image, getActivity());
            gridView.setAdapter(adapter);
        } else if (info != null) {
            urls = new ArrayList<String>();
            if (TextUtils.isEmpty(info.id)) {
                return;
            }
            File f = new File(AppEnum.IMAGE, info.id);
            if (f.exists()) {
                File[] lis = f.listFiles();
                for (int i = 0; i < lis.length; i++) {
                    urls.add(lis[i].getAbsolutePath());
                }
            } else {
                presenter.getData();
            }
            adapter = new DetailImageFragmentAdapter(urls, R.layout.item_fragment_detail_image, getActivity());
            gridView.setAdapter(adapter);
        } else if (tour != null) {
            urls = new ArrayList<String>();
            if (TextUtils.isEmpty(tour.id)) {
                return;
            }
            File f = new File(AppEnum.IMAGE, tour.id);
            if (f.exists()) {
                File[] lis = f.listFiles();
                for (int i = 0; i < lis.length; i++) {
                    urls.add(lis[i].getAbsolutePath());
                }
            } else {
                presenter.getData();
            }
            adapter = new DetailImageFragmentAdapter(urls, R.layout.item_fragment_detail_image, getActivity());
            gridView.setAdapter(adapter);
        }

    }

    private void initListener() {
        gridView.setOnItemClickListener(new ItemClickListenter() {
            @Override
            public void NoDoubleItemClickListener(AdapterView<?> parent, View view, int position, long id) {
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
        });
    }

    @Override
    public void onLoginSuccess(List<String> data) {
        if (data == null) {
            return;
        }
        File id = new File(AppEnum.IMAGE, getDataID());
        if (!id.exists()) {
            id.mkdir();
        }
        for (String sr : data) {
            Bitmap bt = BitmapChangeUtil.convertStringToIcon(sr);
            if (bt != null) {
                //通过工单ID建立文件夹，保存下载下来的图片。
                File file = new File(AppEnum.IMAGE + getDataID(), "/" + System.currentTimeMillis() + ".png");
                try {
                    file.createNewFile();
                    FileOutputStream out = new FileOutputStream(file);
                    bt.compress(Bitmap.CompressFormat.PNG, 90, out);
                    bt.recycle();
                    urls.add(file.getPath());
                    out.flush();
                    out.close();
                } catch (FileNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        DLog.i(getClass().getSimpleName(), urls);
        text.setVisibility(View.GONE);
        adapter.setData(urls);
    }

    @Override
    public void onLoginFailed() {
        text.setVisibility(View.VISIBLE);
    }

    @Override
    public String getDataID() {
        if (data != null) {
            return data.id;
        } else if (info != null) {
            return info.id;
        } else if (tour != null) {
            return tour.id;
        } else {
            return "";
        }
    }

    @Override
    public void setDisableClick() {

    }

    @Override
    public void setEnableClick() {

    }
}
