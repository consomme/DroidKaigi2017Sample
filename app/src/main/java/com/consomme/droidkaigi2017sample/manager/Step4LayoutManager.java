package com.consomme.droidkaigi2017sample.manager;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Step4LayoutManager
 * Step1を継承し、スクロールできるようにする
 */
public class Step4LayoutManager extends Step1LayoutManager {

    @Override
    public boolean canScrollVertically() {
        // 縦方向にスクロールできるようにする
        return true;
    }

    @Override
    public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
        // 子Viewがない場合は何もしない
        if (getChildCount() == 0 || dy == 0) {
            return 0;
        }

        int delta = 0;
        final int parentLeft = getPaddingLeft();
        final int parentRight = getWidth() - getPaddingRight();

        if (dy > 0) {
            // 下から上へのスクロール
            while (delta < dy) {
                // 表示中の一番下のViewを取得
                final View bottomView = getChildAt(getChildCount() - 1);

                // 画面からはみ出ている部分のサイズを取得
                final int hangingBottom = Math.abs(getDecoratedBottom(bottomView) - getHeight());

                final int scrollBy = Math.min(delta + dy, hangingBottom);
                delta += scrollBy;
                offsetChildrenVertical(-scrollBy);

                int lastPosition = getPosition(bottomView);
                if (lastPosition < getItemCount() - 1 && delta < dy) {
                    // 表示中のViewの後ろに新しいViewを追加する
                    lastPosition++;
                    View view = recycler.getViewForPosition(lastPosition);
                    addView(view, lastPosition);

                    measureChildWithMargins(view, 0, 0);

                    final int top = getDecoratedBottom(bottomView);
                    final int bottom = top + getDecoratedMeasuredHeight(view);
                    layoutDecorated(view, parentLeft, top, parentRight, bottom);
                } else {
                    break;
                }
            }
        } else {
            // 上から下へのスクロール
            while (delta > dy) {
                // 表示中の一番上のViewを取得
                final View topView = getChildAt(0);

                // 画面からはみ出ている部分のサイズを取得
                final int hangingTop = Math.max(-getDecoratedTop(topView), 0);


                final int scrollBy = Math.min(delta - dy, hangingTop);
                delta -= scrollBy;
                offsetChildrenVertical(scrollBy);

                int firstPosition = getPosition(topView);
                if (firstPosition > 0 && delta > dy) {
                    // 表示中のViewの前に新しいViewを追加する
                    firstPosition--;
                    View view = recycler.getViewForPosition(firstPosition);
                    addView(view, 0);

                    measureChildWithMargins(view, 0, 0);

                    final int bottom = getDecoratedTop(topView);
                    final int top = bottom - getDecoratedMeasuredHeight(view);
                    layoutDecorated(view, parentLeft, top, parentRight, bottom);
                } else {
                    break;
                }
            }
        }

        // 非表示になったViewをリサイクルする

        final int childCount = getChildCount();
        final int parentHeight = getHeight();

        // 画面外に出た先頭Viewのposition
        int firstRecyclerPosition = -1;

        // 画面外に出た最後のViewのposition
        int lastRecyclePosition = -1;

        for (int i = 0; i < childCount; i++) {
            final View view = getChildAt(i);

            // 座標を親Viewと比較し、画面外に出ていればrecycle対象となる
            if (getDecoratedTop(view) < parentHeight && getDecoratedBottom(view) > 0) {
                if (firstRecyclerPosition < 0) {
                    firstRecyclerPosition = i;
                }
                lastRecyclePosition = i;
            }
        }

        for (int i = 0; i < firstRecyclerPosition; i++) {
            removeAndRecycleViewAt(i, recycler);
        }
        for (int i = childCount - 1; i > lastRecyclePosition; i--) {
            removeAndRecycleViewAt(i, recycler);
        }

        return delta;
    }
}
