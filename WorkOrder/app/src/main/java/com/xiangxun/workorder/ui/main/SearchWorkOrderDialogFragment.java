package com.xiangxun.workorder.ui.main;

import android.app.DialogFragment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.xiangxun.workorder.R;
import com.xiangxun.workorder.base.AppEnum;
import com.xiangxun.workorder.widget.header.HeaderView;

import java.util.HashMap;

/**
 * Created by Zhangyuhui/Darly on 2017/5/24.
 * Copyright by [Zhangyuhui/Darly]
 * ©2017 XunXiang.Company. All rights reserved.
 *
 * @TODO:搜索条件列表窗口。
 */
public class SearchWorkOrderDialogFragment extends DialogFragment implements View.OnClickListener {


    public interface SearchListener {
        void findParamers(String name, String num, String ip);
    }

    private View view;
    private HeaderView header;
    private EditText name;
    private EditText num;
    private EditText ip;
    private Button commit;
    private Button consel;

    //重写onCreateView方法
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        view = inflater.inflate(R.layout.fragment_dialog_search, container);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        initListener();
    }

    @Override
    public void onStart() {
        super.onStart();
        Window window = getDialog().getWindow();
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));//注意此处
        window.setLayout(AppEnum.WIDTH.getLen(), AppEnum.HEIGHT.getLen());//这2行,和上面的一样,注意顺序就行;
    }

    private void initView() {
        header = (HeaderView) view.findViewById(R.id.id_search_header);
        name = (EditText) view.findViewById(R.id.id_search_name);
        num = (EditText) view.findViewById(R.id.id_search_num);
        ip = (EditText) view.findViewById(R.id.id_search_ip);
        commit = (Button) view.findViewById(R.id.id_search_commit);
        consel = (Button) view.findViewById(R.id.id_search_consel);

        header.setLeftBackgroundResource(R.mipmap.ic_title_back);
        header.setTitle(R.string.main_work_order_search);
        header.setRightBackgroundResource(0);


    }

    private void initListener() {
        header.setLeftBackOneListener(this);
        commit.setOnClickListener(this);
        consel.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.id_search_commit:
                String devicename = name.getText().toString().trim();
                String devicenum = num.getText().toString().trim();
                String deviceip = ip.getText().toString().trim();
                ((SearchListener) getActivity()).findParamers(devicename, devicenum, deviceip);
                break;
            case R.id.title_view_back_llayout:
                break;
            case R.id.id_search_consel:
                break;
            default:
                break;
        }
        dismiss();
    }
}
