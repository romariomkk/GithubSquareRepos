package com.romariomkk.gitrepo.util

import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView

fun RecyclerView.applyTransitionConfigs(containingFragment: Fragment) {
    containingFragment.postponeEnterTransition()
    doOnPreDraw {
        containingFragment.startPostponedEnterTransition()
    }
}