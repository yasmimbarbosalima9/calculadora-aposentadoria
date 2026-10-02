package com.example.calculadoraaposentadoria

import org.junit.Assert.assertEquals
import org.junit.Test

class AposentadoriaTest {

    @Test
    fun homemDe30AnosFaltam35() {
        assertEquals(35, Aposentadoria.anosRestantes(30, Genero.MASCULINO))
    }

    @Test
    fun mulherDe25AnosFaltam37() {
        assertEquals(37, Aposentadoria.anosRestantes(25, Genero.FEMININO))
    }

    @Test
    fun idadeExataDaAposentadoria() {
        assertEquals(0, Aposentadoria.anosRestantes(65, Genero.MASCULINO))
        assertEquals(0, Aposentadoria.anosRestantes(62, Genero.FEMININO))
    }

    @Test
    fun acimaDaIdadeMinimaDaNegativo() {
        assertEquals(-15, Aposentadoria.anosRestantes(80, Genero.MASCULINO))
        assertEquals(-937, Aposentadoria.anosRestantes(999, Genero.FEMININO))
    }
}
