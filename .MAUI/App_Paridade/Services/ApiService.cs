using System.Net.Http.Json;
using App_Paridade.Models;

namespace App_Paridade.Services;

public class ApiService : IApiService
{
    private readonly HttpClient _httpClient;

    public ApiService(HttpClient httpClient)
    {
        _httpClient = httpClient;
        _httpClient.BaseAddress = new Uri("https://servicodados.ibge.gov.br/api/v1/localidades/");
    }

    public async Task<List<Localidade>> GetEstadosAsync()
    {
        try
        {
            var estados = await _httpClient.GetFromJsonAsync<List<Localidade>>("estados");
            return estados ?? new List<Localidade>();
        }
        catch (Exception ex)
        {
            // Aqui poderíamos logar ou tratar erro
            Console.WriteLine($"Erro ao buscar dados do IBGE: {ex.Message}");
            return new List<Localidade>();
        }
    }
}
