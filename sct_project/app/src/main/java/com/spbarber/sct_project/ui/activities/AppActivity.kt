package com.spbarber.sct_project.ui.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu
import android.widget.Toast
import androidx.annotation.MenuRes
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import com.spbarber.sct_project.App
import com.spbarber.sct_project.R
import com.spbarber.sct_project.databinding.ActivityAppBinding

class AppActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAppBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAppBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val navView: BottomNavigationView = binding.bottomNavView
        val navController = findNavController(R.id.nav_host_fragment_container_app)
        navView.setupWithNavController(navController)

        binding.btnMore.setOnClickListener { view ->
            showMenu(view, R.menu.overflow_menu)
            Log.i("TAG", "Se ha pulsado el botón de menú")
        }

        binding.btnNew.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("newProgram", true)
            Toast.makeText(this, "Al init fragment, crear nuevo programa", Toast.LENGTH_LONG).show()
            startActivity(intent)
            finish()
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.overflow_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when(item.itemId) {
        R.id.option_3 -> {
            Log.i("TAG", "Pulsando cerrar sesión del menú")
            signOut()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    private fun showMenu(view: View, @MenuRes menuRes: Int){
        val popup = PopupMenu(this, view)
        popup.menuInflater.inflate(menuRes, popup.menu)
        popup.show()
    }

    private fun signOut(){
        Log.i("TAG", "Hemos pulsado el botón de cerrar sesión!")
        App.getAuth().signOut()
    }

    override fun onStart() {
        super.onStart()
        val currentUser = App.getAuth().currentUser
        val nameUser = currentUser?.email
        if(currentUser != null){
            //Snackbar.make(binding.root, "El usuario logueado es $nameUser", Snackbar.LENGTH_LONG).show()
        } else {
            val intent = Intent(this, MainActivity::class.java)
            Toast.makeText(this, "Volviendo al init fragment", Toast.LENGTH_LONG).show()

            startActivity(intent)
            finish()
        }
    }

    fun signOut(item: MenuItem) {
        Log.i("TAG", "Hemos pulsado el botón de cerrar sesión!")
        App.getAuth().signOut()
        onStart()
    }
}