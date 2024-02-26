package com.example.prueba_tecnica

import org.junit.Test

class QuestionTest4 {

    private fun <T> combinedLet(valor1: T?, valor2: T?, lambda: (T, T) -> Unit) {
        if (valor1 != null && valor2 != null) {
            lambda(valor1, valor2)
        }
    }


    @Test
    fun `test combined let`() {
        combinedLet(1, 2) { v1, v2 ->
            assert(v1 == 1 && v2 == 2)
        }
    }

    @Test
    fun `test combined let null`(){
        combinedLet(null,null){ v1, v2 ->

        }
    }

}