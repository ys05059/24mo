package Fragment

import Main.AdminViewModel
import Main.MainActivity
import Util.DailySumDTO
import Util.SalesDTO
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a24mo.databinding.FragmentAdminBinding
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener


class AdminFragment : Fragment(){
    private  lateinit var avm : AdminViewModel
    //view 바인딩을 위한 변수들
    private  var _binding : FragmentAdminBinding? = null
    private val binding get() = _binding!!
    private val  TAG = "AdminFragment : "


    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("test", "Admin 프래그먼트 전환 완료")
        super.onCreate(savedInstanceState)
    }

    val _tmpList = MutableLiveData<ArrayList<SalesDTO>>()
    val tmpList : LiveData<ArrayList<SalesDTO>> get() = _tmpList

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        avm = ViewModelProvider(requireActivity()).get(AdminViewModel::class.java)
        _binding = FragmentAdminBinding.inflate(inflater,container,false)
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        //chart
        avm.getDailySumList()
        avm.dailySumList.observe(viewLifecycleOwner,Observer{
            initBarChart(binding.chart, avm.dailySumList.value!!)
            setData(binding.chart,  avm.dailySumList.value!!)

        })

        val sales_adapter = AdminListAdapter()
        binding.recyclerView.adapter = sales_adapter

        binding.chart.setOnChartValueSelectedListener(object : OnChartValueSelectedListener {
            override fun onValueSelected(e: Entry, h: Highlight) {
                // Handle the selected bar chart value

                // X축으로 선택한 날짜 넘기는 수 밖에 없을 듯
                val xAxisLabel = e.x.let{
                    binding.chart.xAxis.valueFormatter.getAxisLabel(it, binding.chart.xAxis)
                }
                Log.d(TAG,"e.x : "+ e.x +" e.y : " + e.y + " e.data : " + e.data + " x 값 :" + xAxisLabel )

                avm.getSalesList(xAxisLabel)
                avm.SalesList.observe(viewLifecycleOwner,Observer{
                    sales_adapter.setData(avm.SalesList.value!!)
                })
            }

            override fun onNothingSelected() {
                // Handle when no bar chart value is selected
            }
        })

        binding.HomeBtn.setOnClickListener {
            (activity as MainActivity).replaceTransaction(HomeFragment())
        }

        val view = binding.root

        return view
    }

    fun initBarChart(barChart: BarChart, values: ArrayList<DailySumDTO>) {
        barChart.getDescription().setEnabled(false);
        // X, Y 바의 애니메이션 효과
        barChart.animateY(1000)
        barChart.animateX(1000)

        val labels = ArrayList<String>()
        for (i in 0 until values.count()){
            labels.add(values[i].A_date)
//                .split("-")[2]+"일")
        }


        // 바텀 좌표 값
        val xAxis: XAxis = barChart.xAxis
        val xAxisFormatter: ValueFormatter = IndexAxisValueFormatter(labels)
        xAxis.valueFormatter = xAxisFormatter
        // x축 위치 설정
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        // 그리드 선 수평 거리 설정
        xAxis.granularity = 1f
        //xAxis.textColor = Color.RED
        // x축 선 설정 (default = true)
        xAxis.setDrawAxisLine(false)
        // 격자선 설정 (default = true)
        xAxis.setDrawGridLines(false)

        val leftAxis: YAxis = barChart.axisLeft
        // 좌측 선 설정 (default = true)
        leftAxis.setDrawAxisLine(false)
        // 좌측 텍스트 컬러 설정
        //leftAxis.textColor = Color.BLUE

        barChart.axisRight.isEnabled = false
        // 바차트의 타이틀
        val legend: Legend = barChart.legend
        // 범례 모양 설정 (default = 정사각형)
        legend.form = Legend.LegendForm.LINE
        // 타이틀 텍스트 사이즈 설정
        legend.textSize = 10f
        // 타이틀 텍스트 컬러 설정
        //legend.textColor = Color.BLACK
        // 범례 위치 설정
        legend.verticalAlignment = Legend.LegendVerticalAlignment.TOP
        legend.horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
        // 범례 방향 설정
        legend.orientation = Legend.LegendOrientation.HORIZONTAL
        // 차트 내부 범례 위치하게 함 (default = false)
        legend.setDrawInside(true)

    }

    fun setData(barChart: BarChart, values: ArrayList<DailySumDTO>) {
        barChart.setScaleEnabled(false)

        val valueList = ArrayList<BarEntry>()
        val labels = ArrayList<String>()
        val title = "일 매출 (원)"

        val xAxis: XAxis = barChart.xAxis
        // 임의 데이터
        for (i in 0 until values.count())
        {
            valueList.add(BarEntry(i.toFloat(), values[i].A_amount.toFloat()))
            labels.add(values[i].A_date)
        }

        val barDataSet = BarDataSet(valueList, title)

        // 바 색상 설정
        barDataSet.setColors(
            Color.rgb(219, 42, 57)
        )

        val data = BarData(barDataSet)
        barChart.data = data
        barChart.invalidate()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}