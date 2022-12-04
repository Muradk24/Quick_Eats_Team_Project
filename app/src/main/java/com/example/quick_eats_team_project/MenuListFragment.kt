package com.example.quick_eats_team_project

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.quick_eats_team_project.adapter.MenuAdapter
import com.example.quick_eats_team_project.databinding.FragmentCategoryBinding
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore

class MenuListFragment:Fragment() {
    private lateinit var mBinding: FragmentCategoryBinding
    val  menuAdapter = MenuAdapter()
    private var catId:Long?=null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentCategoryBinding.inflate(inflater, container, false)
         catId=arguments?.getLong("catId")
        getList(catId)
        setView()
        return mBinding.root
    }
    override fun onResume() {
        super.onResume()
        getList(catId)
    }
    private fun setView(){
        menuAdapter.onDataFilter = { items ->
            mBinding.rvCategory.visibility = if (items.isEmpty()) View.GONE else View.VISIBLE
            mBinding.tvNoData.visibility = if (items.isEmpty()) View.VISIBLE else View.GONE
        }
        menuAdapter.itemClickListener = {
            findNavController().navigate(R.id.action_fragmentTab_to_menuListFragment)
        }
        mBinding.apply {
            rvCategory.adapter = menuAdapter
            rvCategory.layoutManager = GridLayoutManager(activity,2)
            etSearch.addTextChangedListener {
                menuAdapter.filter.filter(it)
            }
        }
    }

    private fun getList(catId: Long?) {
        FirebaseApp.initializeApp(requireContext())
        val mDatabase: FirebaseFirestore = FirebaseFirestore.getInstance()
        val mDbRef: CollectionReference = mDatabase.collection("MenuItems")

        mDbRef.whereEqualTo("catId",catId).get().addOnCompleteListener {  task->
// This method is called once with the initial value and again
            // whenever data at this location is updated.
            val response = MenuResponse()
            if (task.isSuccessful) {
                val result = task.result
                result?.let {
                    response.menu= result.documents.mapNotNull { snapShot ->
                        snapShot.toObject(MenuItemModel::class.java)
                    }
                }

                menuAdapter.addData( response.menu as ArrayList<MenuItemModel>)
                mBinding.rvCategory.visibility = if ((response.menu as ArrayList<MenuItemModel>).isEmpty()) View.GONE else View.VISIBLE
                mBinding.tvNoData.visibility = if ((response.menu as ArrayList<MenuItemModel>).isEmpty()) View.VISIBLE else View.GONE
            }
            else
            { mBinding.rvCategory.visibility= View.GONE
                mBinding.tvNoData.visibility =View.VISIBLE
            }




        }
    }
}