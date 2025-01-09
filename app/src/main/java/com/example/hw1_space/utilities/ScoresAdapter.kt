package com.example.hw1_space.utilities

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.hw1_space.R
import com.google.android.material.textview.MaterialTextView
import java.text.SimpleDateFormat
import java.util.*

class ScoresAdapter(
    private val scores: List<ScoreData>,
    private val onItemClick: (ScoreData) -> Unit
) : RecyclerView.Adapter<ScoresAdapter.ScoreViewHolder>() {

    class ScoreViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val scoreTV: MaterialTextView = view.findViewById(R.id.score_LBL_score)
        val locationTV: MaterialTextView = view.findViewById(R.id.score_LBL_location)
        val dateTV: MaterialTextView = view.findViewById(R.id.score_LBL_date)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScoreViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.score_item, parent, false)
        return ScoreViewHolder(view)
    }

    override fun onBindViewHolder(holder: ScoreViewHolder, position: Int) {
        val score = scores[position]
        holder.scoreTV.text = "Score: ${score.score}"
        holder.locationTV.text = "Location: (${score.lat}, ${score.lng})"

        val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
        holder.dateTV.text = sdf.format(Date())

        holder.itemView.setOnClickListener { onItemClick(score) }
    }

    override fun getItemCount() = scores.size
}