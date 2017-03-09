package com.consomme.droidkaigi2017sample.manager;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * Step2LayoutManager
 * 列をカラムに分割して幅を設定
 */
public class Step2LayoutManager extends RecyclerView.LayoutManager {

    private static final int COLUMN_NUM = 3;

    public Step2LayoutManager() {
        super();
        setAutoMeasureEnabled(true);
    }

    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        final View topView = getChildCount() > 0 ? getChildAt(0) : null;
        final int firstTop = topView != null ? topView.getTop() : getPaddingTop();
        final int firstPosition = topView != null ? getPosition(topView) : 0;

        detachAndScrapAttachedViews(recycler);

        int top = firstTop;
        int bottom;
        final int parentLeft = getPaddingLeft();
        final int parentRight = getWidth() - getPaddingRight();
        final int parentBottom = getHeight() - getPaddingBottom();

        final int count = state.getItemCount();

        for (int i = 0; i + firstPosition < count && top < parentBottom; i++) {
            View view = recycler.getViewForPosition(firstPosition + i);
            addView(view, i);
            measureChildWithMargins(view, 0, 0);

            bottom = top + getDecoratedMeasuredHeight(view);

            // ここまでStep1と同じ

            // 子Viewのサイズを取得
            int childWidth = getDecoratedMeasuredWidth(view);

            // 子Viewの位置をカラム別に配置
            // カラムのindexごとに配置する左端と右端を計算
            int columnIndex = i % COLUMN_NUM;
            int layoutLeft = parentLeft + (childWidth / COLUMN_NUM) * columnIndex;
            int layoutRight = parentRight - (parentRight / COLUMN_NUM) * (COLUMN_NUM - (columnIndex + 1));

            // 子Viewを親Viewに表示
            layoutDecoratedWithMargins(view, layoutLeft, top, layoutRight, bottom);

            top = bottom;
        }
    }
}
