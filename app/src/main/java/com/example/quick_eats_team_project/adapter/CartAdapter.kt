package com.example.quick_eats_team_project.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.quick_eats_team_project.MenuItemModel
import com.example.quick_eats_team_project.R

class CartAdapter : RecyclerView.Adapter<CartAdapter.ViewHolder>()  {
    var itemClickListener: ((item: MenuItemModel)->Unit)? = null
    private var itemList = arrayListOf<MenuItemModel>()

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val v = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.layout_cart_item, viewGroup, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        viewHolder.tvTitleCart.text = itemList[i].menuItem
        viewHolder.tvPriceCart.text = "Price : ${itemList[i].price}"
        viewHolder.tvQuantity.text = "Quantity : ${itemList[i].quatity}"
        if(itemList[i].menuImage!=null)
            Glide.with(viewHolder.itemView.context).load(itemList[i].menuImage).into(viewHolder.ivItem);

        viewHolder.itemView.findViewById<ImageView>(R.id.ivDelete).setOnClickListener {
            itemClickListener?.invoke(itemList[i])

        }

    }

    override fun getItemCount(): Int {
        return itemList.size
    }
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var tvTitleCart: TextView
        var tvPriceCart: TextView
        var tvQuantity: TextView
        var ivItem: ImageView

        init {
            tvTitleCart = itemView.findViewById(R.id.tvTitleCart)
            tvPriceCart = itemView.findViewById(R.id.tvPriceCart)
            tvQuantity = itemView.findViewById(R.id.tvQuantity)
            ivItem = itemView.findViewById(R.id.ivItem)


        }
    }
    fun addData(list: ArrayList<MenuItemModel>) {
        itemList = list

        notifyDataSetChanged()
    }



}
