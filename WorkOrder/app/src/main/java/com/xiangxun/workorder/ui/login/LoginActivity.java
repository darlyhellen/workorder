package com.xiangxun.workorder.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.hellen.baseframe.binder.ContentBinder;
import com.hellen.baseframe.binder.ViewsBinder;
import com.hellen.baseframe.common.utiltools.SharePreferHelp;
import com.xiangxun.workorder.R;
import com.xiangxun.workorder.base.AppEnum;
import com.xiangxun.workorder.base.BaseActivity;
import com.xiangxun.workorder.ui.MainActivity;
import com.xiangxun.workorder.ui.MaintenanceActivity;
import com.xiangxun.workorder.ui.biz.LoginListener;
import com.xiangxun.workorder.ui.biz.LoginListener.LoginInterface;
import com.xiangxun.workorder.ui.login.edittext.ClearEditText;
import com.xiangxun.workorder.ui.login.edittext.XSubButton;
import com.xiangxun.workorder.ui.presenter.LoginPresenter;

/**
 * Created by Zhangyuhui/Darly on 2017/5/17.
 * Copyright by [Zhangyuhui/Darly]
 * ©2017 XunXiang.Company. All rights reserved.
 *
 * @TODO: 登陆页面
 */
@ContentBinder(R.layout.login_activity_layout)
public class LoginActivity extends BaseActivity implements OnClickListener, LoginInterface {


    @ViewsBinder(R.id.edt_login_bg)
    private ImageView iv;
    @ViewsBinder(R.id.edt_user_acount)
    private ClearEditText mEdtAcount;
    @ViewsBinder(R.id.edt_login_password)
    private ClearEditText mEdtPassWord;
    @ViewsBinder(R.id.btn_login)
    private XSubButton mBtnLogin;
    @ViewsBinder(R.id.btn_login_post)
    private XSubButton mBtnLogin_post;
    private String password;
    private String acount;


    private LoginPresenter presenter;

    @Override
    protected void initView(Bundle savedInstanceState) {
        presenter = new LoginPresenter(this);
        mBtnLogin.setViewInit(R.string.mine_login_login, R.string.mine_login_loginning, mEdtAcount, mEdtPassWord);
        mBtnLogin_post.setViewInit(R.string.mine_login_login, R.string.mine_login_loginning, mEdtAcount, mEdtPassWord);
        iv.setLayoutParams(new LinearLayout.LayoutParams(AppEnum.WIDTH.getLen(), (int) (AppEnum.WIDTH.getLen() * 0.64)));
        String account = SharePreferHelp.getValue(AppEnum.USERNAME.getDec(), null);
        String password = SharePreferHelp.getValue(AppEnum.PASSWORD.getDec(), null);
        mEdtAcount.setText(account);
        mEdtPassWord.setText(password);
    }

    @Override
    public void initListener() {
        mBtnLogin.setOnClickListener(this);
        mBtnLogin_post.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        presenter.onClickDown(this, v);


        switch (v.getId()) {
            case R.id.btn_login:
                acount = mEdtAcount.getText().toString().trim();
                password = mEdtPassWord.getText().toString().trim();
                break;
        }
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

    @Override
    public void end() {
        finish();
    }

    @Override
    public void setDisableClick() {
        mBtnLogin.setEnabled(false);
        mBtnLogin_post.setEnabled(false);
    }

    @Override
    public void setEnableClick() {
        mBtnLogin.setEnabled(true);
        mBtnLogin.setNormal();
        mBtnLogin_post.setEnabled(true);
        mBtnLogin_post.setNormal();
    }
}
