package com.example.finalsproject

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
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
        val moreInfo7 = findViewById<Button>(R.id.informationButtonYuccaPlant)
        val moreInfo8 = findViewById<Button>(R.id.informationButtonAloeVera)
        val moreInfo9 = findViewById<Button>(R.id.informationButtonPaintersPalette)
        val moreInfo10 = findViewById<Button>(R.id.informationButtonBunnyEarsCactus)

        val bookmarkButton1 = findViewById<Button>(R.id.bookmarkButtonSnakePlant)
        val bookmarkButton2 = findViewById<Button>(R.id.bookmarkButtonMonsteraPlant)
        val bookmarkButton3 = findViewById<Button>(R.id.bookmarkButtonFalseShamrock)
        val bookmarkButton4 = findViewById<Button>(R.id.bookmarkButtonBarrelCactus)
        val bookmarkButton5 = findViewById<Button>(R.id.bookmarkButtonArecaPalm)
        val bookmarkButton6 = findViewById<Button>(R.id.bookmarkButtonSwordFern)
        val bookmarkButton7 = findViewById<Button>(R.id.bookmarkButtonYuccaPlant)
        val bookmarkButton8 = findViewById<Button>(R.id.bookmarkButtonAloeVera)
        val bookmarkButton9 = findViewById<Button>(R.id.bookmarkButtonPaintersPalette)
        val bookmarkButton10 = findViewById<Button>(R.id.bookmarkButtonBunnyEarsCactus)

        val plantImage1 = findViewById<ImageView>(R.id.snakePlantImage)
        val plantImage2 = findViewById<ImageView>(R.id.monsteraPlantImage)
        val plantImage3 = findViewById<ImageView>(R.id.falseShamrockImage)
        val plantImage4 = findViewById<ImageView>(R.id.barrelCactusImage)
        val plantImage5 = findViewById<ImageView>(R.id.arecaPalmImage)
        val plantImage6 = findViewById<ImageView>(R.id.swordFernImage)
        val plantImage7 = findViewById<ImageView>(R.id.yuccaPlantImage)
        val plantImage8 = findViewById<ImageView>(R.id.aloeVeraImage)
        val plantImage9 = findViewById<ImageView>(R.id.paintersPaletteImage)
        val plantImage10 = findViewById<ImageView>(R.id.bunnyEarsCactusImage)

        val bookmark = findViewById<ImageButton>(R.id.bookmarkImageButton)
        val profile = findViewById<ImageButton>(R.id.userImageButton)
        val journal = findViewById<ImageButton>(R.id.journalImageButton)
        val explore = findViewById<ImageButton>(R.id.exploreImageButton)

        val username = intent.getStringExtra("USERNAME")
        val email = intent.getStringExtra("EMAIL")
        val phone = intent.getStringExtra("PHONE")
        val address = intent.getStringExtra("ADDRESS")
        val password = intent.getStringExtra("PASSWORD")

        Log.d("DEBUG_DATA", "Phone: $phone")
        Log.d("DEBUG_DATA", "Address: $address")
        Log.d("DEBUG_DATA", "Username: $username")


        bookmarkedPlants = intent.getStringArrayListExtra("bookmarked_plants") ?: ArrayList()

        moreInfo1.setOnClickListener {
            showPlantInfo("Snake Plant", "Size: 6 to 20 inches tall\n" +
                    "\n" +
                    "Water: Allow the top inch of soil to dry completely before watering. Snake plants store water, so it's almost impossible to underwater them.\n" +
                    "\n" +
                    "Fertilize: Not necessary, but an occasional dose of all-purpose houseplant food during the spring and summer will encourage more vigorous growth.\n" +
                    "\n" +
                    "Temperature: Prefers temperatures between 60°F to 85°F (15°C to 29°C). It can tolerate a range of conditions, but avoid temperatures below 50°F (10°C). \n" +
                    "\n" +
                    "Tip: Avoid overwatering as this can lead to root rot. Be sure to let the soil dry out before giving it more water!")
        }
        moreInfo2.setOnClickListener {
            showPlantInfo("Monstera Plant", "Size: 2 to 3 feet tall (indoor growth), can grow up to 10 feet in its native environment\n" +
                    "\n" +
                    "Water: Water when the top 1-2 inches of soil are dry. Monstera plants like to dry out a bit between waterings.\n" +
                    "\n" +
                    "Fertilize: Feed with a balanced, water-soluble fertilizer once a month during the growing season (spring and summer).\n" +
                    "\n" +
                    "Temperature: Prefers temperatures between 65°F to 85°F (18°C to 29°C). Avoid temperatures below 50°F (10°C). \n" +
                    "\n" +
                    "Tip: Monstera plants love humidity, so occasional misting or placing them in a humid room can help them thrive!")
        }
        moreInfo3.setOnClickListener {
            showPlantInfo("False Shamrock", "Size: 6 to 12 inches tall\n" +
                    "\n" +
                    "Water: Keep the soil consistently moist but not soggy. Water when the top inch of soil feels dry.\n" +
                    "\n" +
                    "Fertilize: Feed with a balanced liquid fertilizer every 4-6 weeks during the growing season (spring and summer).\n" +
                    "\n" +
                    "Temperature: Prefers temperatures between 60°F to 75°F (15°C to 24°C). Avoid exposure to cold drafts or temperatures below 50°F (10°C).\n" +
                    "\n" +
                    "Tip: False shamrocks go dormant during the winter, so reduce watering during this time and let the plant rest.")
        }
        moreInfo4.setOnClickListener {
            showPlantInfo("Barrel Cactus", "Size: 2 to 10 feet tall, depending on species\n" +
                    "\n" +
                    "Water: Water sparingly; allow the soil to dry out completely between waterings. In winter, water even less.\n" +
                    "\n" +
                    "Fertilize: Feed with a diluted cactus fertilizer once during the growing season (spring to early summer).\n" +
                    "\n" +
                    "Temperature: Prefers temperatures between 70°F to 100°F (21°C to 38°C). Tolerates high heat but avoid frost.\n" +
                    "\n" +
                    "Tip: Barrel cacti need plenty of sunlight, so place them in a bright, sunny spot for best growth.")
        }
        moreInfo5.setOnClickListener {
            showPlantInfo("Areca Palm", "Size: 6 to 8 feet tall when grown indoors\n" +
                    "\n" +
                    "Water: Keep the soil lightly moist but not soggy. Water when the top inch of soil feels dry.\n" +
                    "\n" +
                    "Fertilize: Feed with a balanced, slow-release fertilizer during the growing season (spring to summer) every 2-3 months.\n" +
                    "\n" +
                    "Temperature: Prefers temperatures between 65°F to 75°F (18°C to 24°C). Avoid temperatures below 50°F (10°C).\n" +
                    "\n" +
                    "Tip: Areca palms thrive in bright, indirect light. Too much direct sunlight can scorch the leaves.")
        }
        moreInfo6.setOnClickListener {
            showPlantInfo("Sword Fern", "Size: 2 to 3 feet tall and wide\n" +
                    "\n" +
                    "Water: Keep the soil consistently moist, but not soggy. Water when the top inch of soil feels dry.\n" +
                    "\n" +
                    "Fertilize: Feed with a balanced, water-soluble fertilizer once a month during the growing season (spring to summer).\n" +
                    "\n" +
                    "Temperature: Prefers temperatures between 60°F to 75°F (16°C to 24°C). It thrives in cool to moderate temperatures.\n" +
                    "\n" +
                    "Tip: Sword ferns love humidity, so regular misting or placing them in a humid environment will help them thrive.")
        }
        moreInfo7.setOnClickListener {
            showPlantInfo("Yucca Plant", "Size: 2 to 8 feet tall, depending on variety\n" +
                    "\n" +
                    "Water: Allow the top few inches of soil to dry out before watering. Yuccas are drought-tolerant and prefer to be underwatered rather than overwatered.\n" +
                    "\n" +
                    "Fertilize: Feed with a balanced, slow-release fertilizer once during the growing season (spring to summer).\n" +
                    "\n" +
                    "Temperature: Prefers temperatures between 60°F to 80°F (16°C to 27°C). Avoid temperatures below 50°F (10°C).\n" +
                    "\n" +
                    "Tip: Yuccas thrive in bright, direct sunlight. A sunny window or outdoor spot is ideal for their growth.")

        }
        moreInfo8.setOnClickListener {
            showPlantInfo("Aloe Vera", "Size: 1 to 2 feet tall\n" +
                    "\n" +
                    "Water: Allow the soil to dry out completely between waterings. Water sparingly, especially in the winter months.\n" +
                    "\n" +
                    "Fertilize: Feed with a diluted cactus or succulent fertilizer once or twice during the growing season (spring and summer).\n" +
                    "\n" +
                    "Temperature: Prefers temperatures between 59°F to 77°F (15°C to 25°C). Avoid frost and temperatures below 50°F (10°C).\n" +
                    "\n" +
                    "Tip: Aloe vera loves bright, indirect light. Too much direct sunlight can cause the leaves to turn brown or yellow.")
        }
        moreInfo9.setOnClickListener {
            showPlantInfo("Painter's-palette", "Size: 12 to 18 inches tall and wide\n" +
                    "\n" +
                    "Water: Water when the top inch of soil feels dry. Avoid letting the soil stay soggy, as this can lead to root rot.\n" +
                    "\n" +
                    "Fertilize: Feed with a balanced liquid fertilizer once a month during the growing season (spring and summer).\n" +
                    "\n" +
                    "Temperature: Prefers temperatures between 60°F to 80°F (15°C to 27°C). Avoid temperatures below 50°F (10°C).\n" +
                    "\n" +
                    "Tip: Painter's palette plants thrive in bright, indirect light. They can tolerate some direct sunlight but may scorch in full sun.")
        }
        moreInfo10.setOnClickListener {
            showPlantInfo("Bunny Ears Cactus", "Size: 2 to 3 feet tall\n" +
                    "\n" +
                    "Water: Water sparingly, allowing the soil to dry out completely between waterings. In the winter, reduce watering further.\n" +
                    "\n" +
                    "Fertilize: Feed with a diluted cactus fertilizer once a month during the growing season (spring to summer).\n" +
                    "\n" +
                    "Temperature: Prefers temperatures between 70°F to 100°F (21°C to 38°C). Avoid temperatures below 50°F (10°C).\n" +
                    "\n" +
                    "Tip: Bunny ears cactus loves bright, direct sunlight. Be cautious of sunburn if they are exposed to too much direct sunlight initially.")
        }

        plantImage1.setOnClickListener{
            showPlantImage("Snake Plant", R.drawable.snake_plant_image, "The snake plant (Sansevieria), also known as mother-in-law's tongue, is a hardy, low-maintenance houseplant. It has long, upright leaves with striking green and yellow patterns. Known for its air-purifying qualities, it thrives in low light and requires minimal watering. This resilient plant is perfect for beginners and adds a touch of greenery to any space.")
        }
        plantImage2.setOnClickListener{
            showPlantImage("Monstera Plant", R.drawable.monstera_plant_image, "Monstera, often called the \"Swiss cheese plant,\" is a tropical vine known for its large, glossy, split leaves. It's a popular choice for indoor spaces due to its striking appearance and easy care. Monstera thrives in bright, indirect light and needs regular watering. Its unique foliage adds a bold, exotic touch to any room.")
        }
        plantImage3.setOnClickListener{
            showPlantImage("False Shamrock", R.drawable.false_shamrock_image, "The false shamrock (Oxalis triangularis) is a striking plant with deep purple, triangular leaves that resemble clovers. It produces small, delicate white or pink flowers and thrives in bright, indirect light. This plant is easy to care for, requiring moderate watering and well-drained soil. It's often used as an ornamental houseplant for its unique, vibrant foliage.")
        }
        plantImage4.setOnClickListener{
            showPlantImage("Barrel Cactus", R.drawable.barrel_cactus_image, "The barrel cactus is a spiny, round cactus known for its stout, cylindrical shape and vibrant yellow or red flowers. Native to arid desert regions, it can grow quite large and often features prominent ribs and sharp spines. Barrel cacti thrive in full sun and well-draining soil, requiring minimal water once established. They're a hardy, low-maintenance addition to desert gardens or indoor plant collections.")
        }
        plantImage5.setOnClickListener{
            showPlantImage("Areca Palm", R.drawable.areca_plant_image, "The areca palm (Dypsis lutescens) is a popular indoor plant with feathery, arching fronds that give it a graceful, tropical appearance. It thrives in bright, indirect light and requires regular watering, but it doesn’t like to sit in waterlogged soil. Known for its air-purifying qualities, it’s a great choice for adding a lush, green vibe to any space. The areca palm is also relatively easy to care for, making it a favorite among plant enthusiasts.")
        }
        plantImage6.setOnClickListener{
            showPlantImage("Sword Fern", R.drawable.sword_fern_image, "The sword fern (Nephrolepis exaltata) is a lush, evergreen fern with long, arching fronds that resemble swords. It's a low-maintenance plant that thrives in shaded or partially shaded areas and prefers moist, well-draining soil. Known for its air-purifying properties, the sword fern adds a touch of greenery and texture to indoor spaces. It’s also tolerant of a variety of conditions, making it a great choice for both beginners and seasoned plant lovers.")
        }
        plantImage7.setOnClickListener{
            showPlantImage("Yucca Plant", R.drawable.yucca_plant_image, "The Yucca plant is a hardy, spiky perennial with long, sword-like leaves that grow in rosettes. It produces tall flower spikes with creamy white, bell-shaped blooms. Native to arid regions, it thrives in dry, sunny conditions and well-drained soil. Known for its drought tolerance and low maintenance, yucca is often used in desert landscapes.")
        }
        plantImage8.setOnClickListener{
            showPlantImage("Aloe Vera", R.drawable.aloe_vera_image, "Aloe vera is a succulent plant known for its thick, fleshy leaves that store water. These leaves are typically green to grey-green, and when cut, they release a soothing gel used for its medicinal properties, especially in treating burns, skin irritation, and wounds. Aloe vera thrives in warm, dry climates and prefers well-drained soil. It’s low-maintenance, drought-tolerant, and often grown both for its health benefits and as an ornamental plant in gardens or indoors.")
        }
        plantImage9.setOnClickListener{
            showPlantImage("Painter's-palette", R.drawable.painters_palette_image, "The Painter’s Palette (scientifically known as Castilleja coccinea) is a striking wildflower native to North America. It gets its name from its vibrant, multi-colored bracts that resemble the bright hues of a painter’s palette. The plant typically features red, orange, and yellow blooms, with the color variation giving it a bold, eye-catching appearance. It has narrow, lance-shaped leaves and grows in clusters, often in meadows, prairies, or along roadsides. While it's not a true “flower” in the traditional sense, as the colorful bracts are modified leaves, the plant is known for attracting pollinators like bees and hummingbirds. Painter’s Palette thrives in well-drained soil and full sunlight.")
        }
        plantImage10.setOnClickListener{
            showPlantImage("Bunny Ears Cactus", R.drawable.bunny_ears_cactus, "The Bunny Ears Cactus (*Opuntia microdasys*) is a small, distinctive cactus known for its flat, oval pads that resemble bunny ears. These pads are covered with tiny, soft spines called *glochids*, which can be irritating to the skin if touched. The cactus typically has a greenish or yellowish hue, and it produces vibrant yellow or reddish-pink flowers in the spring, followed by small, purple fruits. Native to Mexico, this cactus is a low-maintenance plant that thrives in sunny, dry environments and well-drained soil, making it an ideal choice for xeriscaping or as a decorative houseplant in warmer climates.")
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
        bookmarkButton7.setOnClickListener {
            toggleBookmark("Yucca Plant")
        }
        bookmarkButton8.setOnClickListener {
            toggleBookmark("Aloe Vera")
        }
        bookmarkButton9.setOnClickListener {
            toggleBookmark("Painter's-palette")
        }
        bookmarkButton10.setOnClickListener {
            toggleBookmark("Bunny Ears Cactus")
        }

        bookmark.setOnClickListener {
            val bookmarkIntent = Intent(this, Bookmark_Activity::class.java)
            bookmarkIntent.putStringArrayListExtra("bookmarked_plants", ArrayList(bookmarkedPlants))
            bookmarkIntent.putExtra("USERNAME", username)
            bookmarkIntent.putExtra("EMAIL", email)
            bookmarkIntent.putExtra("PASSWORD", password)
            startActivity(bookmarkIntent)
        }

        profile.setOnClickListener {
            val profileIntent = Intent(this, Profile_Activity::class.java)
            profileIntent.putStringArrayListExtra("bookmarked_plants", ArrayList(bookmarkedPlants))
            profileIntent.putExtra("USERNAME", username)
            profileIntent.putExtra("EMAIL", email)
            profileIntent.putExtra("PHONE", phone)
            profileIntent.putExtra("ADDRESS", address)
            profileIntent.putExtra("PASSWORD", password)
            startActivity(profileIntent)
        }

        journal.setOnClickListener {
            val journalIntent = Intent(this, Journal_Activity::class.java)
            journalIntent.putStringArrayListExtra("bookmarked_plants", ArrayList(bookmarkedPlants))
            journalIntent.putExtra("USERNAME", username)
            journalIntent.putExtra("EMAIL", email)
            journalIntent.putExtra("PASSWORD", password)
            startActivity(journalIntent)
        }
    }

    private fun showPlantInfo(plantName: String, plantInfo: String) {
        val dialogView = layoutInflater.inflate(R.layout.plant_info, null)
        val Name = dialogView.findViewById<TextView>(R.id.plantName)
        val Info = dialogView.findViewById<TextView>(R.id.plantInfo)

        Name.text = plantName
        Info.text = plantInfo

        val dialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .setCancelable(true)
            .create()

        dialog.show()
    }

    private fun showPlantImage(plantName: String, plantImage: Int, plantDescription: String) {
        val dialogView = layoutInflater.inflate(R.layout.plant_image, null)
        val Name = dialogView.findViewById<TextView>(R.id.plantName)
        val Image = dialogView.findViewById<ImageView>(R.id.plantImage)
        val Description = dialogView.findViewById<TextView>(R.id.plantDescription)

        Name.text = plantName
        Image.setImageResource(plantImage)
        Description.text = plantDescription

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
