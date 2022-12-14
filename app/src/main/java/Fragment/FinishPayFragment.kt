package Fragment

import Main.MainActivity
import Main.MainViewModel
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.DialogFragment

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.a24mo.R
import com.example.a24mo.databinding.FragmentFinishPayBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


class FinishPayFragment : DialogFragment(){
    private  lateinit var vm : MainViewModel
    private  var _binding : FragmentFinishPayBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.dialog_fullscreen)

        //false로 설정해 주면 화면 밖 또는 뒤로가기 클릭 시 다이얼로그가 dismiss되지 않음
        isCancelable = true
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentFinishPayBinding.inflate(inflater, container, false)
        val view = binding.root
        vm = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)

        binding.returnHomeButton.setOnClickListener {

            // 결제 내용들 DB에 저장
            vm.saveSales()
            // 장바구니 비우기
            vm.resetShoppingCartList()
            // 이전 다이얼로그 프래그먼트들 닫기
            val prev = (activity as MainActivity).fragmentManager.findFragmentByTag("PayingFragment")
            val pprev = (activity as MainActivity).fragmentManager.findFragmentByTag("shoppingCart")
            prev?.onDestroy()
            pprev?.onDestroy()
            (activity as MainActivity).replaceTransaction(HomeFragment())
            dismiss()
        }
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}