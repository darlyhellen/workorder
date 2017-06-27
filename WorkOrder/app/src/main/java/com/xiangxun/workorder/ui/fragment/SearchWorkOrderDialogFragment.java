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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableRow;
import android.widget.TextView;

import com.hellen.baseframe.common.dlog.DLog;
import com.xiangxun.workorder.R;
import com.xiangxun.workorder.base.AppEnum;
import com.xiangxun.workorder.bean.SearchStatusInfo;
import com.xiangxun.workorder.widget.dialog.TourSelectDialog;
import com.xiangxun.workorder.widget.dialog.TourSelectDialog.onSelectItemClick;
import com.xiangxun.workorder.widget.dialog.TourSelectListener;
import com.xiangxun.workorder.widget.header.HeaderView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zhangyuhui/Darly on 2017/5/24.
 * Copyright by [Zhangyuhui/Darly]
 * ©2017 XunXiang.Company. All rights reserved.
 *
 * @TODO:搜索条件列表窗口。
 */
public class SearchWorkOrderDialogFragment extends DialogFragment implements View.OnClickListener, onSelectItemClick {


    public interface SearchListener {
        void findParamers(String statu, String name, String num, String ip);
    }

    private View view;
    private HeaderView header;
    private EditText name;
    private EditText num;
    private EditText ip;

    private TextView sp;
    private Button commit;
    private Button consel;


    private String status;

    private List<TourSelectListener> data;

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
        sp = (TextView) view.findViewById(R.id.id_search_sp);

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
            data = new ArrayList<TourSelectListener>();
            String[] mItems = getResources().getStringArray(R.array.Status);
            if (TextUtils.isEmpty(status)) {
                sp.setText(mItems[0]);
            }
            for (int i = 0; i < mItems.length; i++) {

                if (status.equals(i - 1 + "")) {
                    sp.setText(mItems[i]);
                }
                if (i == 0) {
                    data.add(new SearchStatusInfo(mItems[i], ""));
                } else {
                    data.add(new SearchStatusInfo(mItems[i], i - 1 + ""));
                }

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
        sp.setOnClickListener(this);
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
                dismiss();
                break;
            case R.id.title_view_back_llayout:
                dismiss();
                break;
            case R.id.id_search_consel:
                dismiss();
                break;
            case R.id.id_search_sp:
                // 建立数据源
                new TourSelectDialog(getActivity(), data, "选择工单状态", this).show();
                break;
            default:
                break;
        }
    }

    @Override
    public void changeState(TourSelectListener type) {
        if (type instanceof SearchStatusInfo) {
            status = ((SearchStatusInfo) type).getStatus();
            sp.setText(((SearchStatusInfo) type).getName());
        }
    }
}