package com.example.quick_eats_team_project.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.quick_eats_team_project.CategoryModel
import com.example.quick_eats_team_project.OrderModel
import com.example.quick_eats_team_project.OrderResponse
import com.example.quick_eats_team_project.R

class OrderAdapter: RecyclerView.Adapter<OrderAdapter.ViewHolder>()  {
    var itemClickListener: ((item: OrderModel)->Unit)? = null
    private var itemList = arrayListOf<OrderModel>()

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val v = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.layout_item_past, viewGroup, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        viewHolder.tvTitlePast.text = itemList[i].menuItem
        viewHolder.tvDate.text = itemList[i].orderDate
        viewHolder.tvOrderPrice.text = "$ ${itemList[i].price}"
        if(itemList[i].menuItem!=null)
            Glide.with(viewHolder.itemView.context).load(itemList[i].menuImage).into(viewHolder.ivCategory);

    }

    override fun getItemCount(): Int {
        return itemList.size
    }
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var tvTitlePast: TextView
        var tvDate: TextView
        var tvOrderPrice: TextView
        var ivCategory: ImageView

        init {
            tvTitlePast = itemView.findViewById(R.id.tvTitlePast)
            tvDate = itemView.findViewById(R.id.tvDate)
            tvOrderPrice = itemView.findViewById(R.id.tvOrderPrice)
            ivCategory = itemView.findViewById(R.id.ivCategory)


        }
    }
    fun addData(list: ArrayList<OrderModel>) {
        itemList = list
        notifyDataSetChanged()
    }



}
