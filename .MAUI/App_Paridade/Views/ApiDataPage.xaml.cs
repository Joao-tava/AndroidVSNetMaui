using App_Paridade.ViewModels;
using System.Linq;
using Microsoft.Maui.Controls;

namespace App_Paridade.Views;

public partial class ApiDataPage : ContentPage
{
    private readonly ApiDataViewModel _viewModel;

    public ApiDataPage(ApiDataViewModel viewModel)
    {
        InitializeComponent();
        BindingContext = _viewModel = viewModel;
    }

    protected override async void OnAppearing()
    {
        base.OnAppearing();

        // await diretamente o método assíncrono exposto pelo ViewModel
        if (!_viewModel.Estados.Any())
            await _viewModel.LoadEstadosAsync();
    }
}