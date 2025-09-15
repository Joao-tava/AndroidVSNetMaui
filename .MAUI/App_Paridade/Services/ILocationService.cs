namespace App_Paridade.Services;

public interface ILocationService
{
    Task<(double Latitude, double Longitude)> GetCurrentLocationAsync();
}
