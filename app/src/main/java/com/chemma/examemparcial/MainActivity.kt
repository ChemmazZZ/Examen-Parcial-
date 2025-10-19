package com.chemma.examemparcial

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import com.chemma.examemparcial.databinding.ActivityMainBinding

/**
 * La actividad principal y única de la aplicación.
 * Actúa como contenedor para todos los fragmentos y gestiona la barra de navegación (Toolbar).
 */
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Establece la Toolbar personalizada como la ActionBar de la actividad.
        setSupportActionBar(binding.toolbar)

        // Obtiene el NavController del NavHostFragment.
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        // Configura la ActionBar para que funcione con el NavController.
        // Esto mostrará el título del fragmento actual y el botón de "atrás".
        setupActionBarWithNavController(navController)
    }

    /**
     * Gestiona el evento de clic en el botón "atrás" (Up) de la ActionBar.
     * Delega la navegación al NavController.
     */
    override fun onSupportNavigateUp(): Boolean {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        return navHostFragment.navController.navigateUp() || super.onSupportNavigateUp()
    }
}
