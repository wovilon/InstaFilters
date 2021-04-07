package com.example.instafilters

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.mukesh.image_processing.ImageProcessor
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), FilterCallback {
    lateinit var bitmap: Bitmap
    lateinit var processedBitmap: Bitmap
    val bitmaps = ArrayList<FilterItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.sample)

        val processor = ImageProcessor()
        processedBitmap = processor.tintImage(bitmap, 220)
        ivMain.setImageBitmap(processedBitmap)


        generateBitmaps()
        initAdapter()
    }

    fun initAdapter(){
        rvFilters.adapter = FiltersAdapter(bitmaps, this,this)
        rvFilters.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
    }

    fun generateBitmaps(){
        val processor = ImageProcessor()
        bitmaps.add(FilterItem("Original", bitmap))
        bitmaps.add(FilterItem("ColorShift", processor.tintImage(bitmap, 220)))
        bitmaps.add(FilterItem("Sepia", processor.createSepiaToningEffect(bitmap, 5, 5.0, 5.0, 1.0)))
        bitmaps.add(FilterItem("Saturation", processor.applySaturationFilter(bitmap, 3)))
        bitmaps.add(FilterItem("Snow", processor.applySnowEffect(bitmap)))
    }

    override fun onSelected(index: Int) {
        ivMain.setImageBitmap(bitmaps[index].image)
    }
}