package com.example.finalsproject

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
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
import androidx.core.view.marginTop
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
    private lateinit var bookmarkedPlants: ArrayList<String>

    private val selectedItems = mutableSetOf<String>()
    private var isSelectionMode = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.journal)

        plusButton = findViewById(R.id.addImageButton)
        deleteButton = findViewById(R.id.deleteImageButton)
        scrollFeed = findViewById(R.id.scrollFeed)

        bookmarkedPlants = intent.getStringArrayListExtra("bookmarked_plants") ?: ArrayList()

        setupNavigation()
        setupAddNoteButton()
        setupDeleteButton()
        updateNotesDisplay()
    }

    private fun setupNavigation() {
        val username = intent.getStringExtra("USERNAME")
        val email = intent.getStringExtra("EMAIL")
        val phone = intent.getStringExtra("PHONE")
        val address = intent.getStringExtra("ADDRESS")
        val password = intent.getStringExtra("PASSWORD")
        val newPassword = intent.getStringExtra("NEW_PASSWORD")
        val finalPassword = newPassword ?: password
        val homeButton = findViewById<ImageButton>(R.id.homeImageButton)
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

        val bookmarkButton = findViewById<ImageButton>(R.id.bookmarkImageButton)
        bookmarkButton.setOnClickListener {
            val bookmarkIntent = Intent(this, Bookmark_Activity::class.java)
            bookmarkIntent.putStringArrayListExtra("bookmarked_plants", ArrayList(bookmarkedPlants))
            bookmarkIntent.putExtra("USERNAME", username)
            bookmarkIntent.putExtra("EMAIL", email)
            bookmarkIntent.putExtra("PHONE", phone)
            bookmarkIntent.putExtra("ADDRESS", address)
            bookmarkIntent.putExtra("PASSWORD", finalPassword)
            startActivity(bookmarkIntent)
        }

        val profileButton = findViewById<ImageButton>(R.id.userImageButton)
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

        val backButton = findViewById<ImageButton>(R.id.backImageButton)
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

        journalNotes.removeAll { selectedItems.contains(it.id) }

        todoItems.removeAll { selectedItems.contains(it.id) }

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
        val todoListContainer = dialogView.findViewById<LinearLayout>(R.id.todoListContainer)
        val subtasksContainer = dialogView.findViewById<LinearLayout>(R.id.subtasksContainer)
        val addSubtaskButton = dialogView.findViewById<Button>(R.id.buttonAddSubtask)

        radioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.radioButtonNote -> {
                    contentEdit.visibility = View.VISIBLE
                    todoListContainer.visibility = View.GONE
                }
                R.id.radioButtonTodo -> {
                    contentEdit.visibility = View.GONE
                    todoListContainer.visibility = View.VISIBLE

                    if (subtasksContainer.childCount == 0) {
                        addSubtaskView(subtasksContainer)
                    }
                }
            }
        }

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
                            isCompleted = false,
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

        scrollFeed.removeAllViews()

        val allItems = mutableListOf<Any>()
        allItems.addAll(journalNotes)
        allItems.addAll(todoItems)

        val sortedItems = allItems.sortedByDescending {
            when (it) {
                is JournalNote -> it.timestamp
                is TodoItem -> it.timestamp
                else -> 0L
            }
        }

        for (item in sortedItems) {
            when (item) {
                is JournalNote -> addNoteView(item)
                is TodoItem -> addTodoView(item)
            }
        }

        if (allItems.isEmpty()) {
            val emptyText = TextView(this)
            emptyText.text = "No notes or todo items yet. Tap + to add one."
            emptyText.gravity = Gravity.CENTER
            emptyText.setTextColor(ContextCompat.getColor(this, R.color.white))
            scrollFeed.addView(emptyText)
        }
    }

    private fun addNoteView(note: JournalNote) {
        val noteView =
            LayoutInflater.from(this).inflate(R.layout.item_journal_note, scrollFeed, false)

        noteView.tag = note.id

        val noteLayout = noteView.findViewById<RelativeLayout>(R.id.noteLayout)
        val titleTextView = noteView.findViewById<TextView>(R.id.noteTitleTextView)
        val dateTextView = noteView.findViewById<TextView>(R.id.noteDateTextView)
        val contentTextView = noteView.findViewById<TextView>(R.id.noteContentTextView)

        titleTextView.text = note.title

        val sdf = SimpleDateFormat("MMM dd, yyyy HH:mm", Locale.getDefault())
        dateTextView.text = sdf.format(Date(note.timestamp))

        contentTextView.text = note.content

        if (selectedItems.contains(note.id)) {
            noteLayout.setBackgroundColor(
                ContextCompat.getColor(
                    this,
                    R.color.selected_item_background
                )
            )
        }

        noteLayout.setOnLongClickListener {
            handleItemSelection(note.id, noteLayout)
            true
        }

        noteLayout.setOnClickListener {
            if (isSelectionMode) {
                handleItemSelection(note.id, noteLayout)
            } else {
                showEditNoteDialog(note)
            }
        }

        scrollFeed.addView(noteView)
    }

    private fun showEditNoteDialog(note: JournalNote) {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_edit_note, null)

        val titleEdit = dialogView.findViewById<EditText>(R.id.editTextEditTitle)
        val contentEdit = dialogView.findViewById<EditText>(R.id.editTextEditContent)

        titleEdit.setText(note.title)
        contentEdit.setText(note.content)

        AlertDialog.Builder(this)
            .setTitle("Edit Note")
            .setView(dialogView)
            .setPositiveButton("Save") { _, _ ->

                val updatedTitle = titleEdit.text.toString()
                val updatedContent = contentEdit.text.toString()

                if (updatedTitle.isNotEmpty()) {

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
        val todoView =
            LayoutInflater.from(this).inflate(R.layout.item_journal_todo, scrollFeed, false)

        todoView.tag = todo.id

        val todoLayout = todoView.findViewById<RelativeLayout>(R.id.todoLayout)
        val titleTextView = todoView.findViewById<TextView>(R.id.todoTitleTextView)
        val dateTextView = todoView.findViewById<TextView>(R.id.todoDateTextView)
        val subtasksContainer = todoView.findViewById<LinearLayout>(R.id.subtasksContainer)

        titleTextView.text = todo.title

        val sdf = SimpleDateFormat("MMM dd, yyyy HH:mm", Locale.getDefault())
        dateTextView.text = sdf.format(Date(todo.timestamp))

        if (selectedItems.contains(todo.id)) {
            todoLayout.setBackgroundColor(
                ContextCompat.getColor(
                    this,
                    R.color.selected_item_background
                )
            )
        }

        for (subtask in todo.subtasks) {

            val subtaskView = LayoutInflater.from(this)
                .inflate(R.layout.item_subtask_view, subtasksContainer, false)

            val checkbox = subtaskView.findViewById<CheckBox>(R.id.subtaskCheckBox)
            val subtaskText = subtaskView.findViewById<TextView>(R.id.subtaskTextView)

            checkbox.isChecked = subtask.isCompleted
            subtaskText.text = subtask.title
            checkbox.tag = subtask.id

            if (subtask.isCompleted) {
                subtaskText.paintFlags =
                    subtaskText.paintFlags or android.graphics.Paint.STRIKE_THRU_TEXT_FLAG
            }

            checkbox.setOnCheckedChangeListener { _, isChecked ->
                subtaskText.paintFlags = if (isChecked) {
                    subtaskText.paintFlags or android.graphics.Paint.STRIKE_THRU_TEXT_FLAG
                } else {
                    subtaskText.paintFlags and android.graphics.Paint.STRIKE_THRU_TEXT_FLAG.inv()
                }

                val todoIdx = todoItems.indexOfFirst { it.id == todo.id }
                if (todoIdx >= 0) {
                    val currentTodo = todoItems[todoIdx]
                    val updatedSubtasks = currentTodo.subtasks.map {
                        if (it.id == subtask.id) it.copy(isCompleted = isChecked) else it
                    }
                    todoItems[todoIdx] = currentTodo.copy(subtasks = updatedSubtasks)
                }
            }

            subtasksContainer.addView(subtaskView)
        }

        todoLayout.setOnLongClickListener {
            handleItemSelection(todo.id, todoLayout)
            true
        }

        todoLayout.setOnClickListener {
            if (isSelectionMode) {
                handleItemSelection(todo.id, todoLayout)
            } else {
                showEditTodoDialog(todo)
            }
        }

        scrollFeed.addView(todoView)
    }

    private fun handleItemSelection(itemId: String, view: View) {
        if (!isSelectionMode) {
            isSelectionMode = true
        }

        if (selectedItems.contains(itemId)) {
            selectedItems.remove(itemId)

            if (view is RelativeLayout) {
                view.setBackgroundResource(R.drawable.rounded_white_background)
            }
        } else {
            selectedItems.add(itemId)

            view.setBackgroundColor(ContextCompat.getColor(this, R.color.selected_item_background))
        }

        if (selectedItems.isEmpty()) {
            isSelectionMode = false
        }
    }

    private fun showEditTodoDialog(todo: TodoItem) {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_edit_todo, null)

        val titleEdit = dialogView.findViewById<EditText>(R.id.editTextEditTitle)
        val subtasksContainer = dialogView.findViewById<LinearLayout>(R.id.subtasksEditContainer)
        val addSubtaskButton = dialogView.findViewById<Button>(R.id.buttonAddSubtaskInEdit)

        titleEdit.setText(todo.title)

        for (subtask in todo.subtasks) {
            addSubtaskToEditView(subtasksContainer, subtask)
        }

        addSubtaskButton.setOnClickListener {
            addSubtaskToEditView(subtasksContainer, null)
        }

        AlertDialog.Builder(this)
            .setTitle("Edit To-Do List")
            .setView(dialogView)
            .setPositiveButton("Save") { _, _ ->
                val updatedTitle = titleEdit.text.toString()

                if (updatedTitle.isNotEmpty()) {
                    val updatedSubtasks = collectSubtasksFromEdit(subtasksContainer, todo.subtasks)

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

        if (subtask != null) {
            checkbox.isChecked = subtask.isCompleted
            editText.setText(subtask.title)
            subtaskView.tag = subtask.id
        } else {
            subtaskView.tag = UUID.randomUUID().toString()
        }

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