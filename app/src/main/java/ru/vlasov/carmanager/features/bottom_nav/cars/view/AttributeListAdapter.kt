package ru.vlasov.carmanager.features.bottom_nav.cars.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import ru.vlasov.carmanager.R

class AttributeListAdapter(private val attributes: Array<String>)  : RecyclerView.Adapter<AttributeListAdapter.ViewHolder>() {


    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        var chip : Chip? = null
        init{
            chip = itemView.findViewById(R.id.chip)
        }
        fun bind(str : String){
            chip?.text = str
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
            ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.chip_item_in_list, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(attributes[position])
    }

    override fun getItemCount(): Int {
        return attributes.size
    }
}