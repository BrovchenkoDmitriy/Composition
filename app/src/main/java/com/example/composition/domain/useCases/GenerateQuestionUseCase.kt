package com.example.composition.domain.useCases

import com.example.composition.data.GameRepositoryImpl
import com.example.composition.domain.entity.Question

class GenerateQuestionUseCase(
    private val repository: GameRepositoryImpl
        ){
    operator fun invoke(maxSumValue:Int):Question{
        return repository.generateQuestion(maxSumValue, COUNT_OF_OPTIONS)
    }

    private companion object{
        const val COUNT_OF_OPTIONS = 6
    }

}