package com.zhangwww.opengles_learning.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import com.zhangwww.opengles_learning.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var viewBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(viewBinding.root)
        initClicks()
    }

    private fun initClicks() {
        viewBinding.btnFirstGl.setOnClickListener {
            FirstGLActivity.launch(this)
        }
        viewBinding.btnAirHockey1.setOnClickListener {
            AirHockeyActivity1.launch(this)
        }
        viewBinding.btnAirHockey2.setOnClickListener {
            AirHockeyActivity2.launch(this)
        }
        viewBinding.btnAirHockey3.setOnClickListener {
            AirHockeyActivity3.launch(this)
        }
        viewBinding.btnAirHockey3d.setOnClickListener {
            AirHockeyActivity3D.launch(this)
        }
        viewBinding.btnAirHockey3d2.setOnClickListener {
            AirHockeyActivity3D2.launch(this)
        }
    }

}
