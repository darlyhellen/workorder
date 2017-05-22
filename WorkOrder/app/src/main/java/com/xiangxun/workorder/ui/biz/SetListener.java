package com.xiangxun.workorder.ui.biz;

import android.app.Dialog;
import android.os.AsyncTask;
import android.os.Message;

import com.hellen.baseframe.application.FrameListener;
import com.hellen.baseframe.application.FramePresenter;
import com.hellen.baseframe.application.FrameView;
import com.xiangxun.workorder.base.AppEnum;
import com.xiangxun.workorder.bean.SetModel;
import com.xiangxun.workorder.bean.WorkOrderData;
import com.xiangxun.workorder.common.urlencode.Tools;

import java.io.File;
import java.util.List;

/**
 * Created by Zhangyuhui/Darly on 2017/5/22.
 * Copyright by [Zhangyuhui/Darly]
 * ©2017 XunXiang.Company. All rights reserved.
 *
 * @TODO:设置页面接口
 */
public class SetListener implements FramePresenter {

    public interface SetInterface extends FrameView {

        void getUserDate(List<SetModel> data);

        void end();
    }


    @Override
    public void onStart(Dialog dialog) {

    }

    public void getSize(FrameListener<Long> listener) {
        new GetCacheSizeTask(listener).execute();
    }

    @Override
    public void onStop(Dialog dialog) {

    }

    private class GetCacheSizeTask extends AsyncTask<Object, Object, Long> {

        FrameListener<Long> listener;

        public GetCacheSizeTask(FrameListener<Long> listener) {
            this.listener = listener;
        }

        @Override
        protected Long doInBackground(Object... params) {
            long lenth = 0;
            File imageCacheFile = new File(AppEnum.ROOT);
            if (imageCacheFile.exists()) {
                try {
                    lenth = Tools.getFileSize(imageCacheFile);
                } catch (Exception e) {
                    lenth = 0;
                }
            } else {
                lenth = 0;
            }
            return lenth;
        }


        @Override
        protected void onPostExecute(Long result) {
            listener.onSucces(result);
        }
    }

}
