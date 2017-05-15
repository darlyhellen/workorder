package com.xiangxun.workorder.base;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.Window;

import com.hellen.baseframe.common.dlog.DLog;

/**
 * @author zhangyh2 BaseActivity $ 下午2:33:01 TODO
 */
public abstract class BaseActivity extends FragmentActivity {


    /*
     * (non-Javadoc)
     *
     * @see android.support.v4.app.FragmentActivity#onCreate(android.os.Bundle)
     */
    @TargetApi(19)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        initView(savedInstanceState);
        loadData();
        initListener();
    }


    /**
     * @param savedInstanceState 下午2:34:08
     * @author zhangyh2 BaseActivity.java TODO 初始化控件
     */
    protected abstract void initView(Bundle savedInstanceState);

    /**
     * 下午2:34:10
     *
     * @author zhangyh2 BaseActivity.java TODO 加载数据
     */
    protected void loadData() {
    }

    ;

    /**
     * 下午2:42:02
     *
     * @author zhangyh2  TODO 初始化坚挺事件
     */
    protected abstract void initListener();

    /*
     * (non-Javadoc)
     *
     * @see android.support.v4.app.FragmentActivity#onResume()
     */
    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        DLog.i(getClass().getSimpleName() + "onResume()");
    }

    /*
     * (non-Javadoc)
     *
     * @see android.support.v4.app.FragmentActivity#onPause()
     */
    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        DLog.i(getClass().getSimpleName() + "onPause()");
    }

    public FragmentManager fragmentManager;
    public String TAGSS = "";

    /*
     * 在fragment的管理类中，我们要实现这部操作，而他的主要作用是，当D这个activity回传数据到
     * 这里碎片管理器下面的fragnment中时，往往会经过这个管理器中的onActivityResult的方法。
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /* 在这里，我们通过碎片管理器中的Tag，就是每个碎片的名称，来获取对应的fragment */
        Fragment f = fragmentManager.findFragmentByTag(TAGSS);
        /* 然后在碎片中调用重写的onActivityResult方法 */
        if (f == null) {
            onActivityResult(requestCode, resultCode, data);
        } else {
            f.onActivityResult(requestCode, resultCode, data);
        }
    }

}
