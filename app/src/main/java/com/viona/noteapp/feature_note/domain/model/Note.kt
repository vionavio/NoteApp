package com.viona.noteapp.feature_note.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.viona.noteapp.ui.theme.Pink80
import com.viona.noteapp.ui.theme.Purple40

@Entity
data class Note(
    val title: String,
    val content: String,
    val timestamp: Long,
    val color: Int,

    @PrimaryKey val id: Int? = null
){
    companion object {
        val noteColors = listOf(Pink80, Purple40)
    }
}

class InvalidNoteException(message: String): Exception(message)
