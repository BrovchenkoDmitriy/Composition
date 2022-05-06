package com.example.composition.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager.POP_BACK_STACK_INCLUSIVE
import androidx.lifecycle.ViewModelProvider
import com.example.composition.R
import com.example.composition.databinding.FragmentGameFinishedBinding
import com.example.composition.domain.entity.GameResult

class GameFinishedFragment : Fragment() {
    private lateinit var gameResult: GameResult

    private var _binding: FragmentGameFinishedBinding? = null
    private val binding: FragmentGameFinishedBinding
        get() {
            return _binding ?: throw RuntimeException("FragmentGameFinishedBinding = null")
        }

    private fun parseArgs() {
        requireArguments().getParcelable<GameResult>(GAME_RESULT)?.let {
            gameResult = it
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseArgs()
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
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    retryGame()
                }
            })
    }

    private fun bindViews() {

        val emoji = if (gameResult.winner) {
            R.drawable.win_smile
        } else {
            R.drawable.lose_smile
        }
        with(binding) {
            emojiResult.setImageResource(emoji)

            tvRequiredScore.text = String.format(
                getString(R.string.required_score),
                gameResult.gameSettings.minCountOfRightAnswers
            )

            tvRequiredPercent.text = String.format(
                getString(R.string.required_percent),
                gameResult.gameSettings.minPercentOfRightAnswers
            )

            tvYourScore.text =
                String.format(getString(R.string.your_score), gameResult.countOfRightAnswers)

            tvYourPercent.text = String.format(
                getString(R.string.your_percent),
                getYourPercent()
            )
        }

    }

    fun getYourPercent():Int{
        return if (gameResult.countOfAnswers == 0) {
            0
        } else {
            ((gameResult.countOfRightAnswers / gameResult.countOfAnswers.toDouble()) * 100).toInt()
        }
    }

    private fun retryGame() {
        requireActivity().supportFragmentManager.popBackStack(
            GameFragment.NAME_FRAGMENT,
            POP_BACK_STACK_INCLUSIVE
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {

        private const val GAME_RESULT = "gameResult"
        fun newInstance(gameResult: GameResult): GameFinishedFragment {
            return GameFinishedFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(GAME_RESULT, gameResult)
                }
            }
        }
    }


}