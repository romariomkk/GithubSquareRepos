package com.romariomkk.gitrepo.view.base

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.romariomkk.gitrepo.R
import com.romariomkk.gitrepo.databinding.ItemLoadingBinding
import com.romariomkk.gitrepo.databinding.ItemLoadingErrorBinding
import com.romariomkk.gitrepo.util.DiffCallback
import java.util.*


abstract class AbsRVAdapter<T, out DB : ViewDataBinding, VH : AbsRVViewHolder<T, DB>>: RecyclerView.Adapter<VH>() {

    internal lateinit var context: Context
    private val items = ArrayList<T>()

    private var isLoadingAdded = false
    private var isLoadingErrorAdded = false

    abstract fun provideLayoutId(viewType: Int): Int
    abstract fun getViewHolder(view: View, viewType: Int): VH

    abstract fun areItemsTheSame(oldItem: T, newItem: T): Boolean
    abstract fun areContentTheSame(oldItem: T, newItem: T): Boolean

    abstract fun getLoadingItem(): T
    abstract fun getLoadingErrorItem(): T

    private fun getView(parent: ViewGroup, viewType: Int): View {
        val view: View
        val layoutId = provideLayoutId(viewType)
        if (layoutId != -1) {
            view = LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
        } else {
            throw IllegalStateException("No item layout specified for " + this.javaClass.name)
        }
        return view
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        context = parent.context
        return getViewHolder(getView(parent, viewType), viewType)
    }

    override fun onBindViewHolder(holder: VH, position: Int) =
        holder.bind(getItemAt(position)!!)

    override fun onBindViewHolder(holder: VH, position: Int, payloads: MutableList<Any>) {
        holder.bind(getItemAt(position)!!, payloads)
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == items.lastIndex) {
            when {
                isLoadingAdded -> R.layout.item_loading
                isLoadingErrorAdded -> R.layout.item_loading_error
                else -> R.layout.item_result
            }
        } else {
            R.layout.item_result
        }
    }


    /* All boilerplate methods */

    fun updateItems(newItems: List<T>) {
        val res = DiffUtil.calculateDiff(object : DiffCallback<T>(items, newItems) {

            override fun getNewListSize() = newItems.size

            override fun getOldListSize() = items.size

            override fun areItemsTheSame(oldItem: T, newItem: T) =
                this@AbsRVAdapter.areItemsTheSame(oldItem, newItem)

            override fun areContentsTheSame(oldItem: T, newItem: T) =
                this@AbsRVAdapter.areContentTheSame(oldItem, newItem)

        })

        removeLoadingItem()
        removeErrorItem()

        items.clear()
        items.addAll(newItems)

        res.dispatchUpdatesTo(this)
    }

    override fun getItemCount() = items.size

    fun getItems() = items

    fun getItemPosition(item: T) = items.indexOf(item)

    fun getItemPosition(predicate: (T) -> Boolean) = items.indexOfFirst(predicate)

    fun getItemAt(position: Int): T? {
        return if (position < 0 || position >= items.size) null
        else items[position]
    }

    fun add(item: T) {
        items.add(item)
        notifyItemInserted(items.size - 1)
    }

    fun replace(item: T, predicate: (T) -> Boolean) {
        val index = items.indexOfFirst(predicate)
        replace(item, index)
    }

    fun replace(item: T, index: Int) {
        if (index < 0) return
        items[index] = item
        notifyItemChanged(index)
    }

    fun remove(item: T) {
        val position = items.indexOf(item)
        items.remove(item)
        notifyItemRemoved(position)
    }

    fun removeAt(index: Int) {
        items.removeAt(index)
        notifyItemRemoved(index)
    }

    fun removeBy(predicate: (T) -> Boolean) {
        remove(items.find(predicate) ?: return)
    }

    fun clear() {
        items.clear()
        notifyDataSetChanged()
    }

    fun isEmpty() = items.isEmpty()


    fun addLoadingItem() {
        removeErrorItem()

        isLoadingAdded = true
        add(getLoadingItem())
    }

    fun removeLoadingItem() {
        if (isLoadingAdded) {
            isLoadingAdded = false
            removeAt(items.size - 1)
        }
    }

    fun addErrorItem() {
        removeLoadingItem()

        isLoadingErrorAdded = true
        add(getLoadingErrorItem())
    }

    fun removeErrorItem() {
        if (isLoadingErrorAdded) {
            isLoadingErrorAdded = false
            removeAt(items.size - 1)
        }
    }

    class LoadingItem<T>(view: View) : AbsRVViewHolder<T, ItemLoadingBinding>(view) {
        override fun bind(item: T, payloads: MutableList<Any>?) {}
    }

    class LoadingErrorItem<T>(view: View): AbsRVViewHolder<T, ItemLoadingErrorBinding>(view) {
        override fun bind(item: T, payloads: MutableList<Any>?) {}
    }
}
