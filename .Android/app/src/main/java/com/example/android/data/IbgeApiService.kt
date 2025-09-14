package com.example.android.data

import com.google.gson.annotations.SerializedName
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

/**
 * 1. MODELO DE DADOS (Data Class)
 * Este é o "molde" que diz ao Gson como converter o JSON da resposta da API
 * em um objeto Kotlin.
 *
 * Exemplo de JSON da API:
 * {
 *   "id": 3550308,
 *   "nome": "São Paulo",
 *   "microrregiao": { ... }
 * }
 */
data class Municipio(
    @SerializedName("id") // Garante que o campo 'id' do JSON será mapeado para a propriedade 'id'
    val id: Long,

    @SerializedName("nome") // Garante que o campo 'nome' do JSON será mapeado para a propriedade 'nome'
    val nome: String
)

/**
 * 2. INTERFACE DO SERVIÇO (API Service)
 * Define quais chamadas de rede podemos fazer.
 * Usamos anotações do Retrofit (@GET) para especificar o endpoint e o método.
 */
interface IbgeApiService {

    // Define uma função para buscar a lista de municípios.
    // O @GET especifica o caminho do endpoint relativo à URL base.
    // 'suspend' indica que esta função deve ser chamada a partir de uma coroutine.
    @GET("api/v1/localidades/estados/SP/municipios") // Exemplo: buscando municípios de São Paulo
    suspend fun getMunicipios(): List<Municipio>
}

/**
 * 3. CLIENTE RETROFIT (Singleton)
 * Objeto responsável por criar e configurar uma única instância do Retrofit para todo o app.
 * Isso é eficiente e evita criar múltiplos objetos desnecessariamente.
 */
object RetrofitClient {

    // URL base da API do IBGE. Todas as chamadas partirão daqui.
    private const val BASE_URL = "https://servicodados.ibge.gov.br/"

    // 'by lazy' garante que a instância do Retrofit só será criada na primeira vez que for usada.
    val instance: IbgeApiService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()) // Usa Gson para converter JSON
            .build()

        retrofit.create(IbgeApiService::class.java) // Cria a implementação da nossa interface
    }
}