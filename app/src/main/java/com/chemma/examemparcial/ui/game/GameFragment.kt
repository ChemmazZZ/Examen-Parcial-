package com.chemma.examemparcial.ui.game

import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.chemma.examemparcial.R
import com.chemma.examemparcial.databinding.FragmentGameBinding

/**
 * Fragment que contiene la lógica principal del juego.
 * El jugador debe seleccionar el color que coincide con el nombre mostrado.
 * La dificultad aumenta a medida que el jugador anota puntos.
 */
class GameFragment : Fragment() {

    // ViewModel compartido para gestionar el estado del juego y los puntajes.
    private val viewModel: GameViewModel by activityViewModels()

    private var _binding: FragmentGameBinding? = null
    private val binding get() = _binding!!

    private var score = 0
    private lateinit var timer: CountDownTimer

    // Lista completa de colores disponibles en el juego.
    private val allColors = listOf(
        Pair("Rojo", R.color.red),
        Pair("Verde", R.color.green),
        Pair("Azul", R.color.blue),
        Pair("Amarillo", R.color.yellow),
        Pair("Naranja", R.color.orange),
        Pair("Violeta", R.color.purple),
        Pair("Rosa", R.color.pink),
        Pair("Marrón", R.color.brown)
    )

    private lateinit var currentColor: Pair<String, Int>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        startGame()
    }

    private fun startGame() {
        score = 0
        updateScore()
        showNextRound()
        startTimer()
    }

    /**
     * Configura y muestra la siguiente ronda del juego, ajustando la dificultad
     * según la puntuación actual.
     */
    private fun showNextRound() {
        val currentLevelColors = getColorsForCurrentLevel()

        // Seleccionar el color correcto y el color para el texto (efecto Stroop)
        currentColor = currentLevelColors.random()
        val displayColor = currentLevelColors.filter { it != currentColor }.random()

        binding.textColorDisplay.text = currentColor.first
        binding.textColorDisplay.setBackgroundColor(requireContext().getColor(displayColor.second))

        // Actualizar el RecyclerView con las opciones de color del nivel actual.
        setupRecyclerView(currentLevelColors)
    }

    /**
     * Devuelve la lista de colores correspondiente al nivel de dificultad actual.
     */
    private fun getColorsForCurrentLevel(): List<Pair<String, Int>> {
        return when (score) {
            in 0..5 -> allColors.take(4) // Nivel 1: 4 colores
            in 6..10 -> allColors.take(6) // Nivel 2: 6 colores
            else -> allColors // Nivel 3: 8 colores
        }
    }

    /**
     * Configura el RecyclerView con la lista de colores proporcionada.
     */
    private fun setupRecyclerView(colorOptions: List<Pair<String, Int>>) {
        val adapter = ColorOptionAdapter(colorOptions.map { it.second }) { selectedColor ->
            checkAnswer(selectedColor)
        }
        binding.recyclerViewColors.adapter = adapter
        binding.recyclerViewColors.layoutManager = GridLayoutManager(context, 2)
    }

    private fun checkAnswer(selectedColor: Int) {
        if (selectedColor == currentColor.second) {
            score++
            updateScore()
        }
        showNextRound()
    }

    private fun updateScore() {
        binding.textScore.text = getString(R.string.score_text, score)
    }

    private fun startTimer() {
        timer = object : CountDownTimer(30000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val timeLeft = (millisUntilFinished / 1000).toInt()
                binding.textTimer.text = getString(R.string.time_text, timeLeft)
            }

            override fun onFinish() {
                // Notificar al ViewModel que el juego ha terminado con el puntaje final.
                viewModel.onGameFinished(requireContext(), score)

                val action = GameFragmentDirections.actionGameFragmentToResultFragment(score)
                findNavController().navigate(action)
            }
        }
        timer.start()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        timer.cancel()
        _binding = null
    }
}
