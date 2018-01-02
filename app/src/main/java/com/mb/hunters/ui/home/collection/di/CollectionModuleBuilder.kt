package com.mb.hunters.ui.home.collection.di

import android.arch.lifecycle.ViewModel
import com.mb.hunters.di.PerFragment
import com.mb.hunters.di.ViewModelKey
import com.mb.hunters.ui.home.collection.CollectionsFragment
import com.mb.hunters.ui.home.collection.CollectionsViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.android.support.FragmentKey
import dagger.multibindings.IntoMap

@Module
abstract class CollectionModuleBuilder {

    @PerFragment
    @ContributesAndroidInjector(modules = [CollectionsModule::class])
    @FragmentKey(CollectionsFragment::class)
    abstract fun bindCollectionsFragment(): CollectionsFragment

    @Binds
    @IntoMap
    @ViewModelKey(CollectionsViewModel::class)
    abstract fun bindCollectionViewModel(collectionsViewModel: CollectionsViewModel): ViewModel


}