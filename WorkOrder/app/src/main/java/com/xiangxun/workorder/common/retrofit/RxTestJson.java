package com.xiangxun.workorder.common.retrofit;

import com.xiangxun.workorder.base.Api;
import com.xiangxun.workorder.bean.EquipmentInfo;
import com.xiangxun.workorder.bean.EquipmentRoot;
import com.xiangxun.workorder.bean.LoginData;
import com.xiangxun.workorder.bean.LoginRoot;
import com.xiangxun.workorder.bean.NewServiceRoot;
import com.xiangxun.workorder.bean.NotifactionData;
import com.xiangxun.workorder.bean.TourInfo;
import com.xiangxun.workorder.bean.TourRoot;
import com.xiangxun.workorder.bean.VersionData;
import com.xiangxun.workorder.bean.VersionRoot;
import com.xiangxun.workorder.bean.WorkOrderData;
import com.xiangxun.workorder.bean.WorkOrderRoot;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Darly on 2017/11/8.
 *
 * @TODO: 制造假数据
 */
public class RxTestJson {

    /**
     * @return 使用管理员账户进行登录，进行假数据测试。并且将在线修改为离线
     */
    public static LoginRoot login() {
        LoginRoot root = new LoginRoot();
        root.setMessage("登录成功");
        root.setStatus(1);
        LoginData data = new LoginData();
        data.setId("130521090208040c87f4ab27fd194da7");
        data.setName("管理员");
        data.setAccount(Api.ACCOUNT);
        data.setOrgName("交警支队");
        data.setMobile("18291422295");
        root.setData(data);
        return root;
    }


