package com.example.composition.presentation

import android.content.Context
import android.content.res.ColorStateList
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat
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
fun getColorByState(context: Context, goodState: Boolean): Int {
    val colorResId = if (goodState) {
        android.R.color.holo_green_light
    } else {
        android.R.color.holo_red_light
    }
    return ContextCompat.getColor(context, colorResId)
}
@BindingAdapter("enoughCount")
fun bindEnoughCount(textView: TextView, enoughCount: Boolean){
    textView.setTextColor(getColorByState(textView.context, enoughCount))
}
@BindingAdapter("enoughPercent")
fun bindEnoughCount(progressBar: ProgressBar, enoughPercent: Boolean){
    progressBar.progressTintList =
        ColorStateList.valueOf(getColorByState(progressBar.context, enoughPercent))
}

@BindingAdapter("numberAsText")
    fun bindNumberAsText(textView: TextView, number:Int){
        textView.text = number.toString()
}

@BindingAdapter("onClickListener")
fun bindOnClickListener(textView: TextView, clickListener: GameFragment.OnOptionClickListener){
    textView.setOnClickListener {
        clickListener.onOptionClick(textView.text.toString().toInt())
    }
}
