package com.example.hw1_space

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.hw1_space.utilities.Constants
import com.google.android.material.textview.MaterialTextView

class GameOverActivity : AppCompatActivity() {

    private lateinit var gameover_LBL_status: MaterialTextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_over)

        findfiwe()
        initview()

        Handler(Looper.getMainLooper()).postDelayed({
            finish()
        },1000)
    }

    private fun findfiwe() {
        gameover_LBL_status = findViewById(R.id.gameover_LBL_status)
    }

    private fun initview() {
        val bundle: Bundle? = intent.extras
        val message = bundle?.getString(Constants.BundleKeys.STATUS_KEY,"GAME OVER!!! 😭")
        gameover_LBL_status.text = message
    }
}