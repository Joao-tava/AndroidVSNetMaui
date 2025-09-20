package com.example.nativo

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.InputStreamReader

class ListViewModel(application: Application) : AndroidViewModel(application) {

    private val _allItems = MutableLiveData<List<Item>>() // Lista original carregada do JSON
    // Expõe filteredItems como LiveData imutável para o Fragment
    private val _filteredItems = MutableLiveData<List<Item>>()
    val filteredItems: LiveData<List<Item>> = _filteredItems

    init {
        loadItemsFromJson()
    }

    private fun loadItemsFromJson() {
        try {
            val inputStream = getApplication<Application>().resources.openRawResource(R.raw.item)
            val reader = InputStreamReader(inputStream)
            val itemType = object : TypeToken<List<Item>>() {}.type
            val items: List<Item>? = Gson().fromJson(reader, itemType)
            _allItems.value = items ?: emptyList()
            _filteredItems.value = items ?: emptyList() // Inicialmente, mostra todos os itens
        } catch (e: Exception) {
            e.printStackTrace()
            _allItems.value = emptyList()
            _filteredItems.value = emptyList()
        }
    }

    fun filterItems(query: String?) {
        val currentList = _allItems.value ?: emptyList()
        if (query.isNullOrEmpty()) {
            _filteredItems.value = currentList // Mostra todos se a busca estiver vazia
        } else {
            val lowerCaseQuery = query.lowercase()
            _filteredItems.value = currentList.filter {
                it.name.lowercase().contains(lowerCaseQuery) ||
                it.description.lowercase().contains(lowerCaseQuery) ||
                it.category.lowercase().contains(lowerCaseQuery) ||
                it.tags.any { tag -> tag.lowercase().contains(lowerCaseQuery) }
            }
        }
    }
}
