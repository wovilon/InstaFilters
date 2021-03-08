package com.example.instafilters

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.mukesh.image_processing.ImageProcessor
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), FiltersCallback {
    var bitmaps = ArrayList<FilterItem>()
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
        generateBitmaps()
        initAdapter()

        val processor = ImageProcessor()
        oneBitMap = processor.tintImage(bitmap, 90)
        ivMainImage.setImageBitmap(oneBitMap)

        ivGallery.setOnClickListener {
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE)
        }
        ivCamera.setOnClickListener {
            val intent = Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, 7);
        }
        ivSave.setOnClickListener { BitmapSaver().saveTempBitmap(bitmap) }
        btCheckLogin.setOnClickListener { checkLogin() }
    }

    fun initAdapter(){
        val adapter = FilterAdapter(bitmaps, this, this)
        rvFilters.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rvFilters.adapter = adapter
    }

    fun generateBitmaps(){
        val processor = ImageProcessor()
        bitmaps.clear()
        bitmaps.add(FilterItem("Original", bitmap))
        bitmaps.add(FilterItem("ColorShift", processor.tintImage(bitmap, 90)))
        bitmaps.add(FilterItem("Sepia", processor.createSepiaToningEffect(bitmap, 5, 5.0, 5.0, 1.0)))
        bitmaps.add(FilterItem("Saturation", processor.applySaturationFilter(bitmap, 3)))
        bitmaps.add(FilterItem("Snow", processor.applySnowEffect(bitmap)))

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE){
            val imageUri = data?.getData()
            //ivMainImage.setImageURI(imageUri)
            bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
            ivMainImage.setImageBitmap(bitmap)
            generateBitmaps()
            initAdapter()
        }else if (requestCode == 7 && resultCode == RESULT_OK) {

            bitmap = data?.getExtras()?.get("data") as Bitmap
            ivMainImage.setImageBitmap(bitmap)
            generateBitmaps()
            initAdapter()
        }
    }

    fun checkLogin(){
        if (etLogin.text.toString().equals("User") && etPassword.text.toString().equals("12345"))
        {
            clLogin.visibility = View.GONE
            hideKeyboard(this)
        }
    }

    override fun onSelected(i: Int) {
        bitmap = bitmaps[i].bitmap
        ivMainImage.setImageBitmap(bitmap)
    }

    fun hideKeyboard(activity: Activity) {
        val imm: InputMethodManager =
            activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        var view = activity.currentFocus
        if (view == null) {
            view = View(activity)
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

}