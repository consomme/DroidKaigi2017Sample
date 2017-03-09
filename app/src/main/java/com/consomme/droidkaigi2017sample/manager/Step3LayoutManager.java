package com.consomme.droidkaigi2017sample.manager;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * Step3LayoutManager
 * GridLayoutManager的にViewを並べる
 */
public class Step3LayoutManager extends RecyclerView.LayoutManager {

    private static final int COLUMN_NUM = 3;

    public Step3LayoutManager() {
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
        final View lastTopView = getChildCount() > 0 ? getChildAt(0) : null;
        final int lastTop = lastTopView != null ? lastTopView.getTop() : getPaddingTop();
        final int firstPosition = lastTopView != null ? getPosition(lastTopView) : 0;

        detachAndScrapAttachedViews(recycler);

        int top = lastTop;
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

            int childWidth = getDecoratedMeasuredWidth(view);

            int columnIndex = i % COLUMN_NUM;
            int layoutLeft = parentLeft + (childWidth / COLUMN_NUM) * columnIndex;
            int layoutRight = parentRight - (parentRight / COLUMN_NUM) * (COLUMN_NUM - (columnIndex + 1));

            layoutDecoratedWithMargins(view, layoutLeft, top, layoutRight, bottom);

            // ここまでStep2と同じ

            // 行内にカラムが全て埋まったら上辺座標を変更
            if (columnIndex == COLUMN_NUM - 1) {
                top = bottom;
            }
        }
    }
}
