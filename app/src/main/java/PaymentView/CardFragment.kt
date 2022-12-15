package PaymentView

import Main.MainActivity
import Main.MainViewModel
import android.content.Context
import android.graphics.Point
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.example.a24mo.R
import com.example.a24mo.databinding.FragmentCardBinding


class CardFragment : DialogFragment() {
    private  lateinit var vm : MainViewModel
    private  var _binding : FragmentCardBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.barcode_dialog)

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
        val windowManager = (activity as MainActivity).getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)
        val params: ViewGroup.LayoutParams? = dialog?.window?.attributes
        val deviceWidth = size.x
        val deviceHeight = size.y
        params?.width = (deviceWidth * 0.85).toInt()
        params?.height = (deviceHeight * 0.65).toInt()
        dialog?.window?.attributes = params as WindowManager.LayoutParams
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