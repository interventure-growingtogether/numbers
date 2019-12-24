package com.interventure.hackathon.numbers

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorInt


class NumberDetailsFragment(val number: CharSequence, @ColorInt color: Int) : Fragment() {

    companion object {
        fun newInstance(number: CharSequence, @ColorInt color: Int) = NumberDetailsFragment(number, color)
    }

    private lateinit var viewModel: NumberDetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.number_details_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(NumberDetailsViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
