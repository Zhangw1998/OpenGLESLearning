package com.zhangwww.chapter3

import android.opengl.GLES20.*
import android.opengl.GLSurfaceView
import android.util.Log
import com.zhangwww.basemodule.opengles.GLUtil
import com.zhangwww.basemodule.opengles.readShaderFromResource
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

class AirHockeyRender : GLSurfaceView.Renderer{

    // 顶点逆时针排序称为卷曲顺序
    private val tableVerticesWithTriangles = floatArrayOf(
        // 加入颜色属性[X, Y, R, G, B]
        // Triangle Fan
        0f, 0f, 1f, 1f, 1f,
        -0.5f, -0.5f, 0.7f, 0.7f, 0.7f,
        0.5f, -0.5f, 0.7f, 0.7f, 0.7f,
        0.5f, 0.5f, 0.7f, 0.7f, 0.7f,
        -0.5f, 0.5f, 0.7f, 0.7f, 0.7f,
        -0.5f, -0.5f, 0.7f, 0.7f, 0.7f,

        //center line
        -0.5f, 0f, 1f, 0f, 0f,
        0.5f, 0f, 1f, 0f, 0f,

        //Mallets
        0f, -0.25f, 0f, 0f, 1f,
        0f, 0.25f, 1f, 0f, 0f
    )

    private val vertexData: FloatBuffer

    init {
        vertexData = ByteBuffer
            // 申请了一块本地内存，大小为数组的长度*数据类型所占的字节数
            .allocateDirect(tableVerticesWithTriangles.size * BYTES_PER_FLOAT)
            // 按照本地字节序组织内容
            .order(ByteOrder.nativeOrder())
            // 转换为FloatBuffer类型
            .asFloatBuffer()
            .put(tableVerticesWithTriangles)
    }

    private var programId = 0
    private var aColorLocation = 0
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
        // 获取 attribute 位置
        aColorLocation = glGetAttribLocation(programId, A_COLOR)
        // 获取 attribute 位置
        aPositionLocation = glGetAttribLocation(programId, A_POSITION)
        // 把位置设置为数据的开头
        vertexData.position(0)
        // 关联属性与顶点数据的数组
        glVertexAttribPointer(aPositionLocation, POSITION_COMPONENT_COUNT, GL_FLOAT, false, STRIDE, vertexData)
        // 使能顶点数组
        glEnableVertexAttribArray(aPositionLocation)

        vertexData.position(POSITION_COMPONENT_COUNT)
        glVertexAttribPointer(aColorLocation, COLOR_COMPONENT_COUNT, GL_FLOAT, false, STRIDE, vertexData)
        glEnableVertexAttribArray(aColorLocation)
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
        // 绘制三角形
        glDrawArrays(GL_TRIANGLE_FAN, 0, 6)
        // 绘制直线
        glDrawArrays(GL_LINES, 6, 2)
        // 绘制点
        glDrawArrays(GL_POINTS, 8, 1)
        glDrawArrays(GL_POINTS, 9, 1)
    }

    companion object {
        private const val TAG = "AirHockeyRender"
        // 用两个float类型的数据表示顶点的位置
        private const val POSITION_COMPONENT_COUNT = 2
        // float类型数据所占的字节数
        private const val BYTES_PER_FLOAT = 4

        // shader程序中的变量，需要完全对应
        private const val A_POSITION = "a_Position"
        private const val A_COLOR = "a_Color"

        private const val COLOR_COMPONENT_COUNT = 3
        private const val STRIDE = (POSITION_COMPONENT_COUNT + COLOR_COMPONENT_COUNT) * BYTES_PER_FLOAT
    }
}