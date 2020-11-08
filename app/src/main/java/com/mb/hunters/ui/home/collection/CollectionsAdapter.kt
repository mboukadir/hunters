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

import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mb.hunters.R
import com.mb.hunters.ui.base.BaseViewHolder
import com.mb.hunters.ui.home.collection.CollectionsAdapter.CollectionViewHolder
import kotlinx.android.synthetic.main.home_collection_list_item.view.*
import timber.log.Timber

class CollectionsAdapter(private val actionListener: ItemActionListener) : ListAdapter<CollectionUiModel, CollectionViewHolder>(DIFF_CALLBACK) {

    override fun onBindViewHolder(holder: CollectionViewHolder, position: Int) {
        Timber.d("Position = $position")
        holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CollectionViewHolder {
        return CollectionViewHolder(parent, R.layout.home_collection_list_item)
    }

    inner class CollectionViewHolder(parent: ViewGroup, @LayoutRes layoutId: Int) :
        BaseViewHolder(parent, layoutId) {

        init {
            itemView.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    actionListener.onItemClick(getItem(adapterPosition))
                }
            }
        }

        fun bind(collectionUiModel: CollectionUiModel) {
            Glide.with(itemView).load(collectionUiModel.backgroundImageUrl).into(
                itemView.backgroundImage
            )
            itemView.name.text = collectionUiModel.name
            itemView.title.text = collectionUiModel.title
        }
    }

    interface ItemActionListener {

        fun onItemClick(item: CollectionUiModel)
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<CollectionUiModel>() {
            override fun areItemsTheSame(oldItem: CollectionUiModel, newItem: CollectionUiModel): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: CollectionUiModel, newItem: CollectionUiModel): Boolean =
                oldItem == newItem
        }
    }
}
