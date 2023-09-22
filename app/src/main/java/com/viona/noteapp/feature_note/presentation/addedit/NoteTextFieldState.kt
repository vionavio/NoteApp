package com.viona.noteapp.feature_note.presentation.addedit

data class NoteTextFieldState(
    val text: String = "",
    val hint: String = "",
    val isHintVisible: Boolean = true
)