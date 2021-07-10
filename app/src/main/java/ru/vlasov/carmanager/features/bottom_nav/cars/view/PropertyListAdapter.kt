package ru.vlasov.carmanager.features.bottom_nav.cars.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.vlasov.carmanager.R

class PropertyListAdapter(private val properties : List<Pair<String, String>>) : RecyclerView.Adapter<PropertyListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
            ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_property, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(properties[position])


    override fun getItemCount(): Int = properties.size

    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        private var propertyName : TextView? = null
        private var propertyValue : TextView? = null
        init{
            propertyName = itemView.findViewById(R.id.propertyName)
            propertyValue = itemView.findViewById(R.id.propertyValue)
        }
        fun bind(property : Pair<String, String>){
            propertyName?.text = property.first
            propertyValue?.text = property.second
        }
    }
}