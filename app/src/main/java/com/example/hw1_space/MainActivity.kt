package com.example.hw1_space

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import com.google.android.material.button.MaterialButton
import kotlin.random.Random
import com.example.hw1_space.log.GameManager
import com.example.hw1_space.utilities.Constants
import com.example.hw1_space.utilities.SignalManager


class MainActivity : AppCompatActivity() {

    private lateinit var spaceshipViews: Array<AppCompatImageView>
    private lateinit var asteroids: Array<Array<AppCompatImageView>>
    private lateinit var main_BTN_buttonLeft: MaterialButton
    private lateinit var main_BTN_buttonRight: MaterialButton
    private lateinit var main_IMG_hearts: Array<AppCompatImageView>
    private lateinit var gameManager: GameManager
    private var spaceshipColumn = 1

    private val handler = Handler(Looper.getMainLooper())
    private var asteroidRunnable: Runnable? = null
    private var gameRunnable: Runnable? = null

    private var collisionOccurred = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViews()
        gameManager = GameManager(main_IMG_hearts.size)
        initViews()
    }

    private fun findViews() {
        spaceshipViews = arrayOf(
            findViewById(R.id.spaceship_1),
            findViewById(R.id.spaceship_2),
            findViewById(R.id.spaceship_3)
        )
        asteroids = arrayOf(
            arrayOf(
                findViewById(R.id.asteroid_1_1),
                findViewById(R.id.asteroid_2_1),
                findViewById(R.id.asteroid_3_1),
                findViewById(R.id.asteroid_4_1),
                findViewById(R.id.asteroid_5_1),
                findViewById(R.id.asteroid_6_1)
            ),
            arrayOf(
                findViewById(R.id.asteroid_1_2),
                findViewById(R.id.asteroid_2_2),
                findViewById(R.id.asteroid_3_2),
                findViewById(R.id.asteroid_4_2),
                findViewById(R.id.asteroid_5_2),
                findViewById(R.id.asteroid_6_2)
            ),
            arrayOf(
                findViewById(R.id.asteroid_1_3),
                findViewById(R.id.asteroid_2_3),
                findViewById(R.id.asteroid_3_3),
                findViewById(R.id.asteroid_4_3),
                findViewById(R.id.asteroid_5_3),
                findViewById(R.id.asteroid_6_3)
            )
        )

        main_BTN_buttonLeft = findViewById(R.id.main_BTN_buttonLeft)
        main_BTN_buttonRight = findViewById(R.id.main_BTN_buttonRight)
        main_IMG_hearts = arrayOf(
            findViewById(R.id.main_IMG_heart1),
            findViewById(R.id.main_IMG_heart2),
            findViewById(R.id.main_IMG_heart3)
        )
    }

    private fun initViews() {
        main_BTN_buttonLeft.setOnClickListener { moveSpaceship("left") }
        main_BTN_buttonRight.setOnClickListener { moveSpaceship("right") }
        refreshUI()
        startAsteroidFall()
    }

    private fun moveSpaceship(direction: String) {
        when (direction) {
            "left" -> if (spaceshipColumn > 0) spaceshipColumn--
            "right" -> if (spaceshipColumn < 2) spaceshipColumn++
        }
        refreshUI()
    }

    private fun dropAsteroid() {
        val column = Random.nextInt(0, 3)
        val asteroidColumn = asteroids[column]
        var currentRow = 0

        asteroidColumn[currentRow].visibility = View.VISIBLE
        val delayPerStep = 200L

        asteroidRunnable = object : Runnable {
            override fun run() {
                if (currentRow < asteroidColumn.size - 1) {
                    asteroidColumn[currentRow].visibility = View.INVISIBLE
                    currentRow++
                    asteroidColumn[currentRow].visibility = View.VISIBLE
                    checkCollision()
                    handler.postDelayed(this, delayPerStep)
                } else {
                    asteroidColumn[currentRow].visibility = View.INVISIBLE
                }
            }
        }

        handler.post(asteroidRunnable!!)
    }

    private fun startAsteroidFall() {
        val delay = 1000L

        gameRunnable = object : Runnable {
            override fun run() {
                dropAsteroid()
                handler.postDelayed(this, delay)
            }
        }
        handler.post(gameRunnable!!)
    }

    private fun checkCollision() {
        if (collisionOccurred) return
        val spaceshipRow = 5
        val spaceshipColumn = this.spaceshipColumn

        val asteroidColumn = asteroids[spaceshipColumn]
        for (row in 0 until asteroidColumn.size) {
            if (asteroidColumn[row].visibility == View.VISIBLE && row == spaceshipRow) {
                collisionOccurred = true
                loseLife()
                toastAndVibrate()
                handler.postDelayed({
                    collisionOccurred = false
                }, 500)
                return
            }
        }
    }

    private fun toastAndVibrate() {
        SignalManager.getInstance().toast("You exploded! Be careful! 🚀")
        SignalManager.getInstance().vibrate()
    }

    private fun loseLife() {
        gameManager.reduceLife(main_IMG_hearts)
        if (gameManager.isGameOver()) {
            asteroidRunnable?.let { handler.removeCallbacks(it) }
            gameRunnable?.let { handler.removeCallbacks(it) }
            changeActivity("GAME OVER!!! 😭")
        }
    }

    private fun refreshUI() {
        for (view in spaceshipViews) {
            view.visibility = View.INVISIBLE
        }

        spaceshipViews[spaceshipColumn].visibility = View.VISIBLE
    }

    private fun changeActivity(message: String) {
        val intent = Intent(this, GameOverActivity::class.java)
        val bundle = Bundle()
        bundle.putString(Constants.BundleKeys.STATUS_KEY, message)
        intent.putExtras(bundle)
        startActivity(intent)
        finish()
    }
}