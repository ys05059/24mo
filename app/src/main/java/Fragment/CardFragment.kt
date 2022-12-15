package Fragment

import Main.MainActivity
import Main.MainViewModel
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.example.a24mo.R
import com.example.a24mo.databinding.FragmentCardBinding
import com.example.a24mo.databinding.FragmentPayingBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class CardFragment : DialogFragment() {
    private  lateinit var vm : MainViewModel
    private  var _binding : FragmentCardBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setStyle(STYLE_NO_TITLE, R.style.dialog_fullscreen)

        //false로 설정해 주면 화면 밖 또는 뒤로가기 클릭 시 다이얼로그가 dismiss되지 않음
        isCancelable = true
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCardBinding.inflate(inflater, container, false)
        val view = binding.root
        vm = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)


        binding.closeButton.setOnClickListener {
            dismiss()
        }
        binding.payButton.setOnClickListener {
            PayingFragment().show((activity as MainActivity).fragmentManager,"PayingFragment")
        }


        return view
    }

    override fun onResume() {
        super.onResume()
//        CoroutineScope(Dispatchers.Default).launch {
//            launch {
//                delay(3000)
//                PayingFragment().show((activity as MainActivity).fragmentManager,"PayingFragment")
//            }
//        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onDestroy() {
        super.onDestroy()
        dismiss()
    }

}