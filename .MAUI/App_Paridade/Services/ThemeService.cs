namespace App_Paridade.Services;

public interface IThemeService
{
    void SetTheme(AppTheme theme);
    AppTheme GetCurrentTheme();
}

public class ThemeService : IThemeService
{
    private AppTheme _currentTheme = AppTheme.Light;

    public void SetTheme(AppTheme theme)
    {
        Application.Current!.UserAppTheme = theme;
        _currentTheme = theme;
    }

    public AppTheme GetCurrentTheme() => _currentTheme;
}
