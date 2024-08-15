package com.me1rel3s.imc

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.me1rel3s.imc.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupListeners()
    }

    private fun setupListeners() {
        binding.btCalculate.setOnClickListener {
            calculateIMC()
        }
    }

    private fun calculateIMC() {
        val weightText = binding.etWeight.text.toString().trim()
        val heightText = binding.etHeight.text.toString().trim()

        val weight = weightText.toFloatOrNull()
        val height = heightText.toFloatOrNull()

        if (weight != null && height != null && height > 0f) {
            val imc = weight / (height * height)
            displayResult(imc)
        } else {
            binding.tvResult.text = ""
            Toast.makeText(this, "Por favor, insira valores válidos", Toast.LENGTH_SHORT).show()
        }
    }

    private fun displayResult(imc: Float) {
        val resultText = when {
            imc < 18.5 -> "Abaixo do peso"
            imc in 18.5..24.9 -> "Peso normal"
            imc in 25.0..29.9 -> "Sobrepeso"
            imc in 30.0..34.9 -> "Obesidade grau 1"
            else -> "Obesidade grau 3"
        }

        binding.tvResult.text = String.format("IMC: %.2f\n%s", imc, resultText)

        // Manipulando a animação Lottie com base no valor do IMC
        val progress = when {
            imc < 18.5 -> 0f        // Abaixo do peso
            imc in 18.5..24.9 -> 0.17f  // Peso normal
            imc in 25.0..29.9 -> 0.5f   // Sobrepeso
            imc in 30.0..34.9 -> 0.33f  // Obesidade grau 1
            else -> 1f              // Obesidade grau 3
        }

        binding.lottieAnimationView.setMinAndMaxProgress(progress, progress)
        binding.lottieAnimationView.playAnimation()
    }

}