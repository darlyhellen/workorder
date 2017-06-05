package com.xiangxun.workorder.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;

import com.hellen.baseframe.binder.ContentBinder;
import com.hellen.baseframe.binder.ViewsBinder;
import com.hellen.baseframe.common.dlog.DLog;
import com.xiangxun.workorder.R;
import com.xiangxun.workorder.base.AppEnum;
import com.xiangxun.workorder.base.BaseActivity;
import com.xiangxun.workorder.ui.adapter.MainDetailImageAdapter;
import com.xiangxun.workorder.ui.adapter.MainDetailImageAdapter.Callback;
import com.xiangxun.workorder.ui.biz.MainListener.MainInterface;
import com.xiangxun.workorder.ui.main.ShowImageViewActivity;
import com.xiangxun.workorder.ui.presenter.MainPresenter;
import com.xiangxun.workorder.widget.camera.PhotoPop;
import com.xiangxun.workorder.widget.grid.WholeGridView;
import com.xiangxun.workorder.widget.header.HeaderView;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Zhangyuhui/Darly on 2017/5/17.
 * Copyright by [Zhangyuhui/Darly]
 * ©2017 XunXiang.Company. All rights reserved.
 *
 * @TODO: 首页静态页面, 暂时没有接口网络请求。
 */
@ContentBinder(R.layout.activity_main)
public class MainActivity extends BaseActivity implements View.OnClickListener, MainInterface, OnItemClickListener, Callback {


    /**
     * 上午9:29:04 TODO 调出选项的POP窗口，主要为相机，相册，取消
     */

    private MainPresenter presenter;
    //首页参数集合
    @ViewsBinder(R.id.comment_title)
    private HeaderView title;


    @ViewsBinder(R.id.id_detail_fragment_age)
    private WholeGridView gridView;
    private List<String> data;
    private MainDetailImageAdapter adapter;


    /**
     * 上午9:29:04 TODO 调出选项的POP窗口，主要为相机，相册，取消
     */
    public PhotoPop pop;

    @Override
    protected void initView(Bundle savedInstanceState) {
        presenter = new MainPresenter(this);
        title.setTitle("測試頁面");
        title.setRightBackgroundResource(R.mipmap.set);
        pop = new PhotoPop(this);
        //添加图片的功能模块
//        imageData = new ArrayList<String>();
//        imageData.add("add");
//        adapter = new DetailOrderImageAdapter(imageData, R.layout.item_fragment_detail_image, this);
//        images.setAdapter(adapter);

        data = new ArrayList<String>();
        data.add("file:///android_asset/add.png");
        adapter = new MainDetailImageAdapter(data, R.layout.item_fragment_detail_image, this, this);
        gridView.setAdapter(adapter);
    }

    @Override
    protected void initListener() {
        title.setRightOnClickListener(this);
        gridView.setOnItemClickListener(this);
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

    //图片加载类
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String st = (String) parent.getItemAtPosition(position);
        if (position == (data.size() - 1)) {
            pop.show(view);
        } else {
            Intent intent = new Intent(this, ShowImageViewActivity.class);
            intent.putExtra("position", position);
            int[] location = new int[2];
            view.getLocationOnScreen(location);
            intent.putExtra("locationX", location[0]);//必须
            intent.putExtra("locationY", location[1]);//必须
            intent.putExtra("url", (String) parent.getItemAtPosition(position));
            intent.putExtra("width", view.getWidth());//必须
            intent.putExtra("height", view.getHeight());//必须
            startActivity(intent);
            overridePendingTransition(0, 0);
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

            this.data.add(this.data.size() - 1, head_path);
            //this.data.add(this.data.size() - 1, "http://img0.imgtn.bdimg.com/it/u=4195805644,827754888&fm=23&gp=0.jpg");
            adapter.notifyDataSetChanged();
            adapter.setData(this.data);
            //pop.cropPhoto(Uri.fromFile(temp));// 裁剪图片
            //这里不需要裁剪图片。
        }
    }

    @Override
    public void click(View v, int position) {
        data.remove(position);
        // gridView.setAdapter(new PhotoGridViewAdapter(this, listData, this));
        adapter.setData(data);
    }
}
