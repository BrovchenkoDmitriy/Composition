package com.example.composition.domain.entity

data class GameResult(
    val winner: Boolean,
    val countOfRightAnswers: Int,
    val countOfAnswers: Int,
    val gameSettings: GameSettings
) {
}