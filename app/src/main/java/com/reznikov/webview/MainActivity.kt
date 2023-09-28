package com.reznikov.webview

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigException
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import com.reznikov.webview.databinding.ActivityMainBinding
import com.reznikov.webview.utils.CheckIsEmu
import com.reznikov.webview.utils.Constants.Companion.KEY_URL
import com.reznikov.webview.utils.Constants.Companion.LINK_FIREBASE
import com.reznikov.webview.utils.InternetConnection
import com.reznikov.webview.utils.SharedPreferences

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private lateinit var remoteConfig: FirebaseRemoteConfig

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        logicApp()
    }

    private fun logicApp() {
        val sharedPreferences = SharedPreferences(this)

        if (sharedPreferences.contains(LINK_FIREBASE)) {
            if (!InternetConnection(this).isAvailable()) {
                showNoInternetConnection()
            } else {
                openLinkInWebView()
            }
        } else {
            initFirebase()
            connectFirebase()
        }
    }

    private fun initFirebase() {
        remoteConfig = FirebaseRemoteConfig.getInstance()
        val configSettings = FirebaseRemoteConfigSettings.Builder()
            .setMinimumFetchIntervalInSeconds(0)
            .build()
        remoteConfig.setConfigSettingsAsync(configSettings)
    }

    private fun connectFirebase() {
        try {
            remoteConfig.fetchAndActivate().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val url = remoteConfig.getString(KEY_URL)
                    if (url.isEmpty() || CheckIsEmu().checkIsEmu()) {
                        showStubScreen()
                    } else {
                        val sharedPreferences = SharedPreferences(this)
                        sharedPreferences.save(LINK_FIREBASE, url)

                        openLinkInWebView()
                    }
                } else {
                    showStubScreen()
                }
            }.addOnFailureListener {
                showNoInternetConnection()
            }
        } catch (e: FirebaseRemoteConfigException) {
            showNoInternetConnection()
        }
    }

    private fun showNoInternetConnection() {
        val intent = Intent(this, NoInternetConnection::class.java)
        startActivity(intent)
    }

    private fun showStubScreen() {
        val intent = Intent(this, StubScreen::class.java)
        startActivity(intent)
    }

    private fun openLinkInWebView() {
        val intent = Intent(this, WebView::class.java)
        startActivity(intent)
    }

}