package uz.pdp.provalyutakurslari.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import uz.pdp.provalyutakurslari.databinding.ItemRvBinding
import uz.pdp.provalyutakurslari.databinding.ItemRvMainBinding
import uz.pdp.provalyutakurslari.models.Course_model
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class AdapterRecyclerViewMain(var list: ArrayList<Course_model>) :
    RecyclerView.Adapter<AdapterRecyclerViewMain.ViewHolder>() {


    inner class ViewHolder(var itemBinding: ItemRvMainBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun onBind(courseModel: Course_model) {
            val dates = courseModel.date.split(" ", ":").toTypedArray()
            itemBinding.cours = courseModel
            itemBinding.apply {
                date.setText(dates[0])
                time.setText(dates[1] + ":" + dates[2])
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemRvMainBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(list[position])


    }

    override fun getItemCount(): Int = list.size

}