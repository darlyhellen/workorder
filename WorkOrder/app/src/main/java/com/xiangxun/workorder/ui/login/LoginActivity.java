package com.xiangxun.workorder.ui.login;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hellen.baseframe.binder.ContentBinder;
import com.hellen.baseframe.binder.ViewsBinder;
import com.hellen.baseframe.common.dlog.DLog;
import com.hellen.baseframe.common.multithread.MultithreadDownLoadCommon;
import com.hellen.baseframe.common.multithread.MultithreadDownLoadManager;
import com.hellen.baseframe.common.multithread.MultithreadDownLoadManager.OnMultithreadUIListener;
import com.hellen.baseframe.common.obsinfo.ToastApp;
import com.hellen.baseframe.common.utiltools.SharePreferHelp;
import com.xiangxun.workorder.R;
import com.xiangxun.workorder.base.APP;
import com.xiangxun.workorder.base.AppEnum;
import com.xiangxun.workorder.base.BaseActivity;
import com.xiangxun.workorder.bean.SetModel;
import com.xiangxun.workorder.bean.VersionData;
import com.xiangxun.workorder.bean.VersionRoot;
import com.xiangxun.workorder.ui.MaintenanceActivity;
import com.xiangxun.workorder.ui.biz.LoginListener.LoginInterface;
import com.xiangxun.workorder.ui.biz.SetListener.SetInterface;
import com.xiangxun.workorder.ui.login.edittext.ClearEditText;
import com.xiangxun.workorder.ui.login.edittext.XSubButton;
import com.xiangxun.workorder.ui.presenter.LoginPresenter;
import com.xiangxun.workorder.ui.presenter.SetPresenter;
import com.xiangxun.workorder.widget.dialog.VersionUpDateDialog;

import java.io.File;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Zhangyuhui/Darly on 2017/5/17.
 * Copyright by [Zhangyuhui/Darly]
 * ©2017 XunXiang.Company. All rights reserved.
 *
 * @TODO: 登陆页面
 */
@ContentBinder(R.layout.login_activity_layout)
public class LoginActivity extends BaseActivity implements OnClickListener, LoginInterface, OnCheckedChangeListener, SetInterface, OnMultithreadUIListener {


    @ViewsBinder(R.id.edt_login_bg)
    private ImageView iv;
    @ViewsBinder(R.id.edt_user_acount)
    private ClearEditText mEdtAcount;
    @ViewsBinder(R.id.edt_login_password)
    private ClearEditText mEdtPassWord;
    @ViewsBinder(R.id.btn_login_github)
    private XSubButton btn_login_github;
    @ViewsBinder(R.id.btn_login_post)
    private XSubButton mBtnLogin_post;
    private String password;
    private String acount;
    @ViewsBinder(R.id.id_login_set)
    private TextView setiv;
    @ViewsBinder(R.id.id_login_check)
    private CheckBox id_login_check;


    private LoginPresenter presenter;


    private VersionUpDateDialog dialog;

    @Override
    protected void initView(Bundle savedInstanceState) {
        presenter = new LoginPresenter(this);
        //第一次更新弹出，间隔一周在弹出。
        DLog.i("自动更新" + (Calendar.getInstance().get(Calendar.DAY_OF_YEAR) >= SharePreferHelp
                .getValue(AppEnum.NOTUPDATE.getDec(), 0)));
        if (Calendar.getInstance().get(Calendar.DAY_OF_YEAR) >= SharePreferHelp
                .getValue(AppEnum.NOTUPDATE.getDec(), 0)) {
            new SetPresenter(this).clickUpdate(this, true);
        }
        btn_login_github.setViewInit(R.string.mine_login_login, R.string.mine_login_loginning, mEdtAcount, mEdtPassWord);
        mBtnLogin_post.setViewInit(R.string.mine_login_login, R.string.mine_login_loginning, mEdtAcount, mEdtPassWord);
        iv.setLayoutParams(new LinearLayout.LayoutParams(AppEnum.WIDTH.getLen() / 5, AppEnum.WIDTH.getLen() / 5));
        String account = SharePreferHelp.getValue(AppEnum.USERNAME.getDec(), null);
        String password = SharePreferHelp.getValue(AppEnum.PASSWORD.getDec(), null);
        mEdtAcount.setText(account);
        if (SharePreferHelp.getValue(AppEnum.ISLOGINPASS.getDec(), false)) {
            mEdtPassWord.setText(password);
            id_login_check.setChecked(true);
        } else {
            mEdtPassWord.setText(null);
            id_login_check.setChecked(false);
        }
        setiv.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
    }

    @Override
    public void initListener() {
        btn_login_github.setOnClickListener(this);
        mBtnLogin_post.setOnClickListener(this);
        setiv.setOnClickListener(this);
        id_login_check.setOnCheckedChangeListener(this);
    }

    @Override
    public void onClick(View v) {

        presenter.onClickDown(this, v);

//        switch (v.getId()) {
//            case R.id.btn_login:
//                acount = mEdtAcount.getText().toString().trim();
//                password = mEdtPassWord.getText().toString().trim();
//                break;
//        }
    }

    @Override
    public void onLoginSuccess() {
        startActivity(new Intent(this, MaintenanceActivity.class));
        end();
    }

    @Override
    public void onLoginFailed() {
//        if (!TextUtils.isEmpty(SharePreferHelp.getValue(AppEnum.USERID.getDec(), null))) {
//            startActivity(new Intent(this, MainActivity.class));
//            end();
//        }
    }

    @Override
    public String getUserName() {
        return mEdtAcount.getText().toString().trim();
    }

    @Override
    public String getPassword() {
        return mEdtPassWord.getText().toString().trim();
    }

    //进行下载运行
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
        } else {
            if (!dialog.isShowing()) {
                dialog.show();
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

    }

    @Override
    public void end() {
        finish();
    }

    @Override
    public void setDisableClick() {
        btn_login_github.setEnabled(false);
        mBtnLogin_post.setEnabled(false);
    }

    @Override
    public void setEnableClick() {
        btn_login_github.setEnabled(true);
        btn_login_github.setNormal();
        mBtnLogin_post.setEnabled(true);
        mBtnLogin_post.setNormal();
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        SharePreferHelp.putValue(AppEnum.ISLOGINPASS.getDec(), isChecked);
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
}
