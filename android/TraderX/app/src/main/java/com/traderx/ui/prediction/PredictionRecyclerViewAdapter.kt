package com.traderx.ui.prediction

import android.annotation.TargetApi
import android.content.Context
import android.icu.text.SimpleDateFormat
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.traderx.R
import com.traderx.api.response.PredictionResponse
import kotlinx.android.synthetic.main.item_prediction.view.*

@TargetApi(Build.VERSION_CODES.N)
class PredictionRecyclerViewAdapter(
    private val predictions: ArrayList<PredictionResponse.Prediction>,
    private val context: Context
) : RecyclerView.Adapter<PredictionRecyclerViewAdapter.ViewHolder>() {

    private var dateFormatter = SimpleDateFormat("yyyy-MM-dd hh:mm:ss")

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_prediction, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val predictionItem = predictions[position]
        with(holder) {
            code.text = predictionItem.equipmentCode
            date.text = dateFormatter.format(predictionItem.predictionDay)
            type.text = predictionItem.predictionType.value.capitalize()
            success.text =
                context.getString(if (predictionItem.isSucceeded) R.string.success else R.string.fail)
        }
    }

    override fun getItemCount(): Int = predictions.size

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val code: TextView = view.code
        val date: TextView = view.date
        val success: TextView = view.success
        val type: TextView = view.type
    }
}