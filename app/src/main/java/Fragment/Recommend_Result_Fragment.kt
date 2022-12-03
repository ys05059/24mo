package Fragment

import Main.MainActivity
import Main.MainViewModel
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.a24mo.R
import com.example.a24mo.databinding.RecommendWineFragmentResultBinding

class Recommend_Result_Fragment : Fragment() {
    private  lateinit var vm : MainViewModel
    private  var _binding : RecommendWineFragmentResultBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = RecommendWineFragmentResultBinding.inflate(inflater, container, false)
        val view = binding.root
        vm = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        binding.firstTag.text = vm.Recommend_First_Tag
        binding.secondTag.text = vm.Recommend_Second_Tag
        val ParentFragment : Recommend_Fragment =
            (activity as MainActivity).fragmentManager.findFragmentById(R.id.fragment_container) as Recommend_Fragment
        ParentFragment.invisible_back_btn(false)

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}