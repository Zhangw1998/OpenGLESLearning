package com.zhangwww.opengles_learning.gles.program

import android.content.Context
import android.opengl.GLES20.*
import com.zhangwww.opengles_learning.R

class TextureShaderProgram(context: Context) : ShaderProgram(
    context,
    R.raw.texture_vertex_shader,
    R.raw.texture_fragment_shader
) {
    // Uniform locations
    private val uMatrixLocation: Int
    private val uTextureUnitLocation: Int

    // Attribute locations
    val aPositionLocation: Int
    val aTextureCoordinatesLocation: Int

    init {
        uMatrixLocation = glGetUniformLocation(program, U_MATRIX)
        uTextureUnitLocation = glGetUniformLocation(program, U_TEXTURE_UNIT)

        aPositionLocation = glGetAttribLocation(program, A_POSITION)
        aTextureCoordinatesLocation = glGetAttribLocation(program, A_TEXTURE_COORDINATES)
    }

    fun setUniforms(matrix: FloatArray, textureId: Int) {
        glUniformMatrix4fv(uMatrixLocation, 1, false, matrix, 0)
        // Set the active unit to texture unit 0
        glActiveTexture(GL_TEXTURE0)
        // Bind the texture to this unit
        glBindTexture(GL_TEXTURE_2D, textureId)
        // Tell the texture uniform sampler to use this texture in the shader by
        // telling it to read form texture unit 0
        glUniform1i(uTextureUnitLocation, 0)
    }

}