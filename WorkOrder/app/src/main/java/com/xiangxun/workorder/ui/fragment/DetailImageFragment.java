package com.xiangxun.workorder.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.hellen.baseframe.binder.InitBinder;
import com.hellen.baseframe.binder.ViewsBinder;
import com.xiangxun.workorder.R;
import com.xiangxun.workorder.bean.WorkOrderData;
import com.xiangxun.workorder.ui.adapter.DetailImageFragmentAdapter;
import com.xiangxun.workorder.ui.main.ShowImageViewActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zhangyuhui/Darly on 2017/5/27.
 * Copyright by [Zhangyuhui/Darly]
 * ©2017 XunXiang.Company. All rights reserved.
 *
 * @TODO:展示服务端传递图片的位置
 */
public class DetailImageFragment extends Fragment implements OnItemClickListener {

    private View root;

    @ViewsBinder(R.id.id_detail_fragment_grid)
    private GridView gridView;
    private WorkOrderData data;

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
        data = getArguments().getParcelable("data");
        if (data != null) {
            List<String> urls = new ArrayList<String>();
            if (TextUtils.isEmpty(data.photo1)) {
                urls.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1496297482491&di=7d96614c976b59569a4b415c5f118dad&imgtype=0&src=http%3A%2F%2Fpic.58pic.com%2F58pic%2F13%2F96%2F55%2F16n58PICUMI_1024.jpg");
            } else {
                urls.add(data.photo1);
            }
            if (TextUtils.isEmpty(data.photo2)) {
                urls.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1496297511916&di=6fbb9547469e3cd8b6e2c5671305022f&imgtype=jpg&src=http%3A%2F%2Fimg1.imgtn.bdimg.com%2Fit%2Fu%3D1533961963%2C371326385%26fm%3D214%26gp%3D0.jpg");
            } else {
                urls.add(data.photo2);
            }
            if (TextUtils.isEmpty(data.photo3)) {
                urls.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1496297482490&di=eb4b3affb728f76ca1e82e5b7331f222&imgtype=0&src=http%3A%2F%2Fkyys.zj.cn%2FDocuments%2F5e692ec5-ebef-440c-8575-c0e32a5ec7ca%2FInstruments%2F20140926_092222%25281%2529.jpg");
            } else {
                urls.add(data.photo3);
            }
            gridView.setAdapter(new DetailImageFragmentAdapter(urls, R.layout.item_fragment_detail_image, getActivity()));
        }
    }

    private void initListener() {
        gridView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

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
