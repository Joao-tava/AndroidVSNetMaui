package com.example.android.data

/**
 * Esta é uma "data class" que serve como modelo para um item.
 * Ela define a estrutura de dados: um Item sempre terá um id (inteiro),
 * um nome (texto) e uma descrição (texto).
 *
 * As propriedades (id, name, description) correspondem exatamente
 * às chaves no arquivo items.json, permitindo que a biblioteca Gson
 * faça a conversão automática do JSON para objetos desta classe.
 */
data class Item(
    val id: Int,
    val name: String,
    val description: String
)