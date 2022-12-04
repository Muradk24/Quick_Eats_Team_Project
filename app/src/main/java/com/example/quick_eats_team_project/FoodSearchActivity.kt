package com.example.quick_eats_team_project

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.example.quick_eats_team_project.adapter.TabsPagerAdapter
import com.example.quick_eats_team_project.databinding.ActivityFoodSearchBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class FoodSearchActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityFoodSearchBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityFoodSearchBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        initNavigation()
     }
    private fun initNavigation() {
        val myNavHostFragment: NavHostFragment = supportFragmentManager
            .findFragmentById(R.id.fNav) as NavHostFragment
        val inflater = myNavHostFragment.navController.navInflater
        val graph = inflater.inflate(R.navigation.main_navigation)

        myNavHostFragment.navController.graph = graph
    }

}