package com.zhangwww.opengles_learning.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import com.zhangwww.opengles_learning.databinding.ActivityFirstGLBinding
import com.zhangwww.opengles_learning.gles.GLRender

class FirstGLActivity : AppCompatActivity() {

    private lateinit var viewBinding: ActivityFirstGLBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityFirstGLBinding.inflate(LayoutInflater.from(this))
        setContentView(viewBinding.root)
        viewBinding.glSurfaceView.setEGLContextClientVersion(2)
        viewBinding.glSurfaceView.setRenderer(GLRender())
    }

    override fun onResume() {
        super.onResume()
        viewBinding.glSurfaceView.onResume()
    }

    override fun onPause() {
        super.onPause()
        viewBinding.glSurfaceView.onPause()
    }

    companion object {

        fun launch(ctx: Context) {
            ctx.startActivity(Intent(ctx, FirstGLActivity::class.java))
        }

    }
}
