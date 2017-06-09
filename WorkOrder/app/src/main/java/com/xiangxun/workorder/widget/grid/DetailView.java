package com.xiangxun.workorder.widget.grid;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xiangxun.workorder.R;


/**
 * @TODO：自定义详情页面键值对组合控件
 */
public class DetailView extends LinearLayout {
    private Context mContext;
    private TextView name, value;
    private LinearLayout lin;


    public DetailView(Context context) {
        super(context);
        mContext = context;
        init();
    }

    public DetailView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    private void init() {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_detail, this, true);

        name = (TextView) findViewById(R.id.id_view_dt_name);
        value = (TextView) findViewById(R.id.id_view_dt_value);
        lin = (LinearLayout) findViewById(R.id.id_view_details);
    }

    public TextView getValue() {
        return value;
    }

    public void setName(int titls) {
        name.setText(titls);
    }
    public void setName(String titls) {
        name.setText(titls);
    }

    public void setNameValue(String tital, String content) {
        if (TextUtils.isEmpty(tital) || TextUtils.isEmpty(content)) {
            lin.setVisibility(View.GONE);
        } else {
            lin.setVisibility(VISIBLE);
            name.setText(tital);
            value.setText(content);
        }
    }

    public void setNameValue(int tital, String content) {
        if (tital == 0 || TextUtils.isEmpty(content)) {
            lin.setVisibility(View.GONE);
        } else {
            lin.setVisibility(VISIBLE);
            name.setText(tital);
            value.setText(content);
        }
    }

    public void setNameValue(String tital, int content) {
        if (TextUtils.isEmpty(tital) || content == 0) {
            lin.setVisibility(View.GONE);
        } else {
            lin.setVisibility(VISIBLE);
            name.setText(tital);
            value.setText(content);
        }
    }

    public void setNameValue(int tital, int content) {
        if (tital == 0 || content == 0) {
            lin.setVisibility(View.GONE);
        } else {
            lin.setVisibility(VISIBLE);
            name.setText(tital);
            value.setText(content);
        }
    }

}
