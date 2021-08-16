package com.jiwon.pytorch_demo.model

class TorchBufferUint8 : TorchBuffer{
    constructor(shape : IntArray) : super(shape)
    constructor() : super()

    override val dataType: DataType  = DATA_TYPE
    override val floatArray: FloatArray
        get() {
            buffer!!.rewind()
            val arr = FloatArray(flatSize)
            for (i in 0 until flatSize) {
                arr[i] = (buffer!!.getInt() and 255) as Float
            }

            return arr
        }

    override fun getFloatValue(index: Int) : Float {
        return (buffer!!.getInt(index) and 255).toFloat()
    }

    override val intArray: IntArray
        get() {
            buffer!!.rewind()
            val arr = IntArray(flatSize)

            for (i in 0 until flatSize) {
                arr[i] = buffer!!.getInt() and 255
            }
            return arr
        }

    override fun getIntValue(index: Int) : Int {
        return buffer!!.getInt(index) and 255
    }

    override val typeSize: Int = DATA_TYPE.byteSize()


    override fun loadArray(src: FloatArray, shape: IntArray) {
        require(src.size == computeFlatSize(shape)){"The size of the array to be loaded does not match the specified shape." }
        resize(shape)
        buffer!!.rewind()
        val var4 = src.size

        for (var5 in 0 until var4) {
            val a = src[var5]
            buffer!!.put(
                Math.max(Math.min(a.toDouble(), 255.0), 0.0).toInt().toByte()
            )
        }
        buffer!!.clear()
    }

    override fun loadArray(src: IntArray, shape: IntArray) {
        require(src.size == computeFlatSize(shape)){"The size of the array to be loaded does not match the specified shape." }

        resize(shape)
        buffer!!.rewind()
        val var4 = src.size

        for (var5 in 0 until var4) {
            val a = src[var5]
            buffer!!.put(Math.max(Math.min(a, 255), 0).toByte())
        }
    }

    companion object{
        val DATA_TYPE = DataType.UINT8
    }
}