    /**
     * @return 主要是所有工单界面展示JSON
     */
    public static WorkOrderRoot workOrderRoot(String status) {
        WorkOrderRoot root = new WorkOrderRoot();
        root.setMessage("查询成功");
        root.setTotalSize(2);
        root.setTotalPage(1);
        root.setStatus(1);
        root.setPageNo(1);
        List<WorkOrderData> data = new ArrayList<WorkOrderData>();
        if ("0".equals(status)){
            //新工单
            WorkOrderData d1 = new WorkOrderData(
                    "22",
                    "0",
                    "2017-09-28 08:40:26",
                    "5.3.6.3",
                    "",
                    "213321",
                    "1",
                    "130521090208040c87f4ab27fd194da7",
                    "测试，你好：位于[22]的平台_资产设备发生故障。",
                    "170914093733367fc434",
                    "170531135954965ba567",
                    "",
                    "",
                    "", 3,
                    "中电",
                    "",
                    "超级管理员",
                    "2017-09-14 09:37:33",
                    "00",
                    "平台_资产",
                    "project",
                    "",
                    "",
                    "130521090208040c87f4ab27fd194da7",
                    "18606510280",
                    "",
                    "170913171321369fc09f",
                    "",
                    "0"
            );
            data.add(d1);
            WorkOrderData d2 = new WorkOrderData(
                    "22",
                    "0",
                    "2017-09-28 08:40:26",
                    "5.3.6.3",
                    "",
                    "213321",
                    "1",
                    "130521090208040c87f4ab27fd194da7",
                    "测试，你好：位于[22]的平台_资产设备发生故障。",
                    "170914093733367fc434",
                    "170531135954965ba567",
                    "",
                    "",
                    "",
                    3,
                    "中电",
                    "",
                    "超级管理员",
                    "2017-09-14 09:37:33",
                    "00",
                    "平台_资产",
                    "project",
                    "",
                    "",
                    "130521090208040c87f4ab27fd194da7",
                    "18606510280",
                    "",
                    "170913171321369fc09f",
                    "",
                    "0"
            );
            data.add(d2);
        }else if ("6".equals(status)){
            //完成的工单
            WorkOrderData d1 = new WorkOrderData(
                    "内场服务器",
                    "0",
                    "2017-09-28 08:40:22",
                    "1.2.4.4",
                    "upload\files\20170914\170914095223725f9b5bdcf6881f4df2.jpg",
                    "21321",
                    "1",
                    "130521090208040c87f4ab27fd194da7",
                    "测试，你好：位于[内场服务器]的测试，你好：位于[内场服务器]的ftp_资产设备发生故障。",
                    "1709140937334484a881",
                    "170531135954965ba567",
                    "",
                    "",
                    "90",
                    6,
                    "中电",
                    "1709140952197769bf10",
                    "超级管理员",
                    "2017-09-14 09:51:03",
                    "130521090208040c87f4ab27fd194da7",
                    "ftp_资产",
                    "ftp",
                    "upload\files\20170914\1709140952237273853205ee3eca890d.jpg",
                    "",
                    "130521090208040c87f4ab27fd194da7",
                    "18606510280",
                    "",
                    "17091317135429083052",
                    "",
                    "0"
            );
            data.add(d1);
            WorkOrderData d2 = new WorkOrderData(
                    "内场服务器",
                    "0",
                    "2017-09-28 08:40:22",
                    "1.2.4.4",
                    "upload\files\20170914\170914095223725f9b5bdcf6881f4df2.jpg",
                    "21321",
                    "1",
                    "130521090208040c87f4ab27fd194da7",
                    "测试，你好：位于[内场服务器]的测试，你好：位于[内场服务器]的ftp_资产设备发生故障。",
                    "1709140937334484a881",
                    "170531135954965ba567",
                    "",
                    "",
                    "90",
                    6,
                    "中电",
                    "1709140952197769bf10",
                    "超级管理员",
                    "2017-09-14 09:51:03",
                    "130521090208040c87f4ab27fd194da7",
                    "ftp_资产",
                    "ftp",
                    "upload\files\20170914\1709140952237273853205ee3eca890d.jpg",
                    "",
                    "130521090208040c87f4ab27fd194da7",
                    "18606510280",
                    "",
                    "17091317135429083052",
                    "",
                    "0"
            );
            data.add(d2);
        }else if ("".equals(status)){
            //请求全部的接口
            WorkOrderData d1 = new WorkOrderData(
                    "内场服务器",
                    "0",
                    "2017-09-28 08:40:22",
                    "1.2.4.4",
                    "upload\files\20170914\170914095223725f9b5bdcf6881f4df2.jpg",
                    "21321",
                    "1",
                    "130521090208040c87f4ab27fd194da7",
                    "测试，你好：位于[内场服务器]的测试，你好：位于[内场服务器]的ftp_资产设备发生故障。",
                    "1709140937334484a881",
                    "170531135954965ba567",
                    "",
                    "",
                    "90",
                    6,
                    "中电",
                    "1709140952197769bf10",
                    "超级管理员",
                    "2017-09-14 09:51:03",
                    "130521090208040c87f4ab27fd194da7",
                    "ftp_资产",
                    "ftp",
                    "upload\files\20170914\1709140952237273853205ee3eca890d.jpg",
                    "",
                    "130521090208040c87f4ab27fd194da7",
                    "18606510280",
                    "",
                    "17091317135429083052",
                    "",
                    "0"
            );
            data.add(d1);
            WorkOrderData d2 = new WorkOrderData(
                    "内场服务器",
                    "0",
                    "2017-09-28 08:40:22",
                    "1.2.4.4",
                    "upload\files\20170914\170914095223725f9b5bdcf6881f4df2.jpg",
                    "21321",
                    "1",
                    "130521090208040c87f4ab27fd194da7",
                    "测试，你好：位于[内场服务器]的测试，你好：位于[内场服务器]的ftp_资产设备发生故障。",
                    "1709140937334484a881",
                    "170531135954965ba567",
                    "",
                    "",
                    "90",
                    6,
                    "中电",
                    "1709140952197769bf10",
                    "超级管理员",
                    "2017-09-14 09:51:03",
                    "130521090208040c87f4ab27fd194da7",
                    "ftp_资产",
                    "ftp",
                    "upload\files\20170914\1709140952237273853205ee3eca890d.jpg",
                    "",
                    "130521090208040c87f4ab27fd194da7",
                    "18606510280",
                    "",
                    "17091317135429083052",
                    "",
                    "0"
            );
            data.add(d2);
        }else if ("-6".equals(status)){
            //未完成的工单
            WorkOrderData d1 = new WorkOrderData(
                    "211国道552公里 400米土桥街十字",
                    "0",
                    "",
                    "12.12.12.12",
                    "",
                    "",
                    "0",
                    "130521090208040c87f4ab27fd194da7",
                    "西安翔迅科技有限责任公司的张三，你好：位于[211国道552公里 400米土桥街十字]上的IP为[12.12.12.12]的设备[45]发生故障，请及时维修。",
                    "171108113250974d3b07",
                    "160321194637834dc78d",
                    "",
                    "1711071500348686",
                    "",
                    1,
                    "西安翔迅科技有限责任公司",
                    "",
                    "",
                    "2017-11-08 11:32:50",
                    "130521090208040c87f4ab27fd194da7",
                    "45",
                    "cabinet",
                    "",
                    "",
                    "",
                    "13555555555",
                    "",
                    "17110715012073601850",
                    "",
                    "0"
            );
            data.add(d1);
            WorkOrderData d2 = new WorkOrderData(
                    "22",
                    "0",
                    "2017-09-28 08:40:26",
                    "5.3.6.3",
                    "",
                    "213321",
                    "1",
                    "130521090208040c87f4ab27fd194da7",
                    "测试，你好：位于[22]的平台_资产设备发生故障。",
                    "170914093733367fc434",
                    "170531135954965ba567",
                    "",
                    "",
                    "",
                    1,
                    "中电",
                    "",
                    "超级管理员",
                    "2017-09-14 09:37:33",
                    "00",
                    "平台_资产",
                    "project",
                    "",
                    "",
                    "130521090208040c87f4ab27fd194da7",
                    "18606510280",
                    "",
                    "170913171321369fc09f",
                    "",
                    "0"
            );
            data.add(d2);
        }else if ("10000".equals(status)){
            //通知公告
            WorkOrderData d1 = new WorkOrderData(
            );
            data.add(d1);
            WorkOrderData d2 = new WorkOrderData(
            );
            data.add(d2);
        }
        root.setData(data);
        return root;
    }

