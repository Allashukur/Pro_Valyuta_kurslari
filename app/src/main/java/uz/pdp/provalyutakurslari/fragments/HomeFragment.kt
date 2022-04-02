package uz.pdp.provalyutakurslari.fragments

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.viewpager.widget.ViewPager
import com.eightbitlab.shadowview.RenderScriptBlur
import com.eightbitlab.shadowview.RenderScriptProvider
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import uz.pdp.provalyutakurslari.MainActivity
import uz.pdp.provalyutakurslari.R
import uz.pdp.provalyutakurslari.adapter.AdapterRecyclerView
import uz.pdp.provalyutakurslari.adapter.AdapterRecyclerViewMain
import uz.pdp.provalyutakurslari.adapter.ViewPager2Adapter
import uz.pdp.provalyutakurslari.databinding.FragmentHomeBinding
import uz.pdp.provalyutakurslari.databinding.TabLayoutItemBinding
import uz.pdp.provalyutakurslari.models.Course_model
import uz.pdp.provalyutakurslari.retrofit.NetworkServise
import kotlin.math.log

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    lateinit var binding: FragmentHomeBinding
    lateinit var adapter: ViewPager2Adapter
    lateinit var networkServise: NetworkServise
    lateinit var adapterMain: AdapterRecyclerViewMain
    var tabView = -1


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding.apply {
            progress.visibility = View.VISIBLE
            text.visibility = View.INVISIBLE
        }
        LoadNetworkData()



        binding.apply {
            toolbar.search.visibility = View.INVISIBLE
            toolbar.drawerIcon.setOnClickListener {
                (activity as MainActivity)
                    .openMenu()

            }

            tablayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    var tabItemBinding = tab?.customView?.let { TabLayoutItemBinding.bind(it) }
                    if (tabItemBinding != null && tab != null) {
                        tabView = tab.position
                        tabItemBinding.apply {
                            textView.setTextColor(Color.WHITE)
                            tabItemBinding.linear.setBackgroundResource(R.drawable.item_tab_layout_background)
                        }
                    }

                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {
                    var tabItemBinding = tab?.customView?.let { TabLayoutItemBinding.bind(it) }
                    if (tabItemBinding != null) {
                        tabItemBinding.apply {
                            linear.setBackgroundColor(Color.WHITE)
                            textView.setTextColor(Color.parseColor("#B3B3B3"))
                        }
                    }
                }

                override fun onTabReselected(tab: TabLayout.Tab?) {
                    var tabItemBinding = tab?.customView?.let { TabLayoutItemBinding.bind(it) }
                    if (tabItemBinding != null)
                        tabItemBinding.apply {
                            text.setTextColor(Color.WHITE)
                            tabItemBinding.linear.setBackgroundResource(R.drawable.item_tab_layout_background)
                        }
                }

            })
        }









        return binding.root
    }

    private fun LoadNetworkData() {
        networkServise = NetworkServise()

        networkServise.getData().observe(requireActivity(), Observer {
            if (it != null) {
                adapter = ViewPager2Adapter(requireActivity(), it)
                binding.apply {
                    viewpager2.adapter = adapter
                    indicator.attachToPager(viewpager2)
                    var lists: ArrayList<Course_model> = ArrayList<Course_model>(it.subList(7, 12))

                    adapterMain = AdapterRecyclerViewMain(lists)
                    rvMain.adapter = adapterMain
                    progress.apply {
                        visibility = View.INVISIBLE
                        text.visibility = View.VISIBLE
                    }
                }

                TabLayoutMediator(binding.tablayout, binding.viewpager2) { tab, position ->
//                    val bind = TabLayoutItemBinding.inflate(layoutInflater)
//                    bind.textView.setText(it[position].code)
//                    tab.customView = bind.root

//                    tab.setText(it[position].code)

                }.attach()
                setTabs(it as ArrayList<Course_model>)
            }
        })

    }

    private fun setTabs(list: ArrayList<Course_model>) {
        var tabcount: Int = binding.tablayout.tabCount
        for (i in 0..tabcount - 1) {
            var tabItemBinding = TabLayoutItemBinding.inflate(layoutInflater)
            val tabAt = binding.tablayout.getTabAt(i)

            if ((0 == i && tabView == -1) || tabView == i) {
                tabItemBinding.apply {
                    textView.setTextColor(Color.WHITE)
                    tabItemBinding.textView.setText(list[i].code)
                    tabItemBinding.linear.setBackgroundResource(R.drawable.item_tab_layout_background)
                }
            } else {
                tabItemBinding.apply {
                    linear.setBackgroundColor(Color.WHITE)
                    tabItemBinding.textView.setText(list[i].code)
                    textView.setTextColor(Color.parseColor("#B3B3B3"))
                }
            }

            tabAt?.customView = tabItemBinding.root

        }
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}