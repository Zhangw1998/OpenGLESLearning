package com.zhangwww.opengles_learning.gles.program

import android.content.Context
import android.opengl.GLES20
import com.zhangwww.opengles_learning.CommonApplication
import com.zhangwww.opengles_learning.gles.GLUtil
import com.zhangwww.opengles_learning.utils.readShaderFromResource


open class ShaderProgram(
    context: Context,
    vertexShaderResourceId: Int,
    fragmentShaderResourceId: Int
) {

    val program: Int

    init {
        val vertexShader = readShaderFromResource(context, vertexShaderResourceId)
        val fragmentShader = readShaderFromResource(context, fragmentShaderResourceId)
        program = GLUtil.createProgram(vertexShader, fragmentShader)
    }

    fun useProgram() {
        // Set the current OpenGL program to this program
        GLES20.glUseProgram(program)
    }


    companion object {
        // Uniform constants
        const val U_MATRIX = "u_Matrix"
        const val U_TEXTURE_UNIT = "u_TextureUnit"

        // Attribute constants
        const val A_POSITION = "a_Position"
        const val A_Color = "a_Color"
        const val A_TEXTURE_COORDINATES = "a_TextureCoordinates"
    }
}