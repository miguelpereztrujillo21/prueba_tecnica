package com.example.prueba_tecnica

import CustomResult
import ProductData
import org.junit.Test

class Question1Test {

    private fun combineOutputs(priorityResult: CustomResult, secondaryResult: CustomResult): CustomResult {
        return when {
            priorityResult is CustomResult.RSuccess && secondaryResult is CustomResult.RSuccess -> {
                if (priorityResult.product.id == secondaryResult.product.id) {
                    priorityResult.apply {
                        product = product.copy(
                            amount = product.amount + secondaryResult.product.amount,
                            providers = product.providers.toMutableList()
                                .apply { addAll(secondaryResult.product.providers) }
                        )
                    }
                } else {
                    priorityResult
                }
            }
            else -> {
                CustomResult.RCriticalError
            }
        }
    }


	private fun combineOutputsSimplified(priorityResult: CustomResult, secondaryResult: CustomResult): CustomResult {
        var result = priorityResult
         if (priorityResult is CustomResult.RSuccess && secondaryResult is CustomResult.RSuccess &&
            priorityResult.product.id == secondaryResult.product.id) {
           result = CustomResult.RSuccess(
               priorityResult.product.copy(
                   amount = priorityResult.product.amount + secondaryResult.product.amount,
                   providers = priorityResult.product.providers + secondaryResult.product.providers
               )
           )
        } else {
             CustomResult.RCriticalError
        }
        return result
    }


    @Test
    fun `test any critical error received`(){
        val priorityError = CustomResult.RCriticalError
        val result = combineOutputsSimplified(priorityError ,priorityError )
        assert(result is CustomResult.RCriticalError)
    }

    @Test
    fun `test successful results, return only priority result`(){
        val product1 = ProductData("id123", 10, listOf("Provider A"))
        val product2 = ProductData("id456", 5, listOf("Provider B"))
        val priorityResult = CustomResult.RSuccess(product1)
        val secondaryResult = CustomResult.RSuccess(product2)

        val testSubject = combineOutputsSimplified(priorityResult,secondaryResult)

        assert(testSubject == priorityResult)
    }


    @Test
    fun `test successful results, combine output`(){
        val product1 = ProductData("id123", 10, listOf("Provider A"))
        val product2 = ProductData("id123", 5, listOf("Provider B"))
        val priorityResult = CustomResult.RSuccess(product1)
        val secondaryResult = CustomResult.RSuccess(product2)

       val testResult = combineOutputsSimplified(priorityResult,secondaryResult)
            if (testResult is CustomResult.RSuccess) {
                assert(testResult.product.amount == 15)
                assert(listOf("Provider A","Provider B") == testResult.product.providers)
            }

    }

}