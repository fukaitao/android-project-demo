package com.example.sliding;

import com.nineoldandroids.view.ViewHelper;

import android.R;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

public class SliddingMenu extends HorizontalScrollView{
	//定义屏幕的宽度
	private int mScreenWidth;
	//定义mWrapper HorizontalScrollView 中横向布局，装载菜单和内容的容器
	private LinearLayout mWrapper;
	//定义mMenu
	private ViewGroup mMenu;
	//定义mContent
	private ViewGroup mContent;
	//定义菜单距离右边的距离 单位dp
	private int mPaddingRight = 50;
	//定义mMenu的宽
	private int mMenuWidth;
	//定义内容的宽
	private int mContentWidth;
	//定义第一次加载的boolean变量
	private boolean once;
	/**
	 * 未自定义属性的时候调用此方法
	 * @param context
	 * @param attrs
	 */
	public SliddingMenu(Context context, AttributeSet attrs) {
		this(context ,attrs,0);
		
	}
	
	public SliddingMenu(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		//得到屏幕的宽度
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics outMetrics = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(outMetrics );
		mScreenWidth = outMetrics.widthPixels;
		//将dp转换为xp
		mPaddingRight  = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, context.getResources().getDisplayMetrics());
	}

	public SliddingMenu(Context context) {
		this(context,null);
	}

	/**
	 * 定义子View的宽高，自己的宽高
	 * 
	 */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		if(!once){
			//得到mWapper
			mWrapper = (LinearLayout) getChildAt(0);
			//得到mMenu
			mMenu = (ViewGroup) mWrapper.getChildAt(0);
			//得到mContent
			mContent = (ViewGroup) mWrapper.getChildAt(1);
			//设置mMenu的宽
			mMenuWidth = mMenu.getLayoutParams().width = mScreenWidth - mPaddingRight;
			//设置mConten的宽
			mContentWidth = mContent.getLayoutParams().width = mScreenWidth;
			once = true;
		}
		
	}
	/**
	 * 布局记载的位置
	 */
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		// TODO Auto-generated method stub
		super.onLayout(changed, l, t, r, b);
		//设置偏移量，使得菜单隐藏
		if(changed){
			this.scrollTo(mMenuWidth, 0);
		}
	}
	@SuppressLint("ClickableViewAccessibility")
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		int action = ev.getAction();
		switch (action) {
		case MotionEvent.ACTION_UP:
			//定义隐藏在左边的宽度
			int scrollX = getScrollX();
			if(scrollX >= mMenuWidth / 2){
				this.smoothScrollTo(mMenuWidth, 0);
			}else{
				this.smoothScrollTo(0, 0);
			}
			return true;
		}
			
		
		return super.onTouchEvent(ev);
		
	}
	/**
	 * 抽屉式的菜单
	 */
	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		// TODO Auto-generated method stub
		super.onScrollChanged(l, t, oldl, oldt);
		float scale =  l*1.0f/mScreenWidth;
		ViewHelper.setTranslationX(mMenu, mScreenWidth*scale);
		
		//内容的缩放
		float scaleContent = 0.7f+0.3f*scale;
		ViewHelper.setScaleX(mContent, scaleContent);
		ViewHelper.setScaleY(mContent, scaleContent);
		//设置缩放中心
		ViewHelper.setPivotX(mContent, 0);
		ViewHelper.setPivotY(mContent, mContent.getHeight() / 2);
		
		
		//设置菜单的缩放
		float menuScale =1- (0.3f*scale);
		
		ViewHelper.setScaleX(mMenu, menuScale);
		ViewHelper.setScaleY(mMenu, menuScale);
		//设置渐变
		float menuAlpha = 0.5f+(1-0.5f*scale);
		ViewHelper.setAlpha(mMenu, menuAlpha);
		//设置显示的位置
		
		ViewHelper.setTranslationX(mMenu, mScreenWidth*0.8f*scale);
	}

}
