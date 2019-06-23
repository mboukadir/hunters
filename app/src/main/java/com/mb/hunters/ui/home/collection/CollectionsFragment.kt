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

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mb.hunters.R
import com.mb.hunters.ui.base.BaseFragment
import com.mb.hunters.ui.base.Navigator
import kotlinx.android.synthetic.main.home_collection_fragment_list.collectionRecyclerView
import kotlinx.android.synthetic.main.home_collection_fragment_list.loading
import kotlinx.android.synthetic.main.home_collection_fragment_list.view.collectionRecyclerView
import javax.inject.Inject

class CollectionsFragment : BaseFragment() {

    @Inject lateinit var navigator: Navigator
    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var collectionsViewModel: CollectionsViewModel
    private lateinit var collectionAdapter: CollectionsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        collectionsViewModel = ViewModelProviders.of(this, viewModelFactory).get(
                CollectionsViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?): View? {

        val root = inflater.inflate(R.layout.home_collection_fragment_list, container, false)

        setupRecyclerView(root)

        return root
    }

    private fun setupRecyclerView(root: View) {

        collectionAdapter = CollectionsAdapter()
        root.collectionRecyclerView.apply {
            layoutManager = androidx.recyclerview.widget.LinearLayoutManager(context)
            adapter = collectionAdapter
            addItemDecoration(androidx.recyclerview.widget.DividerItemDecoration(context, androidx.recyclerview.widget.DividerItemDecoration.VERTICAL))
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        collectionsViewModel.errorMessage.observe(this@CollectionsFragment, Observer {
            it?.let {
                Snackbar.make(this.view!!, it.toString(), Snackbar.LENGTH_LONG).show()
            }
        })

        collectionsViewModel.collections.observe(this, Observer {
            it?.let {
                collectionAdapter.render(it)
                loading.visibility = View.GONE
                collectionRecyclerView.visibility = View.VISIBLE

            }
        })

        collectionsViewModel.loadCollections()

    }

    companion object {
        fun newInstance(): androidx.fragment.app.Fragment {

            return CollectionsFragment()
        }
    }
}
