package com.romariomkk.gitrepo.util

import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.romariomkk.gitrepo.domain.pojo.git.app.GithubRepo
import com.romariomkk.gitrepo.domain.pojo.git.server.ServerGithubRepo

fun ServerGithubRepo.toGithubRepo(): GithubRepo {
    return GithubRepo(
        createdAt,
        description,
        forksCount,
        fullName,
        htmlUrl,
        id,
        language,
        name,
        openIssuesCount,
        isPrivate,
        size,
        stargazersCount,
        updatedAt,
        url,
        watchersCount,
        owner.login,
        owner.avatarUrl
    )
}

fun RecyclerView.applyTransitionConfigs(containingFragment: Fragment) {
    containingFragment.postponeEnterTransition()
    doOnPreDraw {
        containingFragment.startPostponedEnterTransition()
    }
}