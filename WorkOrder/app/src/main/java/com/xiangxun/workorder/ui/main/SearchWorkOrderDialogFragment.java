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

    private View view;
    private EditText editText;
    private EditText insert;
    private EditText update;
    private EditText select;
    private EditText delete;
    private EditText down;
    private EditText video;
    private Button commit;

    private Map<String, String> map;

    //重写onCreateView方法
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        view = inflater.inflate(R.layout.fragment_dialog_search, container);
        Window dialogWindow = getDialog().getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        dialogWindow.setGravity(Gravity.RIGHT | Gravity.TOP);
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
        editText = (EditText) view.findViewById(R.id.EditText);
        insert = (EditText) view.findViewById(R.id.insert);
        update = (EditText) view.findViewById(R.id.update);
        select = (EditText) view.findViewById(R.id.select);
        delete = (EditText) view.findViewById(R.id.delete);
        down = (EditText) view.findViewById(R.id.down);
        video = (EditText) view.findViewById(R.id.video);
        commit = (Button) view.findViewById(R.id.commit);

    }

    private void initListener() {
        commit.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.commit:
                map.put(editText.getText().toString(), "R.id.editText");
                map.put(insert.getText().toString(), "R.id.insert");
                map.put(update.getText().toString(), "R.id.update");
                map.put(select.getText().toString(), "R.id.select");
                map.put(delete.getText().toString(), "R.id.delete");
                map.put(down.getText().toString(), "R.id.down");
                map.put(video.getText().toString(), "R.id.video");
                ((SearchListener) getActivity()).findParamers(map);
                break;
            default:
                break;
        }
        dismiss();
    }
}
