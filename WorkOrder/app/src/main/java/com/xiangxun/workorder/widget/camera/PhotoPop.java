package com.xiangxun.workorder.widget.camera;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.MediaStore.MediaColumns;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.PopupWindow;

import com.hellen.baseframe.common.dlog.DLog;
import com.xiangxun.workorder.R;
import com.xiangxun.workorder.base.ConsMVP;

import net.bither.util.NativeUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class PhotoPop extends PopupWindow implements OnClickListener {

    private String rota = "ROTATE.png";

    public PhotoPop(Context context) {
        super();
        this.context = context;
        init();
    }

    /**
     * 下午1:29:10 TODO 系统参数。
     */
    private Context context;

    private Button item_popupwindows_camera;

    private String capUri;

    private Button item_popupwindows_Photo;

    private Button item_popupwindows_cancel;

    private Uri photoUri;

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

        setWidth(LayoutParams.MATCH_PARENT);
        setHeight(LayoutParams.WRAP_CONTENT);
        setFocusable(true);
        setOutsideTouchable(true);
        setContentView(view);

    }

    /*
     * (non-Javadoc)
     *
     * @see android.view.View.OnClickListener#onClick(android.view.View)
     */
    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.item_popupwindows_camera:
                // 照相功能
                capPhoto();
                DLog.i("item_popupwindows_camera");
                break;
            case R.id.item_popupwindows_Photo:
                // 相册功能
                albumPhoto();
                DLog.i("item_popupwindows_Photo");
                break;
            case R.id.item_popupwindows_cancel:
                // 取消
            default:
                break;
        }
        dismiss();
    }

    /**
     * 下午1:56:36
     *
     * @author Zhangyuhui PhotoPop.java TODO 相册内容
     */
    private void albumPhoto() {
        // TODO Auto-generated method stub
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_PICK);
        ((Activity) context).startActivityForResult(intent,
                ConsMVP.REQUESTCODE_CAM);
        DLog.i("albumPhoto");
    }

    /**
     * 下午1:54:52
     *
     * @author Zhangyuhui PhotoPop.java TODO 相机内容
     */
    private void capPhoto() {
        // 照相
        capUri = ConsMVP.ROOT + System.currentTimeMillis() + ".png";
        ConsMVP.capUri = capUri;
        File destDir = new File(ConsMVP.ROOT);
        if (!destDir.exists()) {
            destDir.mkdirs();
        }

        File file = new File(capUri);
        if (!file.exists()) {
            try {
                // 在指定的文件夹中创建文件
                file.createNewFile();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // 指定照片保存路径（SD卡），image.jpg为一个临时文件，每次拍照后这个图片都会被替换
        openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                Uri.fromFile(new File(capUri)));
        ((Activity) context).startActivityForResult(openCameraIntent,
                ConsMVP.REQUESTCODE_CAP);
        DLog.i("capPhoto");

    }

    /**
     * @param uri 下午2:07:46
     * @author Zhangyuhui PhotoPop.java TODO 調用手機裁剪功能。
     */
    public void cropPhoto(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data", true);
        ((Activity) context).startActivityForResult(intent,
                ConsMVP.REQUESTCODE_CUT);
        DLog.i("cropPhoto");
    }


    /**
     * @param data
     * @param tag
     * @return 下午3:14:45
     * @author Zhangyuhui PhotoPop.java TODO 回调方法获取图片路径
     */
    public String PopStringActivityResult(Intent data, int tag) {
        DLog.i("PopStringActivityResult");
        switch (tag) {
            case ConsMVP.REQUESTCODE_CAP:
                // 照相机程序返回的
                return /* capUri */getImagePathForCAP(capUri);
            case ConsMVP.REQUESTCODE_CAM:
                // 照片的原始资源地址
                photoUri = data.getData();
                DLog.i(getImagePath(photoUri));
                return getImagePath(photoUri);
            default:
                break;
        }

        return null;

    }

    /**
     * @return 上午10:23:25
     * @author Zhangyuhui PhotoPop.java TODO 获取相机拍照完成后的文件路径
     */
    private String getImagePathForCAP(String capUri) {
        // TODO Auto-generated method stub
        if (capUri == null) {
            return null;
        }
        // 由于相机拍照照片过大。故而必须进行压缩。
        int degree = getBitmapDegree(capUri);
        if (degree == 0) {
            return capUri;
        } else {
            // 先压缩图片。然后旋转图片。最后保存图片。
            rotateBitmapByDegree(compressImageCap(capUri), degree);
            return ConsMVP.ROOT + rota;
        }
    }

    /**
     * @author Zhangyuhui PhotoPop.java TODO 对原始图片进行压缩。
     */
    private Bitmap compressImageCap(String capUri) {
        // TODO Auto-generated method stub
        // 对大图进行压缩。小图直接进行旋转。
        File file = new File(capUri);
        if (file.exists()) {
            // 计算文件大小。进行对比。大于320则进行压缩。
            int file_size = (int) (file.length() / 1024);
            Bitmap bit = null;
            if (file_size > 320) {
                bit = decodeSampledBitmapFromFile(capUri, 480, 640);
                // 获取图片大小的比对关系。是100KB的多少。
                int quality = 1024 * 100 / file_size;
                return compressBitmap(bit, quality);
            } else {
                try {
                    FileInputStream is = new FileInputStream(capUri);
                    bit = BitmapFactory.decodeStream(is);

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                // 按质量压缩
                return compressBitmap(bit, 100);
            }
        }
        return null;
    }

    /**
     * 官网：获取压缩后的图片
     *
     * @param reqWidth  所需图片压缩尺寸最小宽度
     * @param reqHeight 所需图片压缩尺寸最小高度
     * @return
     */
    public Bitmap decodeSampledBitmapFromFile(String filepath, int reqWidth,
                                              int reqHeight) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filepath, options);
        // 计算压缩比例。
        options.inSampleSize = calculateInSampleSize(options, reqWidth,
                reqHeight);
        options.inJustDecodeBounds = false;
        options.inPreferredConfig = Config.RGB_565;
        options.inDither = true;
        return BitmapFactory.decodeFile(filepath, options);
    }

    /**
     * 计算压缩比例值(改进版 by touch_ping) 原版2>4>8...倍压缩 当前2>3>4...倍压缩
     *
     * @param options   解析图片的配置信息
     * @param reqWidth  所需图片压缩尺寸最小宽度O
     * @param reqHeight 所需图片压缩尺寸最小高度
     * @return
     */
    public int calculateInSampleSize(BitmapFactory.Options options,
                                     int reqWidth, int reqHeight) {

        final int picheight = options.outHeight;
        final int picwidth = options.outWidth;
        int targetheight = picheight;
        int targetwidth = picwidth;
        int inSampleSize = 1;
        if (targetheight > reqHeight || targetwidth > reqWidth) {
            while (targetheight >= reqHeight && targetwidth >= reqWidth) {
                inSampleSize += 1;
                targetheight = picheight / inSampleSize;
                targetwidth = picwidth / inSampleSize;
            }
        }
        return inSampleSize;
    }

    /**
     * @param bit
     * @param quality
     * @return 上午10:49:02
     * @author Zhangyuhui PhotoPop.java TODO 进行图片对照比例压缩，比例为quality
     */
    public Bitmap compressBitmap(final Bitmap bit, int quality) {
        // int quality = 10;// 同学们可以与原生的压缩方法对比一下，同样设置成50效果如何
        // 获取文件存储的文件夹。
        File dirFile = new File(ConsMVP.ROOT);
        if (!dirFile.exists()) {
            dirFile.mkdirs();
        }
        // 压缩后文件存储名称为固定名称。压缩后的文件路径。
        File jpegTrueFile = new File(dirFile, rota);
        NativeUtil.compressBitmap(bit, quality, jpegTrueFile.getAbsolutePath(),
                true);
        recycleBitmap(bit);
        return BitmapFactory.decodeFile(jpegTrueFile.getAbsolutePath());

    }

    /**
     * 需要旋转的图片
     *
     * @param degree 旋转角度
     * @return 旋转后的图片 上午10:44:08
     * @author Zhangyuhui MeDetailsAcitvity.java TODO将图片按照某个角度进行旋转
     */
    private void rotateBitmapByDegree(Bitmap bitmap, int degree) {
        Bitmap returnBm = null;

        // 根据旋转角度，生成旋转矩阵
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        try {
            // 将原始图片按照旋转矩阵进行旋转，并得到新的图片
            returnBm = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                    bitmap.getHeight(), matrix, true);
            if (returnBm == null) {
                returnBm = bitmap;
            }
            if (bitmap != returnBm) {
                recycleBitmap(bitmap);
            }
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
        }

    }

    /**
     * @param originalUri
     * @return 下午3:15:15
     * @author Zhangyuhui PhotoPop.java TODO 获取相冊图片路径
     */
    public String getImagePath(Uri originalUri) {
        String[] proj = {MediaColumns.DATA};

        if (originalUri.toString().contains("file")) {
            String urlString = originalUri.toString().replaceAll("file://", "");
            return urlString;
        }

        // 好像是android多媒体数据库的封装接口，具体的看Android文档
        Cursor cursor = null;
        try {
            cursor = context.getContentResolver().query(originalUri, proj,
                    null, null, null);

            // 按我个人理解 这个是获得用户选择的图片的索引值
            int column_index = cursor.getColumnIndexOrThrow(MediaColumns.DATA);
            // 将光标移至开头 ，这个很重要，不小心很容易引起越界
            cursor.moveToFirst();
            // 最后根据索引值获取图片路径
            String path = cursor.getString(column_index);
            return path;
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return null;
    }

    /**
     * @param path
     * @return 上午10:42:09
     * @author Zhangyuhui MeDetailsAcitvity.java TODO 获取图片的旋转角度。
     */
    public int getBitmapDegree(String path) {
        int degree = 0;
        try {
            // 从指定路径下读取图片，并获取其EXIF信息
            ExifInterface exifInterface = new ExifInterface(path);
            // 获取图片的旋转信息
            int orientation = exifInterface.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    /**
     * @param v 下午3:15:27
     * @author Zhangyuhui PhotoPop.java TODO 展示POP
     */
    public void show(View v) {
        showAtLocation(v, Gravity.CENTER, 0, 0);
    }

    /**
     * @return the capUri
     */
    public String getCapUri() {
        return capUri;
    }

    /**
     * 回收bitmap
     *
     * @param bit
     */
    public static void recycleBitmap(Bitmap bit) {
        if (bit != null && !bit.isRecycled()) {
            bit.recycle();
            bit = null;
            System.gc();
        }
    }
}
