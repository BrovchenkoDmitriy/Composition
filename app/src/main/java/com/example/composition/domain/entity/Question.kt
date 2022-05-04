package com.example.composition.domain.entity

data class Question (
    val sum:Int,
    val visibleNumber:Int,
    val options: List<Int>
        ){
    val rightAnswer: Int
    get() {
        return sum-visibleNumber
    }
}