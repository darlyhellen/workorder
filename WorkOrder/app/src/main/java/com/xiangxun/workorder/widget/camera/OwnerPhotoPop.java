package com.xiangxun.workorder.widget.camera;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;

import com.hellen.baseframe.common.dlog.DLog;
import com.hellen.baseframe.common.obsinfo.ToastApp;
import com.xiangxun.workorder.R;
import com.xiangxun.workorder.base.AppEnum;
import com.xiangxun.workorder.common.image.ImageLoaderUtil;

/**
 * Created by Darly on 2017/9/13.
 */
public class OwnerPhotoPop extends PopupWindow implements View.OnClickListener {

    /**
     * 下午1:29:10 TODO 系统参数。
     */
    private Context context;

    private Button item_popupwindows_camera;

    private Button item_popupwindows_Photo;

    private Button item_popupwindows_cancel;

    private int size;
    private String path;

    public OwnerPhotoPop(Context context) {
        super();
        this.context = context;
        init();
    }
    /**
     * 下午1:29:54
     *
     * @author Zhangyuhui PhotoPop.java TODO 初始化控件集合。
     */
    private void init() {
        // TODO Auto-generated method stub

        View view = LayoutInflater.from(context).inflate(R.layout.popupwindows,
                null);
        item_popupwindows_camera = (Button) view
                .findViewById(R.id.item_popupwindows_camera);
        item_popupwindows_camera.setOnClickListener(this);
        item_popupwindows_Photo = (Button) view
                .findViewById(R.id.item_popupwindows_Photo);
        item_popupwindows_Photo.setOnClickListener(this);
        item_popupwindows_cancel = (Button) view
                .findViewById(R.id.item_popupwindows_cancel);
        item_popupwindows_cancel.setOnClickListener(this);

        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        setFocusable(true);
        setOutsideTouchable(true);
        setContentView(view);
    }
    /**
     * @param v 下午3:15:27
     * @author Zhangyuhui PhotoPop.java TODO 展示POP
     */
    public void show(View v,int size,String path) {
        this.size = size;
        this.path = path;
        showAtLocation(v, Gravity.CENTER, 0, 0);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.item_popupwindows_camera:
                // 照相功能
                if (ImageLoaderUtil.isCameraUseable()) {
                Intent intentCamera = new Intent(context, CameraActivity.class);
                intentCamera.putExtra("size", size);
                intentCamera.putExtra("file", path);
                intentCamera.setAction("Sence");
                intentCamera.putExtra("LOGO", false);//不打印水印
                ((Activity)context).startActivityForResult(intentCamera, 1);
                } else {
                    ToastApp.showToast("需要调用摄像头权限，请在设置中打开摄像头权限");
                }
                DLog.i("item_popupwindows_camera");
                break;
            case R.id.item_popupwindows_Photo:
                // 相册功能
                Intent intentAlbum = new Intent();
                intentAlbum.putExtra("size", size);
                intentAlbum.setAction("publishFourPhotos");
                intentAlbum.setClass(context, PhotoSelectActivity.class);
                ((Activity)context).startActivityForResult(intentAlbum, 99);
                DLog.i("item_popupwindows_Photo");
                break;
            case R.id.item_popupwindows_cancel:
                // 取消
            default:
                break;
        }
        dismiss();
    }
}
