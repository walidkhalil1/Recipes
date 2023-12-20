package com.wkhalil.foodapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.wkhalil.foodapp.R
import com.wkhalil.foodapp.ViewModel.HomeViewModel
import com.wkhalil.foodapp.ViewModel.HomeViewModelFactory
import com.wkhalil.foodapp.db.MealDatabase
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    val viewModel: HomeViewModel by lazy {
        val mealDatabase = MealDatabase.getInstance(this)
        val homeViewModelProviderFactory = HomeViewModelFactory(mealDatabase)
        ViewModelProvider(this, homeViewModelProviderFactory)[HomeViewModel::class.java]
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bnv_main)
        val navigationController = Navigation.findNavController(this, R.id.fragment_main)

        NavigationUI.setupWithNavController(bottomNavigation, navigationController)

    }
}