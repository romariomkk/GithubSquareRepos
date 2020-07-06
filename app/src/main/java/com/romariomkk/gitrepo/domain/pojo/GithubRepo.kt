package com.romariomkk.gitrepo.domain.pojo

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GithubRepo(
    val createdAt: String,
    val description: String?,
    val forksCount: Int,
    val fullName: String,
    val htmlUrl: String,
    val id: Int,
    val language: String?,
    val name: String,
    val openIssuesCount: Int,
    val isPrivate: Boolean,
    val size: Int,
    val stargazersCount: Int,
    val updatedAt: String,
    val url: String,
    val watchersCount: Int,
    val ownerLogin: String,
    val ownerAvatarUrl: String
): Parcelable {

    companion object {
        fun empty(): GithubRepo {
            return GithubRepo(
                "", "", 0,
                "", "", -1, "", "",
                0, false, 0,
                0, "", "",
                0, "", ""
            )
        }
    }
}