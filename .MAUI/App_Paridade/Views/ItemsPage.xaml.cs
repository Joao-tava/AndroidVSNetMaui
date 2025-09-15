using App_Paridade.ViewModels;

namespace App_Paridade.Views;

public partial class ItemsPage : ContentPage
{
    private readonly ItemsViewModel _viewModel;

    public ItemsPage(ItemsViewModel viewModel)
    {
        InitializeComponent();
        BindingContext = _viewModel = viewModel;
    }

    protected override async void OnAppearing()
    {
        base.OnAppearing();

        if (_viewModel.Items.Count == 0)
        {
            await _viewModel.LoadItemsAsync();
        }
    }
    private async void OnSelectionChanged(object sender, SelectionChangedEventArgs e)
    {
        if (e.CurrentSelection.FirstOrDefault() is Models.Item selectedItem)
        {
            // Navegação via Shell: passamos o objeto como parâmetro
            await Shell.Current.GoToAsync(nameof(ItemDetailPage), true,
                new Dictionary<string, object>
                {
                { "Item", selectedItem }
                });

            // Limpa a seleção (pra não ficar marcado quando voltar)
            ((CollectionView)sender).SelectedItem = null;
        }
    }
    
}
