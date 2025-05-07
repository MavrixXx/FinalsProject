package com.example.finalsproject

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.finalsproject.model.JournalNote
import com.example.finalsproject.model.TodoItem
import com.example.finalsproject.model.Subtask
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID

class Journal_Activity : AppCompatActivity() {

    private lateinit var plusButton: ImageButton
    private lateinit var deleteButton: ImageButton
    private lateinit var scrollFeed: LinearLayout
    private val journalNotes = mutableListOf<JournalNote>()
    private val todoItems = mutableListOf<TodoItem>()

    // Track selected items
    private val selectedItems = mutableSetOf<String>()
    private var isSelectionMode = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.journal)

        plusButton = findViewById(R.id.notificationsImageButton)
        deleteButton = findViewById(R.id.deleteImageButton)
        scrollFeed = findViewById(R.id.scrollFeed)

        setupNavigation()
        setupAddNoteButton()
        setupDeleteButton()
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

    private fun setupDeleteButton() {
        deleteButton.setOnClickListener {
            if (selectedItems.isEmpty()) {
                Toast.makeText(this, "No items selected", Toast.LENGTH_SHORT).show()
            } else {
                AlertDialog.Builder(this)
                    .setTitle("Delete Selected Items")
                    .setMessage("Are you sure you want to delete the selected items?")
                    .setPositiveButton("Delete") { _, _ ->
                        deleteSelectedItems()
                    }
                    .setNegativeButton("Cancel", null)
                    .show()
            }
        }
    }

    private fun deleteSelectedItems() {
        // Remove selected notes
        journalNotes.removeAll { selectedItems.contains(it.id) }

        // Remove selected todo items
        todoItems.removeAll { selectedItems.contains(it.id) }

        // Clear selection and update display
        selectedItems.clear()
        isSelectionMode = false
        updateNotesDisplay()

        Toast.makeText(this, "Items deleted", Toast.LENGTH_SHORT).show()
    }

    private fun showAddNoteDialog() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_journal_item, null)
        val titleEdit = dialogView.findViewById<EditText>(R.id.editTextTitle)
        val contentEdit = dialogView.findViewById<EditText>(R.id.editTextContent)
        val radioGroup = dialogView.findViewById<RadioGroup>(R.id.radioGroupType)
        val radioNote = dialogView.findViewById<RadioButton>(R.id.radioButtonNote)
        val radioTodo = dialogView.findViewById<RadioButton>(R.id.radioButtonTodo)
        val todoListContainer = dialogView.findViewById<LinearLayout>(R.id.todoListContainer)
        val subtasksContainer = dialogView.findViewById<LinearLayout>(R.id.subtasksContainer)
        val addSubtaskButton = dialogView.findViewById<Button>(R.id.buttonAddSubtask)

        // Set up visibility changes based on selection
        radioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.radioButtonNote -> {
                    contentEdit.visibility = View.VISIBLE
                    todoListContainer.visibility = View.GONE
                }
                R.id.radioButtonTodo -> {
                    contentEdit.visibility = View.GONE
                    todoListContainer.visibility = View.VISIBLE

                    // If no subtasks exist yet, add one initially
                    if (subtasksContainer.childCount == 0) {
                        addSubtaskView(subtasksContainer)
                    }
                }
            }
        }

        // Setup add subtask button
        addSubtaskButton.setOnClickListener {
            addSubtaskView(subtasksContainer)
        }

        val dialog = AlertDialog.Builder(this)
            .setTitle("Add New Journal Item")
            .setView(dialogView)
            .setPositiveButton("Save") { _, _ ->
                val title = titleEdit.text.toString()

                if (title.isNotEmpty()) {
                    if (radioNote.isChecked) {
                        val content = contentEdit.text.toString()
                        val note = JournalNote(
                            id = UUID.randomUUID().toString(),
                            title = title,
                            content = content,
                            timestamp = System.currentTimeMillis()
                        )
                        journalNotes.add(note)
                    } else {
                        val subtasks = collectSubtasks(subtasksContainer)
                        val todo = TodoItem(
                            id = UUID.randomUUID().toString(),
                            title = title,
                            isCompleted = false, // Set initial status based on subtasks completion
                            timestamp = System.currentTimeMillis(),
                            subtasks = subtasks
                        )
                        todoItems.add(todo)
                    }
                    updateNotesDisplay()
                }
            }
            .setNegativeButton("Cancel", null)
            .create()

        dialog.show()
    }

    private fun addSubtaskView(container: LinearLayout) {
        val subtaskView = LayoutInflater.from(this).inflate(R.layout.subtask_item, container, false)
        val removeButton = subtaskView.findViewById<ImageButton>(R.id.buttonRemoveSubtask)

        // Setup remove button functionality
        removeButton.setOnClickListener {
            container.removeView(subtaskView)
        }

        container.addView(subtaskView)
    }

    private fun collectSubtasks(container: LinearLayout): List<Subtask> {
        val subtasks = mutableListOf<Subtask>()

        for (i in 0 until container.childCount) {
            val subtaskView = container.getChildAt(i)
            val checkbox = subtaskView.findViewById<CheckBox>(R.id.checkBoxSubtask)
            val editText = subtaskView.findViewById<EditText>(R.id.editTextSubtask)
            val subtaskTitle = editText.text.toString()

            if (subtaskTitle.isNotEmpty()) {
                subtasks.add(
                    Subtask(
                        id = UUID.randomUUID().toString(),
                        title = subtaskTitle,
                        isCompleted = checkbox.isChecked
                    )
                )
            }
        }

        return subtasks
    }

    private fun updateNotesDisplay() {
        // Clear all views and re-add them to ensure proper display
        scrollFeed.removeAllViews()

        // Combine notes and todos for sorting by timestamp
        val allItems = mutableListOf<Any>()
        allItems.addAll(journalNotes)
        allItems.addAll(todoItems)

        // Sort all items by timestamp (newest first)
        val sortedItems = allItems.sortedByDescending {
            when (it) {
                is JournalNote -> it.timestamp
                is TodoItem -> it.timestamp
                else -> 0L
            }
        }

        // Add all items in order
        for (item in sortedItems) {
            when (item) {
                is JournalNote -> addNoteView(item)
                is TodoItem -> addTodoView(item)
            }
        }

        // If no items, show message
        if (allItems.isEmpty()) {
            val emptyText = TextView(this)
            emptyText.text = "No notes or todo items yet. Tap + to add one."
            emptyText.textSize = 16f
            emptyText.textAlignment = View.TEXT_ALIGNMENT_CENTER
            emptyText.setTextColor(resources.getColor(R.color.white, null))
            emptyText.setPadding(0, 100, 0, 0)
            scrollFeed.addView(emptyText)
        }
    }

    private fun addNoteView(note: JournalNote) {
        // Create a container for the note and its decorative elements
        val noteContainer = LinearLayout(this)
        noteContainer.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        noteContainer.orientation = LinearLayout.VERTICAL
        noteContainer.setPadding(0, 0, 0, 32)
        noteContainer.tag = note.id // Store the note ID

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

        // Apply selection background if selected
        if (selectedItems.contains(note.id)) {
            noteLayout.setBackgroundColor(
                ContextCompat.getColor(
                    this,
                    R.color.selected_item_background
                )
            )
        }

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

        // Setup long click for selection
        noteLayout.setOnLongClickListener {
            handleItemSelection(note.id, noteLayout)
            true
        }

        // Setup click for editing (if not in selection mode)
        noteLayout.setOnClickListener {
            if (isSelectionMode) {
                handleItemSelection(note.id, noteLayout)
            } else {
                showEditNoteDialog(note)
            }
        }

        // Add the entire container to the feed
        scrollFeed.addView(noteContainer)
    }

    private fun showEditNoteDialog(note: JournalNote) {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_edit_note, null)

        // Find views
        val titleEdit = dialogView.findViewById<EditText>(R.id.editTextEditTitle)
        val contentEdit = dialogView.findViewById<EditText>(R.id.editTextEditContent)

        // Set current values
        titleEdit.setText(note.title)
        contentEdit.setText(note.content)

        // Show dialog
        AlertDialog.Builder(this)
            .setTitle("Edit Note")
            .setView(dialogView)
            .setPositiveButton("Save") { _, _ ->
                // Get updated values
                val updatedTitle = titleEdit.text.toString()
                val updatedContent = contentEdit.text.toString()

                if (updatedTitle.isNotEmpty()) {
                    // Find and update the note
                    val index = journalNotes.indexOfFirst { it.id == note.id }
                    if (index >= 0) {
                        journalNotes[index] = JournalNote(
                            id = note.id,
                            title = updatedTitle,
                            content = updatedContent,
                            timestamp = note.timestamp
                        )
                        updateNotesDisplay()
                    }
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun addTodoView(todo: TodoItem) {
        // Create a container for the to-do item
        val todoContainer = LinearLayout(this)
        todoContainer.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        todoContainer.orientation = LinearLayout.VERTICAL
        todoContainer.setPadding(0, 0, 0, 32)
        todoContainer.tag = todo.id // Store the todo ID

        // First decorative rectangle - small accent bar with different color for todo
        val accentBar = View(this)
        accentBar.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            8
        ).apply {
            setMargins(40, 0, 40, 10)
        }
        accentBar.setBackgroundColor(resources.getColor(R.color.purple_200, null))
        todoContainer.addView(accentBar)

        // Add small decorative circle on the left
        val leftCircle = View(this)
        leftCircle.layoutParams = LinearLayout.LayoutParams(
            20,
            20
        ).apply {
            setMargins(40, 0, 0, 5)
        }
        leftCircle.background = resources.getDrawable(R.drawable.circle_background, null)
        todoContainer.addView(leftCircle)

        // Main todo layout
        val todoLayout = RelativeLayout(this)
        todoLayout.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        todoLayout.setPadding(20, 20, 20, 20)
        todoLayout.setBackgroundResource(R.drawable.rounded_white_background)
        todoLayout.id = View.generateViewId()

        // Apply selection background if selected
        if (selectedItems.contains(todo.id)) {
            todoLayout.setBackgroundColor(
                ContextCompat.getColor(
                    this,
                    R.color.selected_item_background
                )
            )
        }

        // Title text view
        val titleTextView = TextView(this)
        titleTextView.text = todo.title
        titleTextView.textSize = 18f
        titleTextView.setTextColor(resources.getColor(R.color.black, null))
        titleTextView.textAlignment = View.TEXT_ALIGNMENT_CENTER
        titleTextView.id = View.generateViewId()

        // Date text view
        val dateTextView = TextView(this)
        val sdf = SimpleDateFormat("MMM dd, yyyy HH:mm", Locale.getDefault())
        dateTextView.text = sdf.format(Date(todo.timestamp))
        dateTextView.textSize = 12f
        dateTextView.setTextColor(resources.getColor(R.color.black, null))
        dateTextView.id = View.generateViewId()

        // Subtasks container
        val subtasksContainer = LinearLayout(this)
        subtasksContainer.orientation = LinearLayout.VERTICAL
        subtasksContainer.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        subtasksContainer.id = View.generateViewId()

        // Add title to layout
        val titleParams = RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.WRAP_CONTENT,
            RelativeLayout.LayoutParams.WRAP_CONTENT
        )
        titleParams.addRule(RelativeLayout.CENTER_HORIZONTAL)
        todoLayout.addView(titleTextView, titleParams)

        // Add date to layout
        val dateParams = RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.WRAP_CONTENT,
            RelativeLayout.LayoutParams.WRAP_CONTENT
        )
        dateParams.addRule(RelativeLayout.CENTER_HORIZONTAL)
        dateParams.addRule(RelativeLayout.BELOW, titleTextView.id)
        dateParams.topMargin = 8
        todoLayout.addView(dateTextView, dateParams)

        // Add subtasks container to layout
        val subtasksParams = RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.MATCH_PARENT,
            RelativeLayout.LayoutParams.WRAP_CONTENT
        )
        subtasksParams.addRule(RelativeLayout.BELOW, dateTextView.id)
        subtasksParams.topMargin = 16
        todoLayout.addView(subtasksContainer, subtasksParams)

        // Add each subtask
        for (subtask in todo.subtasks) {
            val subtaskLayout = LinearLayout(this)
            subtaskLayout.orientation = LinearLayout.HORIZONTAL
            subtaskLayout.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            subtaskLayout.setPadding(0, 4, 0, 4)

            val checkbox = CheckBox(this)
            checkbox.isChecked = subtask.isCompleted
            checkbox.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            checkbox.tag = subtask.id // Store subtask ID for reference

            val subtaskText = TextView(this)
            subtaskText.text = subtask.title
            subtaskText.textSize = 16f
            subtaskText.setTextColor(resources.getColor(R.color.black, null))
            subtaskText.layoutParams = LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1.0f
            )
            subtaskText.setPadding(16, 0, 0, 0)

            // Apply strikethrough if subtask is completed
            if (subtask.isCompleted) {
                subtaskText.paintFlags =
                    subtaskText.paintFlags or android.graphics.Paint.STRIKE_THRU_TEXT_FLAG
            }

            // Update strikethrough when checkbox is toggled
            checkbox.setOnCheckedChangeListener { _, isChecked ->
                subtaskText.paintFlags = if (isChecked) {
                    subtaskText.paintFlags or android.graphics.Paint.STRIKE_THRU_TEXT_FLAG
                } else {
                    subtaskText.paintFlags and android.graphics.Paint.STRIKE_THRU_TEXT_FLAG.inv()
                }

                // Find and update the subtask in the todo item
                val todoIdx = todoItems.indexOfFirst { it.id == todo.id }
                if (todoIdx >= 0) {
                    val currentTodo = todoItems[todoIdx]
                    val updatedSubtasks = currentTodo.subtasks.map {
                        if (it.id == subtask.id) it.copy(isCompleted = isChecked) else it
                    }
                    todoItems[todoIdx] = currentTodo.copy(subtasks = updatedSubtasks)
                }
            }

            subtaskLayout.addView(checkbox)
            subtaskLayout.addView(subtaskText)
            subtasksContainer.addView(subtaskLayout)
        }

        // Setup long click for selection
        todoLayout.setOnLongClickListener {
            handleItemSelection(todo.id, todoLayout)
            true
        }

        // Setup click for editing (if not in selection mode)
        todoLayout.setOnClickListener {
            if (isSelectionMode) {
                handleItemSelection(todo.id, todoLayout)
            } else {
                showEditTodoDialog(todo)
            }
        }

        // Add main todo layout to container
        todoContainer.addView(todoLayout)

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
        todoContainer.addView(rightCircle)

        // Second decorative rectangle - bottom accent with different color for todo
        val bottomAccent = View(this)
        bottomAccent.layoutParams = LinearLayout.LayoutParams(
            200,
            5
        ).apply {
            gravity = android.view.Gravity.CENTER_HORIZONTAL
            setMargins(0, 10, 0, 0)
        }
        bottomAccent.setBackgroundColor(resources.getColor(R.color.purple_200, null))
        todoContainer.addView(bottomAccent)

        // Add the entire container to the feed
        scrollFeed.addView(todoContainer)
    }

    private fun handleItemSelection(itemId: String, view: View) {
        if (!isSelectionMode) {
            isSelectionMode = true
        }

        // Toggle selection
        if (selectedItems.contains(itemId)) {
            selectedItems.remove(itemId)

            // Reset background
            if (view is RelativeLayout) {
                view.setBackgroundResource(R.drawable.rounded_white_background)
            }
        } else {
            selectedItems.add(itemId)

            // Highlight selected item
            view.setBackgroundColor(ContextCompat.getColor(this, R.color.selected_item_background))
        }

        // If nothing selected anymore, exit selection mode
        if (selectedItems.isEmpty()) {
            isSelectionMode = false
        }
    }

    private fun showEditTodoDialog(todo: TodoItem) {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_edit_todo, null)

        // Find views
        val titleEdit = dialogView.findViewById<EditText>(R.id.editTextEditTitle)
        val subtasksContainer = dialogView.findViewById<LinearLayout>(R.id.subtasksEditContainer)
        val addSubtaskButton = dialogView.findViewById<Button>(R.id.buttonAddSubtaskInEdit)

        // Set current title
        titleEdit.setText(todo.title)

        // Add existing subtasks
        for (subtask in todo.subtasks) {
            addSubtaskToEditView(subtasksContainer, subtask)
        }

        // Setup add subtask button
        addSubtaskButton.setOnClickListener {
            addSubtaskToEditView(subtasksContainer, null)
        }

        // Show dialog
        AlertDialog.Builder(this)
            .setTitle("Edit To-Do List")
            .setView(dialogView)
            .setPositiveButton("Save") { _, _ ->
                val updatedTitle = titleEdit.text.toString()

                if (updatedTitle.isNotEmpty()) {
                    val updatedSubtasks = collectSubtasksFromEdit(subtasksContainer, todo.subtasks)

                    // Find and update the todo
                    val index = todoItems.indexOfFirst { it.id == todo.id }
                    if (index >= 0) {
                        todoItems[index] = TodoItem(
                            id = todo.id,
                            title = updatedTitle,
                            isCompleted = todo.isCompleted,
                            timestamp = todo.timestamp,
                            subtasks = updatedSubtasks
                        )
                        updateNotesDisplay()
                    }
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun addSubtaskToEditView(container: LinearLayout, subtask: Subtask?) {
        val subtaskView = LayoutInflater.from(this).inflate(R.layout.subtask_item, container, false)

        val checkbox = subtaskView.findViewById<CheckBox>(R.id.checkBoxSubtask)
        val editText = subtaskView.findViewById<EditText>(R.id.editTextSubtask)
        val removeButton = subtaskView.findViewById<ImageButton>(R.id.buttonRemoveSubtask)

        // Set existing values if editing a subtask
        if (subtask != null) {
            checkbox.isChecked = subtask.isCompleted
            editText.setText(subtask.title)
            subtaskView.tag = subtask.id // Store ID for reference
        } else {
            subtaskView.tag = UUID.randomUUID().toString() // New ID for new subtask
        }

        // Setup remove button
        removeButton.setOnClickListener {
            container.removeView(subtaskView)
        }

        container.addView(subtaskView)
    }

    private fun collectSubtasksFromEdit(
        container: LinearLayout,
        originalSubtasks: List<Subtask>
    ): List<Subtask> {
        val subtasks = mutableListOf<Subtask>()

        for (i in 0 until container.childCount) {
            val subtaskView = container.getChildAt(i)
            val checkbox = subtaskView.findViewById<CheckBox>(R.id.checkBoxSubtask)
            val editText = subtaskView.findViewById<EditText>(R.id.editTextSubtask)
            val subtaskTitle = editText.text.toString()
            val subtaskId = subtaskView.tag as String

            if (subtaskTitle.isNotEmpty()) {
                // Try to find original subtask to preserve ID
                val subtask = Subtask(
                    id = subtaskId,
                    title = subtaskTitle,
                    isCompleted = checkbox.isChecked
                )
                subtasks.add(subtask)
            }
        }

        return subtasks
    }
}