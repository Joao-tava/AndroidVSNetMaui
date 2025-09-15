using System.Collections.ObjectModel;
using System.ComponentModel;
using System.Runtime.CompilerServices;
using App_Paridade.Models;
using App_Paridade.Services;

namespace App_Paridade.ViewModels;

public class ItemsViewModel : INotifyPropertyChanged
{
    private readonly IDataService _dataService;

    private ObservableCollection<Item> _items = new();
    private List<Item> _allItems = new(); // lista completa para não perdermos os dados no filtro
    private string _searchText = string.Empty;

    public ObservableCollection<Item> Items
    {
        get => _items;
        set
        {
            _items = value;
            OnPropertyChanged();
        }
    }

    public string SearchText
    {
        get => _searchText;
        set
        {
            if (_searchText != value)
            {
                _searchText = value;
                OnPropertyChanged();
                ApplyFilter();
            }
        }
    }

    public event PropertyChangedEventHandler PropertyChanged;

    public ItemsViewModel(IDataService dataService)
    {
        _dataService = dataService;
    }

    /// <summary>
    /// Carrega os itens do JSON local.
    /// Deve ser chamado no OnAppearing da View.
    /// </summary>
    public async Task LoadItemsAsync()
    {
        var items = await _dataService.GetItemsAsync();
        _allItems = items;
        Items = new ObservableCollection<Item>(_allItems);
    }

    /// <summary>
    /// Aplica o filtro de busca em tempo real.
    /// </summary>
    private void ApplyFilter()
    {
        if (string.IsNullOrWhiteSpace(SearchText))
        {
            Items = new ObservableCollection<Item>(_allItems);
        }
        else
        {
            var filtered = _allItems
                .Where(i => i.Title.Contains(SearchText, StringComparison.OrdinalIgnoreCase))
                .ToList();


            Items = new ObservableCollection<Item>(filtered);
        }
    }

    private void OnPropertyChanged([CallerMemberName] string propertyName = "")
    {
        PropertyChanged?.Invoke(this, new PropertyChangedEventArgs(propertyName));
    }
}
