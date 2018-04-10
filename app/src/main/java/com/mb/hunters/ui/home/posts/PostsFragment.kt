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
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mb.hunters.R
import com.mb.hunters.ui.base.BaseFragment
import com.mb.hunters.ui.base.Navigator
import com.mb.hunters.ui.common.EndlessRecyclerViewScrollListener
import kotlinx.android.synthetic.main.home_post_fragment_list.loading
import kotlinx.android.synthetic.main.home_post_fragment_list.postRecyclerView
import kotlinx.android.synthetic.main.home_post_fragment_list.postSwipeRefreshLayout
import kotlinx.android.synthetic.main.home_post_fragment_list.view.postRecyclerView
import kotlinx.android.synthetic.main.home_post_fragment_list.view.posts_fragment_toolbar
import timber.log.Timber
import javax.inject.Inject

class PostsFragment : BaseFragment(), PostAdapter.ItemActionListener {

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    @Inject lateinit var navigator: Navigator

    private lateinit var postAdapter: PostAdapter
    private lateinit var postViewModel: PostsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        Timber.d("OnCreatView")

        val view = inflater.inflate(R.layout.home_post_fragment_list, container, false)

        setupToolbar(view)

        setupRecyclerView(view)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        Timber.d("onActivityCreated")

        postViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(PostsViewModel::class.java)

        observe()

        postSwipeRefreshLayout.setOnRefreshListener {
            postViewModel.refreshToDayPost()
        }

        postViewModel.loadToDayPost()

    }

    private fun observe() {
        with(postViewModel) {

            morePosts.observe(this@PostsFragment, Observer {
                it?.let {
                    postAdapter.finishedLoadingMore()
                    postAdapter.showMore(it)

                }
            })

            toDayPosts.observe(this@PostsFragment, Observer {
                it?.let {
                    postAdapter.update(it)
                    loading.visibility = View.GONE
                    postRecyclerView.visibility = View.VISIBLE

                }
            })

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
            layoutManager = LinearLayoutManager(context)
            adapter = this@PostsFragment.postAdapter
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            addOnScrollListener(EndlessRecyclerViewScrollListener(
                layoutManager as LinearLayoutManager
            ) { _: Int, recyclerView: RecyclerView ->
                recyclerView.post {
                    if (postAdapter.showLoadingMore.not()) {
                        postAdapter.startLoadingMore()
                        postViewModel.loadMore(postAdapter.getLastItemDayAgo() + 1)
                    }
                }

            })
        }
    }

    override fun onItemClick(item: PostUiModel) {

        navigator.toDetailPost(activity as Activity, item)

    }

}
