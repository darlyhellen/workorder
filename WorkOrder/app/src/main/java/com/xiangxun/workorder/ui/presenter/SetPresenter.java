package com.xiangxun.workorder.ui.presenter;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.hellen.baseframe.application.FrameListener;
import com.hellen.baseframe.common.multithread.MultithreadDownLoadCommon;
import com.hellen.baseframe.common.utiltools.SharePreferHelp;
import com.xiangxun.workorder.R;
import com.xiangxun.workorder.base.APP;
import com.xiangxun.workorder.base.Api;
import com.xiangxun.workorder.base.AppEnum;
import com.xiangxun.workorder.base.BaseActivity;
import com.xiangxun.workorder.bean.SetModel;
import com.xiangxun.workorder.bean.VersionRoot;
import com.xiangxun.workorder.ui.biz.SetListener;
import com.xiangxun.workorder.ui.login.LoginActivity;
import com.xiangxun.workorder.widget.dialog.APPDialg;
import com.xiangxun.workorder.widget.dialog.OndialogListener;
import com.xiangxun.workorder.widget.loading.ShowLoading;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Zhangyuhui/Darly on 2017/5/22.
 * Copyright by [Zhangyuhui/Darly]
 * ©2017 XunXiang.Company. All rights reserved.
 *
 * @TODO:
 */
public class SetPresenter {

    private SetListener biz;

    private SetListener.SetInterface view;

    private ShowLoading loading;


    public SetPresenter(SetListener.SetInterface view) {
        this.view = view;
        this.biz = new SetListener();
        this.loading = new ShowLoading((BaseActivity) view);
        this.loading.setMessage(R.string.loading);
    }

    public void findFileSize(final int login) {
        biz.onStart(loading);
        biz.getSize(new FrameListener<Long>() {
            @Override
            public void onSucces(Long aLong) {
                biz.onStop(loading);
                List<SetModel> datas = new ArrayList<>();
                datas.add(new SetModel(R.string.set_service, R.string.curr_service, Api.getIp(), R.mipmap.arrows, false));
                datas.add(new SetModel(R.string.set_clean_cache, R.string.curr_cache, (aLong / (1024 * 1024)) + "M", R.mipmap.arrows, false));
                datas.add(new SetModel(R.string.set_update, R.string.curr_ver, APP.getAppVersionName(), R.mipmap.arrows, false));
                if (login != 0) {
                    datas.add(new SetModel(R.string.set_loginout, 0, null, 0, true));
                }
                view.getUserDate(datas);
            }

            @Override
            public void onFaild(int i, String s) {
                biz.onStop(loading);
            }
        });
    }


    /**
     * @param context
     * @param v       TODO点击事件在这里进行处理
     */
    public void onClickDown(Context context, View v) {
        switch (v.getId()) {
            case R.id.title_view_back_llayout:
                view.end();
                break;
            case R.id.xw_share:
                break;
            default:
                break;
        }
    }


    public void clickClean(final Context context, final int login) {
        APPDialg dialg = new APPDialg(context);
        dialg.setTitle(R.string.set_decl);
        dialg.setContent(R.string.set_clean_cache_des);
        dialg.setSure(R.string.set_sure);
        dialg.setConsel(R.string.consel);
        dialg.setOndialogListener(new OndialogListener() {
            @Override
            public void onSureClick() {
                RecursionDeleteFile(new File(AppEnum.CACTH));
                APP.getInstance().creatFile();
                findFileSize(login);
            }

            @Override
            public void onConselClick() {

            }
        });
    }

    public void clickUpdate(final Context context, final boolean isLogin) {
        //先请求版本更新接口,获取版本更新内容,判断是否是最新版本,不是最新版本,提示更新.
        if (!isLogin) {
            biz.onStart(loading);
        }
        biz.findNewVersion(APP.getInstance().getVersionCode(), new FrameListener<VersionRoot>() {
            @Override
            public void onSucces(final VersionRoot versionRoot) {
                if (!isLogin) {
                    biz.onStop(loading);
                }
                if (versionRoot != null && versionRoot.getData() != null) {
                    if (versionRoot.getData().getVersion() > APP.getInstance().getVersionCode()) {
                        APPDialg dialg = new APPDialg(context);
                        dialg.setViewVisible();
                        dialg.setTitle(R.string.set_decl);
                        dialg.setContent(versionRoot.getData().getRemark());
                        dialg.setSure("更新");
                        dialg.setConsel("暂不更新");
                        dialg.setOndialogListener(new OndialogListener() {
                            @Override
                            public void onSureClick() {
                                MultithreadDownLoadCommon.ISPUASE = false;
                                view.onStartDownVersion(versionRoot);
                            }

                            @Override
                            public void onConselClick() {
                                if (isLogin) {
                                    Calendar calendar = Calendar.getInstance();
                                    calendar.set(Calendar.DAY_OF_YEAR,
                                            calendar.get(Calendar.DAY_OF_YEAR) + 7);
                                    SharePreferHelp.putValue(AppEnum.NOTUPDATE.getDec(),
                                            calendar.get(Calendar.DAY_OF_YEAR));
                                }
                            }
                        });
                    } else {
                        if (!isLogin) {
                            APPDialg dialg = new APPDialg(context);
                            dialg.setViewInvisible();
                            dialg.setContent("已经是最新版本");
                            dialg.setSure(R.string.set_sure);
                        }

                    }
                }
            }

            @Override
            public void onFaild(int i, String s) {
                if (!isLogin) {
                    biz.onStop(loading);
                    APPDialg dialg = new APPDialg(context);
                    dialg.setViewInvisible();
                    dialg.setContent("已经是最新版本");
                    dialg.setSure(R.string.set_sure);
                }
            }
        });
    }

    public void clickLoginOut(final Context context) {
        //退出登录，清空缓存
        APPDialg dialg = new APPDialg(context);
        dialg.setTitle(R.string.set_decl);
        dialg.setContent(R.string.set_loginout_des);
        dialg.setSure(R.string.set_sure);
        dialg.setConsel(R.string.consel);
        dialg.setOndialogListener(new OndialogListener() {
            @Override
            public void onSureClick() {
                SharePreferHelp.remove(AppEnum.USERNAME.getDec());
                SharePreferHelp.remove(AppEnum.PASSWORD.getDec());
                SharePreferHelp.remove(AppEnum.USERID.getDec());

                Intent intent = new Intent(context, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_TASK_ON_HOME);
                context.startActivity(intent);
                view.end();
            }

            @Override
            public void onConselClick() {

            }
        });
    }


    /**
     * @param file TODO：删除文件夹下所有文件。
     */
    public void RecursionDeleteFile(File file) {
        if (file.isFile()) {
            file.delete();
            return;
        }
        if (file.isDirectory()) {
            File[] childFile = file.listFiles();
            if (childFile == null || childFile.length == 0) {
                file.delete();
                return;
            }
            for (File f : childFile) {
                RecursionDeleteFile(f);
            }
            file.delete();
        }
    }
}
