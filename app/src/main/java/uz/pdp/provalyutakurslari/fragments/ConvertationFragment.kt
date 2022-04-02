package uz.pdp.provalyutakurslari.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import uz.pdp.provalyutakurslari.MainActivity
import uz.pdp.provalyutakurslari.R
import uz.pdp.provalyutakurslari.adapter.AdapterSpinner
import uz.pdp.provalyutakurslari.databinding.FragmentConvertationBinding
import uz.pdp.provalyutakurslari.models.Course_model
import uz.pdp.provalyutakurslari.retrofit.NetworkServise
import java.math.RoundingMode
import java.text.DecimalFormat

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ConvertationFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ConvertationFragment : Fragment() {
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

    lateinit var binding: FragmentConvertationBinding
    lateinit var adapterSpinner: AdapterSpinner

    //    var list: ArrayList<Course_model>()
    lateinit var list: ArrayList<Course_model>
    var pos = -1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentConvertationBinding.inflate(inflater, container, false)
        binding.apply {
            progress.visibility = View.VISIBLE
            cardView.visibility = View.INVISIBLE
        }


        binding.apply {
            toolbar.drawerIcon.setOnClickListener {
                (activity as MainActivity)
                    .openMenu()
            }
            toolbar.search.visibility = View.INVISIBLE
        }
        loadData()
        calculation()



        return binding.root

    }

    private fun calculation() {
        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val cours_model = list.get(position + 1)
                val dec = DecimalFormat("###,###,##0.00")

                if (cours_model.cb_price.isNotEmpty()) {
                    var price_course = dec.format(cours_model.cb_price.toDouble())
                    binding.priceText.setText(price_course)

                }
                val text = binding.coursEditText.text
                if (text.isNotEmpty()) {
                    if (cours_model.cb_price.isNotEmpty()) {
                        var price = cours_model.cb_price.toDouble()
                        var count = text.toString().toDouble()

                        val dec = DecimalFormat("###,###,##0.00")
                        var price_course = dec.format(price * count)


                        binding.priceText.setText(price_course.toString())


                    }
                }

                binding.cours = cours_model

                pos = position

            }

        }
        list.add(
            list.size,
            Course_model(
                title = "O'zbekiston so'mi",
                code = "UZB",
                cb_price = "",
                date = "",
                nbu_buy_price = "",
                nbu_cell_price = ""
            )
        )
        adapterSpinner = AdapterSpinner(list)
        binding.coursEditText.addTextChangedListener {
            val courseModel1 = list[pos + 1]
            val courseModel = Course_model(
                courseModel1.cb_price,
                courseModel1.code,
                courseModel1.date,
                courseModel1.nbu_buy_price,
                courseModel1.nbu_cell_price,
                courseModel1.title
            )
            if (!it.isNullOrEmpty() && courseModel.cb_price.isNotEmpty()) {

                var price = courseModel.cb_price.toDouble()
                var count = it.toString().toDouble()

                val dec = DecimalFormat("###,###,##0.00")
                var price_course = dec.format(price * count)

                binding.priceText.setText(price_course.toString())
                binding.cours = courseModel
            } else {
                binding.cours = courseModel
            }
        }


    }

    private fun loadData() {
        val networkServise = NetworkServise()
        list = ArrayList()

        networkServise.getData().observe(requireActivity(), Observer {
            if (it != null && it.isNotEmpty()) {
                adapterSpinner = AdapterSpinner(it as ArrayList<Course_model>)
                binding.spinner.adapter = adapterSpinner
                list.addAll(it)

                val index_cours = arguments?.getInt("Index")
                if (index_cours != null) {
                    binding.spinner.setSelection(index_cours)
                }
                binding.apply {
                    progress.visibility = View.INVISIBLE
                    cardView.visibility = View.VISIBLE
                }
            }
        })

    }

    override fun onResume() {
        super.onResume()

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ConvertationFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ConvertationFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}