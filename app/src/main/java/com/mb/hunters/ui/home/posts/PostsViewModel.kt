/*
 * Copyright 2017-2022 Mohammed Boukadir
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.mb.hunters.ui.home.posts

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mb.hunters.common.dispatcher.DispatchersProvider
import com.mb.hunters.data.repository.post.PostRepository
import com.mb.hunters.ui.base.BaseViewModel
import com.mb.hunters.ui.home.posts.model.PostMapper
import com.mb.hunters.ui.home.posts.model.PostUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber

@HiltViewModel
class PostsViewModel @Inject constructor(
    private val mapper: PostMapper,
    private val postRepository: PostRepository,
    dispatchersProvider: DispatchersProvider

) : BaseViewModel(dispatchersProvider) {

    private val _posts = MutableLiveData<List<PostUiModel>>()
    val posts: LiveData<List<PostUiModel>> = _posts

    val isRefreshing = mutableStateOf(false)

    init {
        loadToDayPost()
    }

    private fun loadToDayPost() {
        viewModelScope.launch {
            runCatching {
                _posts.value =
                    mapper.map(
                        postRepository
                            .loadPosts(0)
                    )
            }.onFailure {
                Timber.e(it)
            }

            delay(5000)
            isRefreshing.value = false
        }
    }

    fun onRefresh() {
        isRefreshing.value = true
        loadToDayPost()
    }

    fun loadMore(currentDay: Long) {
        loadPosts(currentDay + 1)
    }

    private fun loadPosts(daysAgo: Long = 0) {
        viewModelScope.launch {
            runCatching {
                val newItems = mapper.map(
                    postRepository.loadPosts(daysAgo)
                )
                _posts.value = _posts.value!!.toMutableList() + newItems
            }.onFailure {
                Timber.e(it)
            }
        }
    }
}
