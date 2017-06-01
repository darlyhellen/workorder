package com.xiangxun.workorder.common;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import com.xiangxun.workorder.base.AppEnum;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Zhangyuhui/Darly on 2017/5/31.
 * Copyright by [Zhangyuhui/Darly]
 * ©2017 XunXiang.Company. All rights reserved.
 *
 * @TODO: 将Assets中的文件复制到文件夹中。
 */
public class Aset {
    private static String filename = "xian.dat";

    public static void copyAssets(Context context) {

        AssetManager assetManager = context.getAssets();
        InputStream in = null;
        OutputStream out = null;
        try {
            in = assetManager.open(filename);
            File outFile = new File(AppEnum.MAP, filename);
            if (!outFile.exists()) {
                out = new FileOutputStream(outFile);
                copyFile(in, out);
            }
        } catch (IOException e) {
            //初始化失败
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                }
            }
        }
    }

    private static void copyFile(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int read;
        while ((read = in.read(buffer)) != -1) {
            out.write(buffer, 0, read);
        }
    }

}
