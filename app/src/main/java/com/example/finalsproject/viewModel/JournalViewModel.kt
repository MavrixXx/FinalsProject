package com.example.finalsproject.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.finalsproject.repository.JournalRepository
import com.example.florasense.data.model.JournalModel
import kotlinx.coroutines.launch

class JournalViewModel : ViewModel() {

    private val journalRepository = JournalRepository()

    private val _journal = MutableLiveData<JournalModel?>()
    val journal: LiveData<JournalModel?> = _journal

    fun addJournal(journalModel: JournalModel) {
        viewModelScope.launch {
            val response = journalRepository.addJournal(journalModel)
            if (response.isSuccessful) {
                _journal.value = response.body()
            } else {
                _journal.value = null
            }
        }
    }
}
