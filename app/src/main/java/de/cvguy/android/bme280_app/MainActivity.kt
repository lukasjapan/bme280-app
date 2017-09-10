package de.cvguy.android.bme280_app

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Button
import com.jjoe64.graphview.series.LineGraphSeries
import com.jjoe64.graphview.GraphView
import com.jjoe64.graphview.series.DataPoint


class MainActivity : AppCompatActivity() {
    lateinit var graph: GraphView
    lateinit var button: Button

    val handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        graph = findViewById(R.id.graph) as GraphView
        button = findViewById(R.id.button) as Button

        button.setOnClickListener {
            LoadLatestDataTask(handler, this::graphData).execute()
        }
    }

    fun graphData(data: LineGraphSeries<DataPoint>) {
        graph.removeAllSeries()
        graph.addSeries(data)
    }
}
