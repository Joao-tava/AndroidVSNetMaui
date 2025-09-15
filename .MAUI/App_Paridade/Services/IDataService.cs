using App_Paridade.Models;

namespace App_Paridade.Services;

public interface IDataService
{
    Task<List<Item>> GetItemsAsync();
}
