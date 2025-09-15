using App_Paridade.Services;
using Microsoft.Extensions.Logging;
using App_Paridade.ViewModels;
using App_Paridade.Views;


namespace App_Paridade
{
    public static class MauiProgram
    {
        public static MauiApp CreateMauiApp()
        {
            var builder = MauiApp.CreateBuilder();
            builder
                .UseMauiApp<App>()
                .ConfigureFonts(fonts =>
                {
                    fonts.AddFont("OpenSans-Regular.ttf", "OpenSansRegular");
                    fonts.AddFont("OpenSans-Semibold.ttf", "OpenSansSemibold");
                });

#if DEBUG
    		builder.Logging.AddDebug();
#endif
            builder.Services.AddSingleton<IDataService, LocalDataService>();
            builder.Services.AddSingleton<ItemsViewModel>();
            builder.Services.AddSingleton<ItemsPage>();
            builder.Services.AddTransient<ItemDetailViewModel>();
            builder.Services.AddTransient<ItemDetailPage>();

            builder.Services.AddSingleton<IApiService>(sp =>
            {
                var http = new HttpClient
                {
                    BaseAddress = new Uri("https://servicodados.ibge.gov.br/api/v1/localidades/")
                };
                return new ApiService(http);
            });

            builder.Services.AddSingleton<ApiDataViewModel>();
            builder.Services.AddSingleton<ApiDataPage>();

            builder.Services.AddSingleton<ILocationService, LocationService>();
            builder.Services.AddSingleton<LocationViewModel>();
            builder.Services.AddSingleton<LocationPage>();
            builder.Services.AddSingleton<IThemeService, ThemeService>();


            return builder.Build();
        }
    }
}
