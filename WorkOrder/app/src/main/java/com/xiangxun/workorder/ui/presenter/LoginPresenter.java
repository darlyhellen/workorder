package com.xiangxun.workorder.ui.presenter;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.hellen.baseframe.application.FrameListener;
import com.hellen.baseframe.common.dlog.DLog;
import com.hellen.baseframe.common.obsinfo.LogApp;
import com.hellen.baseframe.common.obsinfo.ToastApp;
import com.hellen.baseframe.common.utiltools.SharePreferHelp;
import com.xiangxun.workorder.R;
import com.xiangxun.workorder.base.APP;
import com.xiangxun.workorder.base.AppEnum;
import com.xiangxun.workorder.bean.LoginRoot;
import com.xiangxun.workorder.bean.VersionRoot;
import com.xiangxun.workorder.service.VersionUpdateService;
import com.xiangxun.workorder.ui.biz.LoginListener;
import com.xiangxun.workorder.ui.biz.SetListener;
import com.xiangxun.workorder.ui.login.LoginActivity;
import com.xiangxun.workorder.ui.main.SetActivity;
import com.xiangxun.workorder.widget.dialog.APPDialg;
import com.xiangxun.workorder.widget.dialog.OndialogListener;
import com.xiangxun.workorder.widget.loading.ShowLoading;


/**
 * @author zhangyh2 s 上午10:57:39 TODO 控制器 页面动作操作
 */
public class LoginPresenter {

    private String TAG = getClass().getSimpleName();
    private LoginListener userBiz;
    private LoginListener.LoginInterface main;
    private ShowLoading loading;


    public LoginPresenter(LoginListener.LoginInterface main) {
        this.main = main;
        this.userBiz = new LoginListener();
        loading = new ShowLoading((LoginActivity) main);
        loading.setMessage(R.string.mine_login_loginning);
    }


    /**
     * @param context
     * @param v       TODO点击事件在这里进行处理
     */
    public void onClickDown(Context context, View v) {
        switch (v.getId()) {
            case R.id.btn_login_github:
                logingithub();
//                login(context);
                break;
            case R.id.btn_login_post:
                login_post(context);
                break;
            case R.id.id_login_set:
                Intent intent = new Intent(context, SetActivity.class);
                intent.putExtra("LOGIN", 0);
                context.startActivity(intent);
                break;
            default:
                break;
        }
    }


    public void logingithub() {
        LogApp.i(TAG, "login");
        userBiz.onStart(loading);
        main.setDisableClick();
        userBiz.login_github(new FrameListener<String>() {

            @Override
            public void onSucces(final String result) {
                // TODO Auto-generated method stub
                main.setEnableClick();
                userBiz.onStop(loading);
            }

            @Override
            public void onFaild(int code, String info) {
                // TODO Auto-generated method stub
                main.setEnableClick();
                userBiz.onStop(loading);
                switch (code) {
                    case 1:
                        ToastApp.showToast(info);
                        break;
                    case 0:
                        ToastApp.showToast("网络请求异常");
                        break;
                    default:
                        break;
                }
            }
        });

    }

    public void login(Context context) {
        LogApp.i(TAG, "login");
        userBiz.onStart(loading);
        main.setDisableClick();
        userBiz.login_get(context, main.getUserName(), main.getPassword(),
                new FrameListener<LoginRoot>() {

                    @Override
                    public void onSucces(final LoginRoot result) {
                        // TODO Auto-generated method stub
                        main.onLoginSuccess();
                        main.setEnableClick();
                        userBiz.onStop(loading);
                    }

                    @Override
                    public void onFaild(int code, String info) {
                        // TODO Auto-generated method stub
                        main.onLoginFailed();
                        main.setEnableClick();
                        userBiz.onStop(loading);
                        switch (code) {
                            case 0:
                                ToastApp.showToast(info);
                                break;
                            case 1:
                                ToastApp.showToast("网络请求异常");
                                break;
                            default:
                                break;
                        }
                    }
                });

    }

    public void login_post(Context context) {
        LogApp.i(TAG, "login");
        //userBiz.onStart(loading);
        main.setDisableClick();
        userBiz.login_in(context, main.getUserName(), main.getPassword(),
                new FrameListener<LoginRoot>() {

                    @Override
                    public void onSucces(final LoginRoot result) {
                        // TODO Auto-generated method stub
                        main.onLoginSuccess();
                        main.setEnableClick();
                        //userBiz.onStop(loading);
                    }

                    @Override
                    public void onFaild(int code, String info) {
                        // TODO Auto-generated method stub
                        main.onLoginFailed();
                        main.setEnableClick();
                        //userBiz.onStop(loading);
                        switch (code) {
                            case 0:
                                ToastApp.showToast(info);
                                break;
                            case 1:
                                ToastApp.showToast("网络请求异常");
                                break;
                            default:
                                break;
                        }
                    }
                });
    }

}