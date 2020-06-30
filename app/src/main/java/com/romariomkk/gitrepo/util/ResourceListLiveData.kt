package com.romariomkk.gitrepo.util

import androidx.lifecycle.MutableLiveData

open class ResourceListLiveData<T> : MutableLiveData<Resource<List<T>>>(), MutableList<T> {

    private val internalList = mutableListOf<T>()

    fun error(exception: Throwable? = null) = postValue(Resource.error(exception))
    fun initialError(exception: Throwable? = null) = postValue(Resource.initialError(exception))

    fun loading() = postValue(Resource.loading())
    fun initialLoading() = postValue(Resource.initialLoading())

    var hasMore: Boolean = false

    open fun set(list: List<T>, hasMore: Boolean = false, isInitial: Boolean = false) {
        internalList.clear()
        internalList.addAll(list)
        this.hasMore = hasMore
        notifyObservers(hasMore, isInitial)
    }

    fun replaceSilent(item: T, predicate: (T) -> Boolean): Int {
        val index = internalList.indexOfFirst(predicate)
        if (index >= 0) {
            internalList[index] = item
        }
        return index
    }

    fun replace(item: T, predicate: (T) -> Boolean) {
        val index = internalList.indexOfFirst(predicate)
        if (index >= 0) {
            internalList[index] = item
        }
        notifyObservers()
    }

    override fun add(element: T): Boolean {
        val result = internalList.add(element)
        notifyObservers()
        return result
    }

    override fun add(index: Int, element: T) {
        val result = internalList.add(index, element)
        notifyObservers()
        return result
    }

    override fun addAll(index: Int, elements: Collection<T>): Boolean {
        val result = internalList.addAll(index, elements)
        notifyObservers()
        return result
    }

    override fun addAll(elements: Collection<T>): Boolean {
        val result = internalList.addAll(elements)
        notifyObservers()
        return result
    }

    open fun addAll(index: Int, elements: Collection<T>, hasMore: Boolean = false): Boolean {
        val result = internalList.addAll(index, elements)
        this.hasMore = hasMore
        notifyObservers(hasMore)
        return result
    }

    open fun addAll(elements: Collection<T>, hasMore: Boolean = false): Boolean {
        val result = internalList.addAll(elements)
        this.hasMore = hasMore
        notifyObservers(hasMore)
        return result
    }

    override fun clear() {
        internalList.clear()
        notifyObservers()
    }

    override fun remove(element: T): Boolean {
        val result = internalList.remove(element)
        notifyObservers()
        return result
    }

    override fun removeAll(elements: Collection<T>): Boolean {
        val result = internalList.removeAll(elements)
        notifyObservers()
        return result
    }

    override fun removeAt(index: Int): T {
        val result = internalList.removeAt(index)
        notifyObservers()
        return result
    }

    override fun retainAll(elements: Collection<T>): Boolean {
        val result = internalList.retainAll(elements)
        notifyObservers()
        return result
    }

    override fun set(index: Int, element: T): T {
        val result = internalList.set(index, element)
        notifyObservers()
        return result
    }

    override val size = internalList.size
    override fun contains(element: T) = internalList.contains(element)
    override fun containsAll(elements: Collection<T>) = internalList.containsAll(elements)
    override fun get(index: Int) = internalList[index]
    override fun indexOf(element: T) = internalList.indexOf(element)
    override fun isEmpty() = internalList.isEmpty()
    override fun iterator() = internalList.iterator()
    override fun lastIndexOf(element: T) = internalList.lastIndexOf(element)
    override fun listIterator() = internalList.listIterator()
    override fun listIterator(index: Int) = internalList.listIterator(index)
    override fun subList(fromIndex: Int, toIndex: Int) = internalList.subList(fromIndex, toIndex)

    private fun notifyObservers(hasMore: Boolean = false, isInitial: Boolean = false) {
        if (isInitial) postValue(Resource.success(internalList, hasMore))
        else postValue(Resource.success(internalList, hasMore))
    }

}