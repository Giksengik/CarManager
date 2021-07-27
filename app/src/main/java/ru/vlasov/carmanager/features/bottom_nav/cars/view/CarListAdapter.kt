package ru.vlasov.carmanager.features.bottom_nav.cars.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.Transformation
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.vlasov.carmanager.R
import ru.vlasov.carmanager.models.Car


class CarListAdapter(private val listener : CarListener) : RecyclerView.Adapter<CarListAdapter.ViewHolder>() {

    interface CarListener{
        fun onCarClick(car : Car)
    }

    class ExpandableCarItem(var isExpanded: Boolean, val car: Car)

    private val items : MutableList<ExpandableCarItem> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
         ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.car_item_in_list, parent, false))


    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(items[position], listener)



    override fun getItemCount(): Int = items.size

    fun setItems(cars : List<Car>){
        for(item in cars)
            items.add(ExpandableCarItem(false, item))
        notifyDataSetChanged()
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var image : ImageView? = null
        var name : TextView? = null
        var expandButton : ImageView? = null
        var propertyList : RecyclerView? = null
        var expandLayout : FrameLayout? = null
        init{
            expandLayout = itemView.findViewById(R.id.expandLayout)
            propertyList = itemView.findViewById(R.id.propertyList)
            image = itemView.findViewById(R.id.carInListImage)
            name = itemView.findViewById(R.id.carInListName)
            expandButton = itemView.findViewById(R.id.carInListExpandButton)
        }
        fun bind(expandableCarItem: ExpandableCarItem, listener: CarListener) {
            image?.setImageDrawable(ResourcesCompat.getDrawable(itemView.context.resources, R.drawable.ic_car, null))
            name?.text = expandableCarItem.car.name
            propertyList?.setHasFixedSize(true)
            expandButton?.setOnClickListener{
                val show = toggleLayout(!expandableCarItem.isExpanded, it, expandLayout)
                expandableCarItem.isExpanded = show
            }
            showProperties(expandableCarItem.car)
            itemView.setOnClickListener{
                listener.onCarClick(expandableCarItem.car)
            }
        }

        private fun toggleArrow(view: View?, isExpanded: Boolean): Boolean {
            return if (isExpanded) {
                view?.animate()?.setDuration(200)?.rotation(180f)
                true
            } else {
                view?.animate()?.setDuration(200)?.rotation(0f)
                false
            }
        }

        private fun toggleLayout(isExpanded: Boolean, v: View?, layoutExpand: View?): Boolean {
            toggleArrow(v, isExpanded);
            if (isExpanded) {
                expand(layoutExpand);
            } else {
                collapse(layoutExpand);
            }
            return isExpanded;
        }

        fun expand(view: View?) {
            val animation = expandAction(view)
            view?.startAnimation(animation)
        }

        private fun expandAction(view: View?): Animation? {
            view?.measure(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            val actualheight = view?.measuredHeight
            view?.layoutParams?.height = 0
            view?.visibility = View.VISIBLE
            val animation: Animation = object : Animation() {
                override fun applyTransformation(interpolatedTime: Float, t: Transformation?) {
                    if (actualheight != null) {
                        view.layoutParams?.height = if (interpolatedTime == 1f) ViewGroup.LayoutParams.WRAP_CONTENT else (actualheight * interpolatedTime).toInt()
                    }
                    view?.requestLayout()
                }
            }
            if (actualheight != null) {
                animation.duration = (actualheight / view.context?.resources?.displayMetrics?.density!!).toLong()
            }
            view?.startAnimation(animation)
            return animation
        }

        private fun collapse(view: View?) {
            val actualHeight = view?.measuredHeight
            val animation: Animation = object : Animation() {
                override fun applyTransformation(interpolatedTime: Float, t: Transformation?) {
                    if (interpolatedTime == 1f) {
                        view?.visibility = View.GONE
                    } else {
                        if (actualHeight != null) {
                            view.layoutParams?.height = actualHeight - (actualHeight * interpolatedTime).toInt()
                        }
                        view?.requestLayout()
                    }
                }
            }
            if (actualHeight != null) {
                animation.duration = (actualHeight / view.context?.resources?.displayMetrics?.density!!).toLong()
            }
            view?.startAnimation(animation)
        }

        private fun showProperties(car: Car){
            val properties : MutableList<Pair<String, String>> = mutableListOf()
            if(car.yearOfIssue != "")
                properties.add(Pair(itemView.context.getString(R.string.year_of_issue), car.yearOfIssue))
            if(car.mileageInKm != 0.0)
                properties.add(Pair(itemView.context.getString(R.string.mileage), "${car.mileageInKm} ${itemView.context.getString(R.string.km)}"))
            if(car.automobileBody != "")
                properties.add(Pair(itemView.context.getString(R.string.automobile_type_body), car.automobileBody))
            if(car.color != "")
                properties.add(Pair(itemView.context.getString(R.string.color), car.color))
            if(car.engine.cylinderVolumeInLitres != 0.0 || car.engine.type != "" || car.engine.enginePower != 0.0) {
                val res = StringBuilder()
                if(car.engine.cylinderVolumeInLitres != 0.0)
                    res.append("${car.engine.cylinderVolumeInLitres} ${itemView.context.getString(R.string.litres)}")
                if(car.engine.enginePower != 0.0){
                    if(res.isNotEmpty())
                        res.append("/")
                    res.append("${car.engine.enginePower} ${itemView.context.getString(R.string.horse_power)}")
                }
                if(car.engine.type != ""){
                    if(res.isNotEmpty())
                        res.append("/")
                    res.append(car.engine.type)
                }
                properties.add(Pair(itemView.context.getString(R.string.engine), res.toString()))
            }
            if(car.transmissionType != "")
                properties.add(Pair(itemView.context.getString(R.string.transmission_type), car.transmissionType))
            if(car.typeOfDriveUnit != "")
                properties.add(Pair(itemView.context.getString(R.string.typeOfDriveUnit), car.typeOfDriveUnit))
            if(car.steeringWheelLocation != "")
                properties.add(Pair(itemView.context.getString(R.string.steering_wheel_location), car.steeringWheelLocation))
            if(car.VIN != "")
                properties.add(Pair(itemView.context.getString(R.string.VIN), car.VIN))
            if(car.stateNumber != "")
                properties.add(Pair(itemView.context.getString(R.string.stateNumber), car.stateNumber))
            propertyList?.apply{
                layoutManager = LinearLayoutManager(itemView.context)
                adapter = PropertyListAdapter(properties)
            }
        }
    }



}