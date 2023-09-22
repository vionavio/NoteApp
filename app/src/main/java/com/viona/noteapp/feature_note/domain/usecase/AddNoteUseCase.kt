package com.viona.noteapp.feature_note.domain.usecase

import com.viona.noteapp.feature_note.data.repository.NoteRepository
import com.viona.noteapp.feature_note.domain.model.InvalidNoteException
import com.viona.noteapp.feature_note.domain.model.Note

class AddNoteUseCase(
    private val repository: NoteRepository
) {
    suspend operator fun invoke(note: Note){
        if (note.title.isBlank()) {
            throw InvalidNoteException("The title of note cant be empty")
        }
        if (note.content.isBlank()) {
            throw InvalidNoteException("The content of note cant be empty")
        }
        repository.insertNote(note)
    }
}