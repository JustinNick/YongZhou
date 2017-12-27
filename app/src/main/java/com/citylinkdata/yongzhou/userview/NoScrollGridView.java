package com.citylinkdata.yongzhou.userview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.GridView;

/**
 * 因为android没有提供直接禁止Gridview滑动的API， 
 * 也没有提供相应的属性来在XML布局文件中直接禁止滑动，
 * 当我们做菜单时要禁止Gridview上下滑动怎么办呢？
 *  1、自定义一个Gridview 
 *  2、通过重新dispatchTouchEvent方法来禁止滑动
 * 
 * @author chenwensen
 */
public class NoScrollGridView extends GridView {
	public NoScrollGridView(Context context) {
		super(context);
	}

	public NoScrollGridView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	/**
	 * 设置不滚动 其中onMeasure函数决定了组件显示的高度与宽度；
	 * makeMeasureSpec函数中第一个函数决定布局空间的大小，第二个参数是布局模式
	 * MeasureSpec.AT_MOST的意思就是子控件需要多大的控件就扩展到多大的空间
	 * 之后在ScrollView中添加这个组件就OK了，同样的道理，ListView也适用。
	 */
	@Override
	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);

	}

	// 通过重新dispatchTouchEvent方法来禁止滑动
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		if (ev.getAction() == MotionEvent.ACTION_MOVE) {
			return true;// 禁止Gridview进行滑动
		}
		return super.dispatchTouchEvent(ev);
	}
}
