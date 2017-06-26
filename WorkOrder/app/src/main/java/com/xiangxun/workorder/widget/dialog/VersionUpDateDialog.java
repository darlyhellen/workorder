package com.xiangxun.workorder.widget.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.hellen.baseframe.common.dlog.DLog;
import com.hellen.baseframe.common.multithread.MultithreadDownLoadCommon;
import com.hellen.baseframe.common.multithread.MultithreadDownLoadManager;
import com.hellen.baseframe.common.multithread.MultithreadDownLoadManager.OnMultithreadUIListener;
import com.hellen.baseframe.common.obsinfo.ToastApp;
import com.xiangxun.workorder.R;
import com.xiangxun.workorder.base.AppEnum;
import com.xiangxun.workorder.bean.VersionData;
import com.xiangxun.workorder.bean.VersionRoot;
import com.xiangxun.workorder.common.urlencode.Tools;
import com.xiangxun.workorder.ui.biz.SetListener;

import java.io.File;
import java.util.List;


/**
 * Created by Zhangyuhui/Darly on 2017/6/6.
 * Copyright by [Zhangyuhui/Darly]
 * ©2017 XunXiang.Company. All rights reserved.
 *
 * @TODO: 巡检页面弹出的对话框
 */
public class VersionUpDateDialog extends Dialog {

    private Context mContext = null;
    private View mCustomView = null;
    private VersionRoot root;

    private MultithreadProgressBar bar;

    public VersionUpDateDialog(Context context, VersionRoot root) {
        super(context, R.style.PublishDialog);
        this.mContext = context;
        this.root = root;
        init();
    }

    private void init() {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        mCustomView = inflater.inflate(R.layout.version_update_dialog, null);
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
        TextView mTvPublishSelectTitle = (TextView) mCustomView.findViewById(R.id.id_version_update_title);
        bar = (MultithreadProgressBar) mCustomView.findViewById(R.id.id_version_update_multithread_bar);
        mTvPublishSelectTitle.setText("运维通版本更新");
        Button stop = (Button) mCustomView.findViewById(R.id.id_version_update_stop);
        Button cont = (Button) mCustomView.findViewById(R.id.id_version_update_continue);
        Button close = (Button) mCustomView.findViewById(R.id.id_version_update_close);
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MultithreadDownLoadCommon.ISPUASE = true;
            }
        });
        cont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MultithreadDownLoadCommon.ISPUASE = false;
                ((SetListener.SetInterface) mContext).onStartDownVersion(root);
            }
        });
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MultithreadDownLoadCommon.ISPUASE = true;
                dismiss();
            }
        });

    }

    public MultithreadProgressBar getBar() {
        return bar;
    }
}
