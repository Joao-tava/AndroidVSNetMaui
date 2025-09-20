package com.example.nativo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class IbgeViewModel : ViewModel() {

    private val _estados = MutableLiveData<List<EstadoIBGE>>()
    val estados: LiveData<List<EstadoIBGE>> = _estados

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    init {
        fetchEstados()
    }

    fun fetchEstados() {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            try {
                val response = RetrofitInstance.api.getEstados()
                if (response.isSuccessful) {
                    _estados.value = response.body()
                } else {
                    _errorMessage.value = "Erro ao buscar dados: ${response.code()}"
                }
            } catch (e: Exception) {
                _errorMessage.value = "Falha na conexão: ${e.message}"
            }
            _isLoading.value = false
        }
    }
}