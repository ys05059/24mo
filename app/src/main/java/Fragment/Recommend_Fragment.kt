package Fragment

import Main.MainActivity
import Main.MainViewModel
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.a24mo.databinding.RecommendWineLayoutBinding

class Recommend_Fragment : Fragment() {
    private  lateinit var vm : MainViewModel
    private  var _binding : RecommendWineLayoutBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = RecommendWineLayoutBinding.inflate(inflater, container, false)
        val view = binding.root
        vm = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)

        binding.goToHomeBtn.setOnClickListener {
//            (activity as MainActivity).replaceTransaction(MainActivity)
        }
        binding.goBackBtn


        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}