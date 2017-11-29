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

package com.mb.hunters.ui.common

import android.app.Activity
import android.graphics.BitmapFactory
import android.net.Uri
import android.support.customtabs.CustomTabsIntent
import com.mb.hunters.R
import com.mb.hunters.ui.base.Navigator
import com.mb.hunters.ui.common.chromtab.CustomTabActivityHelper
import com.mb.hunters.ui.common.chromtab.WebviewFallback
import com.mb.hunters.ui.home.posts.PostUiModel
import dagger.Reusable
import javax.inject.Inject

@Reusable
class HuntersNavigator @Inject constructor() : Navigator {

    override fun toDetailPost(from: Activity, post: PostUiModel) {

        val customTabsIntent = CustomTabsIntent.Builder().apply {
            setStartAnimations(from, R.anim.slide_in_right, R.anim.slide_out_left)
            setExitAnimations(from, android.R.anim.slide_in_left,
                    android.R.anim.slide_out_right)

            setToolbarColor(from.resources.getColor(R.color.primary))
            setSecondaryToolbarColor(
                    from.resources.getColor(R.color.primary_dark))
            setCloseButtonIcon(BitmapFactory.decodeResource(from.getResources(),
                    R.drawable.ic_arrow_back))
            setShowTitle(true)
            addDefaultShareMenuItem()
        }.build()

        CustomTabActivityHelper.openCustomTab(from, customTabsIntent,
                Uri.parse(post.postUrl), post.title, WebviewFallback())
    }
}