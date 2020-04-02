package com.zhangwww.opengles_learning.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.zhangwww.opengles_learning.gles.GLRender
import com.zhangwww.opengles_learning.R
import com.zhangwww.opengles_learning.gles.AirHockeyRender
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        glSurfaceView.setEGLContextClientVersion(2)
        glSurfaceView.setRenderer(GLRender())
        btn.setOnClickListener {
            AirHockeyActivity.launch(this)
        }
    }

    override fun onResume() {
        super.onResume()
        glSurfaceView.onResume()
    }

    override fun onPause() {
        super.onPause()
        glSurfaceView.onPause()
    }

}
