package com.xiangxun.workorder.base;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.Window;

import com.hellen.baseframe.binder.InitBinder;
import com.hellen.baseframe.common.dlog.DLog;
import com.xiangxun.workorder.R;

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
        InitBinder.InitAll(this);
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

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        intForRight();
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
        intForRight();
    }


    @Override
    public void finish() {
        super.finish();
        outToLeft();
    }

    public void outToLeft() {
        overridePendingTransition(R.anim.activity_nothing, R.anim.activity_out_to_buttom);
    }

    public void intForRight() {
        overridePendingTransition(R.anim.right_in, R.anim.activity_nothing);
    }
}
