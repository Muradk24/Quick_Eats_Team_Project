package com.example.quick_eats_team_project.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.quick_eats_team_project.CategoryModel
import com.example.quick_eats_team_project.R


class CategoryAdapter: RecyclerView.Adapter<CategoryAdapter.ViewHolder>() , Filterable {
    var onDataFilter: ((list: ArrayList<CategoryModel>) -> Unit)? = null


var itemClickListener: ((item: CategoryModel)->Unit)? = null
    private var kode = arrayListOf<CategoryModel>()
    var itemListFiltered: ArrayList<CategoryModel> = arrayListOf()



    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val v = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.category_item, viewGroup, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        viewHolder.itemTitle.text = itemListFiltered[i].cat_name
        viewHolder.tvRating.text = itemListFiltered[i].cat_rating.toString()
        if(itemListFiltered[i].cat_image!=null)
            Glide.with(viewHolder.itemView.context).load(itemListFiltered[i].cat_image).into(viewHolder.itemBg);

        viewHolder.itemView.findViewById<CardView>(R.id.cvCategory).setOnClickListener {
            itemClickListener?.invoke(itemListFiltered[i])

        }

    }

    override fun getItemCount(): Int {
        return itemListFiltered.size
    }
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var itemTitle: TextView
        var tvRating: TextView
        var itemBg: ImageView

        init {
            itemTitle = itemView.findViewById(R.id.ivTitle)
            tvRating = itemView.findViewById(R.id.tvRating)
            itemBg = itemView.findViewById(R.id.ivCategory)


        }
    }
    fun addData(list: ArrayList<CategoryModel>) {
        kode = list as ArrayList<CategoryModel>
        itemListFiltered = kode
        notifyDataSetChanged()
    }


    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charString = constraint?.toString()

                itemListFiltered = if (charString?.isEmpty()==true) kode  else {
                    val filteredList = ArrayList<CategoryModel>()

                    val filterList = kode.filter {
                        (it.cat_name?.contains(constraint!!,true)==true)
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
                    results.values as ArrayList<CategoryModel>

notifyDataSetChanged()
                onDataFilter?.invoke(itemListFiltered  )
            }
        }
    }
}
