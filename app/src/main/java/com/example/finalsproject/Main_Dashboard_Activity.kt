package com.example.finalsproject

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

class Main_Dashboard_Activity : Activity() {

    private var bookmarkedPlants = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_dashboard)

        val moreInfo1 = findViewById<Button>(R.id.informationButtonSnakePlant)
        val moreInfo2 = findViewById<Button>(R.id.informationButtonMonsteraPlant)
        val moreInfo3 = findViewById<Button>(R.id.informationButtonFalseShamrock)
        val moreInfo4 = findViewById<Button>(R.id.informationButtonBarrelCactus)
        val moreInfo5 = findViewById<Button>(R.id.informationButtonArecaPalm)
        val moreInfo6 = findViewById<Button>(R.id.informationButtonSwordFern)

        val bookmarkButton1 = findViewById<Button>(R.id.bookmarkButtonSnakePlant)
        val bookmarkButton2 = findViewById<Button>(R.id.bookmarkButtonMonsteraPlant)
        val bookmarkButton3 = findViewById<Button>(R.id.bookmarkButtonFalseShamrock)
        val bookmarkButton4 = findViewById<Button>(R.id.bookmarkButtonBarrelCactus)
        val bookmarkButton5 = findViewById<Button>(R.id.bookmarkButtonArecaPalm)
        val bookmarkButton6 = findViewById<Button>(R.id.bookmarkButtonSwordFern)

        val plantImage1 = findViewById<ImageView>(R.id.snakePlantImage)
        val plantImage2 = findViewById<ImageView>(R.id.monsteraPlantImage)
        val plantImage3 = findViewById<ImageView>(R.id.falseShamrockImage)
        val plantImage4 = findViewById<ImageView>(R.id.barrelCactusImage)
        val plantImage5 = findViewById<ImageView>(R.id.arecaPalmImage)
        val plantImage6 = findViewById<ImageView>(R.id.swordFernImage)

        val bookmark = findViewById<ImageButton>(R.id.bookmarkImageButton)
        val profile = findViewById<ImageButton>(R.id.userImageButton)

        val username = intent.getStringExtra("USERNAME")
        val email = intent.getStringExtra("EMAIL")
        val password = intent.getStringExtra("PASSWORD")
        val newPassword = intent.getStringExtra("NEW_PASSWORD")
        bookmarkedPlants = intent.getStringArrayListExtra("bookmarked_plants") ?: ArrayList()

        val finalPassword = newPassword ?: password

        moreInfo1.setOnClickListener {
            showPlantInfo("Snake Plant", "The snake plant (Sansevieria), also known as mother-in-law's tongue, is a hardy, low-maintenance houseplant. It has long, upright leaves with striking green and yellow patterns. Known for its air-purifying qualities, it thrives in low light and requires minimal watering. This resilient plant is perfect for beginners and adds a touch of greenery to any space.")
        }
        moreInfo2.setOnClickListener {
            showPlantInfo("Monstera Plant", "Monstera, often called the \"Swiss cheese plant,\" is a tropical vine known for its large, glossy, split leaves. It's a popular choice for indoor spaces due to its striking appearance and easy care. Monstera thrives in bright, indirect light and needs regular watering. Its unique foliage adds a bold, exotic touch to any room.")
        }
        moreInfo3.setOnClickListener {
            showPlantInfo("False Shamrock", "The false shamrock (Oxalis triangularis) is a striking plant with deep purple, triangular leaves that resemble clovers. It produces small, delicate white or pink flowers and thrives in bright, indirect light. This plant is easy to care for, requiring moderate watering and well-drained soil. It's often used as an ornamental houseplant for its unique, vibrant foliage.")
        }
        moreInfo4.setOnClickListener {
            showPlantInfo("Barrel Cactus", "The barrel cactus is a spiny, round cactus known for its stout, cylindrical shape and vibrant yellow or red flowers. Native to arid desert regions, it can grow quite large and often features prominent ribs and sharp spines. Barrel cacti thrive in full sun and well-draining soil, requiring minimal water once established. They're a hardy, low-maintenance addition to desert gardens or indoor plant collections.")
        }
        moreInfo5.setOnClickListener {
            showPlantInfo("Areca Palm", "The areca palm (Dypsis lutescens) is a popular indoor plant with feathery, arching fronds that give it a graceful, tropical appearance. It thrives in bright, indirect light and requires regular watering, but it doesn’t like to sit in waterlogged soil. Known for its air-purifying qualities, it’s a great choice for adding a lush, green vibe to any space. The areca palm is also relatively easy to care for, making it a favorite among plant enthusiasts.")
        }
        moreInfo6.setOnClickListener {
            showPlantInfo("Sword Fern", "The sword fern (Nephrolepis exaltata) is a lush, evergreen fern with long, arching fronds that resemble swords. It's a low-maintenance plant that thrives in shaded or partially shaded areas and prefers moist, well-draining soil. Known for its air-purifying properties, the sword fern adds a touch of greenery and texture to indoor spaces. It’s also tolerant of a variety of conditions, making it a great choice for both beginners and seasoned plant lovers.")
        }

        plantImage1.setOnClickListener{
            showPlantImage("Snake Plant", R.drawable.snake_plant_image)
        }
        plantImage2.setOnClickListener{
            showPlantImage("Monstera Plant", R.drawable.monstera_plant_image)
        }
        plantImage3.setOnClickListener{
            showPlantImage("False Shamrock", R.drawable.false_shamrock_image)
        }
        plantImage4.setOnClickListener{
            showPlantImage("Barrel Cactus", R.drawable.barrel_cactus_image)
        }
        plantImage5.setOnClickListener{
            showPlantImage("Areca Palm", R.drawable.areca_plant_image)
        }
        plantImage6.setOnClickListener{
            showPlantImage("Sword Fern", R.drawable.sword_fern_image)
        }


        bookmarkButton1.setOnClickListener {
            toggleBookmark("Snake Plant")
        }
        bookmarkButton2.setOnClickListener {
            toggleBookmark("Monstera Plant")
        }
        bookmarkButton3.setOnClickListener {
            toggleBookmark("False Shamrock")
        }
        bookmarkButton4.setOnClickListener {
            toggleBookmark("Barrel Cactus")
        }
        bookmarkButton5.setOnClickListener {
            toggleBookmark("Areca Palm")
        }
        bookmarkButton6.setOnClickListener {
            toggleBookmark("Sword Fern")
        }

        bookmark.setOnClickListener {
            val bookmarkIntent = Intent(this, Bookmark_Activity::class.java)
            bookmarkIntent.putStringArrayListExtra("bookmarked_plants", ArrayList(bookmarkedPlants))
            bookmarkIntent.putExtra("USERNAME", username)
            bookmarkIntent.putExtra("EMAIL", email)
            bookmarkIntent.putExtra("PASSWORD", finalPassword)
            startActivity(bookmarkIntent)
        }

        profile.setOnClickListener {
            val profileIntent = Intent(this, Profile_Activity::class.java)
            profileIntent.putStringArrayListExtra("bookmarked_plants", ArrayList(bookmarkedPlants))
            profileIntent.putExtra("USERNAME", username)
            profileIntent.putExtra("EMAIL", email)
            profileIntent.putExtra("PASSWORD", finalPassword)
            startActivity(profileIntent)
        }
    }

    private fun showPlantInfo(plantName: String, plantDescription: String) {
        val dialogView = layoutInflater.inflate(R.layout.plant_info, null)
        val Name = dialogView.findViewById<TextView>(R.id.plantName)
        val Description = dialogView.findViewById<TextView>(R.id.plantDescription)

        Name.text = plantName
        Description.text = plantDescription

        val dialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .setCancelable(true)
            .create()

        dialog.show()
    }

    private fun showPlantImage(plantName: String, plantImage: Int) {
        val dialogView = layoutInflater.inflate(R.layout.plant_image, null)
        val Name = dialogView.findViewById<TextView>(R.id.plantName)
        val Image = dialogView.findViewById<ImageView>(R.id.plantImage)

        Name.text = plantName
        Image.setImageResource(plantImage)

        val dialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .setCancelable(true)
            .create()

        dialog.show()
    }

    private fun toggleBookmark(plant: String) {
        if (bookmarkedPlants.contains(plant)) {
            bookmarkedPlants.remove(plant)
            Toast.makeText(this, "${plant} removed from bookmarks", Toast.LENGTH_SHORT).show()
        } else {
            bookmarkedPlants.add(plant)
            Toast.makeText(this, "${plant} added to bookmarks", Toast.LENGTH_SHORT).show()
        }
    }
}
