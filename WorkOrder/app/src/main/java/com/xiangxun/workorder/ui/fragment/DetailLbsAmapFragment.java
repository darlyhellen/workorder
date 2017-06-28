package com.xiangxun.workorder.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.MapsInitializer;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MarkerOptions;
import com.hellen.baseframe.binder.InitBinder;
import com.hellen.baseframe.binder.ViewsBinder;
import com.hellen.baseframe.common.dlog.DLog;
import com.xiangxun.workorder.R;
import com.xiangxun.workorder.base.AppEnum;
import com.xiangxun.workorder.bean.EquipmentInfo;
import com.xiangxun.workorder.bean.TourInfo;
import com.xiangxun.workorder.bean.WorkOrderData;
import com.xiangxun.workorder.common.Aset;

/**
 * Created by Zhangyuhui/Darly on 2017/5/27.
 * Copyright by [Zhangyuhui/Darly]
 * ©2017 XunXiang.Company. All rights reserved.
 *
 * @TODO:传递位置，标记位置的页面
 */
public class DetailLbsAmapFragment extends Fragment {
    private View root;

    @ViewsBinder(R.id.id_lbs_amap_map)
    private MapView mapView;
    private AMap aMap;
    private LatLng latlng;

    private WorkOrderData data;
    private EquipmentInfo info;
    private TourInfo tour;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_detail_lbs, null);
        InitBinder.InitAll(this, root);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
         /* * 设置离线地图存储目录，在下载离线地图或初始化地图设置; 使用过程中可自行设置, 若自行设置了离线地图存储的路径，
         * 则需要在离线地图下载和使用地图页面都进行路径设置
		 */
        // Demo中为了其他界面可以使用下载的离线地图，使用默认位置存储，屏蔽了自定义设置


        MapsInitializer.sdcardDir = AppEnum.DOWNS;
        //复制文件
        Aset.copyAssets(getActivity());
        mapView.onCreate(savedInstanceState); // 此方法必须重写
        initView();
        initListener();
    }


    private void initView() {


        if (aMap == null) {
            aMap = mapView.getMap();
            aMap.setMapType(AMap.MAP_TYPE_NORMAL);
        }
        data = (WorkOrderData) getArguments().getSerializable("WorkOrderData");
        info = (EquipmentInfo) getArguments().getSerializable("EquipmentInfo");
        tour = (TourInfo) getArguments().getSerializable("TourInfo");
        if (AppEnum.TEST) {
            //单独的测试数据,假数据.
            data = AppEnum.getData();
        }
        if (aMap != null) {
            addMarkersToMap();// 往地图上添加marker
        }
    }

    private void initListener() {

    }


    /**
     * 在地图上添加marker
     */
    private void addMarkersToMap() {
        if (data != null) {
            if (AppEnum.TEST) {
                data.mapx = "34.164469";
                data.mapy = "108.951279";
            }
            if (TextUtils.isEmpty(data.mapx) || TextUtils.isEmpty(data.mapy)) {
                //108.951279,34.164469
                data.mapx = "34.164469";
                data.mapy = "108.951279";
            }
            latlng = new LatLng(Double.parseDouble(data.mapx), Double.parseDouble(data.mapy));
            //这里进行视角，等参数调整。0度就是平面图
            changeCamera(
                    CameraUpdateFactory.newCameraPosition(new CameraPosition(
                            latlng, 14, 0, 0)));
            aMap.clear();
            aMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory
                    .defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                    .position(latlng)
                    .draggable(true));
        } else if (info != null) {
            if (TextUtils.isEmpty(info.mapx) || TextUtils.isEmpty(info.mapy)) {
                //108.951279,34.164469
                info.mapx = "34.164469";
                info.mapy = "108.951279";
            }
            latlng = new LatLng(Double.parseDouble(info.mapx), Double.parseDouble(info.mapy));
            //这里进行视角，等参数调整。0度就是平面图
            changeCamera(
                    CameraUpdateFactory.newCameraPosition(new CameraPosition(
                            latlng, 14, 0, 0)));
            aMap.clear();
            aMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory
                    .defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                    .position(latlng)
                    .draggable(true));
        } else if (tour != null) {
            if (TextUtils.isEmpty(tour.mapx) || TextUtils.isEmpty(tour.mapy)) {
                //108.951279,34.164469
                tour.mapx = "34.164469";
                tour.mapy = "108.951279";
            }
            latlng = new LatLng(Double.parseDouble(tour.mapx), Double.parseDouble(tour.mapy));
            //这里进行视角，等参数调整。0度就是平面图
            changeCamera(
                    CameraUpdateFactory.newCameraPosition(new CameraPosition(
                            latlng, 14, 0, 0)));
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
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }


    /**
     * 方法必须重写
     */
    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    /**
     * 方法必须重写
     */
    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }


    /**
     * 方法必须重写
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }
}
