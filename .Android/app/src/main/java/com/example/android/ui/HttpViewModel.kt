import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.data.Municipio
import com.example.android.data.RetrofitClient
import kotlinx.coroutines.launch

/**
 * ViewModel responsável por buscar os dados da API do IBGE e fornecê-los para a UI.
 */
class HttpViewModel : ViewModel() {

    // 1. LiveData para armazenar a lista de municípios.
    // A versão com _ (underline) é a 'Mutable' e privada, só o ViewModel pode alterá-la.
    private val _municipios = MutableLiveData<List<Municipio>>()

    // A versão pública é 'LiveData' (imutável), para que a UI possa apenas observar os dados.
    val municipios: LiveData<List<Municipio>> = _municipios

    // 2. LiveData para controlar o estado de carregamento (ex: mostrar uma barra de progresso).
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    // 3. LiveData para tratar possíveis erros de rede.
    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    /**
     * Função para iniciar a busca dos dados da API.
     * Ela será chamada pela UI (o Fragment).
     */
    fun fetchMunicipios() {
        // Usa o viewModelScope para lançar uma coroutine.
        // Isso garante que a chamada de rede seja cancelada se o ViewModel for destruído.
        viewModelScope.launch {
            _isLoading.value = true // Avisa a UI que o carregamento começou
            _error.value = null // Limpa erros anteriores
            try {
                // Chama a função 'suspend' do nosso serviço Retrofit.
                val response = RetrofitClient.instance.getMunicipios()
                // Atualiza o LiveData com a lista de municípios recebida.
                _municipios.value = response
            } catch (e: Exception) {
                // Em caso de erro (ex: sem internet), atualiza o LiveData de erro.
                _error.value = "Falha ao carregar dados: ${e.message}"
            } finally {
                _isLoading.value = false // Avisa a UI que o carregamento terminou
            }
        }
    }
}