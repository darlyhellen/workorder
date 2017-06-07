package com.xiangxun.workorder.ui.presenter;

import android.content.Context;
import android.view.View;

import com.hellen.baseframe.application.FrameListener;
import com.hellen.baseframe.common.dlog.DLog;
import com.xiangxun.workorder.R;
import com.xiangxun.workorder.bean.ChildData;
import com.xiangxun.workorder.bean.EquipMenuChildData;
import com.xiangxun.workorder.bean.EquipMenuGroupData;
import com.xiangxun.workorder.bean.EquipmentRoot;
import com.xiangxun.workorder.bean.GroupData;
import com.xiangxun.workorder.bean.ObjectData;
import com.xiangxun.workorder.ui.EquipmentMenuAcitvity;
import com.xiangxun.workorder.ui.biz.EquipmentMenuListener;
import com.xiangxun.workorder.widget.loading.ShowLoading;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zhangyuhui/Darly on 2017/5/17.
 * Copyright by [Zhangyuhui/Darly]
 * ©2017 XunXiang.Company. All rights reserved.
 *
 * @TODO: 新增巡检工单解析类
 */
public class EquipmentMenuPresenter {

    private EquipmentMenuListener biz;

    private EquipmentMenuListener.EquipmentMenuInterface view;

    private ShowLoading loading;

    public EquipmentMenuPresenter(EquipmentMenuListener.EquipmentMenuInterface view) {
        this.view = view;
        this.biz = new EquipmentMenuListener();
        loading = new ShowLoading((EquipmentMenuAcitvity) view);
    }

    public List<EquipMenuGroupData> findData() {
        List<EquipMenuGroupData> groupDatas = new ArrayList<EquipMenuGroupData>();

        for (int i = 0; i < 2; i++) {
            EquipMenuGroupData groupData = new EquipMenuGroupData();
            if (i % 2 == 0) {
                groupData.setName("场外设备");
            } else {
                groupData.setName("场内设备");

            }
            groupDatas.add(groupData);
        }

        for (int i = 0; i < groupDatas.size(); i++) {
            List<EquipMenuChildData> list = new ArrayList<EquipMenuChildData>();
            if (i % 2 == 0) {
                for (int j = 0; j < 2 * i + 2; j++) {
                    EquipMenuChildData cd = null;
                    if (i == 1) {
                        cd = new EquipMenuChildData("卡口设备", "device", 0);
                        list.add(cd);
                        cd = new EquipMenuChildData("智能机柜", "icabinef", 0);
                        list.add(cd);
                    }
                }
            } else {
                for (int j = 0; j < 2 * i + 2; j++) {
                    EquipMenuChildData cd = null;
                    if (i == 1) {
                        cd = new EquipMenuChildData("服务器", "server", 0);
                        list.add(cd);
                        cd = new EquipMenuChildData("数据库", "database", 0);
                        list.add(cd);
                        cd = new EquipMenuChildData("平台信息", "project", 0);
                        list.add(cd);
                        cd = new EquipMenuChildData("FTP信息", "ftp", 0);
                        list.add(cd);
                    }
                }
            }
            groupDatas.get(i).setData(list);
        }
        return groupDatas;
    }


    public static ObjectData testData() {
        List<GroupData> groupDatas = new ArrayList<GroupData>();

        for (int i = 0; i < 2; i++) {
            GroupData groupData = new GroupData();
            if (i % 2 == 0) {
                groupData.setName("场外设备");


            } else {
                groupData.setName("场内设备");


            }
            groupDatas.add(groupData);
        }

        for (int i = 0; i < groupDatas.size(); i++) {
            List<ChildData> list = new ArrayList<>();
            if (i % 2 == 0) {
                for (int j = 0; j < 2 * i + 2; j++) {
                    ChildData cd = null;
                    if (i == 0) {
                        cd = new ChildData("null", "卡口设备", "");
                        cd.setData(getRoot(j, cd.getName()));
                        list.add(cd);
                        cd = new ChildData("null", "智能机柜", "");
                        cd.setData(getRoot(j, cd.getName()));
                        list.add(cd);
                        break;
                    } else {
                        cd = new ChildData(null, "其他设备" + j, "");
                        list.add(cd);
                    }
                }
            } else {
                for (int j = 0; j < 2 * i + 2; j++) {
                    ChildData cd = null;
                    if (i == 1) {
                        cd = new ChildData("null", "服务器", "");
                        cd.setData(getRoot(j, cd.getName()));
                        list.add(cd);
                        cd = new ChildData("null", "数据库", "");
                        cd.setData(getRoot(j, cd.getName()));
                        list.add(cd);
                        cd = new ChildData("null", "平台信息", "");
                        cd.setData(getRoot(j, cd.getName()));
                        list.add(cd);
                        cd = new ChildData("null", "FTP信息", "");
                        cd.setData(getRoot(j, cd.getName()));
                        list.add(cd);
                        break;
                    } else {
                        cd = new ChildData(null, "其他设备" + j, "");
                        list.add(cd);
                    }
                }
            }
            groupDatas.get(i).setData(list);
        }

        ObjectData data = new ObjectData();
        data.setData(groupDatas);
        data.setMessage("组合信息");
        data.setStatus(1);
        return data;
    }

    private static List<EquipmentRoot> getRoot(int j, String name) {
        List<EquipmentRoot> ka = new ArrayList<>();
        for (int k = 0; k < 2 * j + 2; k++) {
            EquipmentRoot root = new EquipmentRoot();
            root.setName(name + "编号:" + k);
            ka.add(root);
        }
        return ka;
    }


}
