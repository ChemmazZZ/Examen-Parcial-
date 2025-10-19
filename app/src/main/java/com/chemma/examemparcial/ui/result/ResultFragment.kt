package com.chemma.examemparcial.ui.result

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.chemma.examemparcial.R
import com.chemma.examemparcial.databinding.FragmentResultBinding

/**
 * Fragment que muestra el resultado final del juego y ofrece la opción de volver a jugar.
 */
class ResultFragment : Fragment() {

    // View Binding para acceder a las vistas del layout de forma segura.
    // _binding puede ser nulo, pero 'binding' no, para evitar comprobaciones de nulidad.
    private var _binding: FragmentResultBinding? = null
    private val binding get() = _binding!!

    // Delegado de propiedad para obtener los argumentos de navegación de forma segura.
    private val args: ResultFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Infla el layout para este fragment usando View Binding.
        _binding = FragmentResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Muestra la puntuación obtenida, que se pasa como argumento de navegación.
        binding.scoreTextView.text = getString(R.string.your_score, args.score)

        // Configura el listener del botón para mostrar el diálogo de confirmación.
        binding.playAgainButton.setOnClickListener {
            showPlayAgainConfirmationDialog()
        }
    }

    /**
     * Muestra un diálogo de confirmación antes de reiniciar el juego.
     */
    private fun showPlayAgainConfirmationDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle(R.string.play_again_dialog_title)
            .setMessage(R.string.play_again_dialog_message)
            .setPositiveButton(R.string.play_again_dialog_positive) { _, _ ->
                // Si el usuario confirma, navega de vuelta a la pantalla de bienvenida.
                findNavController().navigate(R.id.action_resultFragment_to_welcomeFragment)
            }
            .setNegativeButton(R.string.play_again_dialog_negative, null)
            .show()
    }

    /**
     * Limpia la referencia al binding cuando la vista del fragment se destruye.
     * Esto es importante para evitar fugas de memoria.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
