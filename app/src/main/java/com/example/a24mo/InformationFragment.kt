package com.example.a24mo

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.a24mo.databinding.FragmentInformationBinding


class InformationFragment : Fragment() {
    private  lateinit var vm : MainViewModel
    private var _binding: FragmentInformationBinding? =null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("InformationFragment", "프래그먼트 전환 완료")
//        // 만약 값이 바뀌게 하고 싶으면 옵저버 생성해줘야함 -> 이때 mutableLivaData가 바인딩되어있어야할듯
//        val nameObserver = Observer<String> { new_W_name ->
//            binding.informationName = new_W_name
//        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        vm = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)

        //데이터 바인딩 코드
        _binding = DataBindingUtil.inflate(inflater,R.layout.fragment_information,container,false)
        binding.viewModel = vm
        binding.lifecycleOwner = this@InformationFragment
        Log.d("InformationFragment","vm 테스트 중 : " +vm.wineDetail.toString())
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}