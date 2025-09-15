// AppShell.xaml.cs
using Microsoft.Extensions.DependencyInjection; // para GetService<>
using App_Paridade.Services;
using App_Paridade.Views; // 👈 importante para reconhecer as páginas

namespace App_Paridade;

public partial class AppShell : Shell
{
    public AppShell()
    {
        InitializeComponent();

        // 🔑 Registrar rotas de navegação (para Shell.Current.GoToAsync)
        Routing.RegisterRoute(nameof(ItemDetailPage), typeof(ItemDetailPage));

        // se futuramente quiser rotas extras, já fica padronizado:
        // Routing.RegisterRoute(nameof(SettingsPage), typeof(SettingsPage));
    }

    private void OnThemeToggleClicked(object sender, EventArgs e)
    {
        // Resolve o serviço *na hora do clique* (após o app estar inicializado)
        var themeService = Application.Current?.Handler?.MauiContext?.Services.GetService<IThemeService>();

        if (themeService == null)
        {
            // opcional: log ou mensagem
            System.Diagnostics.Debug.WriteLine("ThemeService não encontrado.");
            return;
        }

        var current = themeService.GetCurrentTheme();
        var newTheme = current == AppTheme.Light ? AppTheme.Dark : AppTheme.Light;

        themeService.SetTheme(newTheme);
    }
}
