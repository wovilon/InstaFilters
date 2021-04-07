package com.example.instafilters

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mukesh.image_processing.ImageProcessor
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    lateinit var bitmap: Bitmap
    lateinit var processedBitmap: Bitmap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.sample);

        val processor = ImageProcessor()
        processedBitmap = processor.tintImage(bitmap, 220)
        ivMain.setImageBitmap(processedBitmap)
    }
}