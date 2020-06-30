package com.romariomkk.gitrepo.view.base

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class AbsViewModel: ViewModel() {

    protected val disposables = CompositeDisposable()

    open fun onAttached() {}

    fun remove(disposable: Disposable) {
        disposables.remove(disposable)
    }

    override fun onCleared() {
        disposables.dispose()
        super.onCleared()
    }
}