package Fragment

import Main.MainActivity
import Main.MainViewModel
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import com.example.a24mo.R
import com.example.a24mo.databinding.RecommendWineLayoutBinding

class Recommend_Fragment : Fragment() {

    private  lateinit var vm : MainViewModel
    private  var _binding : RecommendWineLayoutBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = RecommendWineLayoutBinding.inflate(inflater, container, false)
        val view = binding.root
        vm = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)

        binding.goToHomeBtn.setOnClickListener {
            (activity as MainActivity).replaceTransaction(HomeFragment())
        }
        binding.goBackBtn.setOnClickListener{
            (activity as MainActivity).changeRecommendFragment(vm.Recommend_Is_Back,1)
            vm.Recommend_Is_Back--
        }

        vm.Recommend_First_Tag = ""
        vm.Recommend_Second_Tag = ""
        (activity as MainActivity).changeRecommendFragment(1,2)

        return view
    }

    override fun onResume() {

        super.onResume()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun invisible_back_btn (bool :Boolean){
        if (bool)
            binding.goBackBtn.visibility = View.INVISIBLE
        else
            binding.goBackBtn.visibility = View.VISIBLE
    }

}