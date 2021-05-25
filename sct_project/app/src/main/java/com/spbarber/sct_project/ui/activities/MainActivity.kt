package com.spbarber.sct_project.ui.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.spbarber.sct_project.App
import com.spbarber.sct_project.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }

    override fun onStart() {
        super.onStart()
        if (App.getAuth().currentUser?.uid != null) {
            val intent = Intent(this, AppActivity::class.java)
            Toast.makeText(
                this,
                "Usuario  ${App.getAuth().currentUser?.displayName}",
                Toast.LENGTH_LONG
            ).show()
            startActivity(intent)
        }
    }

}