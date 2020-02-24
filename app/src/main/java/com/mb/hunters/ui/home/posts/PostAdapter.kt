/*
 * Copyright 2017 Mohammed Boukadir
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.mb.hunters.ui.home.posts

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.NO_POSITION
import com.bumptech.glide.Glide
import com.mb.hunters.R
import com.mb.hunters.ui.base.BaseViewHolder
import com.mb.hunters.ui.home.posts.PostAdapter.Constant.KEY_COMMENT
import com.mb.hunters.ui.home.posts.PostAdapter.Constant.KEY_VOTES
import com.mb.hunters.ui.home.posts.PostAdapter.Constant.TYPE_ITEM
import com.mb.hunters.ui.home.posts.PostAdapter.Constant.TYPE_LOADING_MORE
import kotlinx.android.synthetic.main.home_post_list_item.view.*
import timber.log.Timber

class PostAdapter(val itemActionListener: ItemActionListener) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    object Constant {
        const val KEY_COMMENT = "key_comment"
        const val KEY_VOTES = "key_votes"
        const val TYPE_ITEM = R.layout.home_post_list_item
        const val TYPE_LOADING_MORE = R.layout.common_infinite_loading
    }

    interface ItemActionListener {

        fun onItemClick(item: PostUiModel)
    }

    var showLoadingMore: Boolean = false
        private set

    private var items = mutableListOf<PostUiModel>()

    fun startLoadingMore() {
        Timber.d("startLoadingMore")
        if (showLoadingMore) return
        showLoadingMore = true
        notifyItemInserted(getLoadingMoreItemPosition())
    }

    fun finishedLoadingMore() {
        Timber.d("finishedLoadingMore")
        if (!showLoadingMore) return
        val loadingPos = getLoadingMoreItemPosition()
        showLoadingMore = false
        notifyItemRemoved(loadingPos)
    }

    private fun getLoadingMoreItemPosition(): Int {
        return if (showLoadingMore) itemCount - 1 else RecyclerView.NO_POSITION
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_ITEM -> PostViewHolder(parent, TYPE_ITEM)
            TYPE_LOADING_MORE -> LoadingViewHolder(parent, TYPE_LOADING_MORE)
            else -> throw IllegalArgumentException("Invalid item type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when (holder) {
            is PostViewHolder -> holder.bind(items[position])
            is LoadingViewHolder -> holder.bind()

            else -> require(false) {
                "Invalid item type"
            }
        }
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {

        when (holder) {
            is PostViewHolder -> holder.bind(items[position], payloads)
            is LoadingViewHolder -> holder.bind()

            else -> require(false) {
                "Invalid item type"
            }
        }
    }

    override fun getItemId(position: Int): Long {
        if (getItemViewType(position) == TYPE_LOADING_MORE) {
            return RecyclerView.NO_ID
        }
        return items[position].id
    }

    fun getDataItemCount(): Int {
        return items.size
    }

    override fun getItemCount(): Int {
        return getDataItemCount() + if (showLoadingMore) 1 else 0
    }

    override fun getItemViewType(position: Int): Int {
        val itemCount = getDataItemCount()
        if (position < itemCount && itemCount > 0) {
            return TYPE_ITEM
        }
        return TYPE_LOADING_MORE
    }

    fun showMore(posts: List<PostUiModel>) {
        val newList = items.plus(posts)
        val diffUtilResult = DiffUtil.calculateDiff(DiffUtilPosts(items, newList))
        items = newList.toMutableList()
        diffUtilResult.dispatchUpdatesTo(this)
    }

    fun update(newPosts: List<PostUiModel>) {
        val diffUtilResult = DiffUtil.calculateDiff(DiffUtilPosts(items, newPosts))
        items = newPosts.toMutableList()
        diffUtilResult.dispatchUpdatesTo(this)
    }

    fun getLastItemDayAgo() = items.last().daysAgo

    inner class PostViewHolder(parent: ViewGroup, @LayoutRes layoutResId: Int) :
        BaseViewHolder(parent, layoutResId) {

        init {
            itemView.setOnClickListener {
                if (adapterPosition != NO_POSITION) {
                    itemActionListener.onItemClick(items[adapterPosition])
                }
            }
        }

        fun bind(post: PostUiModel) {

            Glide.with(itemView).load(post.bigImageUrl).into(itemView.screenShot)
            Glide.with(itemView).load(post.smallImageUrl).into(itemView.thumbnail)

            itemView.name.text = post.title
            itemView.tagline.text = post.subTitle
            itemView.commentsCount.text = post.commentsCount.toString()
            itemView.upVotesCount.text = post.votesCount.toString()
        }

        fun bind(post: PostUiModel, payloads: MutableList<Any>) {
            if (payloads.isEmpty()) {
                this.bind(post)
            } else {

                (payloads[0] as Bundle).let {
                    if (it.containsKey(KEY_COMMENT)) {
                        itemView.commentsCount.text = post.commentsCount.toString()
                    }
                    if (it.containsKey(KEY_VOTES)) {
                        itemView.commentsCount.text = post.votesCount.toString()
                    }
                }
            }
        }
    }

    class LoadingViewHolder(parent: ViewGroup, @LayoutRes layoutResId: Int) :
        BaseViewHolder(parent, layoutResId) {

        override val containerView: View?
            get() = itemView

        fun bind() {
            itemView.visibility = View.VISIBLE
        }
    }

    private class DiffUtilPosts(
        private val oldList: List<PostUiModel>,
        private val newList: List<PostUiModel>
    ) : DiffUtil.Callback() {

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
                oldList[oldItemPosition].id == newList[newItemPosition].id

        override fun getOldListSize() = oldList.size

        override fun getNewListSize() = newList.size

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
                oldList[oldItemPosition] == newList[newItemPosition]

        override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
            val oldPost = oldList[oldItemPosition]
            val newPost = newList[newItemPosition]
            val diffBundle = Bundle()

            if (oldPost.commentsCount != newPost.commentsCount) {
                diffBundle.putLong(KEY_COMMENT, newPost.commentsCount)
            }

            if (oldPost.votesCount != newPost.votesCount) {
                diffBundle.putLong(KEY_VOTES, newPost.votesCount)
            }

            return if (diffBundle.isEmpty) null else diffBundle
        }
    }
}
