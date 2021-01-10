package com.juanpoveda.recipes.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.juanpoveda.recipes.R
import com.juanpoveda.recipes.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // ****ViewBindingActivity s2: Inflate this way with the Binding class
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        drawerLayout = binding.drawerLayout

        // ****NavigationDrawer s7: Add the navController with the name of the <fragment> hosting the navigation
        val navController = this.findNavController(R.id.navHostFragment)
        // ****NavigationDrawer s9: Set up the action bar to display hamburger menu (pass the DrawerLayout)
        NavigationUI.setupActionBarWithNavController(this,navController, drawerLayout)
        // ****NavigationDrawer s8: Set up the drawer by passing the id of the NavigationView and the navController
        NavigationUI.setupWithNavController(binding.navView, navController)
    }

    // ****NavigationDrawer s10: override this function to manage the navigation with the Drawer
    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.navHostFragment)
        return NavigationUI.navigateUp(navController, drawerLayout)
    }
}