package com.romariomkk.gitrepo.util

class PageParams(
    var pageNum: Int = START_PAGE,
    val size: Int = 15
) {

    fun nextPage(): PageParams {
        pageNum++
        return this
    }

    companion object {
        const val START_PAGE = 1
    }
}