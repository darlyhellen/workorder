package com.xiangxun.workorder;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.hellen.baseframe.binder.ContentBinder;
import com.hellen.baseframe.binder.ViewsBinder;
import com.hellen.baseframe.common.dlog.DLog;
import com.xiangxun.workorder.base.BaseActivity;
import com.xiangxun.workorder.base.ConsMVP;
import com.xiangxun.workorder.widget.PhotoPop;

import java.io.File;

@ContentBinder(R.layout.activity_main)
public class MainActivity extends BaseActivity implements View.OnClickListener {

    /**
     * 上午9:29:04 TODO 调出选项的POP窗口，主要为相机，相册，取消
     */
    public PhotoPop pop;
    @ViewsBinder(R.id.button)
    private Button button;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void initView(Bundle savedInstanceState) {
        pop = new PhotoPop(this);
    }

    @Override
    protected void initListener() {
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        pop.show(button);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        DLog.i("onActivityResult" + requestCode + resultCode);
        if (requestCode == ConsMVP.REQUESTCODE_CUT) {
            // 裁剪
            if (data != null) {
                Bundle extras = data.getExtras();
                Bitmap head = extras.getParcelable("data");
            }
        } else if (requestCode == ConsMVP.REQUESTCODE_CAM
                || requestCode == ConsMVP.REQUESTCODE_CAP) {

            // 拍照或相册
            String head_path = null;
            if (data == null) {
                if (pop == null) {
                    head_path = ConsMVP.capUri;
                } else {
                    head_path = pop.PopStringActivityResult(null,
                            ConsMVP.REQUESTCODE_CAP);
                }
            } else {
                head_path = pop.PopStringActivityResult(data,
                        ConsMVP.REQUESTCODE_CAM);
            }
            if (head_path == null) {
                return;
            }
            DLog.i(head_path);
            File temp = new File(head_path);
            pop.cropPhoto(Uri.fromFile(temp));// 裁剪图片
        }
    }
}
