package com.example.cryptotracker

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.cryptotracker.connectivity.CheckConnectionLiveData
import com.example.cryptotracker.databinding.ActivityMainBinding
import com.google.android.material.elevation.SurfaceColors
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var status: CheckConnectionLiveData

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //status bar color
        val color = SurfaceColors.SURFACE_2.getColor(this)
        window.statusBarColor = color

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.navController

        checkNetworkState()

        binding.bottomNav.setupWithNavController(navController)

    }

    fun checkNetworkState() {
        status = CheckConnectionLiveData(application)
        status.observe(this, Observer { status ->
            if (!status) {
                Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show()
            }
        })
    }


}