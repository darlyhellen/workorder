package com.xiangxun.workorder.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.hellen.baseframe.baseadapter.ParentAdapter;
import com.xiangxun.workorder.R;
import com.xiangxun.workorder.base.AppEnum;
import com.xiangxun.workorder.bean.Patrol;
import com.xiangxun.workorder.widget.grid.XwHomeModeButton;

import java.util.List;

/**
 * @package: com.xiangxun.xacity.adapter
 * @ClassName: HomeAdapter.java
 * @Description: 巡视系统首页数据适配器
 * @author: HanGJ
 * @date: 2016-4-14 上午11:20:27
 */
public class PatrolHomeAdapter extends ParentAdapter<Patrol> {


    public PatrolHomeAdapter(List<Patrol> data, int resID, Context context) {
        super(data, resID, context);
    }

    @Override
    public View HockView(int i, View view, ViewGroup viewGroup, int i1, Context context, Patrol patrol) {
        ViewHolder holder = null;
        if (view == null) {
            holder = new ViewHolder();
            view = LayoutInflater.from(context).inflate(resID, null);
            holder.iv = (ImageView) view.findViewById(R.id.mode_image);
            holder.iv.setLayoutParams(new LinearLayout.LayoutParams(AppEnum.WIDTH.getLen() / 4, AppEnum.WIDTH.getLen() / 4));
            holder.modeButton = (XwHomeModeButton) view.findViewById(R.id.mode_button);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.iv.setImageResource(patrol.getId());
        holder.modeButton.setIV(0, context.getResources().getString(patrol.getName()));
        if (patrol.getNewOrder() > 0) {
            holder.modeButton.setHint(patrol.getNewOrder());
        } else {
            holder.modeButton.reset();
        }
        return view;
    }


    class ViewHolder {
        XwHomeModeButton modeButton;
        ImageView iv;
    }

}
