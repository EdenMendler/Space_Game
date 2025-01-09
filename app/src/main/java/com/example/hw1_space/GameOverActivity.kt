package com.example.hw1_space

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.hw1_space.utilities.Constants
import com.google.android.material.textview.MaterialTextView

class GameOverActivity : AppCompatActivity() {

    private lateinit var gameover_LBL_status: MaterialTextView
    private lateinit var score_LBL_score: MaterialTextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_over)

        findfiwe()
        initview()

        Handler(Looper.getMainLooper()).postDelayed({
            try {
                val scoresIntent = Intent(this, ScoresActivity::class.java)
                val score = intent.extras?.getInt(Constants.BundleKeys.SCORE_KEY, 0)
                scoresIntent.putExtra(Constants.BundleKeys.SCORE_KEY, score)
                startActivity(scoresIntent)
                Log.d("GameOverActivity", "Starting ScoresActivity")
                finish()
            } catch (e: Exception) {
                Log.e("GameOverActivity", "Error starting ScoresActivity", e)
                e.printStackTrace()
                finish()
            }
        }, 1000)
    }

    private fun findfiwe() {
        gameover_LBL_status = findViewById(R.id.gameover_LBL_status)
        score_LBL_score = findViewById(R.id.score_LBL_score)
    }

    private fun initview() {
        val bundle: Bundle? = intent.extras
        val score = bundle?.getInt(Constants.BundleKeys.SCORE_KEY, 0)
        val message = bundle?.getString(Constants.BundleKeys.STATUS_KEY, "GAME OVER!!! 😭")

        gameover_LBL_status.text = message
        score_LBL_score.text = "Your score: $score"
    }
}