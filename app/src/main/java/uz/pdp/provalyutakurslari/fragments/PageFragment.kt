package uz.pdp.provalyutakurslari.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.eightbitlab.shadowview.RenderScriptBlur
import com.eightbitlab.shadowview.RenderScriptProvider
import uz.pdp.provalyutakurslari.R
import uz.pdp.provalyutakurslari.databinding.FragmentPageBinding
import uz.pdp.provalyutakurslari.models.Course_model

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [PageFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PageFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: Course_model? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getSerializable(ARG_PARAM1) as Course_model?
        }
    }

    lateinit var binding: FragmentPageBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPageBinding.inflate(inflater, container, false)

        val renderScriptProvider = RenderScriptProvider(requireContext())
        binding.linear.clipToOutline = true
        binding.shadowView.blurScript = RenderScriptBlur(renderScriptProvider)

        loadData()
        return binding.root
    }

    private fun loadData() {
        binding.cours = param1
    }

    override fun onResume() {
        super.onResume()
        loadData()
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment PageFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: Course_model) =
            PageFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_PARAM1, param1)
                }
            }
    }
}