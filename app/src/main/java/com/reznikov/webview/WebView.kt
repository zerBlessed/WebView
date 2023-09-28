package com.reznikov.webview

import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.CookieManager
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.reznikov.webview.databinding.ActivityWebViewBinding
import com.reznikov.webview.utils.Constants.Companion.LINK_FIREBASE
import com.reznikov.webview.utils.SharedPreferences

class WebView : AppCompatActivity() {
    private lateinit var binding: ActivityWebViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWebViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initWebView(savedInstanceState)
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initWebView(savedInstanceState: Bundle?) {
        val sharedPreferences = SharedPreferences(this)
        val url = sharedPreferences.getValue(LINK_FIREBASE)

        binding.webView.webViewClient = WebViewClient()
        val webSettings = binding.webView.settings
        webSettings.javaScriptEnabled = true

        if (savedInstanceState != null)
            binding.webView.restoreState(savedInstanceState);
        else
            url?.let { binding.webView.loadUrl(it) }

        binding.webView.settings.domStorageEnabled = true
        binding.webView.settings.javaScriptCanOpenWindowsAutomatically = true

        val cookieManager = CookieManager.getInstance()
        cookieManager.setAcceptCookie(true)

        webSettings.loadWithOverviewMode = true
        webSettings.useWideViewPort = true
        webSettings.domStorageEnabled = true
        webSettings.databaseEnabled = true
        webSettings.setSupportZoom(false)
        webSettings.allowFileAccess = true
        webSettings.allowContentAccess = true
        webSettings.loadWithOverviewMode = true
        webSettings.useWideViewPort = true
    }

    override fun onSaveInstanceState(outState: Bundle) {
        binding.webView.saveState(outState)
        super.onSaveInstanceState(outState)
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        if (binding.webView.canGoBack()) {
            binding.webView.goBack()
        }
    }
}