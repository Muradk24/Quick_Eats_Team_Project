package com.example.quick_eats_team_project.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.quick_eats_team_project.CategoryModel
import com.example.quick_eats_team_project.MenuItemModel
import com.example.quick_eats_team_project.R

class MenuAdapter : RecyclerView.Adapter<MenuAdapter.ViewHolder>() , Filterable {
    var onDataFilter: ((list: ArrayList<MenuItemModel>) -> Unit)? = null


    var itemClickListener: ((item: MenuItemModel)->Unit)? = null
    private var kode = arrayListOf<MenuItemModel>()
    var itemListFiltered: ArrayList<MenuItemModel> = arrayListOf()



    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val v = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.layout_menu_item, viewGroup, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        viewHolder.itemTitle.text = itemListFiltered[i].menuItem
        viewHolder.tvPrice.text ="$ ${itemListFiltered[i].price}"
        if(itemListFiltered[i].menuImage!=null)
            Glide.with(viewHolder.itemView.context).load(itemListFiltered[i].menuImage).into(viewHolder.itemBg);

        viewHolder.itemView.findViewById<CardView>(R.id.cvCategory).setOnClickListener {
            itemClickListener?.invoke(itemListFiltered[i])

        }

    }

    override fun getItemCount(): Int {
        return itemListFiltered.size
    }
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var itemTitle: TextView
        var tvPrice: TextView
        var itemBg: ImageView

        init {
            itemTitle = itemView.findViewById(R.id.ivTitle)
            tvPrice = itemView.findViewById(R.id.tvPrice)
            itemBg = itemView.findViewById(R.id.ivCategory)


        }
    }
    fun addData(list: ArrayList<MenuItemModel>) {
        kode = list
        itemListFiltered = kode
        notifyDataSetChanged()
    }


    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charString = constraint?.toString()

                itemListFiltered = if (charString?.isEmpty()==true) kode  else {
                    val filteredList = ArrayList<MenuItemModel>()

                    val filterList = kode.filter {
                        (it.menuItem?.contains(constraint!!,true)==true)
                    }.toList()

                    filterList.forEach {
                        filteredList.add(it)
                    }
                    filteredList
                }
                return FilterResults().apply { values = itemListFiltered }
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                itemListFiltered = if (results?.values == null)
                    ArrayList()
                else
                    results.values as ArrayList<MenuItemModel>

                notifyDataSetChanged()
                onDataFilter?.invoke(itemListFiltered  )
            }
        }
    }
}
