package com.example.inventory.fragments.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.inventory.R
import com.example.inventory.model.Things
import kotlinx.android.synthetic.main.recycler_item.view.*

class ListAdapter: RecyclerView.Adapter<ListAdapter.MyViewHolder>() {


    private var thingList = emptyList<Things>()

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.recycler_item, parent, false))
    }

    override fun getItemCount(): Int =  thingList.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = thingList[position]
        holder.itemView.title.text = currentItem.title
        holder.itemView.price.text = currentItem.price.toString()
        holder.itemView.quantity.text = currentItem.quantity.toString()
        holder.itemView.supplier.text = currentItem.supplier


        holder.itemView.cardLayout.setOnClickListener{
            val action = ListFragmentDirections.actionListFragmentToUpdateFragment(currentItem)
            holder.itemView.findNavController().navigate(action)
        }

    }
    fun setData(thing: List<Things>){
        this.thingList = thing
        notifyDataSetChanged()
    }
}