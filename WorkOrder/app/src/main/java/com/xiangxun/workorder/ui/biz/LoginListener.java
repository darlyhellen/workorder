package com.xiangxun.workorder.ui.biz;

import android.app.Dialog;
import android.content.Context;
import android.telephony.TelephonyManager;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.hellen.baseframe.application.FrameListener;
import com.hellen.baseframe.application.FramePresenter;
import com.hellen.baseframe.application.FrameView;
import com.hellen.baseframe.common.dlog.DLog;
import com.hellen.baseframe.common.utiltools.SharePreferHelp;
import com.xiangxun.workorder.base.APP;
import com.xiangxun.workorder.base.AppEnum;
import com.xiangxun.workorder.bean.LoginRoot;
import com.xiangxun.workorder.common.retrofit.RxjavaRetrofitRequestUtil;
import com.xiangxun.workorder.common.urlencode.Tools;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.RequestBody;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;


/**
 * @author zhangyh2 LoginUser 下午3:42:16 TODO 用户登录获取数据传递给了接口
 */
public class LoginListener implements FramePresenter {
    public interface LoginInterface extends FrameView {

        void onLoginSuccess();

        void onLoginFailed();

        String getUserName();

        String getPassword();

        void end();
    }

    /*
     * (non-Javadoc)
     *
     * @see com.hellen.biz.LoginPresent#login(java.lang.String,
     * java.lang.String, com.hellen.biz.OnLoginListener)
     */
    public void login_in(Context context, final String username, final String password,
                         final FrameListener<LoginRoot> listener) {
        // TODO Auto-generated method stub
//        if (!MatcherUtil.isMobile(username)) {
//            listener.onFaild(0, "请输入正确的手机号码");
//            return;
//        }
        if (username == null || username.length() == 0 || password == null
                || password.length() == 0) {
            listener.onFaild(0, "用户名密码不为空");
            return;
        }
//        if (password.length() > 20 || password.length() < 6) {
//            listener.onFaild(0, "密码必须为6-20位");
//            return;
//        }

        if (password.contains(" ") && username.contains(" ")) {
            listener.onFaild(0, "用户名密码不能包含空格");
            return;
        }
        Pattern pattern = Pattern
                .compile("([^\\._\\w\\u4e00-\\u9fa5])*");
        Matcher matcher = pattern.matcher(username);
        if (matcher.matches()) {
            listener.onFaild(0, "用户名不能包含表情");
            return;
        }

        if (!APP.isNetworkConnected(APP.getInstance())) {
            listener.onFaild(0, "网络异常,请检查网络");
            return;
        }

        JSONObject ob = new JSONObject();
        try {
            ob.put("loginName", username);
            ob.put("password", password);
            //ob.put("keyValue", Tools.makeValidate(username, password));
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json;charset=UTF-8"), ob.toString());
        RxjavaRetrofitRequestUtil.getInstance().post()
                .postlogin(body)
                .subscribeOn(Schedulers.io()).unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<JsonObject, LoginRoot>() {
                    @Override
                    public LoginRoot call(JsonObject s) {
                        DLog.json("Func1", s.toString());
                        LoginRoot root = new Gson().fromJson(s, new TypeToken<LoginRoot>() {
                        }.getType());

                        if (root != null && root.getStatus() == 1 && root.getData() != null) {
                            // 对其进行解析。当登录成功时
                            SharePreferHelp.putValue(AppEnum.USERNAME.getDec(), username);
                            SharePreferHelp.putValue(AppEnum.USERREALNAME.getDec(), root.getData().getName());
                            SharePreferHelp.putValue(AppEnum.PASSWORD.getDec(), password);
                            SharePreferHelp.putValue(AppEnum.USERID.getDec(), root.getData().getId());
                        }
                        return root;
                    }
                })
                .subscribe(new Observer<LoginRoot>() {
                               @Override
                               public void onCompleted() {

                               }

                               @Override
                               public void onError(Throwable e) {
                                   listener.onFaild(1, e.getMessage());
                               }

                               @Override
                               public void onNext(LoginRoot data) {
                                   if (data != null) {
                                       if (data.getData() != null && data.getStatus() == 1) {
                                           listener.onSucces(data);
                                       } else {
                                           listener.onFaild(0, data.getMessage());
                                       }
                                   } else {
                                       listener.onFaild(0, "解析错误");
                                   }
                               }
                           }

                );

    }


    public void login_get(Context context, final String username, final String password,
                          final FrameListener<LoginRoot> listener) {
        // TODO Auto-generated method stub
        if (!APP.isNetworkConnected(APP.getInstance())) {
            listener.onFaild(0, "网络异常,请检查网络");
            return;
        }
//        if (!MatcherUtil.isMobile(username)) {
//            listener.onFaild(0, "请输入正确的手机号码");
//            return;
//        }
        if (username == null || username.length() == 0 || password == null
                || password.length() == 0) {
            listener.onFaild(0, "用户名密码不为空");
            return;
        }
//        if (password.length() > 20 || password.length() < 6) {
//            listener.onFaild(0, "密码必须为6-20位");
//            return;
//        }

        if (password.contains(" ") && username.contains(" ")) {
            listener.onFaild(0, "用户名密码不能包含空格");
            return;
        }
        Pattern pattern = Pattern
                .compile("([^\\._\\w\\u4e00-\\u9fa5])*");
        Matcher matcher = pattern.matcher(username);
        if (matcher.matches()) {
            listener.onFaild(0, "用户名不能包含表情");
            return;
        }
        RxjavaRetrofitRequestUtil.getInstance().get()
                .getlogin(username, password, Tools.makeValidate(username, password))
                .subscribeOn(Schedulers.io()).unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<JsonObject, LoginRoot>() {
                    @Override
                    public LoginRoot call(JsonObject s) {
                        DLog.json("Func1", s.toString());
                        LoginRoot root = new Gson().fromJson(s, new TypeToken<LoginRoot>() {
                        }.getType());

                        if (root != null && root.getStatus() == 1 && root.getData() != null) {
                            // 对其进行解析。当登录成功时
                            SharePreferHelp.putValue(AppEnum.USERNAME.getDec(), username);
                            SharePreferHelp.putValue(AppEnum.PASSWORD.getDec(), password);
                            SharePreferHelp.putValue(AppEnum.USERID.getDec(), root.getData().getId());
                            SharePreferHelp.putValue(AppEnum.COOKIE.getDec(), root.getData().getCookie());
                        }
                        return root;
                    }
                })
                .subscribe(new Observer<LoginRoot>() {
                               @Override
                               public void onCompleted() {

                               }

                               @Override
                               public void onError(Throwable e) {
                                   listener.onFaild(1, e.getMessage());
                               }

                               @Override
                               public void onNext(LoginRoot data) {
                                   if (data != null) {
                                       if (data.getData() != null && data.getStatus() == 1) {
                                           listener.onSucces(data);
                                       } else {
                                           listener.onFaild(0, data.getMessage());
                                       }
                                   } else {
                                       listener.onFaild(0, "解析错误");
                                   }
                               }
                           }

                );

    }

    private String getTelNum(Context context) {
        TelephonyManager tm = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);// 取得相关系统服务
        return tm.getLine1Number();
    }

    /*
     * (non-Javadoc)
     *
     * @see com.hellen.biz.LoginInterface#login_out(java.lang.String,
     * java.lang.String, com.hellen.base.BaseListener)
     */
    public void login_out(String username, String password,
                          FrameListener<String> listener) {
    }

    @Override
    public void onStart(Dialog loading) {
        loading.show();
    }

    @Override
    public void onStop(Dialog loading) {
        loading.dismiss();
    }
}
