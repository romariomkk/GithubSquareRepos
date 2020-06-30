package com.romariomkk.gitrepo.view.repos.listing

import android.view.View
import androidx.core.view.ViewCompat
import com.romariomkk.gitrepo.R
import com.romariomkk.gitrepo.databinding.ItemResultBinding
import com.romariomkk.gitrepo.domain.pojo.git.app.GithubRepo
import com.romariomkk.gitrepo.view.base.AbsRVAdapter
import com.romariomkk.gitrepo.view.base.AbsRVViewHolder
import com.romariomkk.gitrepo.view.base.OnItemClickListener
import kotlinx.android.synthetic.main.item_result.*

class ReposAdapter(itemClickListener: OnItemClickListener<GithubRepo>)
    : AbsRVAdapter<GithubRepo, ItemResultBinding, ReposAdapter.SearchResultVH>(itemClickListener) {

    override fun provideLayoutId(viewType: Int) =
        R.layout.item_result

    override fun getViewHolder(view: View, viewType: Int) =
        SearchResultVH(view)

    override fun areItemsTheSame(oldItem: GithubRepo, newItem: GithubRepo) =
        oldItem.id == newItem.id

    override fun areContentTheSame(oldItem: GithubRepo, newItem: GithubRepo) =
        oldItem == newItem


    class SearchResultVH(view: View) : AbsRVViewHolder<GithubRepo, ItemResultBinding>(view) {
        override fun bind(item: GithubRepo, payloads: MutableList<Any>?) {
            ViewCompat.setTransitionName(card_view, containerView.context.getString(R.string.transition_card, item.fullName))
            ViewCompat.setTransitionName(item_profile_img, containerView.context.getString(R.string.transition_image, item.fullName))
            ViewCompat.setTransitionName(item_title, containerView.context.getString(R.string.transition_title, item.fullName))
            ViewCompat.setTransitionName(item_img_language, containerView.context.getString(R.string.transition_lang, item.fullName))
            binding?.repo = item
        }

        override fun getTransitionViewNamePairs(item: GithubRepo): Array<Pair<View, String>> {
            return arrayOf(
                card_view to card_view.transitionName,
                item_profile_img to item_profile_img.transitionName,
                item_title to item_title.transitionName,
                item_img_language to item_img_language.transitionName
            )
        }
    }
}