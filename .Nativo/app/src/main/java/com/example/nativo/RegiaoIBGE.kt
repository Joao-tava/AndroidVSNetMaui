package com.example.nativo

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RegiaoIBGE(
    val id: Int,
    val sigla: String,
    val nome: String
) : Parcelable