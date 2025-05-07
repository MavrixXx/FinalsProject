package com.example.finalsproject

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.finalsproject.model.JournalNote
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID

class Journal_Activity : AppCompatActivity() {

    private lateinit var plusButton: ImageButton
    private lateinit var scrollFeed: LinearLayout
    private val journalNotes = mutableListOf<JournalNote>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.journal)

        plusButton = findViewById(R.id.notificationsImageButton)
        scrollFeed = findViewById(R.id.scrollFeed)

        setupNavigation()
        setupAddNoteButton()
        updateNotesDisplay()
    }

    private fun setupNavigation() {
        findViewById<ImageButton>(R.id.homeImageButton).setOnClickListener {
            finish()
            startActivity(android.content.Intent(this, Main_Dashboard_Activity::class.java))
        }

        findViewById<ImageButton>(R.id.exploreImageButton).setOnClickListener {
            // Navigate to explore
        }

        findViewById<ImageButton>(R.id.bookmarkImageButton).setOnClickListener {
            finish()
            startActivity(android.content.Intent(this, Bookmark_Activity::class.java))
        }

        findViewById<ImageButton>(R.id.userImageButton).setOnClickListener {
            finish()
            startActivity(android.content.Intent(this, Profile_Activity::class.java))
        }
    }

    private fun setupAddNoteButton() {
        plusButton.setOnClickListener {
            showAddNoteDialog()
        }
    }

    private fun showAddNoteDialog() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_note, null)
        val titleEdit = dialogView.findViewById<EditText>(R.id.editTextTitle)
        val contentEdit = dialogView.findViewById<EditText>(R.id.editTextContent)

        val dialog = AlertDialog.Builder(this)
            .setTitle("Add New Note")
            .setView(dialogView)
            .setPositiveButton("Save") { _, _ ->
                val title = titleEdit.text.toString()
                val content = contentEdit.text.toString()

                if (title.isNotEmpty() || content.isNotEmpty()) {
                    val note = JournalNote(
                        id = UUID.randomUUID().toString(),
                        title = title,
                        content = content,
                        timestamp = System.currentTimeMillis()
                    )
                    journalNotes.add(note)
                    updateNotesDisplay()
                }
            }
            .setNegativeButton("Cancel", null)
            .create()

        dialog.show()
    }

    private fun updateNotesDisplay() {
        // Clear all views and re-add them to ensure proper display
        scrollFeed.removeAllViews()

        // Add notes in reverse chronological order (newest first)
        for (note in journalNotes.sortedByDescending { it.timestamp }) {
            // Create a container for the note and its decorative elements
            val noteContainer = LinearLayout(this)
            noteContainer.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            noteContainer.orientation = LinearLayout.VERTICAL
            noteContainer.setPadding(0, 0, 0, 32)

            // First decorative rectangle - small accent bar
            val accentBar = View(this)
            accentBar.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                8
            ).apply {
                setMargins(40, 0, 40, 10)
            }
            accentBar.setBackgroundColor(resources.getColor(R.color.accent_color, null))
            noteContainer.addView(accentBar)

            // Add small decorative circle on the left
            val leftCircle = View(this)
            leftCircle.layoutParams = LinearLayout.LayoutParams(
                20,
                20
            ).apply {
                setMargins(40, 0, 0, 5)
            }
            leftCircle.background = resources.getDrawable(R.drawable.circle_background, null)
            noteContainer.addView(leftCircle)

            // Main note layout
            val noteLayout = RelativeLayout(this)
            noteLayout.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            noteLayout.setPadding(20, 20, 20, 20)
            noteLayout.setBackgroundResource(R.drawable.rounded_white_background)
            noteLayout.id = View.generateViewId()

            val titleTextView = TextView(this)
            titleTextView.text = note.title
            titleTextView.textSize = 16f
            titleTextView.setTextColor(resources.getColor(R.color.black, null))
            titleTextView.textAlignment = View.TEXT_ALIGNMENT_CENTER
            titleTextView.id = View.generateViewId()

            val dateTextView = TextView(this)
            val sdf = SimpleDateFormat("MMM dd, yyyy HH:mm", Locale.getDefault())
            dateTextView.text = sdf.format(Date(note.timestamp))
            dateTextView.textSize = 12f
            dateTextView.setTextColor(resources.getColor(R.color.black, null))
            dateTextView.id = View.generateViewId()

            val contentTextView = TextView(this)
            contentTextView.text = note.content
            contentTextView.textSize = 14f
            contentTextView.setTextColor(resources.getColor(R.color.black, null))
            contentTextView.id = View.generateViewId()

            val titleParams = RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
            )
            titleParams.addRule(RelativeLayout.CENTER_HORIZONTAL)

            val dateParams = RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
            )
            dateParams.addRule(RelativeLayout.CENTER_HORIZONTAL)
            dateParams.addRule(RelativeLayout.BELOW, titleTextView.id)
            dateParams.topMargin = 8

            val contentParams = RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
            )
            contentParams.addRule(RelativeLayout.BELOW, dateTextView.id)
            contentParams.topMargin = 16

            noteLayout.addView(titleTextView, titleParams)
            noteLayout.addView(dateTextView, dateParams)
            noteLayout.addView(contentTextView, contentParams)

            // Add main note layout to container
            noteContainer.addView(noteLayout)

            // Add small decorative circle on the right
            val rightCircle = View(this)
            rightCircle.layoutParams = LinearLayout.LayoutParams(
                20,
                20
            ).apply {
                gravity = android.view.Gravity.END
                setMargins(0, 5, 40, 0)
            }
            rightCircle.background = resources.getDrawable(R.drawable.circle_background, null)
            noteContainer.addView(rightCircle)

            // Second decorative rectangle - bottom accent
            val bottomAccent = View(this)
            bottomAccent.layoutParams = LinearLayout.LayoutParams(
                200,
                5
            ).apply {
                gravity = android.view.Gravity.CENTER_HORIZONTAL
                setMargins(0, 10, 0, 0)
            }
            bottomAccent.setBackgroundColor(resources.getColor(R.color.accent_color, null))
            noteContainer.addView(bottomAccent)

            // Add the entire container to the feed
            scrollFeed.addView(noteContainer)
        }
    }
}