package com.xiangxun.workorder.ui.main;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.hellen.baseframe.binder.ContentBinder;
import com.hellen.baseframe.binder.ViewsBinder;
import com.hellen.baseframe.common.dlog.DLog;
import com.hellen.baseframe.common.multithread.MultithreadDownLoadCommon;
import com.hellen.baseframe.common.multithread.MultithreadDownLoadManager;
import com.hellen.baseframe.common.obsinfo.ToastApp;
import com.xiangxun.workorder.R;
import com.xiangxun.workorder.base.AppEnum;
import com.xiangxun.workorder.base.BaseActivity;
import com.xiangxun.workorder.widget.dialog.MultithreadProgressBar;
import com.xiangxun.workorder.widget.header.HeaderView;

import java.io.File;
import java.text.DecimalFormat;

/**
 * Created by Zhangyuhui/Darly on 2017/5/23.
 * Copyright by [Zhangyuhui/Darly]
 * ©2017 XunXiang.Company. All rights reserved.
 *
 * @TODO:下載文件的類。
 */

@ContentBinder(R.layout.activity_down_load)
public class DownLoadActivity extends BaseActivity implements View.OnClickListener, MultithreadDownLoadManager.OnMultithreadUIListener {

    @ViewsBinder(R.id.multithread_header)
    private HeaderView header;
    @ViewsBinder(R.id.multithread_down)
    private Button button;
    @ViewsBinder(R.id.multithread_pause)
    private Button pause;
    @ViewsBinder(R.id.multithread_text)
    private TextView text;
    @ViewsBinder(R.id.multithread_bar)
    private MultithreadProgressBar load;
    @ViewsBinder(R.id.multithread_edit)
    private EditText edit;

    /**
     * 数字格式化类
     */
    DecimalFormat df = new DecimalFormat(".00");

    @Override
    protected void initView(Bundle savedInstanceState) {
        header.setTitle(R.string.main_work_map);
        header.setLeftBackgroundResource(R.mipmap.ic_title_back);
    }

    @Override
    protected void initListener() {
        header.setLeftBackOneListener(this);
        button.setOnClickListener(this);
        pause.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.multithread_down:
                //启动下载
                //直接进行启动下载，无需点击，打开之后，开始下载时，进行Loading，下载完毕后Loading关闭即可。
                MultithreadDownLoadCommon.ISPUASE = false;
                startMultithread();
                break;
            case R.id.multithread_pause:
                MultithreadDownLoadCommon.ISPUASE = true;
                break;
            case R.id.title_view_back_llayout:
                finish();
                break;
        }
    }


    /**
     * 启动下载
     */
    private void startMultithread() {
        String num = edit.getText().toString().trim();
        int in = 1;
        if (!TextUtils.isEmpty(num)) {
            in = Integer.parseInt(num);
        }
        MultithreadDownLoadManager.init(this, MultithreadDownLoadCommon.TYPE_FIFO, in);
        String qq = "http://gdown.baidu.com/data/wisegame/02ba8a69a5a792b1/QQ_500.apk";
        MultithreadDownLoadManager.getInstance().getFileInfo(qq, AppEnum.DOWN);
        MultithreadDownLoadManager.getInstance().setOnMultithreadUIListener(this);
    }

    @Override
    public void onStartDown() {
        if (load != null) {
            load.setMax(100);
        }
    }

    @Override
    public void onLoading(float progress) {
        if (load != null) {
            load.setProgress((int) progress);
            String tes = df.format(progress) + "%";
            text.setText(tes);
        }
    }

    @Override
    public void onSuccess(File file) {
        DLog.d(getClass().getSimpleName(), "下载完成" + file.getAbsoluteFile());
        ToastApp.showToast("下载完成,目录：" + file.getAbsoluteFile());
    }

    @Override
    public void onFailse(String s) {
        DLog.i(getClass().getSimpleName(), "下载异常");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
