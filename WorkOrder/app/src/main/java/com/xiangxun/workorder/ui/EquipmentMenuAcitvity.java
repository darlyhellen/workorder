package com.xiangxun.workorder.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ExpandableListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hellen.baseframe.binder.ContentBinder;
import com.hellen.baseframe.binder.ViewsBinder;
import com.hellen.baseframe.common.dlog.DLog;
import com.hellen.baseframe.common.utiltools.SharePreferHelp;
import com.xiangxun.workorder.R;
import com.xiangxun.workorder.base.AppEnum;
import com.xiangxun.workorder.base.BaseActivity;
import com.xiangxun.workorder.bean.ChildData;
import com.xiangxun.workorder.bean.EquipmentRoot;
import com.xiangxun.workorder.bean.GroupData;
import com.xiangxun.workorder.bean.ObjectData;
import com.xiangxun.workorder.ui.adapter.ExpandableListViewAdapter;
import com.xiangxun.workorder.ui.adapter.RootExpandableListViewAdapter;
import com.xiangxun.workorder.ui.biz.EquipmentMenuListener;
import com.xiangxun.workorder.ui.biz.EquipmentMenuListener.EquipmentMenuInterface;
import com.xiangxun.workorder.ui.presenter.EquipmentMenuPresenter;
import com.xiangxun.workorder.widget.header.HeaderView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/6/5.
 *
 * @TODO: 設備管理功能頁面，包括幾大點。下拉列表。點擊進設備列表。然後點擊進詳情。
 */
@ContentBinder(R.layout.activity_equipment)
public class EquipmentMenuAcitvity extends BaseActivity implements OnClickListener, EquipmentMenuInterface {
    @ViewsBinder(R.id.id_equipment_header)
    private HeaderView header;
    @ViewsBinder(R.id.id_equipment_listview)
    private ExpandableListView listView;
    private RootExpandableListViewAdapter adapter;

    private EquipmentMenuPresenter presenter;

    private ObjectData data;

    @Override
    protected void initView(Bundle savedInstanceState) {
        presenter = new EquipmentMenuPresenter(this);
        String equip = SharePreferHelp.getValue(AppEnum.EQUIPMENTLIST.getDec(), null);
        if (TextUtils.isEmpty(equip)) {
            //presenter.getEquipment();
            testData();
            adapter = new RootExpandableListViewAdapter(this, data.getData());
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


        } else {
            //对数据进行解析。
            data = new Gson().fromJson(equip, new TypeToken<ObjectData>() {
            }.getType());
        }

        header.setTitle("设备列表");
        header.setLeftBackgroundResource(R.mipmap.ic_title_back);
        header.setRightBackgroundResource(0);
    }

    @Override
    protected void initListener() {
        header.setLeftBackOneListener(this);
        listView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                ChildData data = (ChildData) adapter.getChild(groupPosition, childPosition);
                DLog.i(getClass().getSimpleName(), groupPosition + "---" + childPosition + "---" + data.getName());
                return false;
            }
        });

    }

    private void testData() {


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

        data = new ObjectData();
        data.setData(groupDatas);
        data.setMessage("组合信息");
        data.setStatus(1);
    }

    private List<EquipmentRoot> getRoot(int j, String name) {
        List<EquipmentRoot> ka = new ArrayList<>();
        for (int k = 0; k < 2 * j + 2; k++) {
            EquipmentRoot root = new EquipmentRoot();
            root.setName(name + "编号:" + k);
            ka.add(root);
        }
        return ka;
    }

    @Override
    public void onClick(View v) {
        finish();
    }

    @Override
    public void onLoginSuccess(List<GroupData> data) {
        adapter = new RootExpandableListViewAdapter(this, data);
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
    public void onLoginFailed() {
        testData();
        adapter = new RootExpandableListViewAdapter(this, data.getData());
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
    public void end() {

    }

    @Override
    public void setDisableClick() {

    }

    @Override
    public void setEnableClick() {

    }
}
