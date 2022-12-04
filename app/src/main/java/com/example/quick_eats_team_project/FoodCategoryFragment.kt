package com.example.quick_eats_team_project

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.quick_eats_team_project.adapter.CategoryAdapter
import com.example.quick_eats_team_project.databinding.FragmentCategoryBinding
import com.google.firebase.FirebaseApp
import com.google.firebase.database.*
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore

class FoodCategoryFragment : Fragment() {
    private lateinit var mBinding: FragmentCategoryBinding
    val categoryAdapter = CategoryAdapter()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentCategoryBinding.inflate(inflater, container, false);
       mBinding.progressBar.visibility=View.VISIBLE
        getList()
        setView()
        return mBinding.root
    }

    override fun onResume() {
        super.onResume()
        getList()
    }
    private fun getList(){
        FirebaseApp.initializeApp(requireContext())
        val mDatabase: FirebaseFirestore = FirebaseFirestore.getInstance()
        val mDbRef: CollectionReference = mDatabase.collection("Categories")

        mDbRef.get().addOnCompleteListener {  task->
// This method is called once with the initial value and again
                // whenever data at this location is updated.
             val response = Response()
            mBinding.progressBar.visibility = View.GONE
            if (task.isSuccessful) {
                val result = task.result
                result?.let {
                    response.category= result.documents.mapNotNull { snapShot ->
                        snapShot.toObject(CategoryModel::class.java)
                    }
                }
                 categoryAdapter.addData( response.category as ArrayList<CategoryModel>)
                mBinding.rvCategory.visibility = if ((response.category as ArrayList<MenuItemModel>).isEmpty()) View.GONE else View.VISIBLE
                mBinding.tvNoData.visibility = if ((response.category as ArrayList<MenuItemModel>).isEmpty()) View.VISIBLE else View.GONE
            }
            else
            { mBinding.rvCategory.visibility= View.GONE
                mBinding.tvNoData.visibility =View.VISIBLE
            }


        }
    }

    private fun setView() {
        val linearLayoutManager = LinearLayoutManager(activity)


        categoryAdapter.onDataFilter = { items ->
            mBinding.rvCategory.visibility = if (items.isEmpty()) View.GONE else View.VISIBLE
            mBinding.tvNoData.visibility = if (items.isEmpty()) View.VISIBLE else View.GONE
        }
        categoryAdapter.itemClickListener = {
            findNavController().navigate(R.id.action_fragmentTab_to_menuListFragment, bundleOf("catId" to it.cat_id))
        }

        mBinding.apply {
            rvCategory.adapter = categoryAdapter
            rvCategory.layoutManager = linearLayoutManager

            etSearch.addTextChangedListener {
                categoryAdapter.filter.filter(it)
            }
        }
    }
}