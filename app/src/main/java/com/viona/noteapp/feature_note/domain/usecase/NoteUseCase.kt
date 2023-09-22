package com.viona.noteapp.feature_note.domain.usecase

data class NoteUseCase(
    val getNotes: GetNotesUseCase,
    val deleteNote: DeleteNoteUseCase,
    val addNote: AddNoteUseCase,
    val getNote: GetNoteUseCase
)
