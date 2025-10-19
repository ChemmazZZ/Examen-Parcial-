package com.chemma.examemparcial.ui.game

import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.chemma.examemparcial.R
import com.chemma.examemparcial.databinding.FragmentGameBinding

/**
 * Fragment que contiene la lógica principal del juego.
 * El jugador debe seleccionar el color que coincide con el nombre mostrado.
 */
class GameFragment : Fragment() {

    // View Binding para acceder a las vistas de forma segura.
    private var _binding: FragmentGameBinding? = null
    private val binding get() = _binding!!

    // Variables para el estado del juego.
    private var score = 0
    private lateinit var timer: CountDownTimer

    // Lista de colores con su nombre y recurso de color asociado.
    private val colors = listOf(
        Pair("Rojo", R.color.red),
        Pair("Verde", R.color.green),
        Pair("Azul", R.color.blue),
        Pair("Amarillo", R.color.yellow)
    )

    // Almacena el color correcto para la ronda actual.
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
        setupRecyclerView()
        startGame()
    }

    /**
     * Configura el RecyclerView para mostrar las opciones de colores.
     */
    private fun setupRecyclerView() {
        val colorOptions = colors.map { it.second }
        val adapter = ColorOptionAdapter(colorOptions) { selectedColor ->
            checkAnswer(selectedColor)
        }
        binding.recyclerViewColors.adapter = adapter
        binding.recyclerViewColors.layoutManager = GridLayoutManager(context, 2)
    }

    /**
     * Inicia una nueva partida, reiniciando la puntuación y el temporizador.
     */
    private fun startGame() {
        score = 0
        updateScore()
        showRandomColor()
        startTimer()
    }

    /**
     * Muestra un nuevo color y actualiza la UI. El color de fondo del texto
     * será diferente al color correcto para aumentar la dificultad.
     */
    private fun showRandomColor() {
        currentColor = colors.random()
        val displayColor = colors.filter { it != currentColor }.random()

        binding.textColorDisplay.text = currentColor.first
        binding.textColorDisplay.setBackgroundColor(requireContext().getColor(displayColor.second))
    }

    /**
     * Comprueba si la respuesta del jugador es correcta.
     */
    private fun checkAnswer(selectedColor: Int) {
        if (selectedColor == currentColor.second) {
            score++
            updateScore()
        }
        showRandomColor()
    }

    /**
     * Actualiza el texto de la puntuación en la UI.
     */
    private fun updateScore() {
        binding.textScore.text = getString(R.string.score_text, score)
    }

    /**
     * Inicia el temporizador de 30 segundos. Cuando el tiempo termina,
     * navega a la pantalla de resultados.
     */
    private fun startTimer() {
        timer = object : CountDownTimer(30000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val timeLeft = (millisUntilFinished / 1000).toInt()
                binding.textTimer.text = getString(R.string.time_text, timeLeft)
            }

            override fun onFinish() {
                val action = GameFragmentDirections.actionGameFragmentToResultFragment(score)
                findNavController().navigate(action)
            }
        }
        timer.start()
    }

    /**
     * Se llama cuando la vista del fragment se destruye.
     * Es importante cancelar el temporizador para evitar fugas de memoria y anular el binding.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        timer.cancel()
        _binding = null
    }
}
