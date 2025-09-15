using System.Text.Json;
using App_Paridade.Models;

namespace App_Paridade.Services;

public class LocalDataService : IDataService
{
    private const string JsonFileName = "items.json";

    public async Task<List<Item>> GetItemsAsync()
    {
        // Abre o arquivo "items.json" que está em Resources/Raw
        using var stream = await FileSystem.OpenAppPackageFileAsync(JsonFileName);
        using var reader = new StreamReader(stream);
        var json = await reader.ReadToEndAsync();

        // Converte o JSON em objetos do tipo Item
        var options = new JsonSerializerOptions
        {
            PropertyNameCaseInsensitive = true
        };

        var items = JsonSerializer.Deserialize<List<Item>>(json, options);

        return items ?? new List<Item>();
    }
}
