package com.example.finalsproject

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ImageButton
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.viewModels
import com.example.finalsproject.viewModel.BookmarkViewModel

class Bookmark_Activity : AppCompatActivity() {

    private val bookmarkViewModel: BookmarkViewModel by viewModels()
    private lateinit var bookmarkListView: ListView
    private lateinit var emptyMessageTextView: TextView
    private lateinit var bookmarkedPlants: ArrayList<String>
    private lateinit var adapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bookmark)

        // Initialize views
        bookmarkListView = findViewById(R.id.bookmarkList)
        emptyMessageTextView = findViewById(R.id.emptyMessageText)
        bookmarkedPlants = ArrayList()
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, bookmarkedPlants)
        bookmarkListView.adapter = adapter

        // Observe bookmarks from ViewModel
        bookmarkViewModel.bookmarks.observe(this) { bookmarks ->
            bookmarkedPlants.clear()
            if (!bookmarks.isNullOrEmpty()) {
                bookmarkedPlants.addAll(bookmarks.map { it.plantName })
                adapter.notifyDataSetChanged()
                emptyMessageTextView.visibility = View.GONE
                bookmarkListView.visibility = View.VISIBLE
            } else {
                emptyMessageTextView.visibility = View.VISIBLE
                bookmarkListView.visibility = View.GONE
            }
        }

        // Load bookmarks
        bookmarkViewModel.getBookmarks()

        // Get user data from Intent
        val username = intent.getStringExtra("USERNAME")
        val email = intent.getStringExtra("EMAIL")
        val phone = intent.getStringExtra("PHONE")
        val address = intent.getStringExtra("ADDRESS")
        val password = intent.getStringExtra("PASSWORD")
        val newPassword = intent.getStringExtra("NEW_PASSWORD")
        val finalPassword = newPassword ?: password

        // Long press to remove bookmark
        bookmarkListView.setOnItemLongClickListener { _, _, position, _ ->
            val plantName = bookmarkedPlants[position]
            AlertDialog.Builder(this)
                .setTitle("Remove Plant")
                .setMessage("Do you want to remove $plantName from your bookmarks?")
                .setPositiveButton("Yes") { _, _ ->
                    bookmarkedPlants.removeAt(position)
                    adapter.notifyDataSetChanged()

                    // Optional: also remove from ViewModel/database
                    // bookmarkViewModel.deleteBookmark(plantName)

                    if (bookmarkedPlants.isEmpty()) {
                        emptyMessageTextView.visibility = View.VISIBLE
                        bookmarkListView.visibility = View.GONE
                    }
                }
                .setNegativeButton("No", null)
                .create()
                .show()
            true
        }

        // Click to view plant details
        bookmarkListView.setOnItemClickListener { _, _, position, _ ->
            val plantName = bookmarkedPlants[position]
            val dialogView = layoutInflater.inflate(R.layout.activity_plant_detail, null)
            val dialogTitle = dialogView.findViewById<TextView>(R.id.plantNameTextView)
            val dialogDescription = dialogView.findViewById<TextView>(R.id.plantInfoTextView)

            dialogTitle.text = "Details for $plantName"
            dialogDescription.text = "Description for $plantName goes here."

            AlertDialog.Builder(this)
                .setView(dialogView)
                .setPositiveButton("OK", null)
                .create()
                .show()
        }

        // Navigation buttons
        findViewById<ImageButton>(R.id.backImageButton).setOnClickListener {
            navigateTo(Main_Dashboard_Activity::class.java, username, email, phone, address, finalPassword, bookmarkedPlants)
        }

        findViewById<ImageButton>(R.id.homeImageButton).setOnClickListener {
            navigateTo(Main_Dashboard_Activity::class.java, username, email, phone, address, finalPassword, bookmarkedPlants)
        }

        findViewById<ImageButton>(R.id.userImageButton).setOnClickListener {
            navigateTo(Profile_Activity::class.java, username, email, phone, address, finalPassword, bookmarkedPlants)
        }

        findViewById<ImageButton>(R.id.journalImageButton).setOnClickListener {
            navigateTo(Journal_Activity::class.java, username, email, phone, address, finalPassword, bookmarkedPlants)
        }
    }

    // Function to reduce repeated navigation code
    private fun navigateTo(
        target: Class<*>,
        username: String?,
        email: String?,
        phone: String?,
        address: String?,
        password: String?,
        bookmarkedPlants: ArrayList<String>
    ) {
        val intent = Intent(this, target)
        intent.putStringArrayListExtra("bookmarked_plants", bookmarkedPlants)
        intent.putExtra("USERNAME", username)
        intent.putExtra("EMAIL", email)
        intent.putExtra("PHONE", phone)
        intent.putExtra("ADDRESS", address)
        intent.putExtra("PASSWORD", password)
        startActivity(intent)
    }
}
