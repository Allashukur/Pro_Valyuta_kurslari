package uz.pdp.provalyutakurslari.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import me.ibrahimsn.lib.OnItemReselectedListener
import me.ibrahimsn.lib.OnItemSelectedListener
import uz.pdp.provalyutakurslari.MainActivity
import uz.pdp.provalyutakurslari.R
import uz.pdp.provalyutakurslari.adapter.AdapterRecyclerView
import uz.pdp.provalyutakurslari.databinding.FragmentCourseBinding
import uz.pdp.provalyutakurslari.models.Course_model
import uz.pdp.provalyutakurslari.retrofit.NetworkServise
import java.util.*
import kotlin.collections.ArrayList

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CourseFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CourseFragment : Fragment() {
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

    lateinit var binding: FragmentCourseBinding
    lateinit var adapter: AdapterRecyclerView
    lateinit var tempList: ArrayList<Course_model>
    lateinit var newList: ArrayList<Course_model>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCourseBinding.inflate(inflater, container, false)
        binding.progress.visibility = View.VISIBLE

        loadData()
        binding.toolbar.apply {
            search.isIconified = true
            search.onActionViewCollapsed()
            drawerIcon.setOnClickListener {
                (activity as MainActivity)
                    .openMenu()
            }
            search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false
                }

                @SuppressLint("NotifyDataSetChanged")
                override fun onQueryTextChange(newText: String?): Boolean {
                    tempList.clear()
                    val searchText = newText!!.toLowerCase(Locale.getDefault())

                    if (searchText.isNotEmpty()) {
                        newList.forEach {
                            if (it.title.toLowerCase(Locale.getDefault()).contains(searchText)) {
                                tempList.add(it)
                            }
                        }
                        binding.rv.adapter?.notifyDataSetChanged()
                    } else {
                        tempList.clear()
                        tempList.addAll(newList)
                        binding.rv.adapter?.notifyDataSetChanged()
                    }


                    return false

                }

            })
            search.setOnSearchClickListener {
                titleToolbar.visibility = View.INVISIBLE
            }

            search.setOnCloseListener(object : android.widget.SearchView.OnCloseListener,
                SearchView.OnCloseListener {
                override fun onClose(): Boolean {

                    titleToolbar.visibility = View.VISIBLE
                    return false
                }

            })
        }


        return binding.root
    }

    private fun loadData() {
        tempList = ArrayList()
        newList = ArrayList()

        val networkServise = NetworkServise()
        networkServise.getData().observe(requireActivity(), Observer {
            if (it != null) {

                tempList.addAll(it)
                newList.addAll(tempList)

                adapter = AdapterRecyclerView(
                    tempList,
                    object : AdapterRecyclerView.onClick {
                        override fun onclikc(index: Int) {
                            var bundle = Bundle()
                            bundle.putInt("Index", index)
                            findNavController().popBackStack()
                            findNavController().navigate(R.id.conv, bundle)
                        }
                    })

                binding.rv.adapter = adapter
                binding.progress.visibility = View.INVISIBLE
            }
        })


    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CourseFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CourseFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onDestroy() {
        super.onDestroy()

    }

    override fun onStart() {
        super.onStart()
        binding.toolbar.apply {
            search.isIconified = true
            search.onActionViewCollapsed()
        }
    }

}