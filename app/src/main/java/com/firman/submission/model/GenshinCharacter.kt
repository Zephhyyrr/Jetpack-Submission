package com.firman.submission.model

data class GenshinCharacter(
    val id: Long,
    val image: Int,
    val name: String,
    val element: String,
    val description: String,
)