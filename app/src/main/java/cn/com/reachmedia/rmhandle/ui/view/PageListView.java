package cn.com.reachmedia.rmhandle.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.AbsListView;

/**
 * Author:    tedyuen
 * Version    V1.0
 * Date:      16/4/19 下午1:22
 * Description:
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 16/4/19          tedyuen             1.0             1.0
 * Why & What is modified:
 */
public class PageListView extends StatusListView implements AbsListView.OnScrollListener{

    private OnLoadNextListener mLoadNextListener;
    private boolean isLoadMoreEnable;
    private PageListViewState mStatus;

    public static enum PageListViewState {
        Idle, TheEnd, Loading
    }

    public PageListView(Context context) {
        this(context, null);
    }

    public PageListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PageListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setOnScrollListener(this);
    }

    public void setLoadMoreEnable(boolean isLoadMoreEnable) {
        this.isLoadMoreEnable = isLoadMoreEnable;
    }

    @Override
    public void onScrollStateChanged(AbsListView absListView, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (mStatus == PageListViewState.Loading
                || mStatus == PageListViewState.TheEnd || !isLoadMoreEnable) {
            return;
        }

        if (firstVisibleItem + visibleItemCount >= totalItemCount
                && totalItemCount != 0
                && totalItemCount != getHeaderViewsCount()
                + getFooterViewsCount() && mLoadNextListener != null) {
            setState(PageListViewState.Loading);
            mLoadNextListener.onLoadNext();
        }
    }

    public void setState(PageListViewState status) {
        mStatus = status;
    }

    public void setLoadNextListener(OnLoadNextListener listener) {
        mLoadNextListener = listener;
    }

    public interface OnLoadNextListener {
        public void onLoadNext();
    }
}
