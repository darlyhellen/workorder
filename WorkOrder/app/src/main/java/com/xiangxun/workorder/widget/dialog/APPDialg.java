/**
 * 上午10:32:27
 *
 * @author zhangyh2
 * LoginOutDialg.java
 * TODO
 */
package com.xiangxun.workorder.widget.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.xiangxun.workorder.R;


/**
 * @author zhangyh2 LoginOutDialg 上午10:32:27 TODO 项目总弹出选择框
 */
public class APPDialg extends AlertDialog {
    private TextView title;
    private TextView content;
    private Button sure;
    private Button consel;

    private View titleLine;
    private View conselLine;

    private OndialogListener ondialogListener;

    public APPDialg(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
        init(context);
    }

    public APPDialg(Context context, int theme) {
        super(context, theme);
        // TODO Auto-generated constructor stub
        init(context);
    }

    public APPDialg(Context context, boolean cancelable,
                    OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        // TODO Auto-generated constructor stub
        init(context);
    }

    private void init(Context context) {
        show();
        setCanceledOnTouchOutside(false);
        Window window = getWindow();
        window.setContentView(R.layout.login_out_dialog);// 设置对话框的布局
        title = (TextView) window.findViewById(R.id.out_dialog_title);
        content = (TextView) window.findViewById(R.id.out_dialog_content);
        sure = (Button) window.findViewById(R.id.out_dialog_sure);
        titleLine = (View) window.findViewById(R.id.out_dialog_title_line);
        conselLine = (View) window.findViewById(R.id.out_dialog_cancel_line);


        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ondialogListener != null) {
                    ondialogListener.onSureClick();
                }
                cancel();
            }
        });
        consel = (Button) window.findViewById(R.id.out_dialog_cancel);
        consel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (ondialogListener != null) {
                    ondialogListener.onConselClick();
                }
                cancel();

            }
        });
    }


    public void setViewInvisible() {
        title.setVisibility(View.GONE);
        consel.setVisibility(View.GONE);
        titleLine.setVisibility(View.GONE);
        conselLine.setVisibility(View.GONE);
    }

    public void setViewVisible() {
        title.setVisibility(View.VISIBLE);
        consel.setVisibility(View.VISIBLE);
        titleLine.setVisibility(View.VISIBLE);
        conselLine.setVisibility(View.VISIBLE);
    }

    public void setTitle(String title) {
        this.title.setText(title);
    }


    public void setContent(String content) {
        this.content.setText(content);
    }


    public void setSure(String sure) {
        this.sure.setText(sure);
    }


    public void setConsel(String consel) {
        this.consel.setText(consel);
    }

    public void setTitle(int res) {
        this.title.setText(res);
    }


    public void setContent(int res) {
        this.content.setText(res);
    }


    public void setSure(int res) {
        this.sure.setText(res);
    }


    public void setConsel(int res) {
        this.consel.setText(res);
    }


    public void setOndialogListener(OndialogListener ondialogListener) {
        this.ondialogListener = ondialogListener;
    }
}
