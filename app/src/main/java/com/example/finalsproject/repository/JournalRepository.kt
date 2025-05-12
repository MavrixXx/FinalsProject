package com.example.finalsproject.repository

import com.example.florasense.data.api.RetroFitInstance
import com.example.florasense.data.model.JournalModel

class JournalRepository {
    suspend fun addJournal(journal: JournalModel) = RetroFitInstance.api.addJournal(journal)
}