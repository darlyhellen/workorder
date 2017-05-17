package com.xiangxun.workorder.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.hellen.baseframe.binder.ContentBinder;
import com.hellen.baseframe.binder.ViewsBinder;
import com.hellen.baseframe.common.utiltools.SharePreferHelp;
import com.xiangxun.workorder.ui.MainActivity;
import com.xiangxun.workorder.R;
import com.xiangxun.workorder.base.BaseActivity;
import com.xiangxun.workorder.base.ConsMVP;
import com.xiangxun.workorder.ui.biz.Login;
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
public class LoginActivity extends BaseActivity implements OnClickListener, Login.LoginView {

    @ViewsBinder(R.id.edt_user_acount)
    private ClearEditText mEdtAcount;
    @ViewsBinder(R.id.edt_login_password)
    private ClearEditText mEdtPassWord;
    @ViewsBinder(R.id.btn_login)
    private XSubButton mBtnLogin;
    private String password;
    private String acount;


    private LoginPresenter presenter;
//    private Handler mHandler = new Handler() {
//        public void handleMessage(Message msg) {
//            mBtnLogin.setNormal();
//            switch (msg.what) {
//                case ConstantStatus.loadSuccess:
//                    LoginData loginData = (LoginData) msg.obj;
//                    Login login = loginData.getLogin();
//                    if (login != null && "false".equals(login.getRes())) {
//                        MsgToast.geToast().setMsg("登录失败, 请检查用户名或者密码是否正确~");
//                        return;
//                    }
//                    MsgToast.geToast().setMsg("登录成功");
//                    if (loginData.getUser() != null && loginData.getUser().size() > 0) {
//                        UserData userData = loginData.getUser().get(0);
//                        InfoCache.getInstance().setUserData(userData);
//                        ShareDataUtils.setSharedStringData(mContext, SharedPreferencesKeys.USERNAME, acount);
//                        ShareDataUtils.setSharedStringData(mContext, SharedPreferencesKeys.PASSWORD, password);
//                        ShareDataUtils.setSharedStringData(mContext, SharedPreferencesKeys.USERID, userData.getId());
//                        ShareDataUtils.setSharedStringData(mContext, SharedPreferencesKeys.NAME, userData.getName());
//                        ShareDataUtils.setSharedStringData(mContext, SharedPreferencesKeys.DEPTID, userData.getDeptid());
//                        ShareDataUtils.setSharedStringData(mContext, SharedPreferencesKeys.DUTYORGID, userData.getDutyorgcode());
//                        ShareDataUtils.setSharedStringData(mContext, SharedPreferencesKeys.USERPHONE, userData.getMobile());
//                        ShareDataUtils.setSharedStringData(mContext, SharedPreferencesKeys.MOBILEROLES, userData.getMobileRoles());
//                        XiangXunApplication.getInstance().setIsFirstIn(Tools.getAppVersionName(LoginActivity.this));
//                        List<Menu> menus = loginData.getMenu();
//                        if (menus != null && menus.size() > 0) {
//                            for (int i = 0; i < menus.size(); i++) {
//                                Menu menu = menus.get(i);
//                                if (menu != null && "物资管理".equals(menu.getName())) {
//                                    List<ChildrenRoot> children = menu.getChildren();
//                                    if (children != null && children.size() > 0) {
//                                        ShareDataUtils.saveObject(mContext, "menu_materiel", children);
//                                    }
//                                } else if (menu != null && "设备管理".equals(menu.getName())) {
//                                    List<ChildrenRoot> children = menu.getChildren();
//                                    if (children != null && children.size() > 0) {
//                                        ShareDataUtils.saveObject(mContext, "menu_device", children);
//                                    }
//                                } else if (menu != null && "道路挖占".equals(menu.getName())) {
//                                    List<ChildrenRoot> children = menu.getChildren();
//                                    if (children != null && children.size() > 0) {
//                                        ShareDataUtils.saveObject(mContext, "menu_occupy", children);
//                                    }
//                                } else if (menu != null && "巡视系统".equals(menu.getName())) {
//                                    List<ChildrenRoot> children = menu.getChildren();
//                                    if (children != null && children.size() > 0) {
//                                        ShareDataUtils.saveObject(mContext, "menu_patrol", children);
//                                    }
//                                }
//                            }
//                            ShareDataUtils.saveObject(mContext, "home_menu", menus);
//                        }
//                        startActivity(new Intent(mContext, MainActivity.class));
//                        finish();
//                    } else {
//                        MsgToast.geToast().setMsg("登录失败, 请重新登录~");
//                    }
//                    break;
//                case ConstantStatus.loadFailed:
//                    MsgToast.geToast().setMsg("登录失败");
//                    break;
//                case ConstantStatus.NetWorkError:
//                    MsgToast.geToast().setMsg("数据异常");
//                    break;
//            }
//        }
//    };

    @Override
    protected void initView(Bundle savedInstanceState) {
        presenter = new LoginPresenter(this);
        mBtnLogin.setViewInit(R.string.mine_login_login, R.string.mine_login_loginning, mEdtAcount, mEdtPassWord);
        String account = SharePreferHelp.getValue(ConsMVP.USERNAME.getDec(), null);
        String password = SharePreferHelp.getValue(ConsMVP.PASSWORD.getDec(), null);
        mEdtAcount.setText(account);
        mEdtPassWord.setText(password);
    }

    @Override
    public void initListener() {
        mBtnLogin.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        presenter.onClickDown(this, v);


        switch (v.getId()) {
            case R.id.btn_login:
                acount = mEdtAcount.getText().toString().trim();
                password = mEdtPassWord.getText().toString().trim();

                //DcNetWorkUtils.login(this, acount, password, mHandler);
                break;
        }
    }

    @Override
    public void onLoginSuccess() {
        SharePreferHelp.putValue(ConsMVP.USERNAME.getDec(), "13891431454");
        SharePreferHelp.putValue(ConsMVP.PASSWORD.getDec(), "123456");
        startActivity(new Intent(this, MainActivity.class));
        end();
    }

    @Override
    public void onLoginFailed() {
        SharePreferHelp.remove("loginName");
        SharePreferHelp.remove("password");
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
    }

    @Override
    public void setEnableClick() {
        mBtnLogin.setEnabled(true);
        mBtnLogin.setNormal();
    }
}
