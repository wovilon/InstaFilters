package com.example.instafilters

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.mukesh.image_processing.ImageProcessor
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    lateinit var bitmap : Bitmap
    lateinit var oneBitMap : Bitmap
    val PICK_IMAGE = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val intent: Intent = Intent()
        intent.setType("image/*")
        intent.setAction(Intent.ACTION_GET_CONTENT)




        bitmap = BitmapFactory.decodeResource(resources, R.drawable.image)

        val processor = ImageProcessor()
        oneBitMap = processor.tintImage(bitmap, 90)
        ivMainImage.setImageBitmap(oneBitMap)

        initAdapter()

        btGallery.setOnClickListener {
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE)
        }

    }

    fun initAdapter(){
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE){
            val imageUri = data?.getData()
            //ivMainImage.setImageURI(imageUri)
            bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);

        }
    }

}