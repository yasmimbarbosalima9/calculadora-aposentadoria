package com.example.calculadoraaposentadoria

enum class Genero(val idadeMinima: Int) {
    MASCULINO(65),
    FEMININO(62)
}

object Aposentadoria {

    // Resultado negativo significa que a pessoa já passou da idade mínima
    fun anosRestantes(idade: Int, genero: Genero): Int = genero.idadeMinima - idade
}
