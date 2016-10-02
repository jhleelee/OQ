package com.jackleeentertainment.oq.ui.widget;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;

/**
 * Created by Jacklee on 2016. 9. 21..
 */
public abstract class EndlessRecyclerViewScrollListener extends RecyclerView.OnScrollListener {
    // The minimum amount of items to have below your current scroll position
    // before loading more.
    private int visibleThreshold = 5;
    // The current offset index of data you have loaded
    private int currentPage = 0;
    // The total number of items in the dataset after the last load
    private int previousTotalItemCount = 0;
    // True if we are still waiting for the last set of data to load.
    private boolean loading = true;
    // Sets the starting page index
    private int startingPageIndex = 0;

    /**
     * A <code>LayoutManager</code> is responsible for measuring and positioning item views
     * within a <code>RecyclerView</code> as well as determining the policy for when to recycle
     * item views that are no longer visible to the user. By changing the <code>LayoutManager</code>
     * a <code>RecyclerView</code> can be used to implement a standard vertically scrolling list,
     * a uniform grid, staggered grids, horizontally scrolling collections and more. Several stock
     * layout managers are provided for general use.
     * <p/>
     * If the LayoutManager specifies a default constructor or one with the signature
     * ({@link Context}, {@link AttributeSet}, {@code int}, {@code int}), RecyclerView will
     * instantiate and set the LayoutManager when being inflated. Most used properties can
     * be then obtained from {@link #getProperties(Context, AttributeSet, int, int)}. In case
     * a LayoutManager specifies both constructors, the non-default constructor will take
     * precedence.
     *
     */
    RecyclerView.LayoutManager mLayoutManager;

    public EndlessRecyclerViewScrollListener() {
        super();
    }

    public EndlessRecyclerViewScrollListener(LinearLayoutManager layoutManager) {

        /*
        A LayoutManager is responsible for measuring and positioning item views within
        a RecyclerView as well as determining the policy for when to recycle item views
        that are no longer visible to the user. By changing the LayoutManager a RecyclerView
        can be used to implement a standard vertically scrolling list, a uniform grid,
        staggered grids, horizontally scrolling collections and more.
        Several stock layout managers are provided for general use.

        If the LayoutManager specifies a default constructor or one with the signature
        (Context, AttributeSet, int, int), RecyclerView will instantiate and set the
        LayoutManager when being inflated. Most used properties can be then obtained from
        getProperties(Context, AttributeSet, int, int). In case a LayoutManager specifies
        both constructors, the non-default constructor will take precedence.
         */

        this.mLayoutManager = layoutManager;
    }

    public EndlessRecyclerViewScrollListener(GridLayoutManager layoutManager) {
        this.mLayoutManager = layoutManager;
        /**
         * Returns the number of spans laid out by this grid.
         *
         * @return The number of spans
         * @see #setSpanCount(int)
         */
        visibleThreshold = visibleThreshold * layoutManager.getSpanCount();
    }

    public EndlessRecyclerViewScrollListener(StaggeredGridLayoutManager layoutManager) {
        this.mLayoutManager = layoutManager;
        visibleThreshold = visibleThreshold * layoutManager.getSpanCount();
    }

    /*
    Callback method to be invoked when RecyclerView's scroll state changes.
    RecyclerView: The RecyclerView whose scroll state has changed.
    int newState : The updated scroll state. One of SCROLL_STATE_IDLE, SCROLL_STATE_DRAGGING or
    SCROLL_STATE_SETTLING.
    */

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
    }

    /*
    Callback method to be invoked when the RecyclerView has been scrolled.
    This will be called after the scroll has completed.

    This callback will also be called if visible item range changes after a layout calculation.
    In that case, dx and dy will be 0.

    recyclerView	RecyclerView: The RecyclerView which scrolled.
    dx	int: The amount of horizontal scroll.
    dy	int: The amount of vertical scroll.
    */

    // This happens many times a second during a scroll, so be wary of the code you place here.
    // We are given a few useful parameters to help us work out if we need to load some more data,
    // but first we check if we are waiting for the previous load to finish.

    @Override
    public void onScrolled(RecyclerView view, int dx, int dy) {
        int lastVisibleItemPosition = 0;
        int totalItemCount = mLayoutManager.getItemCount();

        if (mLayoutManager instanceof StaggeredGridLayoutManager) {
            int[] lastVisibleItemPositions = ((StaggeredGridLayoutManager) mLayoutManager).findLastVisibleItemPositions(null);
            // get maximum element within the list
            lastVisibleItemPosition = getLastVisibleItem(lastVisibleItemPositions);
        } else if (mLayoutManager instanceof LinearLayoutManager) {
            lastVisibleItemPosition = ((LinearLayoutManager) mLayoutManager).findLastVisibleItemPosition();
        } else if (mLayoutManager instanceof GridLayoutManager) {
            lastVisibleItemPosition = ((GridLayoutManager) mLayoutManager).findLastVisibleItemPosition();
        }

        // If the total item count is zero and the previous isn't, assume the
        // list is invalidated and should be reset back to initial state
        if (totalItemCount < previousTotalItemCount) {
            this.currentPage = this.startingPageIndex;
            this.previousTotalItemCount = totalItemCount;
            if (totalItemCount == 0) {
                this.loading = true;
            }
        }
        // If it’s still loading, we check to see if the dataset count has
        // changed, if so we conclude it has finished loading and update the current page
        // number and total item count.
        if (loading && (totalItemCount > previousTotalItemCount)) {
            loading = false;
            previousTotalItemCount = totalItemCount;
        }

        // If it isn’t currently loading, we check to see if we have breached
        // the visibleThreshold and need to reload more data.
        // If we do need to reload some more data, we execute onLoadMore to fetch the data.
        // threshold should reflect how many total columns there are too
        if (!loading && (lastVisibleItemPosition + visibleThreshold) > totalItemCount) {
            currentPage++;
            onLoadMore(currentPage, totalItemCount);
            loading = true;
        }
    }



    public int getLastVisibleItem(int[] lastVisibleItemPositions) {
        int maxSize = 0;
        for (int i = 0; i < lastVisibleItemPositions.length; i++) {
            if (i == 0) {
                maxSize = lastVisibleItemPositions[i];
            }
            else if (lastVisibleItemPositions[i] > maxSize) {
                maxSize = lastVisibleItemPositions[i];
            }
        }
        return maxSize;
    }



    // Defines the process for actually loading more data based on page
    public abstract void onLoadMore(int page, int totalItemsCount);


}
