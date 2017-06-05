package com.xiangxun.workorder.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hellen.baseframe.baseadapter.ParentAdapter;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.xiangxun.workorder.R;

import java.util.List;

/**
 * Created by Zhangyuhui/Darly on 2017/6/5.
 * Copyright by [Zhangyuhui/Darly]
 * ©2017 XunXiang.Company. All rights reserved.
 *
 * @TODO:
 */
public class MainDetailImageAdapter extends ParentAdapter<String> {

    private Callback mCallback;

    public MainDetailImageAdapter(List<String> data, int resID, Context context, Callback callback) {
        super(data, resID, context);
        mCallback = callback;
    }

    /**
     * 自定义接口，用于回调按钮点击事件到Activity，也可以仅用来传递参数
     */
    public interface Callback {
        public void click(View v, int position);
    }

    @Override
    public int getCount() {
        return data.size() > 4 ? 4 : data.size();
    }


    @Override
    public View HockView(int position, View view, ViewGroup viewGroup, int resId, Context context, String url) {
        final int po = position;

        ViewHolder viewHolder = null;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(
                    R.layout.item_main_detail_image_adapter, null);
            viewHolder = new ViewHolder();
            viewHolder.photo = (ImageView) view
                    .findViewById(R.id.id_iv_photo);
            viewHolder.close = (ImageView) view
                    .findViewById(R.id.id_iv_close);
            viewHolder.desc = (TextView) view
                    .findViewById(R.id.id_tv_desc);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        if (viewGroup.getChildCount() == position) { // 里面就是正常的position

            if (position == (data.size() - 1)) {
                viewHolder.photo.setImageResource(R.drawable.add_publish_image);
                viewHolder.photo.setScaleType(ImageView.ScaleType.CENTER_CROP);
                viewHolder.close.setVisibility(View.GONE);
            } else {
                viewHolder.close.setVisibility(View.VISIBLE);
                viewHolder.desc.setText("");
                ImageLoader.getInstance().displayImage(
                        "file://" + data.get(position), viewHolder.photo);

            }
        }
        // 给删除绑定点击监听器，点击触发OnClickListener的onClick()方法
        // 响应按钮点击事件,调用子定义接口，并传入View
        viewHolder.close.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mCallback.click(v, po);
            }
        });

        return view;
    }

    class ViewHolder {
        ImageView photo;
        ImageView close;
        TextView desc;
    }
}
