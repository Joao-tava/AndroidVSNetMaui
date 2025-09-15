using System.Windows.Input;
using Microsoft.Maui.Controls;
using App_Paridade.Services;

namespace App_Paridade.ViewModels;

public class LocationViewModel : BindableObject
{
    private readonly ILocationService _locationService;

    private string _latitude;
    public string Latitude
    {
        get => _latitude;
        set
        {
            if (_latitude != value)
            {
                _latitude = value;
                OnPropertyChanged();
            }
        }
    }

    private string _longitude;
    public string Longitude
    {
        get => _longitude;
        set
        {
            if (_longitude != value)
            {
                _longitude = value;
                OnPropertyChanged();
            }
        }
    }

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
                (GetLocationCommand as Command)?.ChangeCanExecute();
            }
        }
    }

    public ICommand GetLocationCommand { get; }

    public LocationViewModel(ILocationService locationService)
    {
        _locationService = locationService ?? throw new ArgumentNullException(nameof(locationService));

        Latitude = "Ainda não carregada";
        Longitude = "Ainda não carregada";

        GetLocationCommand = new Command(async () => await GetLocationAsync(), () => !IsBusy);
    }

    public async Task GetLocationAsync()
    {
        if (IsBusy) return;

        try
        {
            IsBusy = true;

            var (lat, lon) = await _locationService.GetCurrentLocationAsync();

            Latitude = lat != 0 ? lat.ToString("F6") : "Não disponível";
            Longitude = lon != 0 ? lon.ToString("F6") : "Não disponível";
        }
        finally
        {
            IsBusy = false;
        }
    }
}
