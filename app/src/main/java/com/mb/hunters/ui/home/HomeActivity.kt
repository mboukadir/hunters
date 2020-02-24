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

package com.mb.hunters.ui.home

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.mb.hunters.R
import com.mb.hunters.ui.base.BaseActivity
import com.mb.hunters.ui.common.chromtab.CustomTabActivityHelper
import com.mb.hunters.ui.common.extensions.replaceFragmentInActivity
import com.mb.hunters.ui.home.collection.CollectionsFragment
import com.mb.hunters.ui.home.posts.PostsFragment
import kotlinx.android.synthetic.main.home_activity.*
import timber.log.Timber

class HomeActivity : BaseActivity() {

    private val customTabActivityHelper = CustomTabActivityHelper()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportFragmentManager.registerFragmentLifecycleCallbacks(fragmentLifecycleCallbacks, true)
        setContentView(R.layout.home_activity)

        homeBottomNav.setOnNavigationItemSelectedListener {

            when (it.itemId) {
                R.id.home_nav_posts -> {

                    replaceFragmentInActivity(PostsFragment(), homeContainer.id)
                    true
                }
                R.id.home_nav_collection -> {
                    replaceFragmentInActivity(CollectionsFragment.newInstance(), homeContainer.id)
                    true
                }
                else -> true
            }
        }

        if (savedInstanceState == null) {

            homeBottomNav.selectedItemId = R.id.home_nav_posts
        }
    }

    override fun onStart() {
        super.onStart()
        customTabActivityHelper.bindCustomTabsService(this)
    }

    override fun onStop() {
        super.onStop()
        customTabActivityHelper.unbindCustomTabsService(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        supportFragmentManager.unregisterFragmentLifecycleCallbacks(fragmentLifecycleCallbacks)
    }

    private val fragmentLifecycleCallbacks = object : FragmentManager.FragmentLifecycleCallbacks() {

        override fun onFragmentAttached(fm: FragmentManager, f: Fragment, context: Context) {
            super.onFragmentAttached(fm, f, context)
            Timber.d("onFragmentAttached : $f")
        }

        override fun onFragmentActivityCreated(
            fm: FragmentManager,
            f: Fragment,
            savedInstanceState: Bundle?
        ) {
            super.onFragmentActivityCreated(fm, f, savedInstanceState)
            Timber.d("onFragmentActivityCreated : $f")
            Timber.d("onFragmentActivityCreated  savedInstanceState : $savedInstanceState")
        }

        override fun onFragmentViewCreated(
            fm: FragmentManager,
            f: Fragment,
            v: View,
            savedInstanceState: Bundle?
        ) {
            super.onFragmentViewCreated(fm, f, v, savedInstanceState)
            Timber.d("onFragmentViewCreated : $f")
        }

        override fun onFragmentStarted(fm: FragmentManager, f: Fragment) {
            super.onFragmentStarted(fm, f)
            Timber.d("onFragmentStarted : $f")
        }

        override fun onFragmentResumed(fm: FragmentManager, f: Fragment) {
            super.onFragmentResumed(fm, f)
            Timber.d("onFragmentResumed : $f")
        }

        override fun onFragmentPaused(fm: FragmentManager, f: Fragment) {
            super.onFragmentPaused(fm, f)
            Timber.d("onFragmentPaused : $f")
        }

        override fun onFragmentSaveInstanceState(
            fm: FragmentManager,
            f: Fragment,
            outState: Bundle
        ) {
            super.onFragmentSaveInstanceState(fm, f, outState)
            Timber.d("onFragmentSaveInstanceState : $outState")
        }

        override fun onFragmentStopped(fm: FragmentManager, f: Fragment) {
            super.onFragmentStopped(fm, f)
            Timber.d("onFragmentStopped : $f")
        }

        override fun onFragmentViewDestroyed(fm: FragmentManager, f: Fragment) {
            super.onFragmentViewDestroyed(fm, f)
            Timber.d("onFragmentViewDestroyed : $f")
        }

        override fun onFragmentDestroyed(fm: FragmentManager, f: Fragment) {
            super.onFragmentDestroyed(fm, f)
            Timber.d("onFragmentDestroyed : $f")
        }

        override fun onFragmentDetached(fm: FragmentManager, f: Fragment) {
            super.onFragmentDetached(fm, f)
            Timber.d("onFragmentDetached : $f")
        }
    }
}
