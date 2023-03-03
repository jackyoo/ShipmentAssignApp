package com.jacky.shipment.view

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.jacky.shipment.R
import com.jacky.shipment.domain.Driver

class ListAdapter(private val list: List<Driver>, val handleClick: (position: Int) -> Unit)
    : RecyclerView.Adapter<DriverViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DriverViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return DriverViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: DriverViewHolder, position: Int) {
        val driver: Driver = list[position]
        holder.bind(driver)
        holder.itemView.setOnClickListener {
            handleClick(driver.id)
        }
    }

    override fun getItemCount(): Int = list.size

}

class DriverViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.list_item, parent, false)) {
    private var driverName: TextView? = null


    init {
        driverName = itemView.findViewById(R.id.list_driver)
    }

    fun bind(movie: Driver) {
        driverName?.text = movie.fullName
    }

}
