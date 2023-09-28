package com.reznikov.webview

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.reznikov.webview.databinding.ActivityNoInternetConnectionBinding

class NoInternetConnection : AppCompatActivity() {
    private lateinit var binding: ActivityNoInternetConnectionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNoInternetConnectionBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}