package com.example.finalsproject

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.*
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.finalsproject.data.api.Session
import com.example.florasense.data.model.UserModel
import com.example.florasense.viewModel.UserViewModel
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.PUT
import retrofit2.Response
import java.io.IOException

class Edit_Profile_Activity : AppCompatActivity() {

    private var selectedImageUri: Uri? = null
    private val userViewModel: UserViewModel by viewModels()

    companion object {
        private const val IMAGE_REQUEST_CODE = 1001
        private const val CAMERA_REQUEST_CODE = 1002
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        var userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        val profileImage = findViewById<ImageView>(R.id.profileImageView)
        val usernameEditText = findViewById<EditText>(R.id.editUsername)
        val emailEditText = findViewById<EditText>(R.id.editEmail)
        val phoneEditText = findViewById<EditText>(R.id.editPhone)
        val addressEditText = findViewById<EditText>(R.id.editAddress)
        val passwordEditText = findViewById<EditText>(R.id.editPassword)
        val saveButton = findViewById<Button>(R.id.saveButton)

        initializedData()

        // Observe updates
        userViewModel.user.observe(this, Observer { updatedUser ->
            // Handle successful update (update UI or inform user)
            Toast.makeText(this, "Profile updated successfully!", Toast.LENGTH_SHORT).show()
            finish()  // Optional: Close the activity or return the updated data
        })

        userViewModel.updateError.observe(this, Observer { error ->
            // Handle error
            Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
        })

        // Handle profile image change
        profileImage.setOnClickListener { showImageSourceDialog() }

        // Handle save button click
        saveButton.setOnClickListener {
            showConfirmationDialog(
                usernameEditText, emailEditText, phoneEditText, addressEditText, passwordEditText
            )
        }
    }

    private fun showImageSourceDialog() {
        val options = arrayOf("Take a Picture", "Choose from Gallery", "Cancel")
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Select Profile Image")
        builder.setItems(options) { dialog, which ->
            when (which) {
                0 -> takePicture()
                1 -> chooseFromGallery()
                2 -> dialog.dismiss()
            }
        }
        builder.show()
    }

    private fun takePicture() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (intent.resolveActivity(packageManager) != null) {
            startActivityForResult(intent, CAMERA_REQUEST_CODE)
        }
    }

    private fun chooseFromGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            try {
                if (requestCode == IMAGE_REQUEST_CODE && data != null) {
                    selectedImageUri = data.data
                    updateProfileImage()
                } else if (requestCode == CAMERA_REQUEST_CODE && data != null) {
                    val imageBitmap = data.extras?.get("data") as? Bitmap
                    if (imageBitmap != null) {
                        selectedImageUri = getImageUriFromBitmap(imageBitmap)
                        updateProfileImage()
                    }
                }
            } catch (e: Exception) {
                Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                e.printStackTrace()
            }
        }
    }

    private fun getImageUriFromBitmap(bitmap: Bitmap): Uri {
        val path = MediaStore.Images.Media.insertImage(contentResolver, bitmap, "profile_image", null)
        return Uri.parse(path)
    }

    private fun updateProfileImage() {
        val profileImage = findViewById<ImageView>(R.id.profileImageView)
        val sizeInPx = dpToPx(150)

        selectedImageUri?.let { uri ->
            try {
                val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, uri)
                val croppedBitmap = getCroppedBitmap(bitmap, sizeInPx)
                val resizedBitmap = Bitmap.createScaledBitmap(croppedBitmap, sizeInPx, sizeInPx, false)
                profileImage.setImageBitmap(resizedBitmap)
                val croppedImageUri = getImageUriFromBitmap(resizedBitmap)
                selectedImageUri = croppedImageUri
            } catch (e: IOException) {
                Toast.makeText(this, "Error loading image: ${e.message}", Toast.LENGTH_SHORT).show()
                e.printStackTrace()
            }
        }
    }

    private fun getCroppedBitmap(bitmap: Bitmap, size: Int): Bitmap {
        val width = bitmap.width
        val height = bitmap.height
        val newEdge = minOf(width, height)
        val startX = (width - newEdge) / 2
        val startY = (height - newEdge) / 2
        val squareBitmap = Bitmap.createBitmap(bitmap, startX, startY, newEdge, newEdge)
        return Bitmap.createScaledBitmap(squareBitmap, size, size, true)
    }

    private fun dpToPx(dp: Int): Int {
        return (dp * resources.displayMetrics.density).toInt()
    }

    private fun showConfirmationDialog(
        usernameEditText: EditText,
        emailEditText: EditText,
        phoneEditText: EditText,
        addressEditText: EditText,
        passwordEditText: EditText
    ) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Confirm Changes")
        builder.setMessage("Are you sure you want to save the changes to your profile?")
        builder.setPositiveButton("Yes") { _, _ ->
            saveChanges(usernameEditText, emailEditText, phoneEditText, addressEditText, passwordEditText)
        }
        builder.setNegativeButton("No") { dialog, _ -> dialog.dismiss() }
        builder.create().show()
    }

    private fun saveChanges(

        usernameEditText: EditText,
        emailEditText: EditText,
        phoneEditText: EditText,
        addressEditText: EditText,
        passwordEditText: EditText
    ) {
        // Collect current data (from previous activity or intent)
        val currentUsername = intent.getStringExtra("USERNAME") ?: ""
        val currentEmail = intent.getStringExtra("EMAIL") ?: ""
        val currentPassword = intent.getStringExtra("PASSWORD") ?: ""

        // Gather updated data
        val updatedUsername = if (usernameEditText.text.isNullOrEmpty()) currentUsername else usernameEditText.text.toString()
        val updatedEmail = if (emailEditText.text.isNullOrEmpty()) currentEmail else emailEditText.text.toString()
        val updatedPhone = if (phoneEditText.text.isNullOrEmpty()) "Enter phone number" else phoneEditText.text.toString()
        val updatedAddress = if (addressEditText.text.isNullOrEmpty()) "Enter address" else addressEditText.text.toString()
        val updatedPassword = if (passwordEditText.text.isNullOrEmpty()) currentPassword else passwordEditText.text.toString()

        // Create user model for update
        val updatedUser = UserModel(
            username = updatedUsername,
            email = updatedEmail,
            password = updatedPassword,
            phone = updatedPhone,
            address = updatedAddress
        )

        // Trigger ViewModel to handle update logic
        userViewModel.updateUser(updatedUser)
    }

    private fun initializedData(){
        userViewModel.fetchUser(Session.userID)

        userViewModel.user.observe(this){
            val usernameEditText = findViewById<EditText>(R.id.editUsername)
            val emailEditText = findViewById<EditText>(R.id.editEmail)
            val phoneEditText = findViewById<EditText>(R.id.editPhone)
            val addressEditText = findViewById<EditText>(R.id.editAddress)
            val passwordEditText = findViewById<EditText>(R.id.editPassword)

            usernameEditText.setText(it?.username)
            emailEditText.setText(it?.email)
            phoneEditText.setText(it?.phone)
            addressEditText.setText(it?.address)
            passwordEditText.setText(it?.password)


        }

    }
}

