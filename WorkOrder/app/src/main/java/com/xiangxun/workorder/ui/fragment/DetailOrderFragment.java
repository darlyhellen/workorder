package com.xiangxun.workorder.ui.fragment;

import android.app.DialogFragment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.hellen.baseframe.binder.InitBinder;
import com.hellen.baseframe.common.dlog.DLog;
import com.xiangxun.workorder.R;
import com.xiangxun.workorder.base.AppEnum;
import com.xiangxun.workorder.widget.header.HeaderView;

/**
 * Created by Zhangyuhui/Darly on 2017/5/27.
 * Copyright by [Zhangyuhui/Darly]
 * ©2017 XunXiang.Company. All rights reserved.
 *
 * @TODO: 固态详情展示页面。
 */
public class DetailOrderFragment extends Fragment {
    private View root;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_detail_order, null);
        InitBinder.InitAll(this, root);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        initListener();
    }


    private void initView() {

    }

    private void initListener() {

    }

    /**
     * Created by Zhangyuhui/Darly on 2017/5/24.
     * Copyright by [Zhangyuhui/Darly]
     * ©2017 XunXiang.Company. All rights reserved.
     *
     * @TODO:搜索条件列表窗口。
     */
    public static class SearchWorkOrderDialogFragment extends DialogFragment implements View.OnClickListener {


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
            header.getTitleViewOperationText().setText(R.string.st_search_commit);


        }

        private void initListener() {
            header.setLeftBackOneListener(this);
            header.setRightImageTextFlipper(this);
            commit.setOnClickListener(this);
            consel.setOnClickListener(this);

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
                    ((SearchListener) getActivity()).findParamers(devicename, devicenum, deviceip);
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
    }
}
