package com.zhangwww.opengles_learning

import android.opengl.GLES20
import android.opengl.GLSurfaceView
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL
import javax.microedition.khronos.opengles.GL10

/**
 * 该方法调用环境为OpenGL环境
 * 渲染环境与UI环境通信可以使用 runOnUIThread() 方法
 * UI环境与渲染环境通信使用 GLSurface的queueEvent()方法
 */
class GLRender : GLSurfaceView.Renderer {


    /**
     * 当Surface被创建时，GLSurfaceView会调用该方法（可能会被多次调用）
     */
    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        // 对应RGBA, 范围为[0f, 1f]
        GLES20.glClearColor(1f, 1f, 1f, 0f)
    }

    /**
     * 当Surface尺寸发生变化时，GLSurfaceView会调用该方法
     */
    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        // set the OpenGL viewport
        GLES20.glViewport(0, 0, width, height)
    }

    /**
     * 当绘制一帧时，GLSurfaceView会调用该方法
     */
    override fun onDrawFrame(gl: GL10?) {
        // clear the rendering surface
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT)
    }

}