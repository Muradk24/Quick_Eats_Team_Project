package com.example.quick_eats_team_project

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.quick_eats_team_project.adapter.OrderAdapter
import com.example.quick_eats_team_project.databinding.FragmentOrderBinding
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore

class OrderFragment : Fragment() {

    private lateinit var mBinding: FragmentOrderBinding
    val orderAdapter = OrderAdapter()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentOrderBinding.inflate(inflater, container, false);
        getList()
        setView()
        return mBinding.root
    }

    override fun onResume() {
        super.onResume()
        getList()
    }

    private fun getList() {
        FirebaseApp.initializeApp(requireContext())
        val mDatabase: FirebaseFirestore = FirebaseFirestore.getInstance()
        var slist: ArrayList<OrderModel>? = arrayListOf()
        slist?.add(OrderModel())
        val response = OrderResponse()
        val mDbRef: CollectionReference = mDatabase.collection("orders")
        val sharedPreference =
            requireActivity().getSharedPreferences("PREFERENCE_USER", Context.MODE_PRIVATE)
        val userid = sharedPreference.getString("userId", "")
        mDbRef.whereEqualTo("userId", userid).get().addOnCompleteListener { task ->


            if (task.isSuccessful) {
                val result = task.result
                response.order= arrayListOf()
                var list=ArrayList<OrderModel>()
                list= arrayListOf()
                result.documents.forEach { it1 ->
//                    result?.let {
//                        response.order = result.documents.mapNotNull { snapShot ->
//                            snapShot.toObject(OrderModel::class.java)
//                        }
//                    }

                    FirebaseFirestore.getInstance().collection("orderItems")
                        .whereEqualTo("orderId", it1.id).get()
                        .addOnCompleteListener { tasks ->
                            if (tasks.isSuccessful) { //menu item

                                tasks.result.documents.forEach { it2 ->

                                    var order:OrderModel= OrderModel()
                                    order.status= it1.get("orderStatus") as Long?
                                    order.orderDate= it1.get("orderDate") as String?
                                    order.id = it1.id
                                    order.menuId = it2.get("menuId") as Long?
                                    order.price = it2.get("price") as String?
                                    order.menuImage = it2.get("menuImage") as String?
                                    order.menuItem = it2.get("menuItem") as String?
                                   list.add(order)
                                        }

                                response.order = list
                                }
                                orderAdapter.addData(response.order as ArrayList<OrderModel>)

                            }
                        }
                }

            }

    }

    private fun setView() {
        val linearLayoutManager = LinearLayoutManager(activity)
        orderAdapter.itemClickListener = {
            findNavController().navigate(R.id.action_fragmentTab_to_menuListFragment)
        }

        mBinding.apply {
            rvCategory.adapter = orderAdapter
            rvCategory.layoutManager = linearLayoutManager
        }
    }
}