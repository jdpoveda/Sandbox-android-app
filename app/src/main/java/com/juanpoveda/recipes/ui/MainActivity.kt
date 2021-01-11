package com.juanpoveda.recipes.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.juanpoveda.recipes.R
import com.juanpoveda.recipes.databinding.ActivityMainBinding
import com.juanpoveda.recipes.lifecycleobservers.LifeCycleLogging
import timber.log.Timber

class MainActivity : AppCompatActivity() {
    private lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // ****Timber s3: Log an event with Timber. This will be shown in the Logcat
        Timber.i("onCreate Called Timber")
        // ****ViewBindingActivity s2: Inflate this way with the Binding class
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // ****LifeCycle s4: Create an instance of the lifecycleObserver class and pass the lifecycle of the Activity/Fragment
        LifeCycleLogging(this.lifecycle, this.localClassName)

        // ****NavigationDrawer s7: Add the navController with the name of the <fragment> hosting the navigation
        val navController = this.findNavController(R.id.navHostFragment)
        // ****NavigationDrawer s9: Set up the action bar to display hamburger menu (pass the DrawerLayout)
        drawerLayout = binding.drawerLayout
        NavigationUI.setupActionBarWithNavController(this,navController, drawerLayout)
        // ****NavigationDrawer s8: Set up the drawer by passing the id of the NavigationView and the navController
        NavigationUI.setupWithNavController(binding.navView, navController)
    }

    // ****NavigationDrawer s10: override this function to manage the navigation with the Drawer
    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.navHostFragment)
        return NavigationUI.navigateUp(navController, drawerLayout)
    }

    //This is called after the activity has been stopped. It's called every time the app goes into the background, and here
    //you can save the data that you'll need to save when the Activity is recreated (for example when rotating the device)
    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
    }
}