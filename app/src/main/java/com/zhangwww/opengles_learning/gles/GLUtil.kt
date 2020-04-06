package com.zhangwww.opengles_learning.gles

import android.opengl.GLES20
import android.util.Log
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer

object GLUtil {

    private const val TAG = "GLUtil"

    private const val BYTES_PER_FLOAT = 4

    /**
     * 使用OpenGL程序的基本流程
     * 1.编译着色器
     * 2.创建OpenGL程序
     * 3.链接着色器
     * 4.验证OpenGL程序
     */
    fun createProgram(vertexShaderCode: String, fragmentShaderCode: String): Int {
        // 编译
        val vertexShaderId = compileVertexShader(vertexShaderCode)
        val fragmentShaderId = compileFragmentShader(fragmentShaderCode)
        // 链接
        val programId = linkProgram(vertexShaderId, fragmentShaderId)
        // 验证
        if (validateProgram(programId)) {
            return programId
        } else {
            Log.e(TAG, GLES20.glGetProgramInfoLog(programId))
            throw RuntimeException("Create OpenGL program failed")
        }
    }

    // 编译顶点着色器
    private fun compileVertexShader(shaderCode: String): Int {
        return compileShader(GLES20.GL_VERTEX_SHADER, shaderCode)
    }

    // 编译片段着色器
    private fun compileFragmentShader(shaderCode: String): Int {
        return compileShader(GLES20.GL_FRAGMENT_SHADER, shaderCode)
    }

    // 创建OpenGL程序并链接着色器
    private fun linkProgram(vertexShaderId: Int, fragmentShaderId: Int): Int {
        val programId = GLES20.glCreateProgram()
        // 创建程序失败
        if (programId == 0) return 0
        // 链接顶点着色器
        GLES20.glAttachShader(programId, vertexShaderId)
        // 链接片段着色器
        GLES20.glAttachShader(programId, fragmentShaderId)
        // 链接OpenGL程序
        GLES20.glLinkProgram(programId)
        // 验证链接结果
        val linkStatus = IntArray(1)
        GLES20.glGetProgramiv(programId, GLES20.GL_LINK_STATUS, linkStatus, 0)
        if (linkStatus[0] == 0) {
            // 链接失败打印失败信息并删除OpenGL程序
            Log.e(TAG, "Could not link program: ")
            Log.e(TAG, GLES20.glGetProgramInfoLog(programId))
            GLES20.glDeleteProgram(programId)
            return 0
        }
        return programId
    }

    // 验证OpenGL程序是否可用
    private fun validateProgram(programId: Int): Boolean {
        GLES20.glValidateProgram(programId)
        val validateStatus = IntArray(1)
        GLES20.glGetProgramiv(programId, GLES20.GL_VALIDATE_STATUS, validateStatus, 0)
        return validateStatus[0] != 0
    }

    // 根据类型编译着色器
    private fun compileShader(shaderType: Int, shaderCode: String): Int {
        // 根据不同的类型创建着色器id
        val shaderId = GLES20.glCreateShader(shaderType)
        checkGlError("glCreateShader type=$shaderType")
        // 连接着色器id和着色器程序内容
        GLES20.glShaderSource(shaderId, shaderCode)
        // 编译着色器
        GLES20.glCompileShader(shaderId)
        // 验证编译结果
        val compileStatus = IntArray(1)
        GLES20.glGetShaderiv(shaderId, GLES20.GL_COMPILE_STATUS, compileStatus, 0)
        if (compileStatus[0] == 0) {
            // 编译失败打印相关信息并删除
            Log.e(TAG, "Could not compile shader $shaderType:")
            // 取出着色器信息日志
            Log.e(TAG, GLES20.glGetShaderInfoLog(shaderId))
            GLES20.glDeleteShader(shaderId)
            return 0
        }
        return shaderId
    }

    private fun checkGlError(op: String) {
        val error = GLES20.glGetError()
        if (error != GLES20.GL_NO_ERROR) {
            val msg = "$op: glError 0x ${Integer.toHexString(error)}"
            throw RuntimeException(msg)
        }
    }

    fun checkLocation(location: Int, label: String) {
        if (location < 0) {
            throw RuntimeException("Unable to locate '$label' in program")
        }
    }

    fun createFloatBuffer(coords: FloatArray): FloatBuffer {
        return ByteBuffer.allocateDirect(coords.size * BYTES_PER_FLOAT)
            .order(ByteOrder.nativeOrder())
            .asFloatBuffer()
            .put(coords)
            .position(0) as FloatBuffer // 把位置设置为数据的开头
    }

    fun logVersionInfo() {
        Log.e(TAG, "vendor  : " + GLES20.glGetString(GLES20.GL_VENDOR))
        Log.e(TAG, "renderer: " + GLES20.glGetString(GLES20.GL_RENDERER))
        Log.e(TAG, "version : " + GLES20.glGetString(GLES20.GL_VERSION))
    }
}