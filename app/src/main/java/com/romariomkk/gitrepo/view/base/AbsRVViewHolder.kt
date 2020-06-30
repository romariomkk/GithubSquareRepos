package com.romariomkk.gitrepo.view.base

import android.view.View
import androidx.annotation.CallSuper
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer

abstract class AbsRVViewHolder<T, DB: ViewDataBinding>(
    override val containerView: View
) : RecyclerView.ViewHolder(containerView), LayoutContainer {

    protected val binding: DB? = DataBindingUtil.bind(itemView)

    private var clickListener: ((Int) -> Unit)? = null

    init {
        itemView.setOnClickListener {
            clickListener?.invoke(adapterPosition)
        }
    }

    abstract fun bind(item: T, payloads: MutableList<Any>? = null)

    internal fun setOnClickListener(listener: (Int) -> Unit) {
        this.clickListener = listener
    }

    open fun getTransitionViewNamePairs(item: T): Array<Pair<View, String>> =
        emptyArray()
}


interface OnItemClickListener<T> {
    fun onItemClicked(item: T, vararg views: Pair<View, String>)
}