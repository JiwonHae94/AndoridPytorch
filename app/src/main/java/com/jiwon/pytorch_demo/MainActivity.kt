package com.jiwon.pytorch_demo

import android.graphics.Bitmap
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import com.jiwon.pytorch_demo.databinding.ActivityMainBinding
import java.time.temporal.TemporalAdjusters.next

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        val bitmap = Bitmap.createBitmap(0, 0, Bitmap.Config.ARGB_8888)
        val inference_btn = findViewById<Button>(R.id.inference)
        inference_btn.setOnClickListener {
            Toast.makeText(this,"start inferencing", Toast.LENGTH_SHORT).show()
        }
    }

}