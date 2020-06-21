package com.zhangwww.chapter4

import android.opengl.GLSurfaceView
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.zhangwww.basemodule.extensions.toast
import com.zhangwww.basemodule.opengles.isSupportOpenGLES20
import kotlinx.android.synthetic.main.activity_air_hockey.*

class AirHockeyActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_air_hockey)
        if (isSupportOpenGLES20(this)) {
            // 设置OpenGLES版本
            gl_surfaceView.setEGLContextClientVersion(2)
            // 设置渲染器
            gl_surfaceView.setRenderer(AirHockeyRender())
            // 设置渲染模式 (RENDERMODE_CONTINUOUSLY | RENDERMODE_WHEN_DIRTY)
            // RENDERMODE_CONTINUOUSLY 表示自动渲染(默认)
            // RENDERMODE_WHEN_DIRTY 表示手动渲染，需要调用 GLSurfaceView.requestRender()方法渲染
            gl_surfaceView.renderMode = GLSurfaceView.RENDERMODE_CONTINUOUSLY
        } else {
            toast("This devi ce does not support OpenGL ES 2.0")
        }
    }

    override fun onResume() {
        super.onResume()
        gl_surfaceView.onResume()
    }

    override fun onPause() {
        super.onPause()
        gl_surfaceView.onPause()
    }
}
