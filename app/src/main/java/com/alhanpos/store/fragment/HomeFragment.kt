package com.alhanpos.store.fragment


import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.alhanpos.store.R
import com.alhanpos.store.databinding.FragmentHomeBinding
import com.alhanpos.store.model.response.dashboard.graph.Dataset
import com.alhanpos.store.model.response.dashboard.graph.DatasetX
import com.alhanpos.store.model.response.dashboard.graph.SellsChart1
import com.alhanpos.store.model.response.dashboard.graph.SellsChart2
import com.alhanpos.store.prefs
import com.alhanpos.store.util.Status
import com.alhanpos.store.viewmodel.HomeViewModel
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*


class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentHomeBinding =
        FragmentHomeBinding::inflate

    private val viewModel: HomeViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setObserver()
    }

    private fun setMonthlyChart(items: SellsChart1) {
        with(binding.chart) {
            animateX(1200, Easing.EaseInSine)
            description.isEnabled = false

            xAxis.setDrawGridLines(false)
            xAxis.position = XAxis.XAxisPosition.BOTTOM
            xAxis.granularity = 1F
            xAxis.valueFormatter = MyAxisFormatter(items.labels)

            axisRight.isEnabled = false
            extraRightOffset = 30f

            legend.orientation = Legend.LegendOrientation.VERTICAL
            legend.verticalAlignment = Legend.LegendVerticalAlignment.TOP
            legend.horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
            legend.textSize = 15F
            legend.form = Legend.LegendForm.LINE
            setDataToLineChart(items.datasets)
        }
    }

    private fun setYearlyChart(items: SellsChart2) {
        with(binding.chart1) {
            animateX(1200, Easing.EaseInSine)
            description.isEnabled = false

            xAxis.setDrawGridLines(false)
            xAxis.position = XAxis.XAxisPosition.BOTTOM
            xAxis.granularity = 1F
            xAxis.valueFormatter = MyAxisFormatter(items.labels)

            axisRight.isEnabled = false
            extraRightOffset = 30f

            legend.orientation = Legend.LegendOrientation.VERTICAL
            legend.verticalAlignment = Legend.LegendVerticalAlignment.TOP
            legend.horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
            legend.textSize = 15F
            legend.form = Legend.LegendForm.LINE
            setYearlyDataToLineChart(items.datasets)
        }
    }

    inner class MyAxisFormatter(private val items: ArrayList<String>) : IndexAxisValueFormatter() {
        override fun getAxisLabel(value: Float, axis: AxisBase?): String? {
            val index = value.toInt()
            return if (index < items.size) {
                items[index]
            } else {
                null
            }
        }
    }

    private fun setDataToLineChart(items: ArrayList<Dataset>) {
        val sales = ArrayList<Entry>()
        val dataSet = ArrayList<ILineDataSet>()
        val rainbow = requireContext().resources.getStringArray(R.array.colors)
        for (i in 0 until items.size) {
            for (j in 0 until items[i].values.size) {
                sales.add(Entry(j.toFloat(), items[i].values[j].toFloat()))
            }
            val datasSets = LineDataSet(sales, items[i].name)
            datasSets.lineWidth = 3f
            datasSets.valueTextSize = 15f
            datasSets.mode = LineDataSet.Mode.LINEAR
            datasSets.color = Color.parseColor(rainbow[i])
            datasSets.valueTextColor = Color.parseColor(rainbow[i])
            dataSet.add(datasSets)
        }
        val lineData = LineData(dataSet)
        binding.chart.data = lineData
        binding.chart.invalidate()
    }

    private fun setYearlyDataToLineChart(items: ArrayList<DatasetX>) {
        val sales = ArrayList<Entry>()
        val dataSet = ArrayList<ILineDataSet>()
        val rainbow = requireContext().resources.getStringArray(R.array.colors)
        for (i in 0 until items.size) {
            for (j in 0 until items[i].values.size) {
                sales.add(Entry(j.toFloat(), items[i].values[j].toFloat()))
            }
            val datasSets = LineDataSet(sales, items[i].name)
            datasSets.lineWidth = 3f
            datasSets.valueTextSize = 15f
            datasSets.mode = LineDataSet.Mode.LINEAR
            datasSets.color = Color.parseColor(rainbow[i])
            datasSets.valueTextColor = Color.parseColor(rainbow[i])
            dataSet.add(datasSets)
        }
        val lineData = LineData(dataSet)
        binding.chart1.data = lineData
        binding.chart1.invalidate()
    }

    @SuppressLint("SetTextI18n")
    private fun setObserver() {
        viewModel.fetchHomeData("Bearer " + prefs.accessToken)
        viewModel.fetchGraphData("Bearer " + prefs.accessToken)
        viewModel.getHomeData.observe(this) {
            when (it.status) {
                Status.LOADING -> {
                }
                Status.SUCCESS
                -> {
                    it.data?.let { it ->
                        binding.txtTotalPurchase?.text =
                            " ج.م " + it.total_purchase.toString()
                        binding.txtTotalPurchaseReturn?.text =
                            " ج.م " + it.total_purchase_return.toString()
                        binding.txtPurchaseDue?.text = " ج.م " + it.purchase_due.toString()
                        binding.txtTotalSales?.text = " ج.م " + it.total_sell.toString()
                        binding.txtTotalSellReturn?.text = " ج.م " + it.total_sell_return.toString()
                        binding.txtInvoiceDue?.text = " ج.م " + it.invoice_due.toString()
                        binding.txtExpense?.text = " ج.م " + it.total_expense.toString()
                    }
                }
                Status.ERROR -> {
                    showToast(it.message!!)
                }
            }
        }
        viewModel.getGraph.observe(this) {
            when (it.status) {
                Status.LOADING -> {
                }
                Status.SUCCESS -> {
                    it.data?.let { it ->
                        setMonthlyChart(it.sells_chart_1)
                        setYearlyChart(it.sells_chart_2)
                    }
                }
                Status.ERROR -> {
                    showToast(it.message!!)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.chart.resetTracking()
        binding.chart1.resetTracking()
    }
}