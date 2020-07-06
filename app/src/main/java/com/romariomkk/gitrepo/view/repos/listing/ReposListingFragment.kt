package com.romariomkk.gitrepo.view.repos.listing

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.fragment.FragmentNavigatorExtras
import com.romariomkk.gitrepo.R
import com.romariomkk.gitrepo.databinding.FragmentReposListingBinding
import com.romariomkk.gitrepo.domain.pojo.GithubRepo
import com.romariomkk.gitrepo.util.*
import com.romariomkk.gitrepo.view.base.AbsFragment
import com.romariomkk.gitrepo.view.base.OnErrorItemClickListener
import com.romariomkk.gitrepo.view.base.OnItemClickListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_repos_listing.*
import timber.log.Timber

@AndroidEntryPoint
class ReposListingFragment : AbsFragment<FragmentReposListingBinding, ReposListingViewModel>() {

    override val layoutRes: Int =
        R.layout.fragment_repos_listing

    override val vmClass: Class<ReposListingViewModel> =
        ReposListingViewModel::class.java

    private val mReposAdapter by lazy {
        ReposAdapter(mItemClickListener, mErrorClickListener)
    }
    private val reposScrollListener by lazy {
        RVLoadMoreScrollListener { viewModel.loadNext() }
    }
    private val mItemClickListener = object : OnItemClickListener<GithubRepo> {
        override fun onItemClicked(item: GithubRepo, vararg views: Pair<View, String>) {
            if (views.isNotEmpty()) {
                val extras = FragmentNavigatorExtras(*views)
                navController.navigate(ReposListingFragmentDirections.toRepoDetail(item), extras)
            } else {
                navController.navigate(ReposListingFragmentDirections.toRepoDetail(item))
            }
        }
    }

    private val mErrorClickListener = object : OnErrorItemClickListener {
        override fun onItemClicked() {
            viewModel.loadCurrent()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(rvResults) {
            adapter = mReposAdapter
            addOnScrollListener(reposScrollListener)
            applyTransitionConfigs(this@ReposListingFragment)
        }

        viewModel.squareRepoSource.reObserve(viewLifecycleOwner, mRepoObserver)
    }

    private val mRepoObserver = Observer<Resource<List<GithubRepo>>> {
        Timber.e("Repos status: ${it.status}")

        if (it.status.isAnySuccess() || it.status.isAnyError()) {
            reposScrollListener.isLoading = false
        }

        when (it.status) {
            is Resource.Status.InitialError, is Resource.Status.Error -> {
                mReposAdapter.addErrorItem()
            }
            is Resource.Status.InitialSuccessHasMore, is Resource.Status.SuccessHasMore -> {
                mReposAdapter.updateItems(it.data!!)
            }
            is Resource.Status.Success -> {
                mReposAdapter.removeAuxiliaryItems()
            }
            is Resource.Status.InitialLoading, is Resource.Status.Loading -> {
                mReposAdapter.addLoadingItem()
            }
        }
    }

}