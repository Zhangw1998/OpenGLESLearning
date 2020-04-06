package com.zhangwww.opengles_learning.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.zhangwww.opengles_learning.R
import com.zhangwww.opengles_learning.gles.render.AirHockeyRenderTextured
import kotlinx.android.synthetic.main.activity_air_hockey.*

class AirHockeyTexturedActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_air_hockey)
        glSurfaceView.setEGLContextClientVersion(2)
        glSurfaceView.setRenderer(AirHockeyRenderTextured())
    }

    override fun onResume() {
        super.onResume()
        glSurfaceView.onResume()
    }

    override fun onPause() {
        super.onPause()
        glSurfaceView.onPause()
    }

    companion object {

        fun launch(ctx: Context) {
            ctx.startActivity(Intent(ctx, AirHockeyTexturedActivity::class.java))
        }
    }

}
