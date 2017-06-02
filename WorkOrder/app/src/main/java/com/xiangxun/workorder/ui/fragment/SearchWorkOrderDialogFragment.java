package com.xiangxun.workorder.ui.fragment;

import android.app.DialogFragment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableRow;

import com.hellen.baseframe.common.dlog.DLog;
import com.xiangxun.workorder.R;
import com.xiangxun.workorder.base.AppEnum;
import com.xiangxun.workorder.widget.header.HeaderView;

/**
 * Created by Zhangyuhui/Darly on 2017/5/24.
 * Copyright by [Zhangyuhui/Darly]
 * ©2017 XunXiang.Company. All rights reserved.
 *
 * @TODO:搜索条件列表窗口。
 */
public class SearchWorkOrderDialogFragment extends DialogFragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    public interface SearchListener {
        void findParamers(String statu, String name, String num, String ip);
    }

    private View view;
    private HeaderView header;
    private EditText name;
    private EditText num;
    private EditText ip;

    private Spinner sp;
    private Button commit;
    private Button consel;


    private String status;

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
        sp = (Spinner) view.findViewById(R.id.id_search_sp);

        commit = (Button) view.findViewById(R.id.id_search_commit);
        consel = (Button) view.findViewById(R.id.id_search_consel);

        TableRow row = (TableRow) view.findViewById(R.id.id_search_status);

        header.setLeftBackgroundResource(R.mipmap.ic_title_back);
        header.setTitle(R.string.main_work_order_search);
        header.getTitleViewOperationText().setText(R.string.st_search_commit);

        name.setText(getArguments().getString("NAME"));
        num.setText(getArguments().getString("NUM"));
        ip.setText(getArguments().getString("IP"));
        if (getArguments().getInt("PATROL") == 5) {
            //展示列表
            status = getArguments().getString("WORKORDER");
            row.setVisibility(View.VISIBLE);
            // 建立数据源
            String[] mItems = getResources().getStringArray(R.array.Status);
            // 建立Adapter并且绑定数据源
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, mItems);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            //绑定 Adapter到控件
            sp.setAdapter(adapter);
            if (TextUtils.isEmpty(status)) {
                sp.setSelection(0);
            } else {
                sp.setSelection(Integer.parseInt(status) + 1);
            }
        } else {
            //不展示信息
            row.setVisibility(View.GONE);
        }

    }

    private void initListener() {
        header.setLeftBackOneListener(this);
        header.setRightImageTextFlipper(this);
        commit.setOnClickListener(this);
        consel.setOnClickListener(this);
        sp.setOnItemSelectedListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.id_search_commit:
                break;
            case R.id.title_view_right_Flipper01:
                String devicename = name.getText().toString().trim();
                String devicenum = num.getText().toString().trim();
                String deviceip = ip.getText().toString().trim();
                ((SearchListener) getActivity()).findParamers(status, devicename, devicenum, deviceip);
                DLog.i("点击提交");
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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (position == 0) {
            status = "";
        } else {
            status = position - 1 + "";
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}