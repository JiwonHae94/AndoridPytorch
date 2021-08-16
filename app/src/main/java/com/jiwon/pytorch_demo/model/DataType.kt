package com.jiwon.pytorch_demo.model

enum class DataType(private val value: Int) {
    FLOAT32(1), INT32(2), UINT8(3), INT64(4), STRING(5), INT8(9);

    fun byteSize(): Int {
        return when (this) {
            FLOAT32 -> 4
            INT32 -> 4
            INT8, UINT8 -> 1
            INT64 -> 8
            STRING -> -1
            else -> throw IllegalArgumentException("DataType error: DataType $this is not supported yet")
        }
    }

    fun c(): Int {
        return value
    }

    fun toStringName(): String {
        return when (this) {
            FLOAT32 -> "float"
            INT32 -> "int"
            INT8, UINT8 -> "byte"
            INT64 -> "long"
            STRING -> "string"
            else -> throw IllegalArgumentException("DataType error: DataType $this is not supported yet")
        }
    }

    companion object {
        private val values: Array<DataType> = values()

        fun fromC(c: Int): DataType {
            val var1 = values
            val var2 = var1.size
            for (var3 in 0 until var2) {
                val t = var1[var3]
                if (t.value == c) {
                    return t
                }
            }
            throw IllegalArgumentException("DataType error: DataType " + c + " is not recognized in Java (version " + TensorFlowLite.runtimeVersion() + ")")
        }
    }
}
