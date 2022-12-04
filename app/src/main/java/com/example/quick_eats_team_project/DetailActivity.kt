package com.example.quick_eats_team_project

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.quick_eats_team_project.databinding.ActivityDetailBinding
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson

class DetailActivity : Fragment() {
    private lateinit var mBinding: ActivityDetailBinding
    private var menuId: Long? = null
    private var itemCount: Int = 1
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = ActivityDetailBinding.inflate(inflater, container, false)
        menuId = arguments?.getLong("menuId")
        getData()

        return mBinding.root
    }


    private fun getData() {
        mBinding.progressBar.visibility=View.VISIBLE
        FirebaseApp.initializeApp(requireContext())
        val mDatabase: FirebaseFirestore = FirebaseFirestore.getInstance()
        val mDbRef: CollectionReference = mDatabase.collection("MenuItems")

        mDbRef.whereEqualTo("menuId", menuId).get().addOnCompleteListener { task ->
// This method is called once with the initial value and again
            // whenever data at this location is updated.
            var item = MenuItemModel()
            mBinding.progressBar.visibility = View.GONE
            if (task.isSuccessful) {
                if (task.result.documents.size>0){
                val result = task.result.documents.get(0).data

                item.catId = result?.get("catId") as Long?
                item.menuItem = result?.get("menuItem") as String?
                item.menuId = result?.get("menuId") as Long?
                item.menuImage = result?.get("menuImage") as String?
                item.menuDesc = result?.get("menuDesc") as String?
                item.price = result?.get("price") as String?

                setView(item)
                }
            }

        }
    }

    private fun setView(item: MenuItemModel) {
        mBinding.apply {
            progressBar.visibility=View.GONE
            tvItemName.text = item.menuItem
            tvItemPrice.text = "Price: $ ${item.price}"
            tvDesc.text = item.menuDesc
            Glide.with(requireContext()).load(item.menuImage).into(ivImage)
            addingButton.setOnClickListener {
                itemCount++
                etQuantity.setText(itemCount.toString())
            }
            subtractButton.setOnClickListener {
                if (itemCount > 1)
                    itemCount--
                etQuantity.setText(itemCount.toString())
            }
            bnPurchase.setOnClickListener {
                item.quatity = itemCount
                putObject(item)
            }
        }
    }

    private fun putObject(obj: MenuItemModel) {

        val gson = Gson()
        val res = MenuResponse()
        val list = arrayListOf<MenuItemModel>()
        list.add(obj)

        val sharedPreference =
            requireActivity().getSharedPreferences("PREFERENCE_USER", Context.MODE_PRIVATE)
        val order = sharedPreference.getString("order", "")
        val jsonObj = gson.fromJson(order, MenuResponse::class.java)
        if (jsonObj != null) {
            jsonObj.menu?.forEach {
                list.add(it)
            }
        }
        res.menu = list
        requireActivity().getSharedPreferences("PREFERENCE_USER", Context.MODE_PRIVATE).edit()
            .putString("order", gson.toJson(res)).apply()

        findNavController().navigate(R.id.action_detailActivity_to_cartScreenActivity)
    }


}