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