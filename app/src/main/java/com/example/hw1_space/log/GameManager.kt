package com.example.hw1_space.log

import android.view.View
import androidx.appcompat.widget.AppCompatImageView

class GameManager (private val lifeCount: Int = 3){
    var currentLives: Int = lifeCount
        private set

    fun isGameOver(): Boolean {
        return currentLives <= 0
    }

    fun reduceLife(main_IMG_hearts: Array<AppCompatImageView>) {
        if (currentLives > 0) {
            currentLives--
            main_IMG_hearts[currentLives].visibility = View.INVISIBLE
        }
    }
}