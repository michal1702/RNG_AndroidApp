package com.project.randomnumbergenerator.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.project.randomnumbergenerator.R
import com.project.randomnumbergenerator.databinding.FragmentSixNumberLotteryBinding

class SixNumberLotteryFragment : Fragment() {

    private lateinit var binding: FragmentSixNumberLotteryBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSixNumberLotteryBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.drawSixNumberLottery.setOnClickListener {
            binding.sixNumberLotteryResult.text = ""
            for (i in 0..5){
                binding.sixNumberLotteryResult.text = binding.sixNumberLotteryResult.text.toString() +
                        (1..49).random().toString() + " "
            }
        }
    }
}