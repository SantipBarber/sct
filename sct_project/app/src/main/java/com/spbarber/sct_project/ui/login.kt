package com.spbarber.sct_project.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.spbarber.sct_project.databinding.ActivityLoginBinding

class login : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


    }
}