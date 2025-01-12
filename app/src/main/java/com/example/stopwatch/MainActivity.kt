package com.example.stopwatch

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.util.Timer
import kotlin.concurrent.timer

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var btn_start: Button
    private lateinit var btn_reset: Button
    private lateinit var btn_record: Button

    private lateinit var tv_hour: TextView
    private lateinit var tv_minute: TextView
    private lateinit var tv_second: TextView
    private lateinit var tv_millisecond: TextView

    private lateinit var tv_hm_colon: TextView
    private lateinit var tv_ms_colon: TextView
    private lateinit var tv_dot: TextView

    private var isRunning = false

    private var timer: Timer? = null
    private var time = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        btn_start = findViewById(R.id.btn_start)
        btn_reset = findViewById(R.id.btn_reset)
        btn_record = findViewById(R.id.btn_record)

        tv_hour = findViewById(R.id.tv_hour)
        tv_minute = findViewById(R.id.tv_minute)
        tv_second = findViewById(R.id.tv_second)
        tv_millisecond = findViewById(R.id.tv_millisecond)

        tv_hm_colon = findViewById(R.id.tv_hm_colon)
        tv_ms_colon = findViewById(R.id.tv_ms_colon)
        tv_dot = findViewById(R.id.tv_dot)

        btn_start.setOnClickListener(this)
        btn_reset.setOnClickListener(this)
        btn_record.setOnClickListener(this)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.btn_start -> {
                if (isRunning) {
                    pause()
                } else {
                    start()
                }
            }

            R.id.btn_reset -> {
                reset()
            }

            R.id.btn_record -> {
                record()
            }
        }
    }

    private fun record() {
        TODO("Not yet implemented")
    }

    private fun reset() {
        pause()
        time = 0
        tv_millisecond.text = getString(R.string.tv_time)
        tv_second.text = getString(R.string.tv_time)
        tv_minute.text = getString(R.string.tv_time)
        tv_hour.text = getString(R.string.tv_time)
    }

    private fun start() {
        btn_start.text = getString(R.string.btn_pause)
        btn_start.setBackgroundColor(getColor(R.color.btn_pause))
        isRunning = true

        timer = timer(period = 10) {
            time++

            val milli_second = time % 100
            val second = (time % 6000) / 100
            val minute = (time % 360000) / 6000
            val hour = time / 360000

            runOnUiThread {
                if (isRunning) {
                    tv_millisecond.text = if (milli_second < 10) "0$milli_second" else "$milli_second"
                    tv_second.text = if (second < 10) "0$second" else "$second"
                    tv_minute.text = if (minute < 10) "0$minute" else "$minute"
                    tv_hour.text = if (hour < 10) "0$hour" else "$hour"
                }
            }
        }
    }

    private fun pause() {
        btn_start.text = getString(R.string.btn_start)
        btn_start.setBackgroundColor(getColor(R.color.btn_tint_color))

        isRunning = false
        timer?.cancel()
    }
}