package com.romariomkk.gitrepo.util

class PageParams(
    var pageNum: Int = 1,
    val size: Int = 15
) {

    fun nextPage(): PageParams {
        pageNum++
        return this
    }
}