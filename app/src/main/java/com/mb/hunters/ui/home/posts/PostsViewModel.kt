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

import android.arch.lifecycle.MutableLiveData
import com.mb.hunters.data.repository.post.PostRepository
import com.mb.hunters.ui.base.BaseViewModel
import com.mb.hunters.ui.base.SchedulerProvider
import dagger.Lazy
import timber.log.Timber
import javax.inject.Inject

class PostsViewModel @Inject constructor(
        private val mapper: PostMapper,
        private val schedulerProvider: SchedulerProvider,
        private val postRepository: Lazy<PostRepository>

) : BaseViewModel() {

    val morePosts = MutableLiveData<List<PostUiModel>>()
    val toDayPosts = MutableLiveData<List<PostUiModel>>()

    fun loadToDayPost() {
        loadPosts(liveData = toDayPosts)
    }

    fun refreshToDayPost() {
        disposables.add(postRepository.get()
                .refreshPosts(0)
                .map { postEntityList -> postEntityList.map { mapper.mapToUiModel(it) } }
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.mainThread())
                .subscribe({
                    toDayPosts.value = it
                }, {
                    Timber.e(it)
                })
        )
    }

    fun loadMore(daysAgo: Long) {
        loadPosts(daysAgo, morePosts)
    }

    private fun loadPosts(daysAgo: Long = 0, liveData: MutableLiveData<List<PostUiModel>>) {
        disposables.add(postRepository.get()
                .loadPosts(daysAgo)
                .map { postEnityList ->
                    postEnityList.map { mapper.mapToUiModel(it) }
                }
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.mainThread())
                .subscribe({
                    liveData.value = it
                }, {
                    Timber.e(it)
                })
        )
    }

}
