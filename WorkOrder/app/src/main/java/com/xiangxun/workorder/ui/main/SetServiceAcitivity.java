package com.xiangxun.workorder.ui.main;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

import com.hellen.baseframe.binder.ContentBinder;
import com.hellen.baseframe.binder.ViewsBinder;
import com.xiangxun.workorder.R;
import com.xiangxun.workorder.base.Api;
import com.xiangxun.workorder.base.BaseActivity;
import com.xiangxun.workorder.widget.header.HeaderView;

/**
 * Created by Zhangyuhui/Darly on 2017/6/2.
 * Copyright by [Zhangyuhui/Darly]
 * ©2017 XunXiang.Company. All rights reserved.
 *
 * @TODO:
 */
@ContentBinder(R.layout.activity_set_service)
public class SetServiceAcitivity extends BaseActivity implements OnClickListener {

    @ViewsBinder(R.id.id_set_ser_title)
    private HeaderView header;

    @ViewsBinder(R.id.id_set_ser_ip)
    private EditText ip;
    @ViewsBinder(R.id.id_set_ser_port)
    private EditText port;

    @Override
    protected void initView(Bundle savedInstanceState) {
        header.getTitleViewOperationText().setText(R.string.st_set_serv_set);
        header.setTitle(R.string.st_set_serv_title);
        header.setLeftBackgroundResource(R.mipmap.ic_title_back);
        ip.setText(Api.getIp());
        port.setText(Api.getPort());
    }

    @Override
    protected void initListener() {
        header.setLeftBackOneListener(this);
        header.setRightImageTextFlipper(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_view_back_llayout:
                break;
            case R.id.title_view_right_Flipper01:
                //设置输入的IP地址和端口。
                String oip = ip.getText().toString().trim();
                String opr = port.getText().toString().trim();
                Api.setRoot(oip, opr);
                setResult(700);
                break;
        }
        finish();
    }
}
