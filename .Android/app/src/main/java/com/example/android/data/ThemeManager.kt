package com.example.android.data

import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// Cria uma instância única do DataStore para o contexto do aplicativo.
// O nome "settings" é o nome do arquivo onde as preferências serão salvas.
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

/**
 * Classe responsável por gerenciar a lógica de troca e persistência do tema do aplicativo.
 */
class ThemeManager(private val context: Context) {

    companion object {
        // Chave para salvar a preferência de tema no DataStore.
        private val THEME_KEY = intPreferencesKey("theme_preference")

        // Constantes que representam os modos de tema disponíveis.
        // Usamos os valores da própria biblioteca do Android para consistência.
        const val LIGHT_MODE = AppCompatDelegate.MODE_NIGHT_NO
        const val DARK_MODE = AppCompatDelegate.MODE_NIGHT_YES
        const val SYSTEM_DEFAULT = AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
    }

    /**
     * Salva a preferência de tema do usuário de forma assíncrona no DataStore.
     * @param themeMode O modo de tema a ser salvo (ex: LIGHT_MODE, DARK_MODE).
     */
    suspend fun saveTheme(themeMode: Int) {
        context.dataStore.edit { settings ->
            settings[THEME_KEY] = themeMode
        }
    }

    /**
     * Um Flow que emite a preferência de tema atual sempre que ela for alterada.
     * Se nenhuma preferência for encontrada, emite o valor SYSTEM_DEFAULT como padrão.
     */
    val theme: Flow<Int> = context.dataStore.data.map { preferences ->
        preferences[THEME_KEY] ?: SYSTEM_DEFAULT
    }

    /**
     * Aplica o modo de tema escolhido a todo o aplicativo.
     * Esta função deve ser chamada para que a mudança visual ocorra.
     * @param themeMode O modo de tema a ser aplicado.
     */
    fun applyTheme(themeMode: Int) {
        AppCompatDelegate.setDefaultNightMode(themeMode)
    }
}