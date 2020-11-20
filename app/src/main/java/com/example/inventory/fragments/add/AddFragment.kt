package com.example.inventory.fragments.add


import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.text.Editable
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.inventory.R
import com.example.inventory.model.Things
import com.example.inventory.vewmodel.ThingViewModel
import kotlinx.android.synthetic.main.fragment_add.*
import kotlinx.android.synthetic.main.fragment_add.view.*
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


class AddFragment : Fragment() {

    private val PERMISSION_CODE = 1000

    var image_uri: ImageView? = null

    private lateinit var mThingViewModel: ThingViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add, container, false)

        mThingViewModel = ViewModelProvider(this).get(ThingViewModel::class.java)

        image_uri = view.addImageId
        view.addImageId.setOnClickListener {
        }

        view.add_button.setOnClickListener {
            insertDataToDatabase()
        }
        view.buttonsid.setOnClickListener {
            takePictureIntent()
        }

        return view
    }


    private fun insertDataToDatabase() {
        val title = editTextThingTitle.text.toString()
        val price = editTextPrice.text
        val quantity = editTextQuantity.text
        val supplier = editTextSupplier.text.toString()


        if (inputCheck(title, price, quantity, supplier)) {

            val thing = Things(
                0,
                title,
                Integer.parseInt(price.toString()),
                Integer.parseInt(quantity.toString()),
                supplier
            )

            mThingViewModel.addThing(thing)

            Toast.makeText(requireContext(), "Successfully added", Toast.LENGTH_SHORT).show()

            findNavController().navigate(R.id.action_addFragment_to_listFragment)
        } else {
            Toast.makeText(requireContext(), "Please fill out all fields", Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun inputCheck(
        title: String,
        price: Editable,
        quantity: Editable,
        supplier: String
    ): Boolean {
        return !(TextUtils.isEmpty(title) || TextUtils.isEmpty(supplier) || price.isEmpty() || quantity.isEmpty())
    }

    var imagePath: String? = null

    val REQUEST_IMAGE_CAPTURE = 1

    private fun takePictureIntent() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (takePictureIntent.resolveActivity(requireActivity().packageManager) != null) {
            var photoFile: File? = null
            try {
                photoFile = createImageFile()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            if (photoFile != null) {
                val photoURI = FileProvider.getUriForFile(
                    requireContext(),
                    "com.example.android.fileprovider",
                    photoFile
                )
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            val file: File = File(imagePath)
            val uri : Uri = Uri.fromFile(file)
            val intent = Intent(requireActivity(), AddFragment::class.java)
            intent.data = uri
            startActivity(intent)
        }

    }

    @SuppressLint("SimpleDateFormat")
    @Throws(IOException::class)
    private fun createImageFile(): File? {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val imageFileName = "JPEG_" + timeStamp + "_"
        val storageDir =
            requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val image = File.createTempFile(
            imageFileName,  /* prefix */
            ".jpg",  /* suffix */
            storageDir /* directory */
        )
        imagePath = image.absolutePath
        return image
    }

}