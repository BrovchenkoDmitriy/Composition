package com.example.composition.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.composition.R
import com.example.composition.databinding.FragmentGameFinishedBinding

class GameFinishedFragment : Fragment() {

    private val gameResult by lazy {
        args.gameResult
    }

    private val args by navArgs<GameFinishedFragmentArgs>()

    private var _binding: FragmentGameFinishedBinding? = null
    private val binding: FragmentGameFinishedBinding
        get() {
            return _binding ?: throw RuntimeException("FragmentGameFinishedBinding = null")
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameFinishedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViews()
        binding.repeatGame.setOnClickListener {
            retryGame()
        }
    }

    private fun bindViews() {
        binding.gameResult = args.gameResult

//        val emoji = if (gameResult.winner) {
//            R.drawable.win_smile
//        } else {
//            R.drawable.lose_smile
//        }
//        with(binding) {
//            emojiResult.setImageResource(emoji)

//            tvRequiredScore.text = String.format(
//                getString(R.string.required_score),
//                gameResult.gameSettings.minCountOfRightAnswers
//            )

//            tvRequiredPercent.text = String.format(
//                getString(R.string.required_percent),
//                gameResult.gameSettings.minPercentOfRightAnswers
//            )

//            tvYourScore.text =
//                String.format(getString(R.string.your_score), gameResult.countOfRightAnswers)
//
//            tvYourPercent.text = String.format(
//                getString(R.string.your_percent),
//                getYourPercent()
//            )
//        }

    }

//    private fun getYourPercent(): Int {
//        return if (gameResult.countOfAnswers == 0) {
//            0
//        } else {
//            ((gameResult.countOfRightAnswers / gameResult.countOfAnswers.toDouble()) * 100).toInt()
//        }
//    }

    private fun retryGame() {
        findNavController().popBackStack()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}