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

import com.hellen.baseframe.binder.ViewsBinder;
import com.xiangxun.workorder.R;
import com.xiangxun.workorder.base.AppEnum;
import com.xiangxun.workorder.widget.header.HeaderView;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Zhangyuhui/Darly on 2017/5/24.
 * Copyright by [Zhangyuhui/Darly]
 * ©2017 XunXiang.Company. All rights reserved.
 *
 * @TODO:搜索条件列表窗口。
 */
public class SearchWorkOrderDialogFragment extends DialogFragment implements View.OnClickListener {


    public interface SearchListener {
        void findParamers(Map<String, String> map);
    }

    private HeaderView title;
    private View view;
    private Button button;
    private Button insert;
    private Button update;
    private Button select;
    private Button delete;
    private Button down;
    private Button video;

    private Map<String, String> map;

    //重写onCreateView方法
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        view = inflater.inflate(R.layout.fragment_dialog_search, container);

        Window dialogWindow = getDialog().getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        dialogWindow.setGravity(Gravity.CENTER);
        lp.width = AppEnum.WIDTH.getLen();
        lp.height = AppEnum.HEIGHT.getLen();
        dialogWindow.setAttributes(lp);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        initListener();
    }

    private void initView() {
        map = new HashMap<String, String>();
        title = (HeaderView) view.findViewById(R.id.comment_title);
        title.setTitle(R.string.main_work_order_search);
        title.setLeftBackgroundResource(R.mipmap.back_image);
        button = (Button) view.findViewById(R.id.button);
        insert = (Button) view.findViewById(R.id.insert);
        update = (Button) view.findViewById(R.id.update);
        select = (Button) view.findViewById(R.id.select);
        delete = (Button) view.findViewById(R.id.delete);
        down = (Button) view.findViewById(R.id.down);
        video = (Button) view.findViewById(R.id.video);
    }

    private void initListener() {
        title.setLeftBackOneListener(this);
        button.setOnClickListener(this);
        insert.setOnClickListener(this);
        update.setOnClickListener(this);
        select.setOnClickListener(this);
        delete.setOnClickListener(this);
        down.setOnClickListener(this);
        video.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_view_back_llayout:
                break;
            case R.id.button:
                map.put(button.getText().toString(), "R.id.button");
                ((SearchListener) getActivity()).findParamers(map);
                break;
            case R.id.insert:
                map.put(insert.getText().toString(), "R.id.insert");
                ((SearchListener) getActivity()).findParamers(map);
                break;
            case R.id.update:
                map.put(update.getText().toString(), "R.id.update");
                ((SearchListener) getActivity()).findParamers(map);
                break;
            case R.id.select:
                map.put(select.getText().toString(), "R.id.select");
                ((SearchListener) getActivity()).findParamers(map);
                break;
            case R.id.delete:
                map.put(delete.getText().toString(), "R.id.delete");
                ((SearchListener) getActivity()).findParamers(map);
                break;
            case R.id.down:
                map.put(down.getText().toString(), "R.id.down");
                ((SearchListener) getActivity()).findParamers(map);
                break;
            case R.id.video:
                map.put(video.getText().toString(), "R.id.video");
                ((SearchListener) getActivity()).findParamers(map);
                break;
            default:
                break;
        }
        dismiss();
    }

}
