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

package com.mb.hunters.ui.common.chromtab

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.mb.hunters.R
import kotlinx.android.synthetic.main.home_detail_post_activity_webview.*

/**
 * This Activity is used as a fallback when there is no browser installed that supports
 * Chrome Custom Tabs
 */
class WebViewActivity : AppCompatActivity() {

    private val url: String by lazy {
        intent.getStringExtra(EXTRA_URL)
    }

    private val title: String by lazy {
        intent.getStringExtra(EXTRA_TITLE)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_detail_post_activity_webview)

        enableJavascript()
        enableCaching()
        enableCustomClients()
        zoomedOut()

        setSupportActionBar(toolbar)
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.title = title
            actionBar.subtitle = url
            actionBar.setDisplayHomeAsUpEnabled(true)
        }

        webView.loadUrl(url)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        // Check if the key event was the Back button and if there's history
        if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
            webView.goBack()
            return true
        }
        // If it wasn't the Back key or there's no web page history, bubble up to the default
        // system behavior (probably exit the activity)
        return super.onKeyDown(keyCode, event)
    }

    private fun enableCustomClients() {
        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                view.loadUrl(url)
                return true
            }

            override fun onPageFinished(view: WebView, url: String) {
                super.onPageFinished(view, url)
                progressBar.visibility = View.GONE
            }
        }

        webView.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView, progress: Int) {
                setProgress(progress * PROGRESS_RATIO)
            }
        }
    }

    private fun enableJavascript() {
        webView.settings.javaScriptEnabled = true
        webView.settings.javaScriptCanOpenWindowsAutomatically = true
    }

    private fun enableCaching() {
        webView.settings.setAppCachePath(filesDir.toString() + packageName + "/cache")
        webView.settings.setAppCacheEnabled(true)
        webView.settings.cacheMode = WebSettings.LOAD_DEFAULT
    }

    private fun zoomedOut() {
        webView.settings.loadWithOverviewMode = true
        webView.settings.useWideViewPort = true
    }

    companion object {
        val EXTRA_URL = "extra.url"
        val EXTRA_TITLE = "EXTRA_TITLE"

        val EXTRA_IS_FAVORIS = "extra.favoris"
        private val PROGRESS_RATIO = 1000

        fun toUrl(context: Activity, url: String, title: String) {
            val intent = Intent(context, WebViewActivity::class.java)
            intent.putExtra(EXTRA_URL, url)
            intent.putExtra(EXTRA_TITLE, title)
            context.startActivity(intent)
        }

        fun toUrl(context: Activity, requestCode: Int, url: String, title: String) {
            val intent = Intent(context, WebViewActivity::class.java)
            intent.putExtra(EXTRA_URL, url)
            intent.putExtra(EXTRA_TITLE, title)
            context.startActivityForResult(intent, requestCode)
        }
    }
}
