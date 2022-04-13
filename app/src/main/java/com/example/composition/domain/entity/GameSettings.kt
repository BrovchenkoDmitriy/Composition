package com.example.composition.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class GameSettings(
    val maxSumValue: Int,
    val maxCountOfRightAnswers: Int,
    val maxPercentOfRightAnswers: Int,
    val gameTinInSeconds: Int
): Parcelable