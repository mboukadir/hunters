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

package com.mb.hunters.ui.home.collection

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.observe
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.mb.hunters.R
import com.mb.hunters.ui.base.BaseFragment
import com.mb.hunters.ui.base.Navigator
import com.mb.hunters.ui.home.collection.adapter.CollectionLoadStateAdapter
import com.mb.hunters.ui.home.collection.adapter.CollectionsAdapter
import com.mb.hunters.ui.home.collection.model.CollectionUiModel
import kotlinx.android.synthetic.main.home_collection_fragment_list.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@ExperimentalCoroutinesApi
class CollectionsFragment : BaseFragment(), CollectionsAdapter.ItemActionListener {

    @Inject
    lateinit var navigator: Navigator
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val collectionsViewModel: CollectionsViewModel by viewModels { viewModelFactory }
    private lateinit var collectionAdapter: CollectionsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.home_collection_fragment_list, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()

        collectionsViewModel.errorMessage.observe(viewLifecycleOwner) {
            Snackbar.make(view, it, Snackbar.LENGTH_LONG).show()
        }


        collectionsViewModel.collections.observe(viewLifecycleOwner) {
            collectionAdapter.submitData(lifecycle,it)

        }

        collectionsViewModel.syncCollections()
    }

    private fun setupRecyclerView() {
        loading.visibility = View.GONE
        collectionRecyclerView.visibility = View.VISIBLE
        collectionAdapter =
            CollectionsAdapter(this)
        lifecycleScope.launchWhenCreated {
            collectionAdapter.loadStateFlow.collectLatest {
                    loadStates ->
                homeCollectionSwipeRefreshLayout.isRefreshing = loadStates.refresh is LoadState.Loading
            }
        }

        lifecycleScope.launchWhenCreated {
            @OptIn(ExperimentalPagingApi::class, ExperimentalCoroutinesApi::class)
            collectionAdapter.dataRefreshFlow.collectLatest {
                collectionRecyclerView.scrollToPosition(0)
            }

        }

        collectionRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = collectionAdapter.withLoadStateFooter(footer = CollectionLoadStateAdapter(
                collectionAdapter
            )
            )
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }


        homeCollectionSwipeRefreshLayout.setOnRefreshListener {
            collectionAdapter.refresh()
        }

    }

    companion object {
        fun newInstance(): Fragment {
            return CollectionsFragment()
        }
    }

    override fun onItemClick(item: CollectionUiModel) {
        collectionsViewModel.onClicked()
    }
}
