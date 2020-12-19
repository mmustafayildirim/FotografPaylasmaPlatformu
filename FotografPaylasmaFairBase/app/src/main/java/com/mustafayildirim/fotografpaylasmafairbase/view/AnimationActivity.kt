package com.mustafayildirim.fotografpaylasmafairbase.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import com.mustafayildirim.fotografpaylasmafairbase.R
import kotlinx.android.synthetic.main.activity_animation.*
import kotlinx.android.synthetic.main.activity_main.*

class AnimationActivity : AppCompatActivity() {
    private var sure: Long = 3000
    private lateinit var topanim: Animation
    private lateinit var bottomanim: Animation
    private lateinit var image: ImageView
    private lateinit var image_second: ImageView
    private var handler = Handler(Looper.myLooper()!!)
    override fun onCreate(savedInstanceState: Bundle?) {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_animation)
        topanim = AnimationUtils.loadAnimation(this, R.anim.top_animation)
        image = Amblemimg

        image.startAnimation(topanim)
        image_second.startAnimation(bottomanim)
        handler.postDelayed(object : Runnable {
            override fun run() {
                val intent = Intent(applicationContext, kullanici_activity::class.java)
                startActivity(intent)
                finish()

            }
        }, sure)



}
}