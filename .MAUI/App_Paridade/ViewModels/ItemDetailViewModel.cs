using App_Paridade.Models;
using Microsoft.Maui.Controls;

namespace App_Paridade.ViewModels;

public class ItemDetailViewModel : BindableObject, IQueryAttributable
{
    private string _title;
    private string _description;

    public string Title
    {
        get => _title;
        set { _title = value; OnPropertyChanged(); }
    }

    public string Description
    {
        get => _description;
        set { _description = value; OnPropertyChanged(); }
    }

    // Recebe o parâmetro da navegação (Item selecionado)
    public void ApplyQueryAttributes(IDictionary<string, object> query)
    {
        if (query.ContainsKey("Item") && query["Item"] is Item item)
        {
            Title = item.Title;
            Description = item.Description;
        }
    }
}
