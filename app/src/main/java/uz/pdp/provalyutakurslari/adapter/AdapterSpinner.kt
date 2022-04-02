package uz.pdp.provalyutakurslari.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.squareup.picasso.Picasso
import uz.pdp.provalyutakurslari.databinding.ItemSpinnerBinding
import uz.pdp.provalyutakurslari.models.Course_model

class AdapterSpinner(val list: ArrayList<Course_model>) : BaseAdapter() {
    override fun getCount(): Int = list.size

    override fun getItem(p0: Int): Course_model = list[p0]

    override fun getItemId(p0: Int): Long = p0.toLong()

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val itemSpinnerBinding: ItemSpinnerBinding
        if (p1 == null) {
            itemSpinnerBinding =
                ItemSpinnerBinding.inflate(LayoutInflater.from(p2?.context), p2, false)

        } else {
            itemSpinnerBinding = ItemSpinnerBinding.bind(p1)
        }
        itemSpinnerBinding.apply {
            val courseModel = list[p0]
            title.setText("${courseModel.title} (${courseModel.code})")
            if (courseModel.code.equals("UZB")) {
                Picasso.get()
                    .load("https://p4.wallpaperbetter.com/wallpaper/136/687/807/flag-uzbekistan-flag-of-uzbekistan-uzbekistan-large-flag-uzbek-hd-wallpaper-preview.jpg")
                    .into(flagImage)
            } else {
                Picasso.get()
                    .load("https://nbu.uz/local/templates/nbu/images/flags/${list[p0].code}.png")
                    .into(flagImage)
            }
        }
        return itemSpinnerBinding.root
    }
}