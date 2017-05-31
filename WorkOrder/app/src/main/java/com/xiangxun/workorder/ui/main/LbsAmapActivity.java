package com.xiangxun.workorder.ui.main;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapException;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.MapsInitializer;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.offlinemap.OfflineMapManager;
import com.amap.api.maps.offlinemap.OfflineMapManager.OfflineMapDownloadListener;
import com.hellen.baseframe.binder.ContentBinder;
import com.hellen.baseframe.binder.ViewsBinder;
import com.hellen.baseframe.common.dlog.DLog;
import com.xiangxun.workorder.R;
import com.xiangxun.workorder.base.AppEnum;
import com.xiangxun.workorder.base.BaseActivity;
import com.xiangxun.workorder.bean.WorkOrderData;
import com.xiangxun.workorder.common.Aset;
import com.xiangxun.workorder.widget.header.HeaderView;

/**
 * Created by Zhangyuhui/Darly on 2017/5/22.
 * Copyright by [Zhangyuhui/Darly]
 * ©2017 XunXiang.Company. All rights reserved.
 *
 * @TODO: 高德地图测试页面
 */
@ContentBinder(R.layout.activity_lbs_amap_marker)
public class LbsAmapActivity extends BaseActivity implements View.OnClickListener, OfflineMapDownloadListener {
    @ViewsBinder(R.id.id_lbs_amap_title)
    private HeaderView header;
    @ViewsBinder(R.id.id_lbs_amap_map)
    private MapView mapView;
    private AMap aMap;
    private LatLng latlng;

    private WorkOrderData data;


    @Override
    protected void initView(Bundle savedInstanceState) {
        /* * 设置离线地图存储目录，在下载离线地图或初始化地图设置; 使用过程中可自行设置, 若自行设置了离线地图存储的路径，
         * 则需要在离线地图下载和使用地图页面都进行路径设置
		 */
        // Demo中为了其他界面可以使用下载的离线地图，使用默认位置存储，屏蔽了自定义设置


        MapsInitializer.sdcardDir = AppEnum.DOWNS;
        //复制文件
        Aset.copyAssets(this);
        mapView.onCreate(savedInstanceState); // 此方法必须重写
        header.setTitle(R.string.main_work_map);
        header.setLeftBackgroundResource(R.mipmap.ic_title_back);

        if (aMap == null) {
            aMap = mapView.getMap();
            aMap.setMapType(AMap.MAP_TYPE_NIGHT);
        }
        if (getIntent() != null) {
            data = (WorkOrderData) getIntent().getSerializableExtra("data");
            if (data == null) {
                data = new WorkOrderData();
                data.position = "ad";
            }
        }
        if (aMap != null) {
            addMarkersToMap();// 往地图上添加marker
        }

//
//        //构造OfflineMapManager对象
//        OfflineMapManager amapManager = new OfflineMapManager(this, this);
//        //按照citycode下载
//        try {
//            amapManager.downloadByCityName("西安市");
//        } catch (AMapException e) {
//            e.printStackTrace();
//        }
    }

    @Override
    protected void initListener() {
        header.setLeftBackOneListener(this);
    }

    /**
     * 在地图上添加marker
     */
    private void addMarkersToMap() {
        if (data != null) {
            if (!TextUtils.isEmpty(data.position)) {
                data.latitude = 34.164469;
                data.longitude = 108.951279;
                //108.951279,34.164469
                latlng = new LatLng(data.latitude, data.longitude);
                changeCamera(
                        CameraUpdateFactory.newCameraPosition(new CameraPosition(
                                latlng, 18, 30, 0)));
                aMap.clear();
                aMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory
                        .defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                        .position(latlng)
                        .draggable(true));
            } else {
                aMap.clear();
                aMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory
                        .defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                        .draggable(true));
            }
        } else {
            aMap.clear();
            aMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory
                    .defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                    .draggable(true));
        }
        DLog.i(latlng);
    }


    /**
     * 根据动画按钮状态，调用函数animateCamera或moveCamera来改变可视区域
     */
    private void changeCamera(CameraUpdate update) {
        aMap.moveCamera(update);
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }


    /**
     * 方法必须重写
     */
    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }


    /**
     * 方法必须重写
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_view_back_llayout:
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    public void onDownload(int i, int i1, String s) {
        DLog.i(getClass().getSimpleName(), "onDownload--" + i + "---" + i1 + "---" + s);
    }

    @Override
    public void onCheckUpdate(boolean b, String s) {

    }

    @Override
    public void onRemove(boolean b, String s, String s1) {

    }
}
