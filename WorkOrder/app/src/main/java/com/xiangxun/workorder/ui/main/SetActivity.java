package com.xiangxun.workorder.ui.main;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.hellen.baseframe.binder.ContentBinder;
import com.hellen.baseframe.binder.ViewsBinder;
import com.hellen.baseframe.common.multithread.MultithreadDownLoadCommon;
import com.hellen.baseframe.common.multithread.MultithreadDownLoadManager;
import com.hellen.baseframe.common.multithread.MultithreadDownLoadManager.OnMultithreadUIListener;
import com.hellen.baseframe.common.obsinfo.ToastApp;
import com.xiangxun.workorder.R;
import com.xiangxun.workorder.base.APP;
import com.xiangxun.workorder.base.AppEnum;
import com.xiangxun.workorder.base.BaseActivity;
import com.xiangxun.workorder.bean.SetModel;
import com.xiangxun.workorder.bean.VersionData;
import com.xiangxun.workorder.bean.VersionRoot;
import com.xiangxun.workorder.ui.adapter.SetAdapter;
import com.xiangxun.workorder.ui.biz.SetListener;
import com.xiangxun.workorder.ui.presenter.SetPresenter;
import com.xiangxun.workorder.widget.dialog.VersionUpDateDialog;
import com.xiangxun.workorder.widget.header.HeaderView;

import java.io.File;
import java.util.List;

/**
 * Created by Zhangyuhui/Darly on 2017/5/22.
 * Copyright by [Zhangyuhui/Darly]
 * ©2017 XunXiang.Company. All rights reserved.
 *
 * @TODO:首页设置页面。
 */
@ContentBinder(R.layout.activity_set)
public class SetActivity extends BaseActivity implements SetListener.SetInterface, View.OnClickListener, OnItemClickListener, OnMultithreadUIListener {

    @ViewsBinder(R.id.id_set_title)
    private HeaderView header;
    @ViewsBinder(R.id.id_set_list)
    private ListView listView;
    private SetAdapter adapter;

    private SetPresenter presenter;

    private VersionUpDateDialog dialog;

    @Override
    protected void initView(Bundle savedInstanceState) {
        header.setTitle(R.string.main_work_set);
        header.setLeftBackgroundResource(R.mipmap.ic_title_back);

        presenter = new SetPresenter(this);
        presenter.findFileSize(getIntent().getIntExtra("LOGIN", -1));

        adapter = new SetAdapter(null, R.layout.item_activity_set, this);
        listView.setAdapter(adapter);
    }


    @Override
    protected void initListener() {
        header.setLeftBackOneListener(this);
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View v) {
        presenter.onClickDown(this, v);
    }

    @Override
    public void onStartDownVersion(VersionRoot root) {
        if (APP.isNetworkConnected(APP.getInstance()) && root != null) {
            initService(root.getData());
        } else {
            ToastApp.showToast(R.string.neterror);
        }
        if (dialog == null) {
            dialog = new VersionUpDateDialog(this, root);
            dialog.show();
            dialog.initClick();
        } else {
            if (!dialog.isShowing()) {
                dialog.show();
                dialog.initClick();
            }
        }
    }

    public void initService(VersionData root) {
        MultithreadDownLoadManager.init(this, MultithreadDownLoadCommon.TYPE_FIFO, 4);
        MultithreadDownLoadManager.getInstance().getFileInfo(root.getSaveUrl(), AppEnum.DOWN);
        MultithreadDownLoadManager.getInstance().setOnMultithreadUIListener(this);

    }

    @Override
    public void getUserDate(List<SetModel> data) {
        adapter.setData(data);
    }

    @Override
    public void end() {
        finish();
    }

    @Override
    public void setDisableClick() {

    }

    @Override
    public void setEnableClick() {

    }


    @Override
    public void onStartDown() {
        if (dialog.getBar() != null) {
            dialog.getBar().setMax(100);
        }
    }

    @Override
    public void onLoading(float progress) {
        if (dialog.getBar() != null) {
            dialog.getBar().setProgress((int) progress);
        }
    }

    @Override
    public void onSuccess(File file) {
        dialog.dismiss();
        Instanll(file, this);
    }

    @Override
    public void onFailse(String s) {
        ToastApp.showToast(s);
    }

    // 安装下载后的apk文件
    private void Instanll(File file, Context context) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file),
                "application/vnd.android.package-archive");
        context.startActivity(intent);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        SetModel model = (SetModel) parent.getItemAtPosition(position);

        switch (model.getTitle()) {
            case R.string.set_clean_cache:
                presenter.clickClean(this, getIntent().getIntExtra("LOGIN", -1));
                break;
            case R.string.set_update:
                presenter.clickUpdate(this, false);
                break;
            case R.string.set_loginout:
                presenter.clickLoginOut(this);
                break;
            case R.string.set_service:
                //设置服务器IP地址和端口。
                Intent in = new Intent(this, SetServiceAcitivity.class);
                startActivityForResult(in, 700);
                break;
            default:
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 700) {
            presenter.findFileSize(getIntent().getIntExtra("LOGIN", -1));
        }
    }
}
