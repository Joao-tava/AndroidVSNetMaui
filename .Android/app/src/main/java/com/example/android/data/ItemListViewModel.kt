package com.example.android.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.android.R // Importa a classe R para acessar os recursos da pasta 'raw'
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.InputStreamReader

/**
 * ViewModel para o ItemListFragment.
 * Herda de AndroidViewModel para ter acesso ao contexto da aplicação,
 * necessário para ler o arquivo JSON dos recursos.
 */
class ItemListViewModel(application: Application) : AndroidViewModel(application) {

    // A lista original de itens, carregada do JSON. É privada para proteger a fonte de dados.
    private val _allItems: List<Item>

    // LiveData que a UI (Fragment) observa para receber a lista de itens a ser exibida.
    private val _items = MutableLiveData<List<Item>>()
    val items: LiveData<List<Item>> = _items

    // O bloco 'init' é executado assim que o ViewModel é criado.
    init {
        // Carrega os dados do arquivo JSON e inicializa a lista de itens.
        _allItems = loadItemsFromJson()
        // Define o valor inicial do LiveData com a lista completa.
        _items.value = _allItems
    }

    /**
     * Lê o arquivo 'items.json' da pasta 'res/raw' e o converte em uma lista de objetos [Item].
     * @return Uma lista de [Item] ou uma lista vazia se ocorrer um erro.
     */
    private fun loadItemsFromJson(): List<Item> {
        try {
            // 1. Usa o contexto da aplicação para abrir o arquivo de recurso bruto.
            val inputStream = getApplication<Application>().resources.openRawResource(R.raw.items)

            // 2. Define o tipo de objeto que o Gson deve criar (uma lista de Itens).
            val itemType = object : TypeToken<List<Item>>() {}.type

            // 3. Usa o Gson para ler o arquivo e converter o JSON diretamente na lista de objetos.
            return Gson().fromJson(InputStreamReader(inputStream), itemType)

        } catch (e: Exception) {
            // Se houver qualquer erro na leitura ou conversão, imprime o erro no Logcat.
            e.printStackTrace()
            // Retorna uma lista vazia para evitar que o aplicativo quebre.
            return emptyList()
        }
    }

    /**
     * Filtra a lista de itens com base em uma consulta de texto.
     * Esta função é chamada pela UI sempre que o texto de busca muda.
     */
    fun setSearchQuery(query: String) {
        val filteredList = if (query.isBlank()) {
            // Se a busca estiver vazia, retorna a lista completa original.
            _allItems
        } else {
            // Caso contrário, filtra a lista original (_allItems) para encontrar
            // itens cujo nome ou descrição contenham o texto da busca (ignorando maiúsculas/minúsculas).
            _allItems.filter { item ->
                item.name.contains(query, ignoreCase = true) ||
                        item.description.contains(query, ignoreCase = true)
            }
        }
        // Atualiza o LiveData '_items' com a lista filtrada, o que notificará a UI para se redesenhar.
        _items.value = filteredList
    }
}
