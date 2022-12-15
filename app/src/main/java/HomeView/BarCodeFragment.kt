package HomeView

import Main.MainActivity
import Main.MainViewModel
import android.content.Context
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.example.a24mo.databinding.BarcodeDialogBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class BarCodeFragment : DialogFragment() {
    private  lateinit var vm : MainViewModel
    private  var _binding : BarcodeDialogBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setStyle(STYLE_NO_TITLE, R.style.barcode_dialog)
        //false로 설정해 주면 화면 밖 또는 뒤로가기 클릭 시 다이얼로그가 dismiss되지 않음
        isCancelable = true
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = BarcodeDialogBinding.inflate(inflater, container, false)
        val view = binding.root
        vm = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        return view
    }

    override fun onResume() {
        super.onResume()
        CoroutineScope(Dispatchers.Default).launch {
            launch {
//                (activity as MainActivity).replaceTransaction(FinishPayFragment())
                delay(2000)
                vm.getWineDetail(158643)
                val info_frag = InformationFragment()                       // 상세조회 페이지로 이동
                if (activity != null){
                    info_frag.show((activity as MainActivity).fragmentManager.findFragmentByTag("HomeFragment")!!.childFragmentManager,"Information")
                }
            }.join()
        }

        val windowManager = (activity as MainActivity).getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)
        val params: ViewGroup.LayoutParams? = dialog?.window?.attributes
        val deviceWidth = size.x
        val deviceHeight = size.y
        params?.width = (deviceWidth * 0.9).toInt()
        params?.height = (deviceHeight * 0.5).toInt()
        dialog?.window?.attributes = params as WindowManager.LayoutParams
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT)) //다이얼로그 테두리 둥글게

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