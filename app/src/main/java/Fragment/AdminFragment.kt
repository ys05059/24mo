package Fragment

import Main.MainActivity
import Main.MainViewModel
import Util.DailyDTO
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
import com.github.mikephil.charting.formatter.IAxisValueFormatter
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.github.mikephil.charting.renderer.XAxisRenderer
import com.github.mikephil.charting.utils.MPPointF


class AdminFragment : Fragment(){
    private  lateinit var vm : MainViewModel
    //view 바인딩을 위한 변수들
    private  var _binding : FragmentAdminBinding? = null
    private val binding get() = _binding!!


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
        vm = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        _binding = FragmentAdminBinding.inflate(inflater,container,false)
        binding.recyclerView.layoutManager = LinearLayoutManager(context)

        //test
        val tmpData = ArrayList<SalesDTO>()
        tmpData.add(0, SalesDTO("2022-12-01", "171277","2"))
        tmpData.add(1, SalesDTO("2022-12-02", "172277","1"))
        tmpData.add(2, SalesDTO("2022-12-03", "111277","2"))
        tmpData.add(3, SalesDTO("2022-12-04", "141277","3"))
        tmpData.add(4, SalesDTO("2022-12-05", "131277","1"))
        tmpData.add(5, SalesDTO("2022-12-06", "161277","1"))
        _tmpList.value = tmpData

        val tmpDaily = ArrayList<DailyDTO>()
        tmpDaily.add(DailyDTO("2022-12-01", "2900000"))
        tmpDaily.add(DailyDTO("2022-12-02", "2900000"))
        tmpDaily.add(DailyDTO("2022-12-03", "4900000"))
        tmpDaily.add(DailyDTO("2022-12-04", "3900000"))
        tmpDaily.add(DailyDTO("2022-12-05", "1200000"))
        tmpDaily.add(DailyDTO("2022-12-06", "4200000"))
        tmpDaily.add(DailyDTO("2022-12-07", "1100000"))
        tmpDaily.add(DailyDTO("2022-12-08", "900000"))

        //chart
        initBarChart(binding.chart, tmpDaily)
        setData(binding.chart, tmpDaily)


        binding.chart.setOnChartValueSelectedListener(object : OnChartValueSelectedListener {
            override fun onValueSelected(e: Entry, h: Highlight) {
                // Handle the selected bar chart value
                val sales_adapter = AdminListAdapter(tmpList)
                binding.recyclerView.adapter = sales_adapter
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

    fun initBarChart(barChart: BarChart, values: ArrayList<DailyDTO>) {
        barChart.getDescription().setEnabled(false);
        // X, Y 바의 애니메이션 효과
        barChart.animateY(1000)
        barChart.animateX(1000)

        val labels = ArrayList<String>()
        for (i in 0 until values.count()){
            labels.add(values[i].A_date.split("-")[2]+"일")
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
        legend.textSize = 20f
        // 타이틀 텍스트 컬러 설정
        //legend.textColor = Color.BLACK
        // 범례 위치 설정
        legend.verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
        legend.horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
        // 범례 방향 설정
        legend.orientation = Legend.LegendOrientation.HORIZONTAL
        // 차트 내부 범례 위치하게 함 (default = false)
        legend.setDrawInside(false)

    }

    fun setData(barChart: BarChart, values: ArrayList<DailyDTO>) {
        barChart.setScaleEnabled(false)

        val valueList = ArrayList<BarEntry>()
        val labels = ArrayList<String>()
        val title = "일 매출"

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