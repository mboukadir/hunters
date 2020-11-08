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

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.DividerItemDecoration
import com.mb.hunters.R
import com.mb.hunters.ui.base.BaseFragment
import com.mb.hunters.ui.base.Navigator
import com.mb.hunters.ui.common.EndlessRecyclerViewScrollListener
import javax.inject.Inject
import kotlinx.android.synthetic.main.home_post_fragment_list.*
import kotlinx.android.synthetic.main.home_post_fragment_list.view.*

class PostsFragment : BaseFragment(), PostAdapter.ItemActionListener {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    @Inject
    lateinit var navigator: Navigator

    private lateinit var postAdapter: PostAdapter
    private val postViewModel: PostsViewModel by viewModels { viewModelFactory }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.home_post_fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar(view)

        setupRecyclerView(view)

        observe()

        postSwipeRefreshLayout.setOnRefreshListener {
            postViewModel.refreshToDayPost()
        }

        postViewModel.loadToDayPost()
    }

    private fun observe() {

        postViewModel.morePosts.observe(viewLifecycleOwner) {
            postAdapter.finishedLoadingMore()
            postAdapter.showMore(it)
        }

        postViewModel.toDayPosts.observe(viewLifecycleOwner) {
            postAdapter.update(it)
            loading.visibility = View.GONE
            postRecyclerView.visibility = View.VISIBLE
        }
    }

    private fun setupToolbar(root: View) {

        with(activity as AppCompatActivity) {
            setSupportActionBar(root.posts_fragment_toolbar)
            supportActionBar?.setTitle(R.string.home_title)
        }
    }

    private fun setupRecyclerView(root: View) {
        postAdapter = PostAdapter(this)

        root.postRecyclerView.apply {
            layoutManager = androidx.recyclerview.widget.LinearLayoutManager(context)
            adapter = this@PostsFragment.postAdapter
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            addOnScrollListener(
                EndlessRecyclerViewScrollListener(
                    layoutManager as androidx.recyclerview.widget.LinearLayoutManager
                ) { _: Int, recyclerView: androidx.recyclerview.widget.RecyclerView ->
                    recyclerView.post {
                        if (postAdapter.showLoadingMore.not()) {
                            postAdapter.startLoadingMore()
                            postViewModel.loadMore(postAdapter.getLastItemDayAgo() + 1)
                        }
                    }
                }
            )
        }
    }

    override fun onItemClick(item: PostUiModel) {

        navigator.toDetailPost(activity as Activity, item)
    }
}
