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
import com.mb.hunters.data.database.entity.PostEntity
import com.mb.hunters.data.repository.PostRepository
import com.mb.hunters.ui.base.BaseViewModel
import com.mb.hunters.ui.common.extensions.daysAgo
import dagger.Lazy
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

class PostsViewModel @Inject constructor(private val postRepository: Lazy<PostRepository>) :
        BaseViewModel() {

    val morePosts = MutableLiveData<List<PostUiModel>>()
    val toDayPosts = MutableLiveData<List<PostUiModel>>()

    fun loadToDayPost() {
        loadPosts(livedata = toDayPosts)
    }

    fun refreshToDayPost() {
        disposables.add(postRepository.get()
                .refreshPosts(0)
                .map { postEnityList -> postEnityList.map { mapToUiModel(it) } }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
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

    fun loadPosts(daysAgo: Long = 0, livedata: MutableLiveData<List<PostUiModel>>) {
        disposables.add(postRepository.get()
                .loadPosts(daysAgo)
                .map { postEnityList ->
                    postEnityList.map { mapToUiModel(it) }
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    livedata.value = it
                }, {
                    Timber.e(it)
                })
        )
    }

    private fun mapToUiModel(postEntity: PostEntity): PostUiModel {

        return with(postEntity) {
            PostUiModel(
                    id = id,
                    title = name,
                    subTitle = tagline,
                    postUrl = redirectUrl,
                    votesCount = votesCount,
                    commentsCount = commentsCount,
                    daysAgo = day.daysAgo(),
                    date = createdAt.toString(),
                    bigImageUrl = screenshotUrl,
                    smallImageUrl = thumbnailUrl
            )
        }

    }

}
