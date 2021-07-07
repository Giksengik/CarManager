package ru.vlasov.carmanager.features.bottom_nav.cars

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.vlasov.carmanager.R
import ru.vlasov.carmanager.models.Car

class CarListAdapter() : ListAdapter<Car, CarListAdapter.ViewHolder>(DiffUtil()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
         ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.car_item_in_list, parent, false))


    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(getItem(position))





    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var image : ImageView? = null
        var name : TextView? = null
        var expandButton : ImageView? = null
        init{
            image = itemView.findViewById(R.id.carInListImage)
            name = itemView.findViewById(R.id.carInListName)
            expandButton = itemView.findViewById(R.id.carInListExpandButton)
        }
        fun bind(car : Car) {
            name?.text = car.name
        }
    }


    class DiffUtil : androidx.recyclerview.widget.DiffUtil.ItemCallback<Car>(){
        override fun areItemsTheSame(oldItem: Car, newItem: Car): Boolean = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Car, newItem: Car): Boolean = oldItem == newItem
    }

}