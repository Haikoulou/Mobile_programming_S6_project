package com.example.elouanproject

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.elouanproject.model.DealsItem
import com.squareup.picasso.Picasso

class RecyclerItemsAdapter(private val itemList: List<DealsItem>) : RecyclerView.Adapter<RecyclerItemsAdapter.RecyclerItemsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerItemsViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.deal_item,
            parent, false)
        return RecyclerItemsViewHolder(itemView)
    }

    override fun getItemCount() = itemList.size

    override fun onBindViewHolder(holder: RecyclerItemsViewHolder, position: Int) {
        val currentItem = itemList[position]

        Picasso.get().load(currentItem.thumb).into(holder.imageView)
        //holder.imageView.setImageResource(0)
        holder.textView1.text = currentItem.title
        holder.textView2.text = currentItem.salePrice + " € - Normal price : " + currentItem.normalPrice + " €"
    }

    class RecyclerItemsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.image_view)
        val textView1: TextView = itemView.findViewById(R.id.text_view_1)
        val textView2: TextView = itemView.findViewById(R.id.text_view_2)
    }
}