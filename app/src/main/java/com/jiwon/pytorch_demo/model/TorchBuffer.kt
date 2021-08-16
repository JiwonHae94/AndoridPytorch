package com.jiwon.pytorch_demo.model

import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.util.*

abstract class TorchBuffer {
    var buffer: ByteBuffer? = null
    protected lateinit var _shape: IntArray
    val shape : IntArray get() = Arrays.copyOf(this._shape!!, this._shape!!.size)


    var flatSize = -1
    protected val isDynamic : Boolean

    abstract val dataType : DataType

    abstract val typeSize : Int

    abstract val floatArray : FloatArray
    abstract val intArray : IntArray

    abstract fun getIntValue(var1 : Int) : Int
    abstract fun getFloatValue(var1 : Int) : Float

    protected constructor(){
        this.isDynamic = true
        allocateMemory(intArrayOf(0))
    }

    protected constructor(shape : IntArray){
        this.isDynamic = false
        allocateMemory(shape)
    }

    fun loadBuffer(buffer : ByteBuffer, shape : IntArray){
        val flatSize = computeFlatSize(shape)
        require(buffer.limit() == (this.typeSize * flatSize)){"The size of byte buffer and the shape do not match."}

        if (!this.isDynamic) {
            require(flatSize == this.flatSize){"The size of byte buffer and the size of the tensor buffer do not match."}
        }else{
            this.flatSize = flatSize
        }

        _shape = shape.clone()
        buffer.rewind()
        this.buffer = buffer
    }

    fun loadBuffer(buffer : ByteBuffer){
        this.loadBuffer(buffer, _shape)
    }

    protected fun resize(shape : IntArray){
        if(this.isDynamic){
            this.allocateMemory(shape)
        }else{
            require(Arrays.equals(shape, this.shape))
            _shape = shape.clone()
        }
    }

    internal fun allocateMemory(shape : IntArray){
        require(isShapeValid(shape)){"Values in TensorBuffer shape should be non-negative."}
        val newFlatSize = computeFlatSize(shape)
        this._shape = shape.clone()
        if (flatSize != newFlatSize) {
            flatSize = newFlatSize
            buffer = ByteBuffer.allocateDirect(flatSize * this.typeSize)
            buffer!!.order(ByteOrder.nativeOrder())
        }
    }

    abstract fun loadArray(var1: IntArray, var2: IntArray)

    fun loadArray(src: IntArray) {
        this.loadArray(src, this._shape!!)
    }

    abstract fun loadArray(var1 : FloatArray, var2 : IntArray)

    fun loadArray(src : FloatArray){
        this.loadArray(src, this._shape!!)
    }

    companion object{
        fun createFixedSize(shape : IntArray, dataType : DataType) : TorchBuffer{
            return when(dataType){
                DataType.FLOAT32-> TorchBufferFloat(shape)
                DataType.INT32 -> TorchBufferUint8(shape)
                else-> throw AssertionError("TensorBuffer does not support data type: " + dataType)
            }
        }

        fun createDynamic(dataType : DataType) : TorchBuffer {
            return when(dataType){
                DataType.FLOAT32-> TorchBufferFloat()
                DataType.INT32 -> TorchBufferUint8()
                else-> throw AssertionError("TensorBuffer does not support data type: " + dataType)
            }
        }

        fun createFrom(buffer : TorchBuffer, dataType : DataType) : TorchBuffer{
            val result : TorchBuffer = if(buffer.isDynamic) createDynamic(dataType)!! else createFixedSize(buffer._shape!!, dataType)!!

            if(buffer.dataType == DataType.FLOAT32 && dataType == DataType.FLOAT32){
                val data = buffer.floatArray
                result.loadArray(data, buffer._shape!!)
            }else{
                val data = buffer.intArray
                result.loadArray(data, buffer._shape!!)
            }

            return result
        }

        fun computeFlatSize(shape : IntArray) : Int{
            var prod = 1
            val var3 = shape.size

            for (var4 in 0 until var3) {
                val s = shape[var4]
                prod *= s
            }

            return prod
        }

        fun isShapeValid(shape : IntArray) : Boolean{
            if(shape.size == 0) return true
            else{
                val var1 = shape
                val var2 = shape.size

                for(indx in 0 until var2){
                    var s= var1[indx]
                    if(s< 0)
                        return false
                }
                return true
            }
        }
    }
}
