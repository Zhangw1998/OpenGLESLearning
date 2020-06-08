package com.zhangwww.opengles_learning.gles.render

import android.opengl.GLES20
import android.opengl.GLSurfaceView
import android.opengl.Matrix
import com.zhangwww.basemodule.opengles.GLUtil
import com.zhangwww.opengles_learning.R
import com.zhangwww.opengles_learning.extensions.appContext
import com.zhangwww.basemodule.opengles.readShaderFromResource
import java.nio.FloatBuffer
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10


class AirHockeyRender3 : GLSurfaceView.Renderer {

    // 顶点属性(attribute)数组
    private val tableVerticesWithTriangles = floatArrayOf(

        //[x, y, r, g, b]

        // Triangle Fan
        0f, 0f, 1f, 1f, 1f,
        -0.5f, -0.8f, 0.7f, 0.7f, 0.7f,
        0.5f, -0.8f, 0.7f, 0.7f, 0.7f,
        0.5f, 0.8f, 0.7f, 0.7f, 0.7f,
        -0.5f, 0.8f, 0.7f, 0.7f, 0.7f,
        -0.5f, -0.8f, 0.7f, 0.7f, 0.7f,

        //center line
        -0.5f, 0f, 1f, 0f, 0f,
        0.5f, 0f, 0f, 0f, 1f,

        //Mallets
        0f, -0.4f, 0f, 0f, 1f,
        0f, 0.4f, 1f, 0f, 0f
    )

    private val projectionMatrix = FloatArray(16)

    private val BYTES_PER_FLOAT = 4

    private val POSITION_COMPONENT_COUNT = 2

    private val COLOR_COMPONENT_COUNT = 3


    // 跨度
    private val STRIDE = (POSITION_COMPONENT_COUNT + COLOR_COMPONENT_COUNT) * BYTES_PER_FLOAT

    private val vertexData: FloatBuffer =
        GLUtil.createFloatBuffer(
            tableVerticesWithTriangles
        )

    private var program = 0
    private var aPositionLocation = 0
    private var aColorLocation = 0
    private var uMatrixLocation = 0

    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        // 对应RGBA, 范围为[0f, 1f]
        GLES20.glClearColor(0f, 0f, 0f, 0f)

        val vertexShader =
            readShaderFromResource(
                appContext,
                R.raw.simple_vertex_shader3
            )
        val fragmentShader =
            readShaderFromResource(
                appContext,
                R.raw.simple_fragment_shader3
            )
        program = GLUtil.createProgram(
            vertexShader,
            fragmentShader
        )

        GLES20.glUseProgram(program)

        // 获取 attribute 位置
        aPositionLocation = GLES20.glGetAttribLocation(program,
            A_POSITION
        )

        aColorLocation = GLES20.glGetAttribLocation(program,
            A_COLOR
        )

        uMatrixLocation = GLES20.glGetUniformLocation(program,
            U_MATRIX
        )

        // 关联属性与顶点数据的数组
        GLES20.glVertexAttribPointer(
            aPositionLocation,
            POSITION_COMPONENT_COUNT,
            GLES20.GL_FLOAT,
            false,
            STRIDE,
            vertexData
        )

        // 使能顶点数组
        GLES20.glEnableVertexAttribArray(aPositionLocation)

        vertexData.position(POSITION_COMPONENT_COUNT)

        GLES20.glVertexAttribPointer(
            aColorLocation,
            COLOR_COMPONENT_COUNT,
            GLES20.GL_FLOAT,
            false,
            STRIDE,
            vertexData
        )

        GLES20.glEnableVertexAttribArray(aColorLocation)
    }


    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        // set the OpenGL viewport
        GLES20.glViewport(0, 0, width, height)

        val aspectRatio = if (width > height) {
            width.toFloat() / height.toFloat()
        } else {
            height.toFloat() / width.toFloat()
        }

        // 归一化
        if (width > height) {
            // Landscape
            Matrix.orthoM(projectionMatrix, 0, -aspectRatio, aspectRatio, -1f, 1f, -1f, 1f)
        } else {
            // Portrait or square
            Matrix.orthoM(projectionMatrix, 0, -1f, 1f, -aspectRatio, aspectRatio, -1f, 1f)
        }
    }


    override fun onDrawFrame(gl: GL10?) {
        // clear the rendering surface
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT)

        GLES20.glUniformMatrix4fv(uMatrixLocation, 1, false, projectionMatrix, 0)

        // draw ground
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN, 0, 6)

        // draw line
        GLES20.glDrawArrays(GLES20.GL_LINES, 6, 2)

        // draw point
        GLES20.glDrawArrays(GLES20.GL_POINTS, 8, 1)

        // draw point
        GLES20.glDrawArrays(GLES20.GL_POINTS, 9, 1)

    }

    companion object {

        private const val A_POSITION = "a_Position"

        private const val A_COLOR = "a_Color"

        private const val U_MATRIX = "u_Matrix"
    }
}