package com.romariomkk.gitrepo.util

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class RVLoadMoreScrollListener(private var onNextPageRequestedListener: () -> Unit): RecyclerView.OnScrollListener() {

    private var totalItemCount: Int = 0
    private var lastVisibleItem: Int = 0
    var isLoading: Boolean = false

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        val itemCount = recyclerView.layoutManager?.itemCount
        itemCount?.let {
            totalItemCount = itemCount
            lastVisibleItem = (recyclerView.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
            if (!isLoading && totalItemCount <= lastVisibleItem + 1) {
                isLoading = true
                onNextPageRequestedListener.invoke()
            }
        }
    }
}
