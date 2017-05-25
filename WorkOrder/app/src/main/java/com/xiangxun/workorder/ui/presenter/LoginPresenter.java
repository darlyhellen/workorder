package com.xiangxun.workorder.ui.presenter;

import android.content.Context;
import android.view.View;

import com.hellen.baseframe.application.FrameListener;
import com.hellen.baseframe.common.obsinfo.LogApp;
import com.hellen.baseframe.common.obsinfo.ToastApp;
import com.xiangxun.workorder.R;
import com.xiangxun.workorder.bean.LoginRoot;
import com.xiangxun.workorder.ui.biz.LoginListener;
import com.xiangxun.workorder.ui.login.LoginActivity;
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
            case R.id.btn_login:
                login(context);
                break;
            case R.id.btn_login_post:
                login_post(context);
                break;
            default:
                break;
        }
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
        userBiz.onStart(loading);
        main.setDisableClick();
        userBiz.login_in(context, main.getUserName(), main.getPassword(),
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
                                ToastApp.showToast("网络请求异常");
                                break;
                            case 1:
                                ToastApp.showToast(info);
                                break;
                            default:
                                break;
                        }
                    }
                });
    }

}