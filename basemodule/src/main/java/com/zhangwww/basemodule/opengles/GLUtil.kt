package com.zhangwww.basemodule.opengles

import android.opengl.GLES20.*
import android.util.Log
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer

object GLUtil {

    private const val TAG = "GLUtil"

    private const val BYTES_PER_FLOAT = 4

    /**
     * 创建OpenGL程序
     *
     * <p>
     *     1.编译着色器
     *     2.创建OpenGL程序
     *     3.链接着色器
     *     4.验证OpenGL程序
     * </p>
     *
     * @param vertexShaderCode 顶点着色器代码
     * @param fragmentShaderCode 片段着色器代码
     * @return 返回OpenGL程序的句柄
     */
    fun createProgram(vertexShaderCode: String, fragmentShaderCode: String): Int {
        // 编译着色器
        val vertexShaderId = compileVertexShader(vertexShaderCode)
        val fragmentShaderId = compileFragmentShader(fragmentShaderCode)
        // 链接着色器
        val programId = linkProgram(vertexShaderId, fragmentShaderId)
        // 验证OpenGL程序
        if (validateProgram(programId)) {
            return programId
        } else {
            Log.e(TAG, glGetProgramInfoLog(programId))
            throw RuntimeException("Create OpenGL program failed")
        }
    }

    /**
     * 编译顶点着色器
     * @param shaderCode 顶点着色器程序的字符串
     * @return 顶点着色器的句柄
     */
    private fun compileVertexShader(shaderCode: String): Int {
        return compileShader(GL_VERTEX_SHADER, shaderCode)
    }

    /**
     * 编译片段着色器
     * @param shaderCode 片段着色器程序的字符串
     * @return 片段着色器的句柄
     */
    private fun compileFragmentShader(shaderCode: String): Int {
        return compileShader(GL_FRAGMENT_SHADER, shaderCode)
    }

    // 创建OpenGL程序并链接着色器
    /**
     * 创建OpenGL程序并链接着色器
     * @param vertexShaderId 顶点着色器的句柄
     * @param fragmentShaderId 片段着色器的句柄
     * @return OpenGL程序的句柄
     */
    private fun linkProgram(vertexShaderId: Int, fragmentShaderId: Int): Int {
        val programId = glCreateProgram()
        // 创建程序失败
        if (programId == 0) return 0
        // 链接顶点着色器
        glAttachShader(programId, vertexShaderId)
        // 链接片段着色器
        glAttachShader(programId, fragmentShaderId)
        // 链接OpenGL程序
        glLinkProgram(programId)
        // 验证链接结果
        val linkStatus = IntArray(1)
        glGetProgramiv(programId, GL_LINK_STATUS, linkStatus, 0)
        if (linkStatus[0] == 0) {
            // 链接失败打印失败信息并删除OpenGL程序
            Log.e(TAG, "Could not link program: ")
            Log.e(TAG, glGetProgramInfoLog(programId))
            glDeleteProgram(programId)
            return 0
        }
        return programId
    }

    /**
     * 验证OpenGL程序是否可用
     * @param programId OpenGL程序的句柄
     * @return 程序是否可以使用
     */
    private fun validateProgram(programId: Int): Boolean {
        glValidateProgram(programId)
        val validateStatus = IntArray(1)
        glGetProgramiv(programId, GL_VALIDATE_STATUS, validateStatus, 0)
        return validateStatus[0] != 0
    }

    /**
     * 根据类型编译着色器
     * @param shaderType 着色器类型 {GL_VERTEX_SHADER | GL_FRAGMENT_SHADER}
     * @param shaderCode 着色器代码
     * @return 着色器的句柄
     */
    private fun compileShader(shaderType: Int, shaderCode: String): Int {
        // 根据不同的类型创建着色器id
        val shaderId = glCreateShader(shaderType)
        checkGlError("glCreateShader type=$shaderType")
        // 连接着色器id和着色器程序内容
        glShaderSource(shaderId, shaderCode)
        // 编译着色器
        glCompileShader(shaderId)
        // 验证编译结果
        val compileStatus = IntArray(1)
        glGetShaderiv(shaderId, GL_COMPILE_STATUS, compileStatus, 0)
        if (compileStatus[0] == 0) {
            // 编译失败打印相关信息并删除
            Log.e(TAG, "Could not compile shader $shaderType:")
            // 取出着色器信息日志
            Log.e(TAG, glGetShaderInfoLog(shaderId))
            glDeleteShader(shaderId)
            return 0
        }
        return shaderId
    }

    /**
     * 检查OpenGL环境中的错误
     */
    private fun checkGlError(op: String) {
        val error = glGetError()
        if (error != GL_NO_ERROR) {
            val msg = "$op: glError 0x ${Integer.toHexString(error)}"
            throw RuntimeException(msg)
        }
    }

    /**
     * 检查OpenGL中的参数
     */
    fun checkLocation(location: Int, label: String) {
        if (location < 0) {
            throw RuntimeException("Unable to locate '$label' in program")
        }
    }

    /**
     * 根据坐标创建FloatBuffer
     * @param coords Float类型的坐标
     * @return 创建的FloatBuffer
     */
    fun createFloatBuffer(coords: FloatArray): FloatBuffer {
        return ByteBuffer.allocateDirect(coords.size * BYTES_PER_FLOAT)
            .order(ByteOrder.nativeOrder())
            .asFloatBuffer()
            .put(coords)
            .position(0) as FloatBuffer // 把位置设置为数据的开头
    }

    /**
     * 打印OpenGLES 版本信息
     */
    fun logVersionInfo() {
        Log.e(TAG, "vendor  : " + glGetString(GL_VENDOR))
        Log.e(TAG, "renderer: " + glGetString(GL_RENDERER))
        Log.e(TAG, "version : " + glGetString(GL_VERSION))
    }
}