package com.example.nativo

import android.os.Bundle
import android.os.SystemClock // Importar SystemClock
import android.util.Log // Importar Log
// import androidx.activity.enableEdgeToEdge // Comentado para teste
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar // Importar Toolbar
// import androidx.core.view.ViewCompat // Comentado para teste
// import androidx.core.view.WindowInsetsCompat // Comentado para teste
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.navigateUp

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // enableEdgeToEdge() // Comentado para teste
        setContentView(R.layout.activity_main)

        // Configurar a Toolbar como a ActionBar da Activity
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        // Ajuste para WindowInsets (Comentado para teste)
        /*
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        */

        // Obter o NavHostFragment e o NavController
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        // Configurar a AppBarConfiguration. O navGraph é passado aqui.
        appBarConfiguration = AppBarConfiguration(navController.graph)
        
        // Configurar a ActionBar (agora a Toolbar) para trabalhar com o NavController
        setupActionBarWithNavController(navController, appBarConfiguration)

        // Log para tempo de Cold Start
        val appFullyReadyTime = SystemClock.uptimeMillis()
        val coldStartTime = appFullyReadyTime - NativoApplication.appStartTime
        Log.d("AppStartup", "MainActivity.onCreate() - App Fully Ready Time: $appFullyReadyTime ms")
        Log.d("AppStartup", "MainActivity.onCreate() - Cold Start Duration: $coldStartTime ms")
    }

    // Lidar com o evento de clique no botão "Up" (voltar) na ActionBar
    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}