package com.xiangxun.workorder.widget.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TextView;

import com.hellen.baseframe.common.dlog.DLog;
import com.hellen.baseframe.common.utiltools.SharePreferHelp;
import com.xiangxun.workorder.R;
import com.xiangxun.workorder.base.AppEnum;
import com.xiangxun.workorder.bean.BaseModel.Type;
import com.xiangxun.workorder.bean.ChildData;
import com.xiangxun.workorder.bean.EquipmentRoot;
import com.xiangxun.workorder.common.urlencode.Tools;
import com.xiangxun.workorder.ui.adapter.RootExpandableListViewAdapter;
import com.xiangxun.workorder.ui.presenter.EquipmentMenuPresenter;

import java.util.List;


/**
 * Created by Zhangyuhui/Darly on 2017/6/6.
 * Copyright by [Zhangyuhui/Darly]
 * ©2017 XunXiang.Company. All rights reserved.
 *
 * @TODO:
 */
public class TourSelectDialog extends Dialog {


    public interface onSelectItemClick {
        void changeState(EquipmentRoot type);
    }

    private onSelectItemClick selectItemClick;
    private Context mContext = null;
    private View mCustomView = null;
    private TextView textView = null;
    private String mTitle = null;
    private TextView mTvCancle = null;
    private TextView mTvPublishSelectTitle = null;
    private ExpandableListView mLvPublishTypes = null;
    private StringBuffer mFinalStyle = new StringBuffer();
    private RootExpandableListViewAdapter adapter;
    private int selectedItemPosition = 0;

    public TourSelectDialog(Context context, TextView textView, String mTitle, onSelectItemClick selectItemClick) {
        super(context, R.style.PublishDialog);
        this.mContext = context;
        this.textView = textView;
        this.mTitle = mTitle;
        this.selectItemClick = selectItemClick;
        init();
    }

    private void init() {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        mCustomView = inflater.inflate(R.layout.publish_select_dialog, null);
        setContentView(mCustomView);
        Window window = this.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        DisplayMetrics dm = new DisplayMetrics();
        ((Activity) mContext).getWindowManager().getDefaultDisplay().getMetrics(dm);
        lp.width = dm.widthPixels - Tools.dip2px(mContext, 5.0f);
        lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        initView();
    }

    @Override
    public void show() {
        super.show();
    }

    private void initView() {
        mTvCancle = (TextView) mCustomView.findViewById(R.id.tv_publish_select_dialog_cancle);
        mTvPublishSelectTitle = (TextView) mCustomView.findViewById(R.id.tv_publish_select_dialog_title);
        mTvPublishSelectTitle.setText(mTitle);
        mLvPublishTypes = (ExpandableListView) mCustomView.findViewById(R.id.lv_publish_select_dialog);
        adapter = new RootExpandableListViewAdapter(mContext, EquipmentMenuPresenter.testData().getData());
        mLvPublishTypes.setAdapter(adapter);
        //重写OnGroupClickListener，实现当展开时，ExpandableListView不自动滚动
        mLvPublishTypes.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
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

        mLvPublishTypes.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                EquipmentRoot data = (EquipmentRoot) adapter.getChild(groupPosition, childPosition);
                if (selectItemClick != null) {
                    selectItemClick.changeState(data);
                }
                dismiss();
                return false;
            }
        });
        mTvCancle.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

    }

    public int getSelectedItemPosition() {
        return selectedItemPosition;
    }

    public class AffairsTypeAdapter extends BaseAdapter {
        private List<Type> types = null;
        private LayoutInflater mInflater = null;

        public AffairsTypeAdapter(Context context, List<Type> types) {
            mInflater = LayoutInflater.from(context);
            this.types = types;
        }

        @Override
        public int getCount() {
            return types.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.publish_select_dialog_item, null);
                holder = new ViewHolder();
                holder.mTvStyleName = (TextView) convertView.findViewById(R.id.tv_publish_slect_dialog_string);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            // if (position == getCount() - 1) {
            // convertView.setBackgroundResource(R.drawable.phone_message_last_selector);
            // } else {
            // convertView.setBackgroundResource(R.drawable.phone_message_selector);
            // }
            holder.mTvStyleName.setText(types.get(position).name);
            return convertView;
        }

        private class ViewHolder {
            private TextView mTvStyleName = null;
        }
    }


}
