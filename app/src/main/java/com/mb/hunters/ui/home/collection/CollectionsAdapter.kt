package com.mb.hunters.ui.home.collection

import android.support.annotation.LayoutRes
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.mb.hunters.R
import com.mb.hunters.ui.base.BaseViewHolder
import com.mb.hunters.ui.home.collection.CollectionsAdapter.CollectionViewHolder
import kotlinx.android.synthetic.main.home_collection_list_item.view.backgroundImage
import kotlinx.android.synthetic.main.home_collection_list_item.view.name
import kotlinx.android.synthetic.main.home_collection_list_item.view.title

class CollectionsAdapter : RecyclerView.Adapter<CollectionViewHolder>() {

    private val items = mutableListOf<CollectionUiModel>()

    override fun onBindViewHolder(holder: CollectionViewHolder, position: Int) {

        holder.bind(items[position])
    }

    override fun getItemCount(): Int {

        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CollectionViewHolder {
        return CollectionViewHolder(parent, R.layout.home_collection_list_item)
    }

    fun render(collectionUiModelList: List<CollectionUiModel>) {
        items.addAll(collectionUiModelList)
        notifyDataSetChanged()
    }

    class CollectionViewHolder(parent: ViewGroup, @LayoutRes layoutId: Int) :
            BaseViewHolder(parent, layoutId) {

        fun bind(collectionUiModel: CollectionUiModel) {
            Glide.with(itemView).load(collectionUiModel.backgroundImageUrl).into(
                    itemView.backgroundImage)
            itemView.name.text = collectionUiModel.name
            itemView.title.text = collectionUiModel.title

        }

    }

}