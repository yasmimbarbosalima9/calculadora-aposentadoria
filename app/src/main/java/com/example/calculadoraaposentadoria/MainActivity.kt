package com.example.calculadoraaposentadoria

import android.content.Context
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.calculadoraaposentadoria.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    // lateinit var: o binding é criado depois, no onCreate
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { view, insets ->
            val barras = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(barras.left, barras.top, barras.right, barras.bottom)
            insets
        }

        configurarSpinner()

        binding.btnCalcular.setOnClickListener { onCalcularClick() }
        binding.btnLimpar.setOnClickListener { onLimparClick() }
    }

    private fun configurarSpinner() {
        val generos = ArrayAdapter.createFromResource(this, R.array.generos, R.layout.item_spinner)
        generos.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerGenero.adapter = generos
    }

    private fun onCalcularClick() {
        esconderTeclado()

        // toIntOrNull evita crash caso o texto não seja um número válido
        val idade = binding.edtIdade.text?.toString()?.trim()?.toIntOrNull()
        if (idade == null) {
            binding.layoutIdade.error = getString(R.string.erro_idade)
            return
        }
        binding.layoutIdade.error = null

        val genero = when (binding.spinnerGenero.selectedItemPosition) {
            1 -> Genero.MASCULINO
            2 -> Genero.FEMININO
            else -> null
        }
        if (genero == null) {
            binding.txtResultado.setText(R.string.erro_genero)
            return
        }

        val anosRestantes = Aposentadoria.anosRestantes(idade, genero)
        binding.txtResultado.text = when {
            anosRestantes < 0 -> getString(R.string.ja_aposentado)
            anosRestantes == 0 -> getString(R.string.pode_aposentar)
            else -> resources.getQuantityString(R.plurals.faltam_anos, anosRestantes, anosRestantes)
        }
    }

    private fun onLimparClick() {
        binding.edtIdade.setText("")
        binding.layoutIdade.error = null
        binding.spinnerGenero.setSelection(0)
        binding.txtResultado.setText(R.string.resultado_inicial)
    }

    private fun esconderTeclado() {
        val teclado = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        teclado?.hideSoftInputFromWindow(binding.root.windowToken, 0)
    }
}
