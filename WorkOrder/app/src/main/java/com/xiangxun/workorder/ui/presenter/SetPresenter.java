package com.xiangxun.workorder.ui.presenter;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.hellen.baseframe.application.FrameListener;
import com.hellen.baseframe.common.dlog.DLog;
import com.hellen.baseframe.common.utiltools.SharePreferHelp;
import com.xiangxun.workorder.R;
import com.xiangxun.workorder.base.APP;
import com.xiangxun.workorder.base.AppEnum;
import com.xiangxun.workorder.bean.SetModel;
import com.xiangxun.workorder.ui.biz.SetListener;
import com.xiangxun.workorder.ui.login.LoginActivity;
import com.xiangxun.workorder.ui.main.SetActivity;
import com.xiangxun.workorder.widget.dialog.APPDialg;
import com.xiangxun.workorder.widget.dialog.OndialogListener;
import com.xiangxun.workorder.widget.loading.ShowLoading;

import java.io.File;
import java.util.ArrayList;
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
        this.loading = new ShowLoading((SetActivity) view);
        this.loading.setMessage(R.string.loading);
    }

    public void findFileSize() {
        biz.onStart(loading);
        biz.getSize(new FrameListener<Long>() {
            @Override
            public void onSucces(Long aLong) {
                biz.onStop(loading);
                List<SetModel> datas = new ArrayList<>();
                datas.add(new SetModel(R.string.set_clean_cache, R.string.curr_cache, (aLong / (1024 * 1024)) + "M", R.mipmap.arrows, false));
                datas.add(new SetModel(R.string.set_update, R.string.curr_ver, APP.getAppVersionName(), R.mipmap.arrows, false));
                datas.add(new SetModel(R.string.set_loginout, 0, null, 0, true));
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
                DLog.i("搜索按钮点击");
                break;
            default:
                break;
        }
    }


    public void clickClean(Context context) {
        APPDialg dialg = new APPDialg(context);
        dialg.setTitle(R.string.set_decl);
        dialg.setContent(R.string.set_clean_cache_des);
        dialg.setSure(R.string.set_sure);
        dialg.setConsel(R.string.consel);
        dialg.setOndialogListener(new OndialogListener() {
            @Override
            public void onSureClick() {
                RecursionDeleteFile(new File(AppEnum.ROOT));
                APP.getInstance().creatFile();
                findFileSize();
            }

            @Override
            public void onConselClick() {

            }
        });
    }

    public void clickUpdate(Context context) {
        APPDialg dialg = new APPDialg(context);
        dialg.setTitle(R.string.set_decl);
        dialg.setContent(R.string.set_update_des);
        dialg.setSure(R.string.set_sure);
        dialg.setConsel(R.string.consel);
        dialg.setOndialogListener(new OndialogListener() {
            @Override
            public void onSureClick() {

            }

            @Override
            public void onConselClick() {

            }
        });
    }

    public void clickLoginOut(final Context context) {
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
