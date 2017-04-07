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
	//������Ļ�Ŀ��
	private int mScreenWidth;
	//����mWrapper HorizontalScrollView �к��򲼾֣�װ�ز˵������ݵ�����
	private LinearLayout mWrapper;
	//����mMenu
	private ViewGroup mMenu;
	//����mContent
	private ViewGroup mContent;
	//����˵������ұߵľ��� ��λdp
	private int mPaddingRight = 50;
	//����mMenu�Ŀ�
	private int mMenuWidth;
	//�������ݵĿ�
	private int mContentWidth;
	//�����һ�μ��ص�boolean����
	private boolean once;
	/**
	 * δ�Զ������Ե�ʱ����ô˷���
	 * @param context
	 * @param attrs
	 */
	public SliddingMenu(Context context, AttributeSet attrs) {
		this(context ,attrs,0);
		
	}
	
	public SliddingMenu(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		//�õ���Ļ�Ŀ��
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics outMetrics = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(outMetrics );
		mScreenWidth = outMetrics.widthPixels;
		//��dpת��Ϊxp
		mPaddingRight  = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, context.getResources().getDisplayMetrics());
	}

	public SliddingMenu(Context context) {
		this(context,null);
	}

	/**
	 * ������View�Ŀ�ߣ��Լ��Ŀ��
	 * 
	 */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		if(!once){
			//�õ�mWapper
			mWrapper = (LinearLayout) getChildAt(0);
			//�õ�mMenu
			mMenu = (ViewGroup) mWrapper.getChildAt(0);
			//�õ�mContent
			mContent = (ViewGroup) mWrapper.getChildAt(1);
			//����mMenu�Ŀ�
			mMenuWidth = mMenu.getLayoutParams().width = mScreenWidth - mPaddingRight;
			//����mConten�Ŀ�
			mContentWidth = mContent.getLayoutParams().width = mScreenWidth;
			once = true;
		}
		
	}
	/**
	 * ���ּ��ص�λ��
	 */
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		// TODO Auto-generated method stub
		super.onLayout(changed, l, t, r, b);
		//����ƫ������ʹ�ò˵�����
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
			//������������ߵĿ��
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
	 * ����ʽ�Ĳ˵�
	 */
	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		// TODO Auto-generated method stub
		super.onScrollChanged(l, t, oldl, oldt);
		float scale =  l*1.0f/mScreenWidth;
		ViewHelper.setTranslationX(mMenu, mScreenWidth*scale);
		
		//���ݵ�����
		float scaleContent = 0.7f+0.3f*scale;
		ViewHelper.setScaleX(mContent, scaleContent);
		ViewHelper.setScaleY(mContent, scaleContent);
		//������������
		ViewHelper.setPivotX(mContent, 0);
		ViewHelper.setPivotY(mContent, mContent.getHeight() / 2);
		
		
		//���ò˵�������
		float menuScale =1- (0.3f*scale);
		
		ViewHelper.setScaleX(mMenu, menuScale);
		ViewHelper.setScaleY(mMenu, menuScale);
		//���ý���
		float menuAlpha = 0.5f+(1-0.5f*scale);
		ViewHelper.setAlpha(mMenu, menuAlpha);
		//������ʾ��λ��
		
		ViewHelper.setTranslationX(mMenu, mScreenWidth*0.8f*scale);
	}

}
