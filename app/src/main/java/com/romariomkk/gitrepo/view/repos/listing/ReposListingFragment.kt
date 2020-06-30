package com.romariomkk.gitrepo.view.repos.listing

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.doOnPreDraw
import androidx.lifecycle.Observer
import androidx.navigation.fragment.FragmentNavigatorExtras
import com.romariomkk.gitrepo.R
import com.romariomkk.gitrepo.databinding.FragmentReposListingBinding
import com.romariomkk.gitrepo.domain.pojo.git.app.GithubRepo
import com.romariomkk.gitrepo.util.RVLoadMoreScrollListener
import com.romariomkk.gitrepo.util.Resource
import com.romariomkk.gitrepo.util.applyTransitionConfigs
import com.romariomkk.gitrepo.util.isAnySuccess
import com.romariomkk.gitrepo.view.base.AbsFragment
import com.romariomkk.gitrepo.view.base.OnItemClickListener
import dagger.hilt.InstallIn
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.components.FragmentComponent
import kotlinx.android.synthetic.main.fragment_repos_listing.*

@AndroidEntryPoint
class ReposListingFragment : AbsFragment<FragmentReposListingBinding, ReposListingViewModel>() {

    override val layoutRes: Int =
        R.layout.fragment_repos_listing

    override val vmClass: Class<ReposListingViewModel> =
        ReposListingViewModel::class.java

    private val mReposAdapter by lazy {
        ReposAdapter(mItemClickListener)
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
        if (it.status.isAnySuccess()) {
            reposScrollListener.isLoading = false
            mReposAdapter.updateItems(it.data!!)
        }
        when (it.status) {
            Resource.Status.Error -> {
                val message = it.exception?.message
                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
            }
        }
    }

}