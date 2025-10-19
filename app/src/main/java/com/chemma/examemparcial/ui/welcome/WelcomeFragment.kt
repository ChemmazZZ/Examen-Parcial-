package com.chemma.examemparcial.ui.welcome

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.chemma.examemparcial.R
import com.chemma.examemparcial.databinding.FragmentWelcomeBinding

/**
 * Fragment que representa la pantalla de bienvenida de la aplicación.
 * Contiene un botón para iniciar el juego.
 */
class WelcomeFragment : Fragment() {

    // View Binding para acceder a las vistas del layout de forma segura.
    private var _binding: FragmentWelcomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Infla el layout para este fragment usando View Binding.
        _binding = FragmentWelcomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Configura el listener del botón para navegar al fragmento del juego.
        binding.startGameButton.setOnClickListener {
            findNavController().navigate(R.id.action_welcomeFragment_to_gameFragment)
        }
    }

    /**
     * Limpia la referencia al binding cuando la vista del fragment se destruye.
     * Esto es crucial para evitar fugas de memoria.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
