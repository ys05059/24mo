package Fragment

import Main.MainActivity
import Main.MainViewModel
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.a24mo.R
import com.example.a24mo.databinding.FragmentDetailSearch1Binding
import com.example.a24mo.databinding.RecommendWineFragment1Binding
import com.example.a24mo.databinding.SelectPriceRangeBinding

class Detail_Search_Fragment_1 : Fragment() {

    private  lateinit var vm : MainViewModel
    private  var _binding : FragmentDetailSearch1Binding? = null
    private val binding get() = _binding!!
    private var dialog_binding : SelectPriceRangeBinding? =null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentDetailSearch1Binding.inflate(inflater, container, false)
        dialog_binding = SelectPriceRangeBinding.inflate(inflater,container,false)
        val view = binding.root
        vm = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)


        //뒤로가기
        val ParentFragment : Detail_Search_Fragment =
            (activity as MainActivity).fragmentManager.findFragmentById(R.id.fragment_container) as Detail_Search_Fragment
        ParentFragment.invisible_back_btn(false)


        //가격대
        binding.priceRange.setOnClickListener{
            binding.priceRange.setBackgroundResource(R.drawable.button_round_white_red)
            Price_Dialog()
        }

        return view
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun Price_Dialog(){
        val builder : AlertDialog.Builder = AlertDialog.Builder(activity as MainActivity)
        val inflater : LayoutInflater = layoutInflater
        builder.setView(inflater.inflate(R.layout.select_price_range, null))
//        builder.setNegativeButton("돌아가기"){dialog,p1 -> dialog.cancel()}
        val alertDialog : AlertDialog = builder.create()
        var temp_min = dialog_binding!!.minP
        var temp_max = dialog_binding!!.maxP
        dialog_binding!!.enter.setOnClickListener {
            vm.minPrice = temp_min.text.toString().toInt()
            vm.maxPrice - temp_max.text.toString().toInt()
            Log.d("Price", vm.minPrice.toString() + vm.maxPrice.toString())
        }
        alertDialog?.setCanceledOnTouchOutside(true)  //바깥 터치시 다이얼로그 사라짐
        alertDialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        alertDialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
        alertDialog.show()
    }
}

