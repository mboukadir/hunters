package com.mb.hunters.ui.home.collection

import android.arch.lifecycle.MutableLiveData
import com.mb.hunters.data.repository.collection.CollectionRepository
import com.mb.hunters.ui.base.BaseViewModel
import com.mb.hunters.ui.base.SchedulerProvider
import com.mb.hunters.ui.common.SingleLiveEvent
import timber.log.Timber
import javax.inject.Inject

class CollectionsViewModel @Inject constructor(
        private val schedulerProvider: SchedulerProvider,
        private val mapper: CollectionMapper,
        private val collectionRepository: CollectionRepository
) : BaseViewModel() {

    val collections = MutableLiveData<List<CollectionUiModel>>()
    val errorMessage = SingleLiveEvent<String>()


    fun loadCollections() {

        disposables.add(
                collectionRepository.getCollections()
                        .map { mapper.mapToUiModel(it) }
                        .subscribeOn(schedulerProvider.io())
                        .observeOn(schedulerProvider.mainThread())
                        .subscribe({
                            collections.value = it
                        }, {

                            errorMessage.value = it.message
                            Timber.e(it)
                        })
        )

    }
}