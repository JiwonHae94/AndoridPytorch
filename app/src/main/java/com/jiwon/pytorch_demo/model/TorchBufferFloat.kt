package com.jiwon.pytorch_demo.model

import android.util.Log
import java.nio.FloatBuffer

class TorchBufferFloat : TorchBuffer{
    constructor(shape : IntArray) : super(shape)
    constructor() : super()

    override val dataType: DataType  = DATA_TYPE
    override val floatArray: FloatArray
        get() {
            this.buffer?.rewind()
            val arr = FloatArray(this.flatSize)
            val floatBuffer = this.buffer?.asFloatBuffer()
            floatBuffer?.get(arr)
            return arr
        }

    override fun getFloatValue(var1: Int) : Float {
        return buffer!!.getFloat(var1 shl 2)
    }

    override val intArray: IntArray
        get() {
            this.buffer?.rewind()
            val arr = IntArray(this.flatSize)
            val intBuffer = this.buffer?.asIntBuffer()
            intBuffer?.get(arr)
            return arr
        }

    override fun getIntValue(var1: Int) : Int {
        return buffer!!.getFloat(var1 shl 2).toInt()
    }

    override val typeSize: Int
        get() = DATA_TYPE.byteSize()

    override fun loadArray(src: FloatArray, shape: IntArray) {
        require(src.size == computeFlatSize(shape)){"The size of the array to be loaded does not match the specified shape." }
        resize(shape)
        this.buffer?.rewind()
        val floatBuffer = this.buffer?.asFloatBuffer()
        floatBuffer?.put(src)
    }

    override fun loadArray(src: IntArray, shape: IntArray) {
        require(src.size == computeFlatSize(shape)){"The size of the array to be loaded does not match the specified shape." }
        this.resize(shape)
        this.buffer?.rewind()
        val intBuffer = this.buffer?.asIntBuffer()
        intBuffer?.put(src)
    }

    companion object{
        val DATA_TYPE = DataType.FLOAT32
    }
}
