package com.xiangxun.workorder;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.hellen.baseframe.binder.ContentBinder;
import com.hellen.baseframe.binder.ViewsBinder;
import com.hellen.baseframe.common.dlog.DLog;
import com.xiangxun.workorder.base.BaseActivity;
import com.xiangxun.workorder.base.ConsMVP;
import com.xiangxun.workorder.db.TestBean;
import com.xiangxun.workorder.widget.camera.PhotoPop;

import java.io.File;
import java.util.List;
import java.util.Map;

@ContentBinder(R.layout.activity_main)
public class MainActivity extends BaseActivity implements View.OnClickListener {

    /**
     * 上午9:29:04 TODO 调出选项的POP窗口，主要为相机，相册，取消
     */
    public PhotoPop pop;
    @ViewsBinder(R.id.button)
    private Button button;
    @ViewsBinder(R.id.insert)
    private Button insert;
    @ViewsBinder(R.id.update)
    private Button update;
    @ViewsBinder(R.id.select)
    private Button select;
    @ViewsBinder(R.id.delete)
    private Button delete;

    TestBean bean;

    @Override
    protected void initView(Bundle savedInstanceState) {
        pop = new PhotoPop(this);
        bean = new TestBean();
        bean.setName("qiangyu");
        bean.setGender("male");
        bean.setAge(23);

    }

    @Override
    protected void initListener() {
        button.setOnClickListener(this);
        insert.setOnClickListener(this);
        update.setOnClickListener(this);
        select.setOnClickListener(this);
        delete.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:
                pop.show(button);
                break;
            case R.id.insert:
                bean.insert();
                break;
            case R.id.update:
                bean.update(new String[]{"name"}, new String[]{"qiangyu"});
                break;
            case R.id.select:
                List<Map> list = bean.queryListMap("select * from " + TestBean.class.getSimpleName(), null);
                DLog.i("SELECT" + "--" + String.valueOf(list));
                break;
            case R.id.delete:
                bean.delete(
                        new String[]{"name"}, new String[]{"qiangyu"});
                break;
        }
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (bean!=null){
            bean.close();
        }
    }
}
