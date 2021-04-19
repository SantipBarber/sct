package com.spbarber.sct_project.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Scroller
import com.google.android.material.snackbar.Snackbar
import com.spbarber.sct_project.databinding.ActivityLoginBinding
import com.spbarber.sct_project.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.btnToLogin.setOnClickListener {
            val intent = Intent(this, login::class.java)
            Snackbar.make(view, "Vamos hacia el Login", Snackbar.LENGTH_LONG).show()
            startActivity(intent)
        }
    }
}