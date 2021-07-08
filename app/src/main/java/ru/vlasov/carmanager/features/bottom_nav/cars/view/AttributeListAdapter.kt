package ru.vlasov.carmanager.features.bottom_nav.cars.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import ru.vlasov.carmanager.R

class AttributeListAdapter(attributes: Array<String>)  : RecyclerView.Adapter<AttributeListAdapter.ViewHolder>() {

        private val checkableItems = attributes.map{
            CheckableItem(false, it)
        }


    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        var chip : Chip? = null
        init{
            chip = itemView.findViewById(R.id.chip)
        }
        fun bind( checkableItem : CheckableItem, onClickListener: View.OnClickListener){
            chip?.text = checkableItem.str
            chip?.setOnClickListener(onClickListener)
            chip?.isChecked = checkableItem.isChecked
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
            ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.chip_item_in_list, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(checkableItems[position]){
            for(item in checkableItems){
                item.isChecked = false
            }
            checkableItems[position].isChecked = true
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int =
        checkableItems.size

    fun getCheckedItem() : String {
        for(item in checkableItems){
            if(item.isChecked)
                return item.str
        }
        return ""
    }

    inner class CheckableItem(var isChecked : Boolean, val str : String)
}