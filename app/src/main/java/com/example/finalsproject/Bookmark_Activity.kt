    package com.example.finalsproject

    import android.app.Activity
    import android.app.AlertDialog
    import android.content.Intent
    import android.os.Bundle
    import android.view.View
    import android.widget.ArrayAdapter
    import android.widget.ImageButton
    import android.widget.ListView
    import android.widget.TextView

        class Bookmark_Activity : Activity() {

            private lateinit var bookmarkListView: ListView
            private lateinit var emptyMessageTextView: TextView
            private lateinit var bookmarkedPlants: ArrayList<String>
            private lateinit var adapter: ArrayAdapter<String>

            override fun onCreate(savedInstanceState: Bundle?) {
                super.onCreate(savedInstanceState)
                setContentView(R.layout.activity_bookmark)

                bookmarkListView = findViewById(R.id.bookmarkList)
                emptyMessageTextView = findViewById(R.id.emptyMessageText)
                bookmarkedPlants = intent.getStringArrayListExtra("bookmarked_plants") ?: ArrayList()
                adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, bookmarkedPlants)
                bookmarkListView.adapter = adapter

                val backButton = findViewById<ImageButton>(R.id.backImageButton)
                val homeButton = findViewById<ImageButton>(R.id.homeImageButton)
                val profileButton = findViewById<ImageButton>(R.id.userImageButton)
                val journalButton = findViewById<ImageButton>(R.id.journalImageButton)

                val username = intent.getStringExtra("USERNAME")
                val email = intent.getStringExtra("EMAIL")
                val phone = intent.getStringExtra("PHONE")
                val address = intent.getStringExtra("ADDRESS")
                val password = intent.getStringExtra("PASSWORD")
                val newPassword = intent.getStringExtra("NEW_PASSWORD")
                val finalPassword = newPassword ?: password

                if (bookmarkedPlants.isEmpty()) {
                    emptyMessageTextView.visibility = View.VISIBLE
                    bookmarkListView.visibility = View.GONE
                } else {
                    emptyMessageTextView.visibility = View.GONE
                    bookmarkListView.visibility = View.VISIBLE
                }

                bookmarkListView.setOnItemLongClickListener { _, _, position, _ ->
                    val plantName = bookmarkedPlants[position]
                    val dialog = AlertDialog.Builder(this)
                        .setTitle("Remove Plant")
                        .setMessage("Do you want to remove $plantName from your bookmarks?")
                        .setPositiveButton("Yes") { _, _ ->
                            bookmarkedPlants.removeAt(position)
                            adapter.notifyDataSetChanged()
                            if (bookmarkedPlants.isEmpty()) {
                                emptyMessageTextView.visibility = View.VISIBLE
                                bookmarkListView.visibility = View.GONE
                            }
                        }
                        .setNegativeButton("No", null)
                        .create()
                    dialog.show()
                    true
                }

                bookmarkListView.setOnItemClickListener { _, _, position, _ ->
                    val plantName = bookmarkedPlants[position]

                    val dialogView = layoutInflater.inflate(R.layout.activity_plant_detail, null)
                    val dialogTitle = dialogView.findViewById<TextView>(R.id.plantNameTextView)
                    val dialogDescription = dialogView.findViewById<TextView>(R.id.plantInfoTextView)

                    dialogTitle.text = "Details for $plantName"
                    dialogDescription.text = "Description for $plantName goes here."

                    val dialog = AlertDialog.Builder(this)
                        .setView(dialogView)
                        .setPositiveButton("OK", null)
                        .create()
                    dialog.show()
                }

                backButton.setOnClickListener {
                    val backIntent = Intent(this, Main_Dashboard_Activity::class.java)
                    backIntent.putStringArrayListExtra("bookmarked_plants", ArrayList(bookmarkedPlants))
                    backIntent.putExtra("USERNAME", username)
                    backIntent.putExtra("EMAIL", email)
                    backIntent.putExtra("PHONE", phone)
                    backIntent.putExtra("ADDRESS", address)
                    backIntent.putExtra("PASSWORD", finalPassword)
                    startActivity(backIntent)
                }

                homeButton.setOnClickListener {
                    val homeIntent = Intent(this, Main_Dashboard_Activity::class.java)
                    homeIntent.putStringArrayListExtra("bookmarked_plants", ArrayList(bookmarkedPlants))
                    homeIntent.putExtra("USERNAME", username)
                    homeIntent.putExtra("EMAIL", email)
                    homeIntent.putExtra("PHONE", phone)
                    homeIntent.putExtra("ADDRESS", address)
                    homeIntent.putExtra("PASSWORD", finalPassword)
                    startActivity(homeIntent)
                }

                profileButton.setOnClickListener {
                    val profileIntent = Intent(this, Profile_Activity::class.java)
                    profileIntent.putStringArrayListExtra("bookmarked_plants", ArrayList(bookmarkedPlants))
                    profileIntent.putExtra("USERNAME", username)
                    profileIntent.putExtra("EMAIL", email)
                    profileIntent.putExtra("PHONE", phone)
                    profileIntent.putExtra("ADDRESS", address)
                    profileIntent.putExtra("PASSWORD", finalPassword)
                    startActivity(profileIntent)
                }

                journalButton.setOnClickListener {
                    val journalIntent = Intent(this, Journal_Activity::class.java)
                    journalIntent.putStringArrayListExtra("bookmarked_plants", ArrayList(bookmarkedPlants))
                    journalIntent.putExtra("USERNAME", username)
                    journalIntent.putExtra("EMAIL", email)
                    journalIntent.putExtra("PHONE", phone)
                    journalIntent.putExtra("ADDRESS", address)
                    journalIntent.putExtra("PASSWORD", finalPassword)
                    startActivity(journalIntent)
                }


            }
        }

