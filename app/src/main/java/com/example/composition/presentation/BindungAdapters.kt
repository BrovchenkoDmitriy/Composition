package com.example.composition.presentation

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.composition.R
import com.example.composition.domain.entity.GameResult

@BindingAdapter("requiredAnswers")
fun bindRequiredAnswers(textView:TextView, count: Int){
    textView.text = String.format(
        textView.context.getString(R.string.required_score),
        count
    )
}

@BindingAdapter("requiredPercent")
fun bindRequiredPercent(textView: TextView, percent:Int){
    textView.text = String.format(
    textView.context.getString(R.string.required_percent),
               percent
    )
}

@BindingAdapter("yourAnswers")
fun bindYourAnswers(textView: TextView, yourAnswers:Int){
    textView.text =
                String.format(textView.context.getString(R.string.your_score), yourAnswers)
}

@BindingAdapter("yourPercent")
fun bindYourPercent(textView: TextView, gameResult: GameResult){
    textView.text =
        String.format(textView.context.getString(R.string.your_percent), getYourPercent(gameResult))
}

private fun getYourPercent(gameResult: GameResult): Int {
    return if (gameResult.countOfAnswers == 0) {
        0
    } else {
        ((gameResult.countOfRightAnswers / gameResult.countOfAnswers.toDouble()) * 100).toInt()
    }
}

@BindingAdapter("isWinner")
fun bindWinner(imageView: ImageView, winner: Boolean){
    val emoji = if (winner) {
        R.drawable.win_smile
    } else {
        R.drawable.lose_smile
    }
        imageView.setImageResource(emoji)
}