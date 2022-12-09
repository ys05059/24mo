package Fragment

import Main.MainActivity
import Main.MainViewModel
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.a24mo.databinding.FragmentDetailSearchBinding

class Detail_Search_Fragment : Fragment() {

    private lateinit var vm: MainViewModel
    private var _binding: FragmentDetailSearchBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailSearchBinding.inflate(inflater, container, false)
        val view = binding.root
        vm = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)


        (activity as MainActivity).changeDetailSearchFragment(1, 2)//초기 상세검색화면

        return view
    }

    override fun onResume() {

        super.onResume()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun invisible_back_btn(bool: Boolean) {
        if (bool)
            binding.goBackBtn.visibility = View.INVISIBLE
        else
            binding.goBackBtn.visibility = View.VISIBLE
    }
}
