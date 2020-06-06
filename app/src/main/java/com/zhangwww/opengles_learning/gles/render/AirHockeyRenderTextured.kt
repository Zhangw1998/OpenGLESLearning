package com.zhangwww.opengles_learning.gles.render

import android.opengl.GLES20.*
import android.opengl.GLSurfaceView
import android.opengl.Matrix
import com.zhangwww.opengles_learning.CommonApplication
import com.zhangwww.opengles_learning.R
import com.zhangwww.opengles_learning.bean.Mallet
import com.zhangwww.opengles_learning.bean.Table
import com.zhangwww.opengles_learning.gles.program.ColorShaderProgram
import com.zhangwww.opengles_learning.gles.program.TextureShaderProgram
import com.zhangwww.basemodule.opengles.MatrixHelper
import com.zhangwww.basemodule.opengles.loadTexture
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10


class AirHockeyRenderTextured : GLSurfaceView.Renderer {

    private val projectionMatrix = FloatArray(16)
    private val modelMatrix = FloatArray(16)

    private lateinit var table: Table
    private lateinit var mallet: Mallet

    private lateinit var textureProgram: TextureShaderProgram
    private lateinit var colorProgram: ColorShaderProgram

    private var texture: Int = 0

    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        glClearColor(0f, 0f, 0f, 0f)

        table = Table()
        mallet = Mallet()

        textureProgram = TextureShaderProgram(CommonApplication.appContext)
        colorProgram = ColorShaderProgram(CommonApplication.appContext)

        texture = loadTexture(
            CommonApplication.appContext,
            R.drawable.air_hockey_surface
        )
    }


    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        // set the OpenGL viewport
        glViewport(0, 0, width, height)

        MatrixHelper.perspectiveM(projectionMatrix, 45f, width.toFloat() / height, 1f, 10f)
        Matrix.setIdentityM(modelMatrix, 0)

        Matrix.translateM(modelMatrix, 0, 0f, 0f, -3f)
        // 加入旋转
        Matrix.rotateM(modelMatrix, 0, -60f, 1f, 0f, 0f)

        val temp = FloatArray(16)
        Matrix.multiplyMM(temp, 0, projectionMatrix, 0, modelMatrix, 0)
        System.arraycopy(temp, 0, projectionMatrix, 0, temp.size)
    }


    override fun onDrawFrame(gl: GL10?) {
        glClear(GL_COLOR_BUFFER_BIT)

        textureProgram.useProgram()
        textureProgram.setUniforms(projectionMatrix, texture)
        table.bindData(textureProgram)
        table.draw()

        colorProgram.useProgram()
        colorProgram.setUniforms(projectionMatrix)
        mallet.bindData(colorProgram)
        mallet.draw()
    }

    companion object {

        private const val A_POSITION = "a_Position"

        private const val A_COLOR = "a_Color"

        private const val U_MATRIX = "u_Matrix"
    }
}