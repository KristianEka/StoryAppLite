package com.ekachandra.storyapplite.ui.camera

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.ekachandra.storyapplite.R
import com.ekachandra.storyapplite.databinding.ActivityAddStoryBinding
import com.ekachandra.storyapplite.ui.ViewModelFactory
import com.ekachandra.storyapplite.ui.main.MainActivity
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

class AddStoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddStoryBinding
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var getFile: File? = null
    private var getLat: Float? = null
    private var getLon: Float? = null

    private val viewModel by viewModels<CameraViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        putImage()

        binding.buttonAdd.setOnClickListener {
            if (binding.edAddDescription.text.isNotEmpty()) {
                observeToken()
                observeIndicator()
            } else {
                showToast(getString(R.string.fill_out_all_the_field))
            }
        }

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        binding.buttonLocation.setOnClickListener {
            getMyLastLocation()
        }
    }

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        when {
            permissions[Manifest.permission.ACCESS_FINE_LOCATION] ?: false -> {
                getMyLastLocation()
            }

            permissions[Manifest.permission.ACCESS_COARSE_LOCATION] ?: false -> {
                getMyLastLocation()
            }

            else -> {}
        }
    }

    private fun checkPermission(permission: String): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            permission,
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun getMyLastLocation() {
        if (checkPermission(Manifest.permission.ACCESS_FINE_LOCATION) && checkPermission(Manifest.permission.ACCESS_COARSE_LOCATION)) {
            fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                if (location != null) {
                    addMyLocation(location)
                } else {
                    showToast(getString(R.string.loc_not_found))
                }
            }
        } else {
            requestPermissionLauncher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        }
    }

    private fun addMyLocation(location: Location) {
        binding.buttonLocation.text = getString(
            R.string.my_loc_button,
            location.latitude.toString(),
            location.longitude.toString()
        )
        getLat = location.latitude.toFloat()
        getLon = location.longitude.toFloat()
    }

    private fun putImage() {
        val myFile = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getSerializableExtra(PICTURE, File::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getSerializableExtra(PICTURE)
        } as File

        val isBackCamera = intent.getBooleanExtra(IS_BACK_CAMERA, true)
        val isGallery = intent.getBooleanExtra(IS_GALLERY, false)

        myFile.let { file ->
            if (!isGallery) {
                rotateFile(file, isBackCamera)
            }
            binding.imgAddStory.setImageBitmap(BitmapFactory.decodeFile(file.path))
            getFile = file
        }
    }

    private fun uploadImage(token: String) {
        if (getFile != null) {
            val file = reduceFileImage(getFile as File)

            val description =
                binding.edAddDescription.text.toString().toRequestBody("text/plain".toMediaType())
            val requestImageFile = file.asRequestBody("image/jpeg".toMediaType())
            val imageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData(
                "photo",
                file.name,
                requestImageFile
            )

            viewModel.postStory(token, imageMultipart, description, getLat, getLon)
            startActivity(Intent(this, MainActivity::class.java))
            finishAffinity()

        } else {
            showToast(getString(R.string.no_image_notification))
        }
    }


    private fun observeToken() {
        viewModel.getToken().observe(this@AddStoryActivity) { token ->
            uploadImage(token)
        }

    }

    private fun observeIndicator() {
        viewModel.apply {
            isLoading().observe(this@AddStoryActivity) { isLoading ->
                if (isLoading) {
                    binding.progressBar.visibility = View.VISIBLE
                } else {
                    binding.progressBar.visibility = View.GONE
                }
            }

            isError().observe(this@AddStoryActivity) { isError ->
                if (isError) {
                    showToast(getString(R.string.error))
                }
            }

            message().observe(this@AddStoryActivity) { message ->
                showToast(message.message)
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this@AddStoryActivity, message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        const val PICTURE = "picture"
        const val IS_BACK_CAMERA = "back_camera"
        const val IS_GALLERY = "gallery"
    }
}