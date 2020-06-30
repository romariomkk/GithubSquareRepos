package com.romariomkk.gitrepo.view.repos.detail

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.navArgs
import androidx.transition.TransitionInflater
import com.romariomkk.gitrepo.R
import com.romariomkk.gitrepo.databinding.FragmentRepoDetailBinding
import com.romariomkk.gitrepo.view.base.AbsFragment
import dagger.hilt.InstallIn
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.components.FragmentComponent
import kotlinx.android.synthetic.main.fragment_repo_detail.*

@AndroidEntryPoint
class RepoDetailFragment : AbsFragment<FragmentRepoDetailBinding, RepoDetailViewModel>() {

    override val layoutRes: Int =
        R.layout.fragment_repo_detail

    override val vmClass: Class<RepoDetailViewModel> =
        RepoDetailViewModel::class.java

    private val args: RepoDetailFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.move)
        sharedElementReturnTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.move)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        card_view.transitionName = getString(R.string.transition_card, args.repo.fullName)
        item_profile_img.transitionName = getString(R.string.transition_image, args.repo.fullName)
        item_title.transitionName = getString(R.string.transition_title, args.repo.fullName)
        item_language.transitionName = getString(R.string.transition_lang, args.repo.fullName)
        binding.repo = args.repo
    }

}