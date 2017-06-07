package com.xiangxun.workorder.ui.adapter;

import android.content.Context;
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
 * @ClassName: PatrolMaintenanceAdapter.java
 * @date: 2016-4-14 上午11:20:27
 * 首頁專用
 */
public class PatrolMaintenanceAdapter extends ParentAdapter<Patrol> {


    public PatrolMaintenanceAdapter(List<Patrol> data, int resID, Context context) {
        super(data, resID, context);
    }

    @Override
    public View HockView(int i, View view, ViewGroup viewGroup, int i1, Context context, Patrol patrol) {
        ViewHolder holder = null;
        if (view == null) {
            holder = new ViewHolder();
            view = LayoutInflater.from(context).inflate(resID, null);
            holder.llt = (LinearLayout) view.findViewById(R.id.ll_background);
           // holder.llt.setLayoutParams(new LinearLayout.LayoutParams(AppEnum.WIDTH.getLen() / 3, AppEnum.WIDTH.getLen() / 3));
            holder.modeButton = (ImageView) view.findViewById(R.id.mode_button);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        holder.modeButton.setImageResource(patrol.getNewOrder());
        return view;
    }


    class ViewHolder {
        ImageView modeButton;
        LinearLayout llt;
    }

}
