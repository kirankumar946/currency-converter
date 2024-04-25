package com.kirankumarbathinoju.currency.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kirankumarbathinoju.currency.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * main activity
 */
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
    }

}