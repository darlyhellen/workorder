package com.xiangxun.workorder.ui;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ExpandableListView;

import com.hellen.baseframe.binder.ContentBinder;
import com.hellen.baseframe.binder.ViewsBinder;
import com.hellen.baseframe.common.dlog.DLog;
import com.xiangxun.workorder.R;
import com.xiangxun.workorder.base.BaseActivity;
import com.xiangxun.workorder.bean.ChildData;
import com.xiangxun.workorder.bean.GroupData;
import com.xiangxun.workorder.ui.adapter.ExpandableListViewAdapter;
import com.xiangxun.workorder.widget.header.HeaderView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/6/5.
 * @TODO: 設備管理功能頁面，包括幾大點。下拉列表。點擊進設備列表。然後點擊進詳情。
 */
@ContentBinder(R.layout.activity_equipment)
public class EquipmentMenuAcitvity extends BaseActivity implements OnClickListener {
    @ViewsBinder(R.id.id_equipment_header)
    private HeaderView header;
    @ViewsBinder(R.id.id_equipment_listview)
    private ExpandableListView listView;
    private ExpandableListViewAdapter adapter;



    private List<GroupData> groupList;
    private List<List<ChildData>> childList;

    private String[] url;
    @Override
    protected void initView(Bundle savedInstanceState) {

        header.setTitle("设备列表");
        header.setLeftBackgroundResource(R.mipmap.ic_title_back);
        header.setRightBackgroundResource(0);
        groupList = new ArrayList<>();
        childList = new ArrayList<>();
        loadsData();
        adapter = new ExpandableListViewAdapter(this, groupList, childList);
        listView.setAdapter(adapter);

        //重写OnGroupClickListener，实现当展开时，ExpandableListView不自动滚动
        listView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                if (parent.isGroupExpanded(groupPosition)) {
                    parent.collapseGroup(groupPosition);
                } else {
                    //第二个参数false表示展开时是否触发默认滚动动画
                    parent.expandGroup(groupPosition, false);
                }
                //telling the listView we have handled the group click, and don't want the default actions.
                return true;
            }
        });
    }

    @Override
    protected void initListener() {
        header.setLeftBackOneListener(this);
        listView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
               ChildData data = (ChildData) adapter.getChild(groupPosition,childPosition);
                DLog.i(getClass().getSimpleName(),groupPosition+"---"+childPosition+"---"+data.getName());
                return false;
            }
        });
    }

    private void loadsData() {
        url = new String[]{
                "http://cdn.duitang.com/uploads/item/201506/07/20150607125903_vFWC5.png",
                "http://upload.qqbody.com/ns/20160915/202359954jalrg3mqoei.jpg",
                "http://tupian.qqjay.com/tou3/2016/0726/8529f425cf23fd5afaa376c166b58e29.jpg",
                "http://cdn.duitang.com/uploads/item/201607/13/20160713094718_Xe3Tc.png",
                "http://img3.imgtn.bdimg.com/it/u=1808104956,526590423&fm=11&gp=0.jpg",
                "http://tupian.qqjay.com/tou3/2016/0725/5d6272a4acd7e21b2391aff92f765018.jpg"
        };

        List<String> group = new ArrayList<>();
        group.add("场外设备");
        group.add("场内设备");

        for (int i = 0; i < group.size(); i++) {
            GroupData gd = new GroupData(group.get(i), (i + 2) + "/" + (2 * i + 2));
            groupList.add(gd);
        }

        for (int i = 0; i < group.size(); i++) {
            List<ChildData> list = new ArrayList<>();
            if (i%2 == 0) {
                for (int j = 0; j < 2 * i + 2; j++) {
                    ChildData cd = null;
                    if (i == 0) {
                        cd = new ChildData("null", "卡口设备", "");
                        list.add(cd);
                        cd = new ChildData("null", "智能机柜", "");
                        list.add(cd);
                        break;
                    } else {
                        cd = new ChildData(url[j % url.length], "其他设备" + j, "");
                        list.add(cd);
                    }
                }
            }else {
                for (int j = 0; j < 2 * i + 2; j++) {
                    ChildData cd = null;
                    if (i == 1) {
                        cd = new ChildData("null", "服务器", "");
                        list.add(cd);
                        cd = new ChildData("null", "数据库", "");
                        list.add(cd);
                        cd = new ChildData("null", "平台信息", "");
                        list.add(cd);
                        cd = new ChildData("null", "FTP信息", "");
                        list.add(cd);
                        break;
                    } else {
                        cd = new ChildData(url[j % url.length], "其他设备" + j, "");
                        list.add(cd);
                    }
                }
            }
            childList.add(list);
        }
    }

    @Override
    public void onClick(View v) {
        finish();
    }
}
