package com.github.romullodev.generic_expandable_adapter.demo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.github.romullodev.generic_expandable_adapter.databinding.ActivityDemoBinding

class DemoActivity : AppCompatActivity() {
    lateinit var binding: ActivityDemoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        theme.applyStyle(androidx.appcompat.R.style.Theme_AppCompat_Light_NoActionBar, true)
        binding = ActivityDemoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.root.setOnClickListener {

        }
    }
}