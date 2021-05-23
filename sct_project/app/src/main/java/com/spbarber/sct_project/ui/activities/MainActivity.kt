package com.spbarber.sct_project.ui.activities

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.spbarber.sct_project.R
import com.spbarber.sct_project.databinding.ActivityMainBinding
import com.spbarber.sct_project.listeners.MainListener

class MainActivity : AppCompatActivity(), MainListener {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        val navView: BottomNavigationView = binding.bottomNavView
        val navController = findNavController(R.id.nav_host_fragment_container)
        navView.setupWithNavController(navController)
    }

    override fun showBottomNavigation() {
        binding.mainBottomNavSeparator.visibility = View.VISIBLE
        binding.bottomNavView.visibility = View.VISIBLE
        binding.btnMore.visibility = View.VISIBLE
        binding.mainTopNavSeparator. visibility = View.VISIBLE
    }

    override fun hideBottomNavigation() {
        binding.bottomNavView.visibility = View.GONE
    }

}