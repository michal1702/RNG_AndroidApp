package com.project.randomnumbergenerator.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.project.randomnumbergenerator.R
import com.project.randomnumbergenerator.databinding.FragmentEuroJackpotBinding


class EuroJackpotFragment : Fragment() {
    private lateinit var binding: FragmentEuroJackpotBinding
    private val drawButtonClickListener = View.OnClickListener { drawButtonClicked() }
    private lateinit var resultTextViewAnimation: Animation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        resultTextViewAnimation = AnimationUtils.loadAnimation(context, R.anim.result_animation)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentEuroJackpotBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.drawJackpotButton.setOnClickListener (drawButtonClickListener )
    }

    @SuppressLint("SetTextI18n")
    private fun drawButtonClicked(){
        binding.jackpotMainNumbers.text = ""
        binding.jackpotExtraNumbers.text = ""
        binding.jackpotMainNumbers.startAnimation(resultTextViewAnimation)
        binding.jackpotExtraNumbers.startAnimation(resultTextViewAnimation)
        for(i in 0 until 5) {
            binding.jackpotMainNumbers.text = binding.jackpotMainNumbers.text.toString() + (1..50).random().toString() + " "
        }
        for(i in 0 until 2) {
            binding.jackpotExtraNumbers.text = binding.jackpotExtraNumbers.text.toString() + (1..10).random().toString() + "  "
        }
    }
}