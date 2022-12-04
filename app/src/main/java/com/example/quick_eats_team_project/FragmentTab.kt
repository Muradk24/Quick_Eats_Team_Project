package com.example.quick_eats_team_project

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.quick_eats_team_project.adapter.TabsPagerAdapter
import com.example.quick_eats_team_project.databinding.FragmentTabBinding
import com.google.android.material.tabs.TabLayoutMediator

class FragmentTab: Fragment() {
    private lateinit var mBinding: FragmentTabBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentTabBinding.inflate(inflater, container, false);
        setView()
        return mBinding.root
    }

    private fun setView() {
        mBinding.apply {
            TabsPagerAdapter.fragments = ArrayList<Fragment>().apply {
                add(FoodCategoryFragment())
                add(OrderFragment())
                add(CartScreenActivity())
            }
            viewPager.adapter = TabsPagerAdapter(childFragmentManager, lifecycle)
            viewPager.isUserInputEnabled = false

            TabLayoutMediator(
                tabLayout, viewPager
            ) { tab, position ->
                tab.icon = when (position) {
                    0 -> resources.getDrawable(android.R.drawable.ic_menu_search,null)
                    1 -> resources.getDrawable(android.R.drawable.ic_menu_view,null)
                    2 -> resources.getDrawable(R.drawable.ic_checkout,null)
                    else -> resources.getDrawable(R.drawable.ic_person,null)
                }
            }.attach()
        }

    }
}