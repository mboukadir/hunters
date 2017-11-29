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

import android.os.Bundle
import com.mb.hunters.R
import com.mb.hunters.ui.base.BaseActivity
import com.mb.hunters.ui.common.chromtab.CustomTabActivityHelper
import com.mb.hunters.ui.common.extensions.replaceFragmentInActivity
import com.mb.hunters.ui.home.posts.PostsFragment
import kotlinx.android.synthetic.main.home_activity.homeBottomNav
import kotlinx.android.synthetic.main.home_activity.homeContainer

class HomeActivity : BaseActivity() {

    private val customTabActivityHelper = CustomTabActivityHelper()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_activity)

        homeBottomNav.setOnNavigationItemSelectedListener({

            when (it.itemId) {
                R.id.home_nav_posts -> {

                    replaceFragmentInActivity(PostsFragment(), homeContainer.id)
                    true
                }
                R.id.home_nav_collection -> {
                    replaceFragmentInActivity(PostsFragment(), homeContainer.id)
                    true
                }
                else -> true
            }
        })

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
}
