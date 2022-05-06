package com.example.composition.presentation

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.composition.R
import com.example.composition.databinding.FragmentGameBinding
import com.example.composition.domain.entity.GameResult
import com.example.composition.domain.entity.Level

class GameFragment : Fragment() {

    private lateinit var level: Level

    private val viewModelFactory by lazy {
        GameViewModelFactory(requireActivity().application, level)
    }
    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[GameFragmentViewModel::class.java]
    }

    private val tvOptions by lazy {
        mutableListOf<TextView>().apply {
            add(binding.tvOptions1)
            add(binding.tvOptions2)
            add(binding.tvOptions3)
            add(binding.tvOptions4)
            add(binding.tvOptions5)
            add(binding.tvOptions6)
        }
    }

    private var _binding: FragmentGameBinding? = null
    private val binding: FragmentGameBinding
        get() {
            return _binding ?: throw RuntimeException("FragmentGameBinding = null")
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseArgs()
    }

    private fun parseArgs() {
        requireArguments().getParcelable<Level>(KEY_LEVEL)?.let {
            level = it
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
        setClickListenersToOptions()
        //viewModel.startGame(level)
    }

    fun setClickListenersToOptions(){
        for(tvOption in tvOptions){
            tvOption.setOnClickListener { viewModel.chooseAnswer(tvOption.text.toString().toInt()) }
        }
    }

    fun observeViewModel() {
        viewModel.question.observe(viewLifecycleOwner) {
            binding.tvSum.text = it.sum.toString()
            binding.tvLeftNumber.text = it.visibleNumber.toString()
            for (i in 0 until tvOptions.size){
                tvOptions[i].text = it.options[i].toString()
            }
        }
        viewModel.percentOfRightAnswer.observe(viewLifecycleOwner){
            binding.progressBar.setProgress(it, true)//API 24 and more
        }
        viewModel.enoughCountOfRightAnswers.observe(viewLifecycleOwner){
            binding.tvAnswerProgress.setTextColor(getColorByState(it))
        }
        viewModel.enoughPercentOFRightAnswers.observe(viewLifecycleOwner){
            binding.progressBar.progressTintList = ColorStateList.valueOf(getColorByState(it))
        }
        viewModel.formattedTime.observe(viewLifecycleOwner){
            binding.tvTimer.text = it
        }
        viewModel.minPercent.observe(viewLifecycleOwner){
            binding.progressBar.secondaryProgress = it
        }
        viewModel.gameResult.observe(viewLifecycleOwner){
            launchGameResultFragment(it)
        }
        viewModel.progressAnswers.observe(viewLifecycleOwner){
            binding.tvAnswerProgress.text = it
        }
    }

    fun getColorByState (goodState: Boolean):Int{
        val colorResId = if (goodState){
            android.R.color.holo_green_light
        } else {
            android.R.color.holo_red_light
        }
        return ContextCompat.getColor(requireContext(),colorResId)
    }

    private fun launchGameResultFragment(gameResult: GameResult) {
        requireActivity().supportFragmentManager.beginTransaction().replace(
            R.id.main_container,
            GameFinishedFragment.newInstance(gameResult)
        ).addToBackStack(null).commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val KEY_LEVEL = "keyLevel"
        const val NAME_FRAGMENT = "nameFragment"

        fun newInstance(level: Level): GameFragment {
            return GameFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(KEY_LEVEL, level)
                }
            }
        }
    }


}