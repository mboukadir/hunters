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

package com.mb.hunters.ui.home.di

import com.mb.hunters.di.PerActivity
import com.mb.hunters.ui.base.Navigator
import com.mb.hunters.ui.common.HuntersNavigator
import com.mb.hunters.ui.home.HomeActivity
import com.mb.hunters.ui.home.collection.di.CollectionModuleBuilder
import com.mb.hunters.ui.home.posts.di.PostsModuleBuilder
import dagger.Binds
import dagger.Module
import dagger.android.ActivityKey
import dagger.android.ContributesAndroidInjector

@Module
abstract class HomeBuilderModule {

    @PerActivity
    @ContributesAndroidInjector(
            modules = [PostsModuleBuilder::class, CollectionModuleBuilder::class])
    @ActivityKey(HomeActivity::class)
    abstract fun bindHomeActivity(): HomeActivity


}