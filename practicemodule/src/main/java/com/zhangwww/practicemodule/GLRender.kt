package com.zhangwww.practicemodule

import android.opengl.GLES20.*
import android.opengl.GLSurfaceView
import android.util.Log
import com.zhangwww.basemodule.opengles.GLUtil
import com.zhangwww.basemodule.opengles.readShaderFromResource
import java.nio.FloatBuffer
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

class GLRender : GLSurfaceView.Renderer{

    private val vertices = floatArrayOf(
        //Triangle
        -0.5f, -0.5f,
        0.5f, -0.5f,
        0f, 0.5f
    )

    private val vertexData: FloatBuffer

    init {
        vertexData = GLUtil.createFloatBuffer(vertices)
    }

    private var programId = 0
    private var uColorLocation = 0
    private var aPositionLocation = 0

    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        Log.d(TAG, "onSurfaceCreated")
        // 设置清空屏幕时用的颜色
        glClearColor(0f, 0f, 0f, 0f)

        // 创建OpenGL程序并绑定数据
        val vertexShader = readShaderFromResource(appContext, R.raw.simple_vertex_shader)
        val fragmentShader = readShaderFromResource(appContext, R.raw.simple_fragment_shader)
        programId = GLUtil.createProgram(vertexShader, fragmentShader)
        // 使用该OpenGL程序
        glUseProgram(programId)
        // 获取 uniform 位置
        uColorLocation = glGetUniformLocation(programId, U_COLOR)
        // 获取 attribute 位置
        aPositionLocation = glGetAttribLocation(programId, A_POSITION)
        // 关联属性与顶点数据的数组
        glVertexAttribPointer(aPositionLocation, POSITION_COMPONENT_COUNT, GL_FLOAT, false, 0, vertexData)
        // 使能顶点数组
        glEnableVertexAttribArray(aPositionLocation)
    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        Log.d(TAG, "onSurfaceChanged")
        Log.d(TAG, "viewport: $width, $height")
        // 设置窗口大小
        glViewport(0, 0, width, height)
    }

    override fun onDrawFrame(gl: GL10?) {
        Log.d(TAG, "onDrawFrame")
        // 清空屏幕
        glClear(GL_COLOR_BUFFER_BIT)
        // 设置颜色
        glUniform4f(uColorLocation, 0.5f, 0.5f, 0.5f, 1f)
        // 绘制三角形
        glDrawArrays(GL_TRIANGLES, 0, 6)
    }

    companion object {
        private const val TAG = "GLRender"
        // 用两个float类型的数据表示顶点的位置
        private const val POSITION_COMPONENT_COUNT = 2

        // shader程序中的变量，需要完全对应
        private const val A_POSITION = "a_Position"
        private const val U_COLOR = "u_Color"
    }
}