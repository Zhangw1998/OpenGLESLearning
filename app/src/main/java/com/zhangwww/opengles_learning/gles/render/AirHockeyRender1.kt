package com.zhangwww.opengles_learning.gles.render

import android.opengl.GLES20
import android.opengl.GLSurfaceView
import com.zhangwww.basemodule.opengles.GLUtil
import com.zhangwww.opengles_learning.R
import com.zhangwww.opengles_learning.extensions.appContext
import com.zhangwww.basemodule.opengles.readShaderFromResource
import java.nio.FloatBuffer
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10


class AirHockeyRender1 : GLSurfaceView.Renderer {

    /**
     * 定义顶点以逆时针的顺序排列顶点，称为卷曲顺序(winding order)
     */

    // 顶点属性(attribute)数组
    val tableVertices = floatArrayOf(
        0f, 0f,
        0f, 14f,
        9f, 14f,
        9f, 0f
    )

    private val tableVerticesWithTriangles = floatArrayOf(
        //border
        -0.6f, -0.6f,
        0.6f, 0.6f,
        -0.6f, 0.6f,

        -0.6f, -0.6f,
        0.6f, -0.6f,
        0.6f, 0.6f,

        //Triangle1
        -0.5f, -0.5f,
        0.5f, 0.5f,
        -0.5f, 0.5f,

        //Triangle2
        -0.5f, -0.5f,
        0.5f, -0.5f,
        0.5f, 0.5f,

        //center line
        -0.5f, 0f,
        0.5f, 0f,

        //Mallets
        0f, -0.25f,
        0f, 0.25f,

        //ball
        -0.1f, -0.05f,
        0.1f, 0.05f,
        -0.1f, 0.05f,

        -0.1f, -0.05f,
        0.1f, -0.05f,
        0.1f, 0.05f
    )

    private val POSITION_COMPONENT_COUNT = 2

    private val vertexData: FloatBuffer =
        GLUtil.createFloatBuffer(
            tableVerticesWithTriangles
        )

    private var program = 0
    private var uColorLocation = 0
    private var aPositionLocation = 0

    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        // 对应RGBA, 范围为[0f, 1f]
        GLES20.glClearColor(0f, 0f, 0f, 0f)

        val vertexShader =
            readShaderFromResource(
                appContext,
                R.raw.simple_vertex_shader1
            )
        val fragmentShader =
            readShaderFromResource(
                appContext,
                R.raw.simple_fragment_shader1
            )
        program = GLUtil.createProgram(
            vertexShader,
            fragmentShader
        )

        GLES20.glUseProgram(program)

        // 获取 uniform 位置
        uColorLocation = GLES20.glGetUniformLocation(program,
            U_COLOR
        )
        // 获取 attribute 位置
        aPositionLocation = GLES20.glGetAttribLocation(program,
            A_POSITION
        )
        // 把位置设置为数据的开头
//        vertexData.position(0) as FloatBuffer
        // 关联属性与顶点数据的数组
        GLES20.glVertexAttribPointer(aPositionLocation, POSITION_COMPONENT_COUNT, GLES20.GL_FLOAT, false, 0, vertexData)
        // 使能顶点数组
        GLES20.glEnableVertexAttribArray(aPositionLocation)
    }


    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        // set the OpenGL viewport
        GLES20.glViewport(0, 0, width, height)
    }


    override fun onDrawFrame(gl: GL10?) {
        // clear the rendering surface
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT)

        // draw border
        GLES20.glUniform4f(uColorLocation, 0f, 1f, 0f, 1f)
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, 6)

        // draw ground
        GLES20.glUniform4f(uColorLocation, 1f, 1f, 1f, 1f)
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 6, 6)

        // draw line
        GLES20.glUniform4f(uColorLocation, 1f, 0f, 0f, 1f)
        GLES20.glDrawArrays(GLES20.GL_LINES, 12, 2)

        // draw point
        GLES20.glUniform4f(uColorLocation, 0f, 0f, 1f, 1f)
        GLES20.glDrawArrays(GLES20.GL_POINTS, 14, 1)

        // draw point
        GLES20.glUniform4f(uColorLocation, 1f, 0f, 0f, 1f)
        GLES20.glDrawArrays(GLES20.GL_POINTS, 15, 1)

        // draw ball
        GLES20.glUniform4f(uColorLocation, 0f, 1f, 1f, 1f)
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 16, 6)

    }

    companion object {

        private const val A_POSITION = "a_Position"
        private const val U_COLOR = "u_Color"
    }
}