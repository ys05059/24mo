package Fragment

import Main.MainActivity
import Main.MainViewModel
import Util.AdminDTO
import Util.CartItem
import Util.price_format
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a24mo.databinding.FragmentAdminBinding

class AdminFragment : Fragment(){
    private  lateinit var vm : MainViewModel
    //view 바인딩을 위한 변수들
    private  var _binding : FragmentAdminBinding? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("test", "Admin 프래그먼트 전환 완료")
        super.onCreate(savedInstanceState)
    }

    val _tmpList = MutableLiveData<ArrayList<AdminDTO>>()
    val tmpList : LiveData<ArrayList<AdminDTO>> get() = _tmpList

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        vm = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        _binding = FragmentAdminBinding.inflate(inflater,container,false)
        binding.recyclerView.layoutManager = LinearLayoutManager(context)


        //test
        val tmpData = ArrayList<AdminDTO>()
        tmpData.add(0, AdminDTO("2022-12-01", "2900000"))
        tmpData.add(1, AdminDTO("2022-12-02", "2900000"))
        tmpData.add(2, AdminDTO("2022-12-03", "2900000"))
        tmpData.add(3, AdminDTO("2022-12-04", "2900000"))
        tmpData.add(4, AdminDTO("2022-12-05", "2900000"))
        _tmpList.value = tmpData


        val daily_adapter = AdminListAdapter(tmpList)
        binding.recyclerView.adapter = daily_adapter

        if(!vm.dailyList.value.isNullOrEmpty()) {
            //val daily_adapter = AdminListAdapter(vm.dailyList)
            //binding.recyclerView.adapter = daily_adapter

            //일매출
            binding.dailyBtn.setOnClickListener {
            }

            binding.weeklyBtn.setOnClickListener {
            }

            binding.monthlyBtn.setOnClickListener {
            }
        }

        binding.HomeBtn.setOnClickListener {
            (activity as MainActivity).replaceTransaction(HomeFragment())
        }

        val view = binding.root

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}