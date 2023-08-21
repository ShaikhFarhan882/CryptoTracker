package com.example.cryptotracker

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.cryptotracker.connectivity.CheckConnectionLiveData
import com.example.cryptotracker.database.CryptoDatabase
import com.example.cryptotracker.databinding.ActivityMainBinding
import com.example.cryptotracker.repo.Repository
import com.example.cryptotracker.viewmodel.CryptoViewModel
import com.example.cryptotracker.viewmodel.ViewModelFactory
import com.google.android.material.elevation.SurfaceColors

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    lateinit var viewModel: CryptoViewModel

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

        val database = CryptoDatabase.getDatabase(this)

        val repository = Repository(database)
        viewModel = ViewModelProvider(
            this,
            ViewModelFactory(repository, application)
        ).get(CryptoViewModel::class.java)

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