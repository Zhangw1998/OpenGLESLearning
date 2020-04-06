package com.zhangwww.opengles_learning.gles.program

import android.content.Context
import android.opengl.GLES20.*
import com.zhangwww.opengles_learning.R

class ColorShaderProgram(context: Context) : ShaderProgram(
    context,
    R.raw.simple_vertex_shader3,
    R.raw.simple_fragment_shader3
) {

    val uMatrixLocation: Int
    val aPositionLocation: Int
    val aColorLocation: Int

    init {
        uMatrixLocation = glGetUniformLocation(program, U_MATRIX)
        aPositionLocation = glGetAttribLocation(program, A_POSITION)
        aColorLocation = glGetAttribLocation(program, A_Color)
    }

    fun setUniforms(matrix: FloatArray) {
        glUniformMatrix4fv(uMatrixLocation, 1, false, matrix, 0)
    }

}