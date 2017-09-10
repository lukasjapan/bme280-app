package de.cvguy.android.bme280_app

import android.os.AsyncTask
import android.os.Handler
import android.util.Log
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries
import java.net.URL
import java.util.*
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule


class LoadLatestDataTask(
        val handler: Handler,
        val success: (LineGraphSeries<DataPoint>) -> Unit,
        val error: (Exception) -> Unit = { Log.e(LoadLatestDataTask::class.toString(), it.toString()) }
) : AsyncTask<Unit, Unit, Unit>() {
    data class ResponseEntry(
            val ts: Long,
            val t: Double,
            val p: Double,
            val h: Double
    )

    override fun doInBackground(vararg args: Unit?) {
        try {
            val url = URL("https://cvguy.de/latest.json?nocache=${UUID.randomUUID()}")

            Log.d(LoadLatestDataTask::class.toString(), "Loading data from: ${url}")

            val json = url.readText()

            Log.d(LoadLatestDataTask::class.toString(), "Got response: ${json}")

            val response = ObjectMapper().registerKotlinModule().readValue(json, Array<ResponseEntry>::class.java)

            val data = LineGraphSeries<DataPoint>(response.map {
                DataPoint(Date(it.ts), it.t)
            }.toTypedArray())

            handler.post({ success(data) })
        }
        catch(e: Exception) {
            handler.post({ error(e) })
        }
    }
}