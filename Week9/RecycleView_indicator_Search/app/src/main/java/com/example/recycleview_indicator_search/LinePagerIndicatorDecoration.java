package com.example.recycleview_indicator_search;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class LinePagerIndicatorDecoration extends RecyclerView.ItemDecoration {
    private MainActivity mainActivity;
    private float DP;
//    private final float DP = mainActivity.getResources().getDisplayMetrics().density;
    private final Paint mPaintInactive = new Paint();
    private final Paint mPaintActive = new Paint();

    // Made Context final as it's needed for DP calculation
    private final Context context;

    /**
     * Height of the space the indicator takes up at the bottom of the view.
     */
    private final int mIndicatorHeight = (int) (DP * 16);

    /**
     * Indicator stroke width.
     */
    private final float mIndicatorStrokeWidth = DP * 2;

    /**
     * Indicator width.
     */
    private final float mIndicatorItemLength = DP * 16; // Length of each indicator line
    /**
     * Padding between indicators.
     */
    private final float mIndicatorItemPadding = DP * 4;

    /**
     * Interpolator for smooth active indicator transitions.
     */
    private final Interpolator mInterpolator = new AccelerateDecelerateInterpolator();

    // DP calculation moved here, using context from constructor
    public LinePagerIndicatorDecoration(Context context) {
        this.context = context;
        // Calculate DP using the provided context
        DP = context.getResources().getDisplayMetrics().density;

        mPaintInactive.setStrokeCap(Paint.Cap.ROUND);
        mPaintInactive.setStrokeWidth(mIndicatorStrokeWidth);
        mPaintInactive.setStyle(Paint.Style.STROKE);
        mPaintInactive.setAntiAlias(true);
        mPaintInactive.setColor(ContextCompat.getColor(context, R.color.black)); // Example inactive color

        mPaintActive.setStrokeCap(Paint.Cap.ROUND);
        mPaintActive.setStrokeWidth(mIndicatorStrokeWidth);
        mPaintActive.setStyle(Paint.Style.STROKE);
        mPaintActive.setAntiAlias(true);
        mPaintActive.setColor(ContextCompat.getColor(context, R.color.white)); // Example active color
    }

    @Override
    public void onDrawOver(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDrawOver(c, parent, state);

        RecyclerView.Adapter adapter = parent.getAdapter();
        if (adapter == null || adapter.getItemCount() <= 0) {
            return;
        }
        int itemCount = adapter.getItemCount();

        // works for LinearLayoutManager and GridLayoutManager with HORIZONTAL orientation
        if (!(parent.getLayoutManager() instanceof LinearLayoutManager) ||
                ((LinearLayoutManager) parent.getLayoutManager()).getOrientation() != LinearLayoutManager.HORIZONTAL) {
            // Only works for horizontal scrolling
            return;
        }

        LinearLayoutManager layoutManager = (LinearLayoutManager) parent.getLayoutManager();

        // Calculate total number of pages / sections.
        // This logic might need adjustment based on how 'pages' are defined in a GridLayout.
        // For simplicity, let's treat each horizontally visible set of items as a 'page'.
        // Or, more simply, indicate progress through the *total scroll width*.

        // Total width = number of items * width per item (approx)
        // Let's use the simpler approach: indicate based on overall scroll range

        int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();
        if (firstVisibleItemPosition == RecyclerView.NO_POSITION) {
            return;
        }

        // Calculate the total number of 'pages' or scrollable sections
        // This is tricky with GridLayoutManager(HORIZONTAL, span>1).
        // Let's calculate based on total scrollable width vs viewport width.
        int totalScrollRange = parent.computeHorizontalScrollRange();
        int scrollOffset = parent.computeHorizontalScrollOffset();
        int extent = parent.computeHorizontalScrollExtent(); // Viewport width

        if (totalScrollRange <= extent) {
            return; // No need for indicator if content doesn't scroll
        }

        // Center the indicators
        float totalLength = mIndicatorItemLength * itemCount + mIndicatorItemPadding * (itemCount - 1);
        float indicatorStartX = (parent.getWidth() - totalLength) / 2f;

        // Position the indicator bar TOP above the bottom padding
        float indicatorPosY = parent.getHeight() - mIndicatorHeight / 2f; // Center vertically in the reserved space

        drawInactiveIndicators(c, indicatorStartX, indicatorPosY, itemCount);

        // find active page (item)
        // Calculate the progress: 0.0 to 1.0
        float progress = (float) scrollOffset / (totalScrollRange - extent);
        // Map progress to the item index
        int activeItemIndex = Math.round(progress * (itemCount - 1));

        // Draw the active indicator line
        drawActiveIndicator(c, indicatorStartX, indicatorPosY, activeItemIndex);


        // --- Alternative Logic (if interpreting GridLayout rows as pages) ---
        // This requires knowing item widths and how many items fit per 'page'.
        // Example for LinearLayoutManager (simpler):
        /*
        View firstVisibleChild = layoutManager.findViewByPosition(firstVisibleItemPosition);
        if(firstVisibleChild == null) return;
        int itemWidth = firstVisibleChild.getWidth(); // Approximate, includes margins/padding
        float pageOffsetPercent = (float)(scrollOffset % itemWidth) / itemWidth; // Progress within the current item scroll

        int activePosition = firstVisibleItemPosition; // The item primarily visible

        // Draw indicators
        drawInactiveIndicators(c, indicatorStartX, indicatorPosY, itemCount);

        // Draw the active indicator
        float activeIndicatorStartX = indicatorStartX + activePosition * (mIndicatorItemLength + mIndicatorItemPadding);

        // If scrolling, interpolate the position
        if (pageOffsetPercent > 0f && activePosition < itemCount - 1) {
             float nextIndicatorStartX = indicatorStartX + (activePosition + 1) * (mIndicatorItemLength + mIndicatorItemPadding);
             activeIndicatorStartX = mInterpolator.getInterpolation(pageOffsetPercent) * (nextIndicatorStartX - activeIndicatorStartX) + activeIndicatorStartX;
        }
        c.drawLine(activeIndicatorStartX, indicatorPosY, activeIndicatorStartX + mIndicatorItemLength, indicatorPosY, mPaintActive);
        */

    }

    private void drawInactiveIndicators(Canvas c, float indicatorStartX, float indicatorPosY, int itemCount) {
        // Draw all inactive indicators
        float startX = indicatorStartX;
        for (int i = 0; i < itemCount; i++) {
            c.drawLine(startX, indicatorPosY, startX + mIndicatorItemLength, indicatorPosY, mPaintInactive);
            startX += mIndicatorItemLength + mIndicatorItemPadding;
        }
    }

    private void drawActiveIndicator(Canvas c, float indicatorStartX, float indicatorPosY, int activeItemIndex) {
        float highlightStart = indicatorStartX + activeItemIndex * (mIndicatorItemLength + mIndicatorItemPadding);
        c.drawLine(highlightStart, indicatorPosY, highlightStart + mIndicatorItemLength, indicatorPosY, mPaintActive);
    }


    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        // Add space at the bottom of the RecyclerView for the indicator
        outRect.bottom = mIndicatorHeight;
    }
}
