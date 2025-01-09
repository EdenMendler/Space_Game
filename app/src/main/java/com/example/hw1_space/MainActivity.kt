package com.example.hw1_space

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.app.ActivityCompat
import com.example.hw1_space.interfaces.TiltCallback
import com.google.android.material.button.MaterialButton
import kotlin.random.Random
import com.example.hw1_space.log.GameManager
import com.example.hw1_space.utilities.Constants
import com.example.hw1_space.utilities.LocationManagerHelper
import com.example.hw1_space.utilities.ScoreData
import com.example.hw1_space.utilities.SharedPreferencesManager
import com.example.hw1_space.utilities.SignalManager
import com.example.hw1_space.utilities.SingleSoundPlayer
import com.example.hw1_space.utilities.TiltDetector
import com.google.android.material.textview.MaterialTextView


class MainActivity : AppCompatActivity() {

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1001
    }

    enum class GameObjectType {
        ASTEROID,
        COIN
    }

    private lateinit var spaceshipViews: Array<AppCompatImageView>
    private lateinit var asteroids: Array<Array<AppCompatImageView>>
    private lateinit var coins: Array<Array<AppCompatImageView>>
    private lateinit var main_LBL_score: MaterialTextView
    private lateinit var main_BTN_buttonLeft: MaterialButton
    private lateinit var main_BTN_buttonRight: MaterialButton
    private lateinit var main_IMG_hearts: Array<AppCompatImageView>
    private lateinit var gameManager: GameManager
    private lateinit var ssp: SingleSoundPlayer
    private lateinit var tiltDetector: TiltDetector
    private lateinit var locationManager: LocationManagerHelper

    private var useTilt = false
    private var spaceshipColumn = 1
    private var baseDelayAsteroid = 200L
    private var baseDelayCoin = 250L
    private var speedMultiplier = 1.0f
    private val handler = Handler(Looper.getMainLooper())
    private var asteroidRunnable: Runnable? = null
    private var coinRunnable: Runnable? = null
    private var gameRunnable: Runnable? = null
    private var collisionOccurred = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        useTilt = intent.getBooleanExtra("USE_TILT", false)

        initManagers()
        findViews()
        initGame()
        requestLocationPermissions()
    }

    private fun initManagers() {
        useTilt = intent.getBooleanExtra("USE_TILT", false)
        SharedPreferencesManager.init(this)
        LocationManagerHelper.init(this)
        locationManager = LocationManagerHelper.getInstance()
    }

    private fun initGame() {
        gameManager = GameManager(main_IMG_hearts.size)
        ssp = SingleSoundPlayer(this)
        initViews()
        initTiltDetector()
        updateControlsVisibility()
    }
    private fun requestLocationPermissions() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                LOCATION_PERMISSION_REQUEST_CODE
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                SignalManager.getInstance().toast("Location permission is required for score tracking")
            }
        }
    }

    private fun updateControlsVisibility() {
        if (useTilt) {
            main_BTN_buttonLeft.visibility = View.INVISIBLE
            main_BTN_buttonRight.visibility = View.INVISIBLE
            tiltDetector.start()
        } else {
            main_BTN_buttonLeft.visibility = View.VISIBLE
            main_BTN_buttonRight.visibility = View.VISIBLE
            tiltDetector.stop()
        }
    }

    private fun findViews() {
        spaceshipViews = arrayOf(
            findViewById(R.id.spaceship_1),
            findViewById(R.id.spaceship_2),
            findViewById(R.id.spaceship_3),
            findViewById(R.id.spaceship_4),
            findViewById(R.id.spaceship_5)
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
            ),
            arrayOf(
                findViewById(R.id.asteroid_1_4),
                findViewById(R.id.asteroid_2_4),
                findViewById(R.id.asteroid_3_4),
                findViewById(R.id.asteroid_4_4),
                findViewById(R.id.asteroid_5_4),
                findViewById(R.id.asteroid_6_4)
            ),
            arrayOf(
                findViewById(R.id.asteroid_1_5),
                findViewById(R.id.asteroid_2_5),
                findViewById(R.id.asteroid_3_5),
                findViewById(R.id.asteroid_4_5),
                findViewById(R.id.asteroid_5_5),
                findViewById(R.id.asteroid_6_5)
            )
        )
        coins = arrayOf(
            arrayOf(
                findViewById(R.id.coin_1_1),
                findViewById(R.id.coin_2_1),
                findViewById(R.id.coin_3_1),
                findViewById(R.id.coin_4_1),
                findViewById(R.id.coin_5_1),
                findViewById(R.id.coin_6_1)
            ),
            arrayOf(
                findViewById(R.id.coin_1_2),
                findViewById(R.id.coin_2_2),
                findViewById(R.id.coin_3_2),
                findViewById(R.id.coin_4_2),
                findViewById(R.id.coin_5_2),
                findViewById(R.id.coin_6_2)
            ),
            arrayOf(
                findViewById(R.id.coin_1_3),
                findViewById(R.id.coin_2_3),
                findViewById(R.id.coin_3_3),
                findViewById(R.id.coin_4_3),
                findViewById(R.id.coin_5_3),
                findViewById(R.id.coin_6_3)
            ),
            arrayOf(
                findViewById(R.id.coin_1_4),
                findViewById(R.id.coin_2_4),
                findViewById(R.id.coin_3_4),
                findViewById(R.id.coin_4_4),
                findViewById(R.id.coin_5_4),
                findViewById(R.id.coin_6_4)
            ),
            arrayOf(
                findViewById(R.id.coin_1_5),
                findViewById(R.id.coin_2_5),
                findViewById(R.id.coin_3_5),
                findViewById(R.id.coin_4_5),
                findViewById(R.id.coin_5_5),
                findViewById(R.id.coin_6_5)
            )
        )
        main_LBL_score = findViewById(R.id.main_LBL_score)
        main_BTN_buttonLeft = findViewById(R.id.main_BTN_buttonLeft)
        main_BTN_buttonRight = findViewById(R.id.main_BTN_buttonRight)
        main_IMG_hearts = arrayOf(
            findViewById(R.id.main_IMG_heart1),
            findViewById(R.id.main_IMG_heart2),
            findViewById(R.id.main_IMG_heart3)
        )
    }

    private fun initViews() {
        main_LBL_score.text = gameManager.score.toString()
        main_BTN_buttonLeft.setOnClickListener { moveSpaceship("left") }
        main_BTN_buttonRight.setOnClickListener { moveSpaceship("right") }
        refreshUI()
        startGameObjectsFall()
    }

    private fun moveSpaceship(direction: String) {
        when (direction) {
            "left" -> if (spaceshipColumn > 0) spaceshipColumn--
            "right" -> if (spaceshipColumn < 4) spaceshipColumn++
        }
        refreshUI()
    }
    private fun initTiltDetector() {
        tiltDetector = TiltDetector(
            context = this,
            tiltCallback = object : TiltCallback {
                override fun tiltX(value: Float) {
                    if (value > 0) {
                        if (spaceshipColumn > 0) {
                            moveSpaceship("left")
                        }
                    } else {
                        if (spaceshipColumn < 4) {
                            moveSpaceship("right")
                        }
                    }
                }

                override fun tiltY(value: Float) {
                    if (handler == null) return

                    speedMultiplier = when {
                        value < -3.0f -> 0.5f
                        value > 3.0f -> 2.0f
                        else -> 1.0f
                    }
                    updateGameSpeed()
                }
            }
        )
    }
    private fun updateGameSpeed() {
        asteroidRunnable?.let { runnable ->
            handler.removeCallbacks(runnable)
            handler.postDelayed(runnable, (baseDelayAsteroid * speedMultiplier).toLong())
        }

        coinRunnable?.let { runnable ->
            handler.removeCallbacks(runnable)
            handler.postDelayed(runnable, (baseDelayCoin * speedMultiplier).toLong())
        }
    }

    private fun isSquareOccupied(column: Int, row: Int): Boolean {
        val hasAsteroid = asteroids[column][row].visibility == View.VISIBLE
        val hasCoin = coins[column][row].visibility == View.VISIBLE
        return hasAsteroid || hasCoin
    }

    private fun dropGameObject(type: GameObjectType) {
        val column = Random.nextInt(0, 5)
        var currentRow = 0

        val objectColumn = when(type) {
            GameObjectType.ASTEROID -> asteroids[column]
            GameObjectType.COIN -> coins[column]
        }

        if (!isSquareOccupied(column, currentRow)) {
            objectColumn[currentRow].visibility = View.VISIBLE
        } else {
            return
        }

        val delayPerStep = when(type) {
            GameObjectType.ASTEROID -> (baseDelayAsteroid * speedMultiplier).toLong()
            GameObjectType.COIN -> (baseDelayCoin * speedMultiplier).toLong()
        }

        val runnable = object : Runnable {
            override fun run() {
                if (currentRow < objectColumn.size - 1) {
                    if (!isSquareOccupied(column, currentRow + 1)) {
                        objectColumn[currentRow].visibility = View.INVISIBLE
                        currentRow++
                        objectColumn[currentRow].visibility = View.VISIBLE

                        when(type) {
                            GameObjectType.ASTEROID -> checkCollision(currentRow, column)
                            GameObjectType.COIN -> checkCoinCollection(currentRow, column)
                        }
                    }

                    handler.postDelayed(this, delayPerStep)
                } else {
                    objectColumn[currentRow].visibility = View.INVISIBLE
                }
            }
        }

        when(type) {
            GameObjectType.ASTEROID -> asteroidRunnable = runnable
            GameObjectType.COIN -> coinRunnable = runnable
        }

        handler.post(runnable)
    }

    private fun startGameObjectsFall() {
        gameRunnable = object : Runnable {
            override fun run() {
                dropGameObject(GameObjectType.ASTEROID)
                handler.postDelayed(this, 1000L)
            }
        }
        handler.post(gameRunnable!!)

        handler.post(object : Runnable {
            override fun run() {
                dropGameObject(GameObjectType.COIN)
                handler.postDelayed(this, 2000L)
            }
        })
    }

    private fun checkCollision(row: Int, column: Int) {
        if (collisionOccurred) return
        if (column == spaceshipColumn && row == 5) {
            collisionOccurred = true
            loseLife()
            toastAndVibrate()
            ssp.playSound(R.raw.explosion)
            handler.postDelayed({
                collisionOccurred = false
            }, 500)
        }
    }

    private fun checkCoinCollection(row: Int, column: Int) {
        if (column == spaceshipColumn && row == 5) {
            collectCoin()
            ssp.playSound(R.raw.coin)
        }
    }

    private fun collectCoin() {
        gameManager.addScore(10)
        main_LBL_score.text = gameManager.score.toString()
    }

    private fun toastAndVibrate() {
        SignalManager.getInstance().toast("You exploded! Be careful! 🚀")
        SignalManager.getInstance().vibrate()
    }

    private fun loseLife() {
        gameManager.reduceLife(main_IMG_hearts)
        if (gameManager.isGameOver()) {
            asteroidRunnable?.let { handler.removeCallbacks(it) }
            coinRunnable?.let { handler.removeCallbacks(it) }
            gameRunnable?.let { handler.removeCallbacks(it) }
            changeActivity("GAME OVER!!! 😭", gameManager.score)
        }
    }

    private fun refreshUI() {
        for (view in spaceshipViews) {
            view.visibility = View.INVISIBLE
        }

        spaceshipViews[spaceshipColumn].visibility = View.VISIBLE
    }

    private fun changeActivity(message: String, score: Int) {
        try {
            val location = LocationManagerHelper.getInstance().getCurrentLocationForMap()

            if (location.first != 0.0 && location.second != 0.0) {
                val scoreData = ScoreData(
                    score = score,
                    lat = location.first,
                    lng = location.second
                )
                SharedPreferencesManager.getInstance().saveScore(scoreData)
            } else {

                Handler(Looper.getMainLooper()).postDelayed({
                    try {
                        val newLocation = LocationManagerHelper.getInstance().getCurrentLocationForMap()
                        val scoreData = ScoreData(
                            score = score,
                            lat = newLocation.first,
                            lng = newLocation.second
                        )
                        SharedPreferencesManager.getInstance().saveScore(scoreData)
                    } catch (e: Exception) {
                        SignalManager.getInstance().toast("Could not save location with score")
                    }
                }, 1000)
            }

            val intent = Intent(this, GameOverActivity::class.java)
            val bundle = Bundle()
            bundle.putInt(Constants.BundleKeys.SCORE_KEY, score)
            bundle.putString(Constants.BundleKeys.STATUS_KEY, message)
            intent.putExtras(bundle)
            startActivity(intent)
            finish()

        } catch (e: SecurityException) {
            SignalManager.getInstance().toast("Missing location permission")
        } catch (e: Exception) {
            SignalManager.getInstance().toast("Could not get location")
        }
    }

    override fun onPause() {
        super.onPause()
        if (useTilt) {
            tiltDetector.stop()
        }
    }
}