package com.example.toggleButton.view;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class ToggleButton extends View{

	private ToggleState tstate;
	private Bitmap switchbg;
	private Bitmap slidebg;
	private int currentX;
	private boolean isSliding = false;
	private OnToggleButtonChangeListener listener;
	private int startX;
	
	public ToggleButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	public ToggleButton(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}



	public enum ToggleState{
		Open,Close
	}
//   设置滑动块 的背景图片
	public void setSlideBackgroundResource(int checkboxBg) {
		slidebg = BitmapFactory.decodeResource(getResources(), checkboxBg);
	}
//  设置开关的背景图片
	public void setSwitchBackgroundResource(int checkboxSwich) {
		switchbg = BitmapFactory.decodeResource(getResources(), checkboxSwich);
	}
	
	public void setToggleState(ToggleState state){
		tstate = state;
	}


	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//		设置当前控件显示在屏幕上的宽高
		setMeasuredDimension(slidebg.getWidth(), slidebg.getHeight());
	}
//	绘制在屏幕上的具体内容
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		canvas.drawBitmap(slidebg, 0, 0, null);
		if(isSliding){
			int left = currentX-switchbg.getWidth()/2;
			if(left<0)
				left = 0;
			if(left>(slidebg.getWidth()-switchbg.getWidth()))
				left = slidebg.getWidth()-switchbg.getWidth();
			canvas.drawBitmap(switchbg, left, 0, null);
		}
		else{
			if(ToggleState.Open == tstate){
				
				canvas.drawBitmap(switchbg, 0, 0, null);
			}
			else{
				canvas.drawBitmap(switchbg, slidebg.getWidth()-switchbg.getWidth(), 0, null);
			}
		}
		
	}
	
	
	
//	设置触摸监听
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		currentX = (int) event.getX();
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			startX = (int) event.getX();
			break;
		case MotionEvent.ACTION_MOVE:
			int moveX = (int) event.getX();
			int slideX = Math.abs(startX-moveX);
//			slideX表示手指触摸屏幕时产生的偏移量，由于手指单击屏幕时，很难不产生移动，所以真正的单击事件（就是偏移量为0）很难在
//			实际中实现，要想获得单击事件的监听，就只能对偏移量进行判断，认定偏移量在一定范围之内是属于单击事件。
//			System.out.println("偏移量"+slideX);
			if(slideX<5)
//				偏移量小于5，判定是单击事件
				isSliding = false;
			else
				isSliding = true;
			break;
		case MotionEvent.ACTION_UP:
//			如果isSliding的值为true，说明发生过移动，执行以下方法
			if(isSliding){
				if(currentX<slidebg.getWidth()/2){
//					滑动距离小于一半，设置开关为开，
					if(tstate!=ToggleState.Open){
						tstate = ToggleState.Open;
						if(listener!=null){
							listener.onToggleButtonStateChange(tstate);
							System.out.println("开启1");
						}
					}
				}
				else{
					if(tstate!=ToggleState.Close){
						tstate = ToggleState.Close;
						if(listener!=null){
							listener.onToggleButtonStateChange(tstate);
							System.out.println("关闭1");
						}
					}
				}
				isSliding = false;
			}
			else{
//				没有滑动过，则说明是单击事件
				if(tstate==ToggleState.Open){
//					点击的是位于左半边，
					tstate = ToggleState.Close;
					if(listener!=null){
						listener.onToggleButtonStateChange(tstate);
						System.out.println("关闭2");
					}
				}
				else{
					tstate = ToggleState.Open;
					if(listener!=null){
						listener.onToggleButtonStateChange(tstate);
						System.out.println("开启2");
					}
				}
//				Toast.makeText(getContext(), "单击事件", 0).show();
			}
			
			break;

		default:
			break;
		}
		invalidate();
		return true;
	}
	
	
	
//	设置接口供其他对象调用
	
	
	public void setOnToggleButtonChangeListener(OnToggleButtonChangeListener listener){
		this.listener = listener;
	}
	public interface OnToggleButtonChangeListener{
		void onToggleButtonStateChange(ToggleState state);
	}
}
