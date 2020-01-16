package me.raghu.expensetracker.ui.chart


import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.*
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import co.csadev.kellocharts.formatter.SimpleAxisValueFormatter
import co.csadev.kellocharts.gesture.ContainerScrollType
import co.csadev.kellocharts.gesture.ZoomType.HORIZONTAL_AND_VERTICAL
import co.csadev.kellocharts.model.*
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.DaggerFragment
import me.raghu.expensetracker.R
import me.raghu.expensetracker.databinding.FragmentLineChartBinding
import me.raghu.expensetracker.ui.databinding.FragmentDataBindingComponent
import me.raghu.expensetracker.utils.autoCleared
import javax.inject.Inject


@Suppress("UNUSED_PARAMETER")
class LineChartFragment : DaggerFragment() {


    private val hasLines = true
    private val hasPoints = true
    private val shape = ValueShape.CIRCLE
    private val isFilled = false
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
        val dataBinding =
            DataBindingUtil.bind<FragmentLineChartBinding>(view, dataBindingComponent)!!
        binding = dataBinding
        binding.lifecycleOwner = this
        resetViewport()
        binding.chart.isInteractive = true
        binding.chart.isZoomEnabled = true
        binding.chart.zoomType = HORIZONTAL_AND_VERTICAL
        binding.chart.setContainerScrollEnabled(true, ContainerScrollType.HORIZONTAL)
        lineChartViewModel.liveDataLineChartValues.observe(this, Observer {
            binding.chart.visibility = View.GONE
            binding.textView2.visibility = View.VISIBLE
            it?.let {
                if (it.size > 0) {
                    binding.chart.visibility = View.VISIBLE
                    binding.textView2.visibility = View.GONE
                    val line = Line(it)
                    line.color = Color.BLUE
                    line.shape = shape
                    line.isCubic = isCubic
                    line.isFilled = isFilled
                    line.strokeWidth = 2
                    line.hasLabels = true
                    line.hasLabelsOnlyForSelected = hasLabelForSelected
                    line.hasLines = hasLines
                    line.hasPoints = hasPoints
                    val lines: MutableList<Line> = ArrayList<Line>()
                    lines.add(line)

                    val data = LineChartData()
                    val axisX = Axis()

                    axisX.formatter = SimpleAxisValueFormatter(0)
                    val typeface = ResourcesCompat.getFont(context!!, R.font.roboto_medium)
                    val axisY = Axis()
                    axisY.hasLines = true
                    axisX.name = "Day"
                    axisX.textColor = Color.BLACK
                    axisX.typeface = typeface
                    axisY.name = "Amount"
                    axisY.textColor = Color.BLACK
                    axisY.typeface = typeface

                    data.axisXBottom = axisX
                    data.axisYLeft = axisY
                    data.lines = lines

                    val left = it.minBy { pointValue ->
                        pointValue.x
                    }?.x
                    val right = it.maxBy { pointValue ->
                        pointValue.x
                    }?.x
                    val bottom = it.minBy { pointValue ->
                        pointValue.y
                    }?.y
                    val top = it.maxBy { pointValue ->
                        pointValue.y
                    }?.y
                    binding.chart.lineChartData = data
                    setViewPort(left, right, top, bottom)
                }

            }
        })
    }

    private fun setViewPort(left: Float?, right: Float?, top: Float?, bottom: Float?) {
        val v = Viewport(0f,0f,0f,0f)

        v.bottom = 0f

        if (top != null) {
            v.top = top
        }


        v.left = 1f

        if (right != null) {
            v.right = right
        }

        binding.chart.maximumViewport = v
        binding.chart.currentViewport = v
    }

    private fun resetViewport() { // Reset viewport height range to (0,100)
        val v = Viewport(0f,0f,0f,0f)
        v.bottom = 0f
        v.top = 100f
        v.left = 0f
        v.right = 20f
        binding.chart.maximumViewport = v
        binding.chart.currentViewport = v
    }
}
