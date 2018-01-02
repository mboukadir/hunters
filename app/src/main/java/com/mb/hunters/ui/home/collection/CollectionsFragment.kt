package com.mb.hunters.ui.home.collection

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mb.hunters.R
import com.mb.hunters.ui.base.BaseFragment
import com.mb.hunters.ui.base.Navigator
import kotlinx.android.synthetic.main.home_collection_fragment_list.collectionRecyclerView
import kotlinx.android.synthetic.main.home_collection_fragment_list.loading
import kotlinx.android.synthetic.main.home_collection_fragment_list.view.collectionRecyclerView
import kotlinx.android.synthetic.main.home_post_fragment_list.postRecyclerView
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


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
            savedInstanceState: Bundle?): View? {

        val root = inflater!!.inflate(R.layout.home_collection_fragment_list, container, false)

        setupRecyclerView(root)

        return root
    }


    private fun setupRecyclerView(root:View) {

        collectionAdapter = CollectionsAdapter()
        root.collectionRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = collectionAdapter
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        collectionsViewModel.errorMessage.observe(this@CollectionsFragment, Observer {
            it?.let {
                Snackbar.make(this.view!!,it.toString(),Snackbar.LENGTH_LONG).show()
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
        fun newInstance(): Fragment {

            return CollectionsFragment()
        }
    }
}