    /**
     * @return 设备管理模块，每个模块2个设备
     */
    public static NewServiceRoot workOrderNew() {
        NewServiceRoot ro = new NewServiceRoot();
        ro.setMessage("查询成功");
        ro.setStatus(1);
        ro.setData(2);
        return ro;
    }
    /**
     * @return 工单页面，展示两个数据
     */
    public static EquipmentRoot equipment(String type) {
        EquipmentRoot root = new EquipmentRoot();
        root.setMessage("查询成功");
        root.setTotalSize(2);
        root.setTotalPage(1);
        root.setStatus(1);
        root.setPageNo(1);
        List<EquipmentInfo> data = new ArrayList<EquipmentInfo>();
        if ("device".equals(type)) {
            //卡口设备
            EquipmentInfo e1 = new EquipmentInfo("170531135954965ba567",
                    "0",
                    "1",
                    "2017-06-22 09:23:59",
                    "610000000001041006",
                    "170531135954965ba567",
                    "2017-06-22 00:00:00",
                    "193.169.100.6",
                    "1710260948503695ebe8",
                    "171026094850369f6d6d",
                    "2018-06-22 00:00:00",
                    "卡口6",
                    "3",
                    "001",
                    "1710111556479558",
                    "device",
                    "卡口6",
                    0);
            data.add(e1);
            EquipmentInfo e2 = new EquipmentInfo(
                    "160321194637834dc78d",
                    "0",
                    "1",
                    "2017-11-07 14:00:51",
                    "111000",
                    "160321194637834dc78d",
                    "2017-11-07 00:00:00",
                    "193.169.100.247",
                    "171107140145018c7364",
                    "1711071401451302bee0",
                    "2018-11-07 00:00:00",
                    "208省道22km南方向",
                    "3",
                    "001",
                    "1711071400517351",
                    "device",
                    "测试出库卡口",
                    0);
            data.add(e2);
        } else if ("cabinet".equals(type)) {
            //智能机柜
            EquipmentInfo e1 = new EquipmentInfo(
                    "160321194637834dc78d",
                    "0",
                    "3",
                    "2017-11-07 15:52:18",
                    "321",
                    "160321194637834dc78d",
                    "2017-11-07 00:00:00",
                    "1.2.4.6",
                    "17110715530014131103",
                    "1711071553001467b222",
                    "2017-11-07 00:00:00",
                    "208省道19km北方向",
                    "123",
                    "0",
                    "001",
                    "DS-TP3200-EC",
                    "1711071552182660",
                    128,
                    "cabinet",
                    "cs1"
            );
            data.add(e1);
            EquipmentInfo e2 = new EquipmentInfo(
                    "170531135954965ba567",
                    "0",
                    "1",
                    "2017-11-01 13:53:29",
                    "123213",
                    "170531135954965ba567",
                    "2017-11-01 00:00:00",
                    "193.169.100.199",
                    "171101135349205c732f",
                    "17110113534921098372",
                    "2017-11-01 00:00:00",
                    "208省道19km北方向",
                    "123213",
                    "0",
                    "001",
                    "DS-TP3200-EC",
                    "1711011353293712",
                    0,
                    "cabinet",
                    "测试机柜"
            );
            data.add(e2);
        } else if ("server".equals(type)) {
            //服务器
            EquipmentInfo e1 = new EquipmentInfo(
                    "170531135954965ba567",
                    "0",
                    "0",
                    "2017-11-02 10:48:04",
                    "2017-11-02 00:00:00",
                    "132.12.12.12",
                    "17110210483829431bed",
                    "17110210483830005d22",
                    "2017-11-02 00:00:00",
                    "0",
                    "内场",
                    "0",
                    "001",
                    "1711021048041612",
                    0,
                    "server",
                    "343"
            );
            data.add(e1);
            EquipmentInfo e2 = new EquipmentInfo(
                    "170531135954965ba567",
                    "0",
                    "0",
                    "2017-11-01 14:17:41",
                    "2017-11-01 00:00:00",
                    "193.169.100.153",
                    "17110114182724106c8c",
                    "171101141830310c1180",
                    "2017-11-01 00:00:00",
                    "0",
                    "内场",
                    "0",
                    "001",
                    "1711011417412991",
                    0,
                    "server",
                    "测试服务器"
            );
            data.add(e2);
        } else if ("database".equals(type)) {
            //数据库
            EquipmentInfo e1 = new EquipmentInfo(
                    "160321194637834dc78d",
                    "0",
                    "1",
                    "2017-11-07 16:39:17",
                    "2",
                    "160321194637834dc78d",
                    "2017-11-07 00:00:00",
                    "193.169.100.250",
                    "171107163952805d8353",
                    "171107163952810e2513",
                    "2017-11-07 00:00:00",
                    "0",
                    "2017-11-08 10:08:20",
                    "3",
                    "1",
                    "1",
                    "001",
                    "1711071639173657",
                    0,
                    "database",
                    "测试"
            );
            data.add(e1);
            EquipmentInfo e2 = new EquipmentInfo(
                    "170531135954965ba567",
                    "0",
                    "3",
                    "2017-11-01 14:11:38",
                    "123213",
                    "170531135954965ba567",
                    "2017-11-01 00:00:00",
                    "193.169.100.199",
                    "17110114120792555712",
                    "171101141207928e1f92",
                    "2017-11-01 00:00:00",
                    "0",
                    "2017-11-08 10:08:20",
                    "123213",
                    "123123",
                    "3",
                    "001",
                    "1711011411382090",
                    0,
                    "database",
                    "测试数据库"
            );
            data.add(e2);
        } else if ("project".equals(type)) {
            //平台信息
            EquipmentInfo e1 = new EquipmentInfo(
                    "17071014535187722c6f",
                    "0",
                    "3",
                    "2017-11-07 14:35:36",
                    "2",
                    "17071014535187722c6f",
                    "2017-11-07 00:00:00",
                    "193.169.100.123",
                    "171107143558630ea3f4",
                    "17110714355863401268",
                    "2017-11-07 00:00:00",
                    "0",
                    "2017-11-08 10:13:27",
                    "3",
                    "1",
                    "3",
                    "001",
                    "1711071435364851",
                    0,
                    "project",
                    "测试平台"
            );
            data.add(e1);
            EquipmentInfo e2 = new EquipmentInfo(
                    "170531135954965ba567",
                    "0",
                    "1",
                    "2017-11-01 14:09:55",
                    "123213",
                    "170531135954965ba567",
                    "2017-11-01 00:00:00",
                    "193.169.100.199",
                    "171101141012765d34ad",
                    "171101141012767bbe31",
                    "2017-11-01 00:00:00",
                    "0",
                    "2017-11-08 10:13:27",
                    "123213",
                    "123123",
                    "3",
                    "001",
                    "171101140955616",
                    0,
                    "project",
                    "测试平台"
            );
            data.add(e2);
        } else if ("ftp".equals(type)) {
            //FTP信息
            EquipmentInfo e1 = new EquipmentInfo(
                    "17071014535187722c6f",
                    "0",
                    "1",
                    "2017-11-07 15:39:05",
                    "17071014535187722c6f",
                    "2017-11-07 00:00:00",
                    "193.169.100.203",
                    "171107153930187f6229",
                    "171107153930192dd143",
                    "2017-11-07 00:00:00",
                    "0",
                    "2017-11-08 10:17:39",
                    "内场服务器",
                    "1",
                    "001",
                    "171107153905357",
                    0,
                    "ftp",
                    "测试ftp服务"
            );
            data.add(e1);
            EquipmentInfo e2 = new EquipmentInfo(
                    "170531135954965ba567",
                    "0",
                    "1",
                    "2017-11-01 14:12:21",
                    "170531135954965ba567",
                    "2017-11-01 00:00:00",
                    "193.169.100.199",
                    "1711011412391406a7fe",
                    "171101141239145daaf5",
                    "2017-11-01 00:00:00",
                    "0",
                    "2017-11-08 10:17:39",
                    "内场服务器",
                    "3",
                    "001",
                    "1711011412215205",
                    0,
                    "ftp",
                    "测试ftp"
            );
            data.add(e2);
        } else {

        }
        root.setData(data);
        return root;
    }
    /**
     * @return 巡检管理页面，展示两个数据
     */
    public static TourRoot tourRoot() {
        TourRoot root = new TourRoot();
        root.setMessage("查询成功");
        root.setTotalSize(2);
        root.setTotalPage(1);
        root.setStatus(1);
        root.setPageNo(1);
        List<TourInfo> data = new ArrayList<TourInfo>();
        TourInfo i1= new TourInfo(       "2017-11-08 08:35:42",
                "1711080839156172831283d7271c55d5",
                "巡检设备，设备出现故障",
                "2017-06-22 09:23:00",
                "测试卡口导入2",
                "管理员",
                "130521090208040c87f4ab27fd194da7",
                "测试卡口导入2",
                "device",
                "193.169.99.1",
                "18291422295",
                "1711071409500774d277");
        data.add(i1);
        TourInfo i2= new TourInfo( "2017-11-08 09:09:13",
                "171108091246364bac206877f9839d52",
                "巡检数据库设备，数据库设备出现故障",
                "2017-11-07 16:39:17",
                "3",
                "管理员",
                "130521090208040c87f4ab27fd194da7",
                "测试",
                "database",
                "193.169.100.250",
                "18291422295",
                "171107163952805d8353");
        data.add(i2);
        root.setData(data);
        return root;
    }
    /**
     * @return 版本更新接口
     */
    public static VersionRoot versionRoot() {

        VersionRoot root = new VersionRoot();
        root.setStatus(1);
        root.setMessage("返回成功");
        VersionData data = new VersionData();
        data.setSaveUrl("http://193.169.100.153:8090/ywt20170705am0856-dev-1.0.apk");
        root.setData(data);
        return root;
    }

    public static List<NotifactionData> notificationData() {
        List<NotifactionData> data = new ArrayList<NotifactionData>();
        data.add(new NotifactionData("1711080839156172831283d7271c55d5","系统通知","根据院领导确认，将图书馆修建工作交由计算机科学与技术研究院。"));
        data.add(new NotifactionData("1711080839156172831283d7271c5445","安全通知","根据院系通知，今年十一放假时间延长到11月。"));
        return data;
    }
}
