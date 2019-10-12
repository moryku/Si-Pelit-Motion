package com.bappedamalang.sipelitmotion.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bappedamalang.sipelitmotion.R
import com.bappedamalang.sipelitmotion.model.MCategory
import kotlinx.android.synthetic.main.item_category.view.*

class KategoryAdapter(private val context: Context) :
    RecyclerView.Adapter<KategoryAdapter.ViewHolderCategory>() {

    private var data: List<MCategory> = ArrayList()

    fun setData(data: List<MCategory>) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderCategory {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_category, parent, false)
        return ViewHolderCategory(itemView, context)
    }

    override fun onBindViewHolder(holder: ViewHolderCategory, position: Int) {
        holder.setData(data?.get(position)!!)
    }

    override fun getItemCount(): Int {
        return if (data != null) data!!.size else 0
    }

    class ViewHolderCategory(var view: View, var context: Context) : RecyclerView.ViewHolder(view) {

        fun setData(data: MCategory) {
            view.category.setText(data.nama)
        }
    }
}