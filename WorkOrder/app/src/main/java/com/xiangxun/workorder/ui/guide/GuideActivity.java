package com.xiangxun.workorder.ui.guide;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.hellen.baseframe.binder.ContentBinder;
import com.hellen.baseframe.binder.ViewsBinder;
import com.xiangxun.workorder.R;
import com.xiangxun.workorder.base.AppEnum;
import com.xiangxun.workorder.base.BaseActivity;
import com.xiangxun.workorder.ui.login.LoginActivity;

/**
 * Created by Zhangyuhui/Darly on 2017/6/20.
 * Copyright by [Zhangyuhui/Darly]
 * ©2017 XunXiang.Company. All rights reserved.
 *
 * @TODO:
 */
@ContentBinder(R.layout.activity_guide)
public class GuideActivity extends BaseActivity {

    @ViewsBinder(R.id.id_guide_iv)
    private ImageView iv;

    @Override
    protected void initView(Bundle savedInstanceState) {
        String url = AppEnum.ROOT+"loading.gif";
        Glide.with(this).asGif().load(url).into(iv);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(GuideActivity.this, LoginActivity.class));
            }
        },1000);
    }

    @Override
    protected void initListener() {

    }
}
