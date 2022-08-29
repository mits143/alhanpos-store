package com.alhanpos.store.activities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.alhanpos.store.R
import com.alhanpos.store.databinding.ActivityMainBinding


class MainActivity : BaseActivity<ActivityMainBinding>(),
    NavController.OnDestinationChangedListener {

    override val bindingInflater: (LayoutInflater) -> ActivityMainBinding =
        ActivityMainBinding::inflate

    lateinit var appBarConfiguration: AppBarConfiguration

    override fun onViewBindingCreated(savedInstanceState: Bundle?) {
        setSupportActionBar(binding.toolbar)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_content_home) as NavHostFragment
        val navController = navHostFragment.navController
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home,
                R.id.nav_pos,
                R.id.nav_product,
                R.id.nav_category,
                R.id.nav_brand,
                R.id.nav_purchase_order,
                R.id.nav_all_sale,
                R.id.nav_subscription,
                R.id.nav_stock_transfer,
                R.id.nav_reports,
                R.id.nav_today,
                R.id.nav_items,
                R.id.nav_more,
            ), binding.drawerLayout
        )

        setupActionBarWithNavController(navController, appBarConfiguration)
        binding.navView.setupWithNavController(navController)
        binding.bottomNavView.setupWithNavController(navController)
        navController.addOnDestinationChangedListener(this)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.home, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_home)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }


    override fun onDestinationChanged(
        controller: NavController,
        destination: NavDestination,
        arguments: Bundle?
    ) {
        when (destination.id) {
            R.id.nav_pos -> hideBottomNavigation()
            R.id.nav_product -> hideBottomNavigation()
            R.id.nav_category -> hideBottomNavigation()
            R.id.nav_brand -> hideBottomNavigation()
            R.id.nav_purchase_order -> hideBottomNavigation()
            R.id.nav_all_sale -> hideBottomNavigation()
            R.id.nav_subscription -> hideBottomNavigation()
            R.id.nav_stock_transfer -> hideBottomNavigation()
            R.id.nav_pos_payment -> hideBottomNavigation()
            R.id.nav_add_category -> hideBottomNavigation()
            R.id.nav_add_brand -> hideBottomNavigation()
            R.id.nav_add_purchase_order -> hideBottomNavigation()
            R.id.nav_add_sale -> hideBottomNavigation()
            R.id.nav_add_stock_transfer -> hideBottomNavigation()
            else -> showBottomNavigationView()
        }
    }

    private fun hideNavigationView() { //Hide both drawer and bottom navigation bar
        binding.navView.visibility = View.GONE
        binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED) //To lock navigation drawer so that it don't respond to swipe gesture
    }

    private fun hideBottomNavigation() { //Hide bottom navigation
        binding.bottomNavView.visibility = View.GONE
    }

    private fun hideBothNavigation() { //Hide both drawer and bottom navigation bar
        binding.bottomNavView.visibility = View.GONE
        binding.navView.visibility = View.GONE
        binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED) //To lock navigation drawer so that it don't respond to swipe gesture
    }

    private fun showNavigationView() {
        binding.navView.visibility = View.VISIBLE
        binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
    }

    private fun showBottomNavigationView() {
        binding.bottomNavView.visibility = View.VISIBLE
    }

    private fun showBothNavigation() {
        binding.bottomNavView.visibility = View.VISIBLE
        binding.navView.visibility = View.VISIBLE
        binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
    }

    override fun onBackPressed() {
        when { //If drawer layout is open close that on back pressed
            binding.drawerLayout.isDrawerOpen(GravityCompat.START) -> {
                binding.drawerLayout.closeDrawer(GravityCompat.START)
            }
            else -> {
                super.onBackPressed() //If drawer is already in closed condition then go back
            }
        }
    }
}