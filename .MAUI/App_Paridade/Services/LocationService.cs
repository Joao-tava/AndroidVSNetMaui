using Microsoft.Maui.ApplicationModel;
using Microsoft.Maui.Devices.Sensors;

namespace App_Paridade.Services;

public class LocationService : ILocationService
{
    public async Task<(double Latitude, double Longitude)> GetCurrentLocationAsync()
    {
        try
        {
            // 🚨 Verifica e pede permissão antes
            var status = await Permissions.CheckStatusAsync<Permissions.LocationWhenInUse>();
            if (status != PermissionStatus.Granted)
            {
                status = await Permissions.RequestAsync<Permissions.LocationWhenInUse>();
                if (status != PermissionStatus.Granted)
                {
                    Console.WriteLine("Permissão de localização negada.");
                    return (0, 0);
                }
            }

            var request = new GeolocationRequest(GeolocationAccuracy.Medium, TimeSpan.FromSeconds(10));
            var location = await Geolocation.Default.GetLocationAsync(request);

            if (location != null)
                return (location.Latitude, location.Longitude);

            return (0, 0);
        }
        catch (Exception ex)
        {
            Console.WriteLine($"Erro ao obter localização: {ex.Message}");
            return (0, 0);
        }
    }
}
