package com.consomme.droidkaigi2017sample.manager;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * Step1LayoutManager
 * 単にViewを一列に並べる。スクロール不可。
 */
public class Step1LayoutManager extends RecyclerView.LayoutManager {

    public Step1LayoutManager() {
        super();

        // WRAP_CONTENTを有効化
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
        // 表示している先頭のViewを取得
        final View topView = getChildCount() > 0 ? getChildAt(0) : null;

        // 先頭Viewの上辺座標を取得
        final int firstTop = topView != null ? topView.getTop() : getPaddingTop();

        // 先頭Viewのpositionを取得
        final int firstPosition = topView != null ? getPosition(topView) : 0;

        // 現在attachされているViewをすべてdetachする
        detachAndScrapAttachedViews(recycler);

        int top = firstTop;
        int bottom;
        final int parentLeft = getPaddingLeft();
        final int parentRight = getWidth() - getPaddingRight();
        final int parentBottom = getHeight() - getPaddingBottom();

        final int count = state.getItemCount();

        // 画面に表示できる範囲までViewを並べる
        for (int i = 0; i + firstPosition < count && top < parentBottom; i++) {
            View view = recycler.getViewForPosition(firstPosition + i);
            addView(view, i);
            measureChildWithMargins(view, 0, 0);

            // 追加するViewの可変座標を取得
            bottom = top + getDecoratedMeasuredHeight(view);

            // 子Viewを親Viewに表示
            layoutDecoratedWithMargins(view, parentLeft, top, parentRight, bottom);

            // 子Viewの上辺座標を変更
            top = bottom;
        }
    }
}
