package com.xiangxun.workorder.widget.camera;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.xiangxun.workorder.R;


/**
 * @package: com.xiangxun.widget
 * @ClassName: TitleView.java
 * @Description: 自定义title
 * @author: HanGJ
 * @date: 2015-7-27 上午10:32:22
 */
public class TitleView extends FrameLayout {
	// 中间title内容
	private TextView title_view_operation_text;
	// 打印/识别
	private ImageView title_view_operation_imageview;
	// 返回
	private ImageView title_view_back_img;
	// 最右图片_个人信息/拍照
	private ImageView title_view_operation_imageview_right;
	// 左边确定按钮
	private TextView title_view_ok;

	public TitleView(Context context) {
		super(context);
	}

	public TitleView(Context context, AttributeSet attrs) {
		super(context, attrs);
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.title_view, this, true);
		title_view_back_img = (ImageView) findViewById(R.id.title_view_back_img);
		title_view_operation_text = (TextView) findViewById(R.id.title_view_operation_text);
		title_view_operation_imageview = (ImageView) findViewById(R.id.title_view_operation_imageview);
		title_view_operation_imageview_right = (ImageView) findViewById(R.id.title_view_operation_imageview_right);
		title_view_ok= (TextView) findViewById(R.id.title_view_ok);
	}

	// 中间title
	public void setTitle(int i) {
		title_view_operation_text.setText(i);
	}

	public void setTitle(String str) {
		title_view_operation_text.setText(str);
	}

	/**
	 * 最右图片_个人信息/拍照
	 * 
	 * @param listener
	 *            监听事件
	 */
	public void setRightViewRightOneListener(OnClickListener listener) {
		title_view_operation_imageview_right.setVisibility(View.VISIBLE);
		title_view_operation_imageview_right.setOnClickListener(listener);
	}

	/**
	 * 最右图片_个人信息/拍照
	 * 
	 * @param drawable
	 *            按钮背景图片
	 * @param listener
	 *            监听事件
	 */
	public void setRightViewRightOneListener(int drawable, OnClickListener listener) {
		title_view_operation_imageview_right.setBackgroundResource(drawable);
		title_view_operation_imageview_right.setVisibility(View.VISIBLE);
		title_view_operation_imageview_right.setOnClickListener(listener);
	}

	/**
	 * 返回
	 * 
	 * @param listener
	 *            监听事件
	 */
	public void setLeftBackOneListener(OnClickListener listener) {
		title_view_back_img.setVisibility(View.VISIBLE);
		title_view_back_img.setOnClickListener(listener);
	}

	/**
	 * 返回
	 * 
	 * @param drawable
	 *            按钮背景图片
	 * @param listener
	 *            监听事件
	 */
	public void setLeftBackOneListener(int drawable, OnClickListener listener) {
		title_view_back_img.setBackgroundResource(drawable);
		title_view_back_img.setVisibility(View.VISIBLE);
		title_view_back_img.setOnClickListener(listener);
	}

	/**
	 * 返回按钮设置背景 无监听事件
	 * 
	 * @param drawable
	 */
	public void setLeftBackOneListener(int drawable) {
		title_view_back_img.setBackgroundResource(drawable);
		title_view_back_img.setVisibility(View.VISIBLE);
	}

	/**
	 * 打印/识别
	 * 
	 * @param listener
	 */
	public void setRightViewLeftOneListener(OnClickListener listener) {
		title_view_operation_imageview.setVisibility(View.VISIBLE);
		title_view_operation_imageview.setOnClickListener(listener);
	}

	/**
	 * 打印/识别
	 * 
	 * @param drawable
	 *            按钮背景图片
	 * @param listener
	 *            监听事件
	 */
	public void setRightViewLeftOneListener(int drawable, OnClickListener listener) {
		title_view_operation_imageview.setBackgroundResource(drawable);
		title_view_operation_imageview.setVisibility(View.VISIBLE);
		title_view_operation_imageview.setOnClickListener(listener);
	}
	
	public void setRightViewLeftVisibile(){
		title_view_operation_imageview.setVisibility(View.GONE);
	}
	
	public void  setRightViewRightVisibile() {
		title_view_operation_imageview_right.setVisibility(View.GONE);
	}
	
	public void setRightViewRightTextOneListener(OnClickListener listener) {
		title_view_ok.setVisibility(View.VISIBLE);
		title_view_ok.setOnClickListener(listener);
	}
	

}
