package com.example.android

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.android.data.ThemeManager // <-- NOVO: Importa o gerenciador de tema
import com.example.android.databinding.ActivityMainBinding
import kotlinx.coroutines.flow.first // <-- NOVO: Para usar com DataStore
import kotlinx.coroutines.launch // <-- NOVO: Para iniciar coroutines

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var themeManager: ThemeManager // <-- NOVO: Declaração do gerenciador de tema

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // --- INÍCIO DA NOVA LÓGICA DE TEMA ---
        themeManager = ThemeManager(this)

        // Aplica o tema que foi salvo anteriormente, antes mesmo da tela ser desenhada
        lifecycleScope.launch {
            val savedTheme = themeManager.theme.first()
            themeManager.applyTheme(savedTheme)
        }
        // --- FIM DA NOVA LÓGICA DE TEMA ---

        // SEU CÓDIGO ANTIGO (INTOCADO):
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_activity_main)

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        // FIM DO SEU CÓDIGO ANTIGO
    }

    // --- MÉTODOS ADICIONADOS PARA GERENCIAR O MENU E A TROCA DE TEMA ---

    // Este método infla (cria) o menu com nosso ícone na barra de ação (Toolbar)
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    // Este método é chamado quando um item de menu é clicado
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            // Se o item clicado for o nosso 'action_change_theme'...
            R.id.action_change_theme -> {
                showThemeSelectorDialog() // ...mostra o diálogo de seleção de tema.
                true
            }
            // Para qualquer outro item de menu, usa o comportamento padrão.
            else -> super.onOptionsItemSelected(item)
        }
    }

    // Este método cria e exibe o diálogo de alerta para o usuário escolher o tema.
    private fun showThemeSelectorDialog() {
        val themes = arrayOf("Claro", "Escuro", "Padrão do Sistema")
        val themeModes = intArrayOf(
            ThemeManager.LIGHT_MODE,
            ThemeManager.DARK_MODE,
            ThemeManager.SYSTEM_DEFAULT
        )

        lifecycleScope.launch {
            // Busca o tema atual para deixar a opção correta já selecionada no diálogo.
            val currentTheme = themeManager.theme.first()
            val checkedItem = themeModes.indexOf(currentTheme)

            AlertDialog.Builder(this@MainActivity)
                .setTitle("Escolha um Tema")
                .setSingleChoiceItems(themes, checkedItem) { dialog, which ->
                    val selectedTheme = themeModes[which]
                    themeManager.applyTheme(selectedTheme) // Aplica o tema na hora
                    lifecycleScope.launch {
                        themeManager.saveTheme(selectedTheme) // Salva a escolha para o futuro
                    }
                    dialog.dismiss() // Fecha o diálogo
                }
                .show()
        }
    }
}
