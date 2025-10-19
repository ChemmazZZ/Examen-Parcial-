package com.chemma.examemparcial.ui.game

import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.chemma.examemparcial.R
import com.chemma.examemparcial.databinding.FragmentGameBinding
import kotlin.random.Random

class GameFragment : Fragment() {

    private var _binding: FragmentGameBinding? = null
    private val binding get() = _binding!!

    private var score = 0
    private var timeLeft = 30
    private lateinit var timer: CountDownTimer

    private val colors = listOf(
        Pair("Rojo", R.color.red),
        Pair("Verde", R.color.green),
        Pair("Azul", R.color.blue),
        Pair("Amarillo", R.color.yellow)
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

        binding.buttonRed.setOnClickListener { checkAnswer(R.color.red) }
        binding.buttonGreen.setOnClickListener { checkAnswer(R.color.green) }
        binding.buttonBlue.setOnClickListener { checkAnswer(R.color.blue) }
        binding.buttonYellow.setOnClickListener { checkAnswer(R.color.yellow) }
    }

    private fun startGame() {
        score = 0
        timeLeft = 30
        updateScore()
        showRandomColor()
        startTimer()
    }

    private fun showRandomColor() {
        currentColor = colors[Random.nextInt(colors.size)]
        binding.textColorDisplay.text = currentColor.first
        binding.textColorDisplay.setBackgroundColor(requireContext().getColor(currentColor.second))
    }

    private fun checkAnswer(selectedColor: Int) {
        if (selectedColor == currentColor.second) {
            score++
            updateScore()
        }
        showRandomColor()
    }

    private fun updateScore() {
        binding.textScore.text = "Puntaje: $score"
    }

    private fun startTimer() {
        timer = object : CountDownTimer(30000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                timeLeft = (millisUntilFinished / 1000).toInt()
                binding.textTimer.text = "Tiempo: ${timeLeft}s"
            }

            override fun onFinish() {
                val bundle = Bundle().apply {
                    putInt("score", score)
                }
                findNavController().navigate(R.id.action_gameFragment_to_resultFragment, bundle)
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
