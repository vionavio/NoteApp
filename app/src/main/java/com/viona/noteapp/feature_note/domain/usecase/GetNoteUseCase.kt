package com.viona.noteapp.feature_note.domain.usecase

import com.viona.noteapp.feature_note.data.repository.NoteRepository
import com.viona.noteapp.feature_note.domain.model.Note

class GetNoteUseCase(
    private val repository: NoteRepository
) {
    suspend operator fun invoke(id: Int): Note? {
        return repository.getNoteById(id)
    }
}