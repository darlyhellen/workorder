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
import com.xiangxun.workorder.bean.EquipMenuChildData;
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
        void changeState(EquipMenuChildData type);
    }

    private onSelectItemClick selectItemClick;
    private Context mContext = null;
    private List<EquipMenuChildData> data = null;
    private View mCustomView = null;
    private TextView textView = null;
    private String mTitle = null;
    private TextView mTvCancle = null;
    private TextView mTvPublishSelectTitle = null;
    private ListView mLvPublishTypes = null;
    private StringBuffer mFinalStyle = new StringBuffer();
    private int selectedItemPosition = 0;

    public TourSelectDialog(Context context, List<EquipMenuChildData> data, TextView textView, String mTitle, onSelectItemClick selectItemClick) {
        super(context, R.style.PublishDialog);
        this.mContext = context;
        this.data = data;
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
        mLvPublishTypes = (ListView) mCustomView.findViewById(R.id.lv_publish_select_dialog);
        mLvPublishTypes.setAdapter(new TourSelectAdapter(data, R.layout.item_select_text, mContext));

        mLvPublishTypes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                EquipMenuChildData data = (EquipMenuChildData) parent.getItemAtPosition(position);
                if (selectItemClick != null) {
                    selectItemClick.changeState(data);
                }
                dismiss();
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
