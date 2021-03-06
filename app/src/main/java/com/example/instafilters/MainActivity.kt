package com.example.instafilters

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.mukesh.image_processing.ImageProcessor
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    lateinit var bitmap : Bitmap
    lateinit var oneBitMap : Bitmap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bitmap = BitmapFactory.decodeResource(resources, R.drawable.image)

        val processor = ImageProcessor()
        oneBitMap = processor.tintImage(bitmap, 90)
        ivMainImage.setImageBitmap(oneBitMap)

        val images = ArrayList<FilterItem>()
        images.add(FilterItem("OneFilter", bitmap))
        images.add(FilterItem("OneFilter", bitmap))
        images.add(FilterItem("OneFilter", bitmap))
        images.add(FilterItem("OneFilter", bitmap))
        images.add(FilterItem("OneFilter", bitmap))

        val adapter = FilterAdapter(images, this)
        rvFilters.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rvFilters.adapter = adapter

    }

}