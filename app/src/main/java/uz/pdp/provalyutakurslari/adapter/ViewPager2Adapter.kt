package uz.pdp.provalyutakurslari.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import uz.pdp.provalyutakurslari.fragments.HomeFragment
import uz.pdp.provalyutakurslari.fragments.PageFragment
import uz.pdp.provalyutakurslari.models.Course_model

class ViewPager2Adapter(fr: FragmentActivity, var list: List<Course_model>) : FragmentStateAdapter(fr) {
    override fun getItemCount(): Int  = list.size

    override fun createFragment(position: Int): Fragment {
        return PageFragment.newInstance(list[position])
    }


}