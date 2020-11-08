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

package com.mb.hunters.di

import com.mb.hunters.common.dispatcher.DispatchersProvider
import com.mb.hunters.di.TestAppModule.InternalTestAppModule
import com.mb.hunters.ui.UiModule
import com.mb.hunters.ui.home.di.HomeBuilderModule
import dagger.Module
import dagger.Provides
import javax.inject.Singleton
import kotlinx.coroutines.Dispatchers

@Module(
    includes = [
        InternalTestAppModule::class,
        TestDataModule::class,
        HomeBuilderModule::class,
        UiModule::class
    ]
)
class TestAppModule {

    @Module
    internal object InternalTestAppModule {

        @JvmStatic
        @Singleton
        @Provides
        fun provideCoroutinesDispatcherProvider(): DispatchersProvider {
            return DispatchersProvider(
                Dispatchers.Unconfined,
                Dispatchers.Unconfined,
                Dispatchers.Unconfined
            )
        }
    }
}
