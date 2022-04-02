package uz.pdp.provalyutakurslari.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import uz.pdp.provalyutakurslari.databinding.ItemRvBinding
import uz.pdp.provalyutakurslari.models.Course_model
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class AdapterRecyclerView(var list: ArrayList<Course_model>, var onClickLister: onClick) :
    RecyclerView.Adapter<AdapterRecyclerView.ViewHolder>() {


    inner class ViewHolder(var itemBinding: ItemRvBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        fun onBind(courseModel: Course_model) {
            itemBinding.apply {
                itemBinding.cours = courseModel
                buyPrice.setText(courseModel.nbu_buy_price)
                sellPrice.setText(courseModel.nbu_cell_price)
                Picasso.get()
                    .load("https://nbu.uz/local/templates/nbu/images/flags/${courseModel.code}.png")
                    .into(flagImage)
                title.setText(courseModel.title)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemRvBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(list[position])
        holder.itemBinding.calc.setOnClickListener {
            onClickLister.onclikc(position)
        }

    }

    override fun getItemCount(): Int = list.size

    interface onClick {
        fun onclikc(index: Int)
    }
}