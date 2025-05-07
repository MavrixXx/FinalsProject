package com.example.finalsproject.model

data class TodoItem(
    val id: String = "",
    val title: String = "",
    val isCompleted: Boolean = false,
    val timestamp: Long = System.currentTimeMillis(),
    val subtasks: List<Subtask> = emptyList()
)

data class Subtask(
    val id: String = "",
    val title: String = "",
    val isCompleted: Boolean = false
)