using App_Paridade.Models;

namespace App_Paridade.Services;

public interface IApiService
{
    Task<List<Localidade>> GetEstadosAsync();
}
