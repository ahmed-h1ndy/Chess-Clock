package com.example.chessclock

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.provider.MediaStore.Audio.Media
import android.util.DisplayMetrics
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import org.w3c.dom.Text

class MainActivity : AppCompatActivity() {
    lateinit var time1 : TextView
    lateinit var time2: TextView
    lateinit var in1: EditText
    lateinit var in2: EditText
    lateinit var start_button : Button

    var time1_left : Long = 0
    var time2_left : Long = 0
    var time1_started : Boolean = false
    var time2_started : Boolean = false
    lateinit var timer1 : CountDownTimer
    lateinit var timer2 : CountDownTimer

    var min1 : Long = 0
    var min2 : Long = 0
    var sec1 : Long = 0
    var sec2 : Long = 0

    lateinit var sound : MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        time1 = findViewById(R.id.txt1)
        time2 = findViewById(R.id.txt2)
        in1 = findViewById(R.id.in1)
        in2 = findViewById(R.id.in2)
        start_button = findViewById(R.id.start_button)

        sound = MediaPlayer.create(this, R.raw.clock_hit)


        start_button.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                time1.text = in1.text.toString()+" : 00"
                time2.text = in2.text.toString()+" : 00"

                time1_left = in1.text.toString().toLong()*60000
                time2_left = in2.text.toString().toLong()*60000

                in1.isEnabled = false
                in2.isEnabled = false
            }
            false
        }


        time1.setOnClickListener {
            sound.start()
            time1.isEnabled = false
            time2.isEnabled = true
            time1.alpha = .25f
            time2.alpha = 1f
            if(time1_started){timer1.cancel()}
            start_time2()
        }

        time2.setOnClickListener {
            sound.start()
            time2.isEnabled = false
            time1.isEnabled = true
            time2.alpha = .25f
            time1.alpha = 1f
            if(time2_started){timer2.cancel()}
            start_time1()
        }
    }

    fun start_time1()
    {

        timer1 = object: CountDownTimer(time1_left, 1000) {
            override fun onTick(l: Long) {
                //update total time
                time1_left = l

                //update the view
                min1 = time1_left/60000
                sec1 = time1_left%60000 / 1000

                time1.text = min1.toString()+" : "+sec1.toString()
                if(sec1<10)
                {
                    time1.text = min1.toString()+" : 0"+sec1.toString()
                }
            }

            override fun onFinish() {

            }
        }.start()
        time1_started = true
    }
    fun start_time2()
    {
        timer2 = object: CountDownTimer(time2_left, 1000) {
            override fun onTick(l: Long) {
                //update total time
                time2_left = l

                //update the view
                min2 = time2_left/60000
                sec2 = time2_left%60000 / 1000

                time2.text = min2.toString()+" : "+sec2.toString()
                if(sec2<10)
                {
                    time2.text = min2.toString()+" : 0"+sec2.toString()
                }
            }

            override fun onFinish() {

            }
        }.start()
        time2_started = true
    }

}

//600000 -> 10 * 60
//60000 -> 60
//10000 -> 10
//1000 -> 1