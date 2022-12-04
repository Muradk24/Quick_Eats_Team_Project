package com.example.quick_eats_team_project

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.quick_eats_team_project.adapter.CartAdapter
import com.example.quick_eats_team_project.databinding.ActivityCartScreenBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.app
import com.google.gson.Gson
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class CartScreenActivity : Fragment() {

    private var itemList: ArrayList<MenuItemModel>? = null
    private lateinit var mBinding: ActivityCartScreenBinding
    val cartAdapter = CartAdapter()
    private var subTotal: Float? = 0F
    private var total: Float? = 0F
    private var sharedPreference: SharedPreferences? = null
    private var deliveryFee: Float = 2.35F
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = ActivityCartScreenBinding.inflate(inflater, container, false);
        sharedPreference =
            requireActivity().getSharedPreferences("PREFERENCE_USER", Context.MODE_PRIVATE)
        getList()
        setView()
        return mBinding.root
    }


    private fun getList() {
        subTotal = 0f
        total = 0f
        val gson = Gson()

        val order = sharedPreference?.getString("order", "null")
        if (order != "null") {
            val jsonObj = gson.fromJson(order, MenuResponse::class.java)
            itemList = jsonObj.menu as ArrayList<MenuItemModel>
            if (itemList != null)
                cartAdapter.addData(itemList!!)
            itemList?.forEach {
                subTotal = subTotal?.plus((it.price?.toFloat() ?: 0F) * it.quatity!!)
            }
            mBinding.apply {
                tvSubTotal.text = "$ $subTotal"
                tvDeliveryFee.text = "$ $deliveryFee"
                total = subTotal?.plus(deliveryFee)
                tvTotal.text = "$ $total"
                if (jsonObj.menu?.isEmpty() == true) {
                    setVisibility()
                }
                purchaseButton.setOnClickListener {
                    placeOrder()

                }
            }
        } else
            setVisibility()
    }

    private fun placeOrder() {
        val db = Firebase.firestore(Firebase.app)
        var count = itemList?.size ?: 0
        val time = Calendar.getInstance().time
        val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm")
        val current = formatter.format(time)

            val orderDetail = hashMapOf(
                "userId" to sharedPreference?.getString("userId", ""),
                "orderDate" to current.toString(),
                "orderStatus" to 1
            )
            // Add a new document with a generated ID
            db.collection("orders").add(orderDetail)
                .addOnSuccessListener { documentReference ->
                    itemList?.forEach {   val order = hashMapOf(

                        "orderId" to documentReference.id,
                        "menuId" to it.menuId,
                        "menuItem" to it.menuItem,
                        "menuImage" to it.menuImage,
                        "price" to it.price,
                        "quantity" to it.quatity,
                    )
                    db.collection("orderItems").add(order)
                        .addOnSuccessListener { documentReference ->

                            count--
                            if (count == 0) {
                                Toast.makeText(
                                    requireContext(),
                                    "Your order place successfully",
                                    Toast.LENGTH_SHORT
                                ).show()

                                    requireActivity().getSharedPreferences("PREFERENCE_USER", Context.MODE_PRIVATE)
                                        .edit()
                                        .putString("order", "null").apply()
                                    setVisibility()

                                findNavController().navigate(R.id.action_cartScreenActivity_to_accountActivity)
                            }
                        }.addOnFailureListener { e ->
                            Log.w(
                                CartScreenActivity::class.simpleName,
                                "Error adding document  ${e.message}"
                            )


                        }

                    }
                }
                .addOnFailureListener { e ->
                    Log.w(
                        CartScreenActivity::class.simpleName,
                        "Error adding document  ${e.message}"
                    )


                }



    }

    private fun setVisibility() {
        mBinding.apply {
            tvSubTotal.visibility = View.GONE
            tvDeliveryFee.visibility = View.GONE
            tvTotal.visibility = View.GONE
            tvTotalText.visibility = View.GONE
            purchaseButton.visibility = View.GONE
            tvSubTotalText.visibility = View.GONE
            tvDeliveryFeeText.visibility = View.GONE
        }
    }

    private fun setView() {
        val linearLayoutManager = LinearLayoutManager(activity)
        cartAdapter.itemClickListener = {
            itemList?.remove(it)
            if (itemList?.size ?: 0 > 0) {
                val res = MenuResponse()
                val gson = Gson()
                res.menu = itemList
                requireActivity().getSharedPreferences("PREFERENCE_USER", Context.MODE_PRIVATE)
                    .edit()
                    .putString("order", gson.toJson(itemList)).apply()
            } else {
                requireActivity().getSharedPreferences("PREFERENCE_USER", Context.MODE_PRIVATE)
                    .edit()
                    .putString("order", "null").apply()
                setVisibility()
            }
            cartAdapter.notifyDataSetChanged()
        }

        mBinding.apply {
            rvFoodItems.adapter = cartAdapter
            rvFoodItems.layoutManager = linearLayoutManager
        }
    }
}