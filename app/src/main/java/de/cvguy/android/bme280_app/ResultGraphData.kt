package de.cvguy.android.bme280_app

import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries

data class ResultGraphData(
        val temperature: LineGraphSeries<DataPoint>,
        val humidity: LineGraphSeries<DataPoint>,
        val pressure: LineGraphSeries<DataPoint>
)