package com.romariomkk.gitrepo.util

class Resource<out T> private constructor(
    val status: Status,
    val data: T?,
    val exception: Throwable?) {

    sealed class Status {
        object InitialSuccess: Status()
        object InitialSuccessHasMore: Status()
        object Success: Status()
        object SuccessHasMore: Status()
        object InitialLoading: Status()
        object Loading: Status()
        object InitialError: Status()
        object Error: Status()
        object InitialEmpty: Status()
    }

    companion object {
        fun <T> success(data: T? = null, hasMore: Boolean = false): Resource<T> {
            return Resource(if (hasMore) Status.SuccessHasMore else Status.Success, data, null)
        }

        fun <T> successInitial(data: T? = null, hasMore: Boolean = false): Resource<T> {
            return Resource(if (hasMore) Status.InitialSuccessHasMore else Status.InitialSuccess, data, null)
        }

        fun <T> error(exception: Throwable? = null, data: T? = null): Resource<T> {
            return Resource(Status.Error, data, exception)
        }

        fun <T> loading(data: T? = null): Resource<T> {
            return Resource(Status.Loading, data, null)
        }

        fun <T> initialLoading(): Resource<T> {
            return Resource(Status.InitialLoading, null, null)
        }

        fun <T> initialError(exception: Throwable? = null): Resource<T> {
            return Resource(Status.InitialError, null, exception)
        }

        fun <T> initialEmpty(): Resource<T> {
            return Resource(Status.InitialEmpty, null, null)
        }


        fun <T> copy(status: Status, data: T? = null, exception: Throwable? = null): Resource<T> {
            return Resource(status, data, exception)
        }

    }
}

fun Resource.Status.isAnySuccess(): Boolean {
    return this == Resource.Status.InitialSuccess || this == Resource.Status.InitialSuccessHasMore || this == Resource.Status.Success || this == Resource.Status.SuccessHasMore
}

fun Resource.Status.isAnyError(): Boolean {
    return this == Resource.Status.InitialError || this == Resource.Status.Error
}