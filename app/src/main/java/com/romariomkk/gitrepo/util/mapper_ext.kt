package com.romariomkk.gitrepo.util

import com.romariomkk.gitrepo.data.pojo.ServerGithubRepo
import com.romariomkk.gitrepo.domain.pojo.GithubRepo

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