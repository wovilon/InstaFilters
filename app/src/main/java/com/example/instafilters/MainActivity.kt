package com.example.instafilters

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import androidx.recyclerview.widget.LinearLayoutManager
import com.mukesh.image_processing.ImageProcessor
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), FilterCallback {
    lateinit var bitmap: Bitmap
    lateinit var processedBitmap: Bitmap
    val bitmaps = ArrayList<FilterItem>()
    val PICK_IMAGE= 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.sample)

        val processor = ImageProcessor()
        processedBitmap = processor.tintImage(bitmap, 220)
        ivMain.setImageBitmap(processedBitmap)


        generateBitmaps()
        initAdapter()
        initOnClicks()
    }

    fun initOnClicks(){
        ivGallery.setOnClickListener {
            val intent = Intent()
            intent.setType("image/*")
            intent.setAction(Intent.ACTION_GET_CONTENT)
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE)
        }
        ivCamera.setOnClickListener {
            val intent = Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(intent, 7)
        }
        ivSave.setOnClickListener { ImageSaver().saveTempBitmap(processedBitmap) }
    }

    fun initAdapter(){
        rvFilters.adapter = FiltersAdapter(bitmaps, this,this)
        rvFilters.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
    }

    fun generateBitmaps(){
        bitmaps.clear()
        val processor = ImageProcessor()
        bitmaps.add(FilterItem("Original", bitmap))
        bitmaps.add(FilterItem("ColorShift", processor.tintImage(bitmap, 220)))
        bitmaps.add(FilterItem("Sepia", processor.createSepiaToningEffect(bitmap, 5, 5.0, 5.0, 1.0)))
        bitmaps.add(FilterItem("Saturation", processor.applySaturationFilter(bitmap, 3)))
        bitmaps.add(FilterItem("Snow", processor.applySnowEffect(bitmap)))
    }

    override fun onSelected(index: Int) {
        processedBitmap = bitmaps[index].image
        ivMain.setImageBitmap(bitmaps[index].image)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK){
            val imageUri = data?.data
            bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, imageUri);

            generateBitmaps()
            initAdapter()
            ivMain.setImageBitmap(bitmap)
        }else if (requestCode == 7 &&resultCode == RESULT_OK ){
            bitmap = data?.extras?.get("data") as Bitmap

            generateBitmaps()
            initAdapter()
            ivMain.setImageBitmap(bitmap)
        }
    }
}