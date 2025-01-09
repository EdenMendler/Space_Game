package com.example.hw1_space

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.hw1_space.fragments.HighScoresFragment
import com.example.hw1_space.fragments.MapFragment
import com.example.hw1_space.interfaces.Callback_HighScoreItemClicked

class ScoresActivity : AppCompatActivity(), Callback_HighScoreItemClicked {

    private lateinit var highScoresFragment: HighScoresFragment
    private lateinit var mapFragment: MapFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scores)
        Log.d("ScoresActivity", "onCreate started")

        try {
            initFragments()
            Log.d("ScoresActivity", "Fragments initialized successfully")
        } catch (e: Exception) {
            Log.e("ScoresActivity", "Error initializing fragments", e)
            e.printStackTrace()
        }
    }

    private fun initFragments() {
        highScoresFragment = HighScoresFragment()
        mapFragment = MapFragment()

        highScoresFragment.highScoreItemClicked = this

        supportFragmentManager.beginTransaction()
            .replace(R.id.main_FRAME_list, highScoresFragment)
            .replace(R.id.main_FRAME_map, mapFragment)
            .commit()
    }

    override fun highScoreItemClicked(lat: Double, lon: Double) {
        mapFragment.focusOnLocation(lat, lon)
    }
}