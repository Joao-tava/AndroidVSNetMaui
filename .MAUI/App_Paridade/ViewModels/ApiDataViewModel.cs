using System.Collections.ObjectModel;
using System.Windows.Input;
using System.Linq;
using System.Threading.Tasks;
using App_Paridade.Models;
using App_Paridade.Services;
using Microsoft.Maui.Controls;

namespace App_Paridade.ViewModels;

public class ApiDataViewModel : BindableObject
{
    private readonly IApiService _apiService;

    public ObservableCollection<Localidade> Estados { get; } = new();

    private bool _isBusy;
    public bool IsBusy
    {
        get => _isBusy;
        set
        {
            if (_isBusy != value)
            {
                _isBusy = value;
                OnPropertyChanged();
                (LoadEstadosCommand as Command)?.ChangeCanExecute();
            }
        }
    }

    public ICommand LoadEstadosCommand { get; }

    public ApiDataViewModel(IApiService apiService)
    {
        _apiService = apiService;
        // o Command ainda aciona o método assíncrono, mas o método fica public para que possamos awaitá-lo
        LoadEstadosCommand = new Command(async () => await LoadEstadosAsync(), () => !IsBusy);
    }

    // <-- Tornamos este método public para poder chamá-lo/aguardá-lo diretamente a partir da View
    public async Task LoadEstadosAsync()
    {
        if (IsBusy) return;

        try
        {
            IsBusy = true;
            Estados.Clear();

            var estados = await _apiService.GetEstadosAsync();

            foreach (var estado in estados.OrderBy(e => e.Nome))
                Estados.Add(estado);
        }
        finally
        {
            IsBusy = false;
        }
    }
}