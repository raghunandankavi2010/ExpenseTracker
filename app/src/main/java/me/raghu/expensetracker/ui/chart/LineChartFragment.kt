package me.raghu.expensetracker.ui.chart


import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.DaggerFragment
import me.raghu.chartslib.hellocharts.formatter.SimpleAxisValueFormatter
import me.raghu.chartslib.hellocharts.formatter.SimpleLineChartValueFormatter
import me.raghu.chartslib.hellocharts.gesture.ContainerScrollType
import me.raghu.chartslib.hellocharts.gesture.ZoomType
import me.raghu.chartslib.hellocharts.model.*
import me.raghu.expensetracker.R
import me.raghu.expensetracker.databinding.FragmentLineChartBinding
import me.raghu.expensetracker.ui.databinding.FragmentDataBindingComponent
import me.raghu.expensetracker.utils.autoCleared
import javax.inject.Inject


class LineChartFragment : DaggerFragment() {


    private val hasLines = true
    private val hasPoints = true
    private val shape = ValueShape.CIRCLE
    private val isFilled = false
    private val hasLabels = false
    private val isCubic = false
    private val hasLabelForSelected = false

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val lineChartViewModel: LineChartViewModel by viewModels {
        viewModelFactory
    }
    var binding by autoCleared<FragmentLineChartBinding>()
    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)
    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_line_chart, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val dataBinding = DataBindingUtil.bind<FragmentLineChartBinding>(view,dataBindingComponent)!!
        binding = dataBinding
        binding.lifecycleOwner = this
        resetViewport()
        binding.chart.isInteractive = true
        binding.chart.isZoomEnabled= true
        binding.chart.zoomType = ZoomType.HORIZONTAL_AND_VERTICAL
        binding.chart.setContainerScrollEnabled(true, ContainerScrollType.HORIZONTAL)
        lineChartViewModel.liveDataLineChartValues.observe(this, Observer {
            binding.chart.visibility= View.GONE
            it?.let {
                if(it.size>0) {
                    binding.chart.visibility = View.VISIBLE
                    //In most cased you can call data model methods in builder-pattern-like manner.
                    //In most cased you can call data model methods in builder-pattern-like manner.
                    val line: Line = Line(it)
                    line.color = Color.BLUE
                    line.shape = shape
                    line.isCubic = isCubic
                    line.isFilled = isFilled
                    line.strokeWidth = 2
                    line.setHasLabels(hasLabels)
                    line.setHasLabelsOnlyForSelected(hasLabelForSelected)
                    line.setHasLines(hasLines)
                    line.setHasPoints(hasPoints)
                    val lines: MutableList<Line> = ArrayList<Line>()
                    lines.add(line)

                    val data = LineChartData()
                    val axisX = Axis()

                    axisX.formatter = SimpleAxisValueFormatter(0)

                    val axisY: Axis = Axis().setHasLines(true)
                    axisX.name = "Day"
                    axisY.name = "Amount"

                    data.axisXBottom = axisX
                    data.axisYLeft = axisY
                    data.lines = lines

                    binding.chart.lineChartData = data
                }

            }
        })
    }
    private fun resetViewport() { // Reset viewport height range to (0,100)
        val v = Viewport(binding.chart.maximumViewport)
        v.bottom = 0f
        v.top = 100f
        v.left = 0f
        v.right = 20f
        binding.chart.maximumViewport = v
        binding.chart.currentViewport = v
    }
}
