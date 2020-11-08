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

package com.mb.hunters.ui.base

import androidx.lifecycle.ViewModel
import com.mb.hunters.common.dispatcher.DispatchersProvider
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import timber.log.Timber

abstract class BaseViewModel(dispatchersProvider: DispatchersProvider) : ViewModel() {
    val disposables = CompositeDisposable()
    val viewModelScope =
        CoroutineScope(
            SupervisorJob() +
                dispatchersProvider.main +
                CoroutineExceptionHandler { _, throwable -> Timber.e(throwable) }
        )

    override fun onCleared() {
        disposables.clear()
        viewModelScope.coroutineContext.cancel()
        super.onCleared()
    }
}
