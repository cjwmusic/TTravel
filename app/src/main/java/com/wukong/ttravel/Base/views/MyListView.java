package com.wukong.ttravel.Base.views;

import android.view.MotionEvent;
import android.widget.ListView;

/**
 * Created by taber on 15/6/10.
 */
public class MyListView extends ListView {
    public MyListView(android.content.Context context, android.util.AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 设置不滚动
     */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_MOVE)
            return true;
        return super.dispatchTouchEvent(ev);
    }


}
