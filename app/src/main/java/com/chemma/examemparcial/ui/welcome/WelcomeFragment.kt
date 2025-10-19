package com.chemma.examenparcial.ui.welcome

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.findNavController
import com.chemma.examenparcial.R
import com.chemma.examenparcial.databinding.FragmentWelcomeBinding

class WelcomeFragment : Fragment() {

    private var _binding: FragmentWelcomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWelcomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Mostrar cuando abbrimos el fragmeto
        showRulesDialog()

        // Boton para itniciar juego
        binding.ButtonStartGame.setOnClickListener {
            findNavController().navigate(R.id.action_WelcomeFragment_to_GameFragment)
        }
    }

    private fun showRulesDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle("Reglas del Juego")
            .setMessage(
                "Presiona el botón del color que coincida con el color mostrado en pantalla. " +
                        "Tienes 30 segundos para conseguir la mayor cantidad de aciertos posible."
            )
            .setPositiveButton("Entendido") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
