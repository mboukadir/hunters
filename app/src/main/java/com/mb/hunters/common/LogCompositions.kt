@file:Suppress("NOTHING_TO_INLINE")

package com.mb.hunters.common

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.remember
import com.mb.hunters.BuildConfig

class Ref(var value: Int)

const val EnableDebugCompositionLogs = false

@Composable
inline fun LogCompositions(tag: String) {
    if (EnableDebugCompositionLogs && BuildConfig.DEBUG) {
        val ref = remember { Ref(0) }
        SideEffect { ref.value++ }
        Log.d(tag, "Compositions: ${ref.value}")
    }
}
