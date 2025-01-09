package com.example.hw1_space

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.switchmaterial.SwitchMaterial

class MenuActivity : AppCompatActivity() {

    private lateinit var menu_SW_controls: SwitchMaterial
    private lateinit var menu_BTN_start: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        findViews()
        initViews()
    }

    private fun findViews() {
        menu_SW_controls = findViewById(R.id.menu_SW_controls)
        menu_BTN_start = findViewById(R.id.menu_BTN_start)
    }

    private fun initViews() {
        menu_BTN_start.setOnClickListener {
            startGame(menu_SW_controls.isChecked)
        }
    }

    private fun startGame(useTilt: Boolean) {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("USE_TILT", useTilt)
        startActivity(intent)
        finish()
    }
}