package com.jiwon.pytorch_demo.model

import android.graphics.Bitmap
import android.util.Log
import org.pytorch.IValue
import org.pytorch.Module
import org.pytorch.Tensor

abstract class PytorchModel  {
    private val model : Module
    private val TAG = PytorchModel::class.java.simpleName

    constructor(modelPath : String){
        this.model = loadModel(modelPath)
    }

    protected fun loadModel(path : String) : Module {
        return Module.load(path)
    }

    private val torchBuffer = TorchBuffer.createFixedSize()


    protected fun inference(input : Tensor){
        val inferenceStartTime = System.currentTimeMillis()
        this.model.forward(IValue.from(input)).toTensor()
        val inferenceEnd = System.currentTimeMillis()

        Log.d(TAG,"inference time taken : ${inferenceEnd - inferenceStartTime}")
    }

    companion object{
        const val INPUT_WIDTH  = 100
        const val INPUT_HEIGHT = 100

    }
}