package com.example.nativo

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Item(
    val id: Int,
    val name: String,
    val description: String,
    val category: String, // Adicionada a propriedade category
    val tags: List<String>  // Adicionada a propriedade tags
) : Parcelable