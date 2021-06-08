package com.spbarber.sct_project.ui.activities

import android.app.PendingIntent
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.material.tabs.TabLayout
import com.spbarber.sct_project.App
import com.spbarber.sct_project.R
import com.spbarber.sct_project.databinding.ActivityMainBinding
import com.spbarber.sct_project.databinding.FragmentLoginBinding
import java.nio.file.attribute.AclEntry

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var _binding: FragmentLoginBinding
    private var newProgram = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root

        _binding = FragmentLoginBinding.inflate(layoutInflater)
        val extras = intent.extras
        if (extras != null) {
                newProgram = extras.getBoolean("newProgram")
        }


        setContentView(view)
    }

    override fun onStart() {
        super.onStart()
        if (App.getAuth().currentUser?.uid != null && !newProgram) {
            val intent = Intent(this, AppActivity::class.java)
            startActivity(intent)
        }
    }

}